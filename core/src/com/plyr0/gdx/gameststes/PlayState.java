package com.plyr0.gdx.gameststes;

import com.plyr0.gdx.managers.GameStateManager;

public class PlayState extends GameState {

    public PlayState(GameStateManager gameStateManager) {
        super(gameStateManager);
        init();
    }

    @Override
    public void init() {

    }

    @Override
    public void update(float dt) {
        System.out.println("PLAY updating");
    }

    @Override
    public void draw() {
        System.out.println("PLAY drawing");
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void dispose() {

    }
}
