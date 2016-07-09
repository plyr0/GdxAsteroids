package com.plyr0.gdx.main.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.plyr0.gdx.main.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Game";
        config.width = 500;
        config.height = 400;
        config.resizable = false;
        config.useGL30 = false;
		new LwjglApplication(new Game(), config);
	}
}
