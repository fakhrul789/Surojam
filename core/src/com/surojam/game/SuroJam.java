package com.surojam.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SuroJam extends Game {
	SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		Asset.load();
		setScreen(new MainMenuScreen(this));
		dispose();
	}

	@Override
	public void render () {
		super.render();
	}
}
