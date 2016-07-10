package com.plyr0.gdx.gameststes;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.plyr0.gdx.entities.Bullet;
import com.plyr0.gdx.entities.Player;
import com.plyr0.gdx.managers.GameKeys;
import com.plyr0.gdx.managers.GameStateManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayState extends GameState {

    private ShapeRenderer renderer;
    private Player player;
    private List<Bullet> bullets;

    public PlayState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        renderer = new ShapeRenderer();
        bullets = new ArrayList<Bullet>();
        player = new Player(bullets);
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
    }

    @Override
    public void draw() {
        player.draw(renderer);
        for (Bullet b : bullets) {
            b.draw(renderer);
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
