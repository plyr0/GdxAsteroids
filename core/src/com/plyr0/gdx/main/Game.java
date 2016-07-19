package com.plyr0.gdx.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.plyr0.gdx.managers.GameInputProcessor;
import com.plyr0.gdx.managers.GameKeys;
import com.plyr0.gdx.managers.GameStateManager;

public class Game extends ApplicationAdapter {
    public static final boolean PLAYER_SPEED_AFFECTS_BULLETS = true;
    public static final boolean SHOOTING_AFFECTS_PLAYER_SPEED = false;
    public static final float DECELERATION = 0;

    private static int width;
    private static int height;
    private GameStateManager gameStateManager;
    private FPSLogger fpsLogger;

    @Override
    public void create() {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        Gdx.app.log("Game", "screen: " + width + " x " + height);

        OrthographicCamera cam = new OrthographicCamera(width, height);
        cam.translate(width / 2, height / 2);
        cam.update();

        Gdx.input.setInputProcessor(new GameInputProcessor());
        gameStateManager = new GameStateManager();
        fpsLogger = new FPSLogger();
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameStateManager.update(Gdx.graphics.getDeltaTime());
        gameStateManager.draw();

        GameKeys.update();
        //fpsLogger.log();
    }
}
