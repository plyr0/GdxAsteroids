package com.plyr0.gdx.managers;

import com.plyr0.gdx.gameststes.GameState;
import com.plyr0.gdx.gameststes.MenuState;
import com.plyr0.gdx.gameststes.PlayState;

public class GameStateManager {
    private GameState gameState;

    public static final int MENU = 0;
    public static final int PLAY = 1;

    public GameStateManager() {
        setState(PLAY);
    }

    public void setState(int newState) {
        if (gameState != null) gameState.dispose();
        if (newState == MENU) {
            gameState = new MenuState(this);
        } else if (newState == PLAY) {
            gameState = new PlayState(this);
        }
    }

    public void update(float dt) {
        gameState.update(dt);
    }

    public void draw() {
        gameState.draw();
    }
}
