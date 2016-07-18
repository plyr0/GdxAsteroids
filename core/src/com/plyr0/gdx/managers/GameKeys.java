package com.plyr0.gdx.managers;

public class GameKeys {
    private static final int NUM_KEYS = 8;

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int ENTER = 4;
    public static final int SPACE = 5;
    public static final int ESC = 6;
    public static final int SHIFT = 7;

    private static boolean keys[] = new boolean[NUM_KEYS];
    private static boolean oldKeys[] = new boolean[NUM_KEYS];

    private GameKeys() {
    }

    public static boolean isDown(int key) {
        return keys[key];
    }

    public static boolean isJustPressed(int key) {
        return keys[key] && !oldKeys[key];
    }

    public static void update() {
        System.arraycopy(keys, 0, oldKeys, 0, NUM_KEYS);
    }

    public static void setKey(int key, boolean isPressed) {
        keys[key] = isPressed;
    }
}
