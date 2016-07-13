package com.plyr0.gdx.gamestates;

import com.plyr0.gdx.managers.GameStateManager;

public abstract class GameState {
    protected GameStateManager gameStateManager;

    public GameState(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    public abstract void init();

    public abstract void update(float dt);

    public abstract void draw();

    public abstract void handleInput();

    public abstract void dispose();
}
