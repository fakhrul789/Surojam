package com.surojam.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.surojam.game.SuroJam;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "SuroJam";
		config.width = 480;
		config.height = 800;
		config.resizable = false;
		new LwjglApplication(new SuroJam(), config);
	}
}
