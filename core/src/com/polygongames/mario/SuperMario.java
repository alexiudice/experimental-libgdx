package com.polygongames.mario;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.polygongames.mario.gamesys.GameManager;
import com.polygongames.mario.gamesys.LevelManager;
import com.polygongames.mario.screens.PlayScreen;

public class SuperMario extends Game {
	public SpriteBatch batch;

	private GameManager gameManager;
	private LevelManager levelManager;

	@Override
	public void create () {
		batch = new SpriteBatch();

		if (GameManager.instance != null) {
			gameManager = GameManager.instance;
		}
		else {
			gameManager = new GameManager();
		}

		if (LevelManager.instance != null)
		{
			levelManager = LevelManager.instance;
		}
		else{
			levelManager = new LevelManager();
		}

		LevelManager.loadNextLevel( this );
	}

	@Override
	public void render () {
		super.render();
	}


	@Override
	public void dispose() {
		super.dispose();
		gameManager.dispose();
	}

}
