package com.plyr0.gdx.managers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class GameInputProcessor extends InputAdapter {

    @Override
    public boolean keyDown(int keycode) {
        switchKey(keycode, true);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switchKey(keycode, false);
        return true;
    }

    private void switchKey(int keycode, boolean isPressed) {
        switch (keycode) {
            case Input.Keys.UP:
                GameKeys.setKey(GameKeys.UP, isPressed);
                break;
            case Input.Keys.DOWN:
                GameKeys.setKey(GameKeys.DOWN, isPressed);
                break;
            case Input.Keys.LEFT:
                GameKeys.setKey(GameKeys.LEFT, isPressed);
                break;
            case Input.Keys.RIGHT:
                GameKeys.setKey(GameKeys.RIGHT, isPressed);
                break;
            case Input.Keys.ENTER:
                GameKeys.setKey(GameKeys.ENTER, isPressed);
                break;
            case Input.Keys.SPACE:
                GameKeys.setKey(GameKeys.SPACE, isPressed);
                break;
            case Input.Keys.ESCAPE:
                GameKeys.setKey(GameKeys.ESC, isPressed);
                break;
            case Input.Keys.SHIFT_LEFT:
            case Input.Keys.SHIFT_RIGHT:
                GameKeys.setKey(GameKeys.SHIFT, isPressed);
                break;
        }
    }
}
