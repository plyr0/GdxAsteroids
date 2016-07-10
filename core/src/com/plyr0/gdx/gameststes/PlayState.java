package com.plyr0.gdx.gameststes;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.plyr0.gdx.entities.Player;
import com.plyr0.gdx.managers.GameKeys;
import com.plyr0.gdx.managers.GameStateManager;

public class PlayState extends GameState {

    private ShapeRenderer renderer;
    private Player player;

    public PlayState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    @Override
    public void init() {
        renderer = new ShapeRenderer();
        player = new Player();
    }

    @Override
    public void update(float dt) {
        handleInput();
        player.update(dt);
    }

    @Override
    public void draw() {
        player.draw(renderer);
    }

    @Override
    public void handleInput() {
        player.setLeft(GameKeys.isDown(GameKeys.LEFT));
        player.setRight(GameKeys.isDown(GameKeys.RIGHT));
        player.setUp(GameKeys.isDown(GameKeys.UP));
    }

    @Override
    public void dispose() {

    }
}
