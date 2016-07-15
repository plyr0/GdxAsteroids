package com.plyr0.gdx.gamestates;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.plyr0.gdx.entities.Asteroid;
import com.plyr0.gdx.entities.Bullet;
import com.plyr0.gdx.entities.Player;
import com.plyr0.gdx.main.Game;
import com.plyr0.gdx.managers.GameKeys;
import com.plyr0.gdx.managers.GameStateManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayState extends GameState {

    private ShapeRenderer renderer;
    private Player player;
    private List<Bullet> bullets;
    private List<Asteroid> asteroids;

    private int level;
    private int numAsteroidsTotal;
    private int numAsteroidsLeft;

    public PlayState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        renderer = new ShapeRenderer();
        bullets = new ArrayList<Bullet>();
        player = new Player(bullets);
        asteroids = new ArrayList<Asteroid>();

        level = 1;

        spawnAsteroids();
    }

    private void spawnAsteroids() {
        asteroids.clear();
        int numToSpawn = 3 + level;
        numAsteroidsLeft = numAsteroidsTotal = numToSpawn * 7; //large = 2 * medium; medium = 2 * small
        for (int i = 0; i < numToSpawn; i++) {
            float x = MathUtils.random(Game.WIDTH);
            float y = MathUtils.random(Game.HEIGHT);
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

    @Override
    public void update(float dt) {
        handleInput();
        player.update(dt);

        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            Bullet b = it.next();
            b.update(dt);
            if (b.shouldRemove()) it.remove();
        }

        Iterator<Asteroid> it2 = asteroids.iterator();
        while (it2.hasNext()) {
            Asteroid a = it2.next();
            a.update(dt);
            if (a.shouldRemove()) it2.remove();
        }

        checkCollisions();
    }

    private void checkCollisions() {
        for (Iterator<Asteroid> ita = asteroids.iterator(); ita.hasNext(); ) {
            Asteroid a = ita.next();
            if (a.intersects(player)) {
                ita.remove();
                splitAsteroid(a);
                player.hit();
                return;
            } else for (Iterator<Bullet> itb = bullets.iterator(); itb.hasNext(); ) {
                Bullet b = itb.next();
                if (a.contains(b.getX(), b.getY())) {
                    ita.remove();
                    itb.remove();
                    splitAsteroid(a);
                    break;
                }
            }
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
    }

    @Override
    public void draw() {
        player.draw(renderer);
        for (Bullet b : bullets) {
            b.draw(renderer);
        }
        for (Asteroid a : asteroids) {
            a.draw(renderer);
        }
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
