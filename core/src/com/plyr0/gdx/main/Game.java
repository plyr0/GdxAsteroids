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
    public static final boolean PLAYER_SPEED_AFFECTS_BULLETS = false;
    public static int WIDTH;
    public static int HEIGHT;
    private static OrthographicCamera cam;

    private GameStateManager gameStateManager;
    private FPSLogger fpsLogger;

    @Override
    public void create() {
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();

        cam = new OrthographicCamera(WIDTH, HEIGHT);
        cam.translate(WIDTH / 2, HEIGHT / 2);
        cam.update();

        Gdx.input.setInputProcessor(new GameInputProcessor());
        gameStateManager = new GameStateManager();
        fpsLogger = new FPSLogger();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameStateManager.update(Gdx.graphics.getDeltaTime());
        gameStateManager.draw();

        GameKeys.update();
        fpsLogger.log();
    }
}
