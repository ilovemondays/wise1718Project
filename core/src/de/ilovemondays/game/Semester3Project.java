package de.ilovemondays.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.ilovemondays.game.actors.Player;
import de.ilovemondays.game.input.InputManager;

public class Semester3Project extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Player player;
	private InputManager input;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 384, 216);
		player = new Player();
		input = new InputManager();

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(player.getTexture(), player.getX(),player.getY());
		batch.end();

		updatePlayer();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	private void updatePlayer() {
		// move left
		if (input.isLeft()) {
			System.out.println("left");
			player.moveLeft();
		}
		if (input.isRight()) {
			System.out.println("right");
			player.moveRight();
		}
	}
}
