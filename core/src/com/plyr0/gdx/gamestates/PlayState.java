package com.plyr0.gdx.gamestates;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.plyr0.gdx.entities.Asteroid;
import com.plyr0.gdx.entities.Bullet;
import com.plyr0.gdx.entities.Particle;
import com.plyr0.gdx.entities.Player;
import com.plyr0.gdx.main.Game;
import com.plyr0.gdx.managers.GameKeys;
import com.plyr0.gdx.managers.GameStateManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayState extends GameState {

    private List<Bullet> bullets = new ArrayList<Bullet>();
    private Player player = new Player(bullets);
    private List<Asteroid> asteroids = new ArrayList<Asteroid>();
    private List<Particle> particles = new ArrayList<Particle>();
    private ShapeRenderer renderer = new ShapeRenderer();

    private int level = 1;
    private int numAsteroidsTotal;
    private int numAsteroidsLeft;

    public PlayState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        spawnAsteroids();
    }

    private void spawnAsteroids() {
        asteroids.clear();
        int numToSpawn = 3 + level;
        numAsteroidsLeft = numAsteroidsTotal = numToSpawn * 7; //large = 2 * medium; medium = 2 * small
        for (int i = 0; i < numToSpawn; i++) {
            float x = MathUtils.random(Game.getWidth());
            float y = MathUtils.random(Game.getHeight());
            float dx = x - player.getX();
            float dy = y - player.getY();
            float dist = (float) Math.sqrt(dx * dx + dy * dy);
            if (dist < 100) {
                --i;
            } else {
                asteroids.add(new Asteroid(x, y, Asteroid.LARGE));
            }
        }
    }

    private void createParticles(float x, float y) {
        for (int i = 0; i < 6; i++) particles.add(new Particle(x, y));
    }

    @Override
    public void update(float dt) {
        if (numAsteroidsLeft == 0) {
            for (int i = 0; i < 10; i++) {
                createParticles(MathUtils.random(Game.getWidth()), MathUtils.random(Game.getHeight()));
            }
            ++level;
            spawnAsteroids();
        }

        handleInput();
        player.update(dt);
        if (player.isDead()) {
            player.reset();
        }

        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            Bullet b = it.next();
            b.update(dt);
            if (b.shouldRemove()) it.remove();
        }

        for (Asteroid a : asteroids) {
            a.update(dt);
        }

        Iterator<Particle> it3 = particles.iterator();
        while (it3.hasNext()) {
            Particle p = it3.next();
            p.update(dt);
            if (p.shouldRemove()) it3.remove();
        }

        checkCollisions();
    }

    private void checkCollisions() {
        List<Asteroid> toSplit = new ArrayList<Asteroid>();
        for (Iterator<Asteroid> ita = asteroids.iterator(); ita.hasNext(); ) {
            Asteroid a = ita.next();
            if (!player.isHit() && a.intersects(player)) {
                ita.remove();
                toSplit.add(a);
                player.hit();
            } else for (Iterator<Bullet> itb = bullets.iterator(); itb.hasNext(); ) {
                Bullet b = itb.next();
                if (a.contains(b.getX(), b.getY())) {
                    ita.remove();
                    itb.remove();
                    toSplit.add(a);
                    break;
                }
            }
        }
        for (Asteroid a : toSplit) {
            splitAsteroid(a);
        }
    }

    private void splitAsteroid(Asteroid asteroid) {
        --numAsteroidsLeft;
        if (asteroid.getType() == Asteroid.LARGE) {
            asteroids.add(new Asteroid(asteroid.getX(), asteroid.getY(), Asteroid.MEDIUM));
            asteroids.add(new Asteroid(asteroid.getX(), asteroid.getY(), Asteroid.MEDIUM));
        } else if (asteroid.getType() == Asteroid.MEDIUM) {
            asteroids.add(new Asteroid(asteroid.getX(), asteroid.getY(), Asteroid.SMALL));
            asteroids.add(new Asteroid(asteroid.getX(), asteroid.getY(), Asteroid.SMALL));
        }
        createParticles(asteroid.getX(), asteroid.getY());
    }

    @Override
    public void draw() {
        for (Asteroid a : asteroids) {
            a.draw(renderer);
        }
        for (Particle p : particles) {
            p.draw(renderer);
        }
        for (Bullet b : bullets) {
            b.draw(renderer);
        }
        player.draw(renderer);
    }

    @Override
    public void handleInput() {
        player.setLeft(GameKeys.isDown(GameKeys.LEFT));
        player.setRight(GameKeys.isDown(GameKeys.RIGHT));
        player.setUp(GameKeys.isDown(GameKeys.UP));
        if (GameKeys.isJustPressed(GameKeys.SPACE)) {
            player.shoot();
        }
    }

    @Override
    public void dispose() {
    }
}
