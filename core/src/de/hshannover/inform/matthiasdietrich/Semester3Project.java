package de.hshannover.inform.matthiasdietrich;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;
import de.hshannover.inform.matthiasdietrich.application.controller.*;
import de.hshannover.inform.matthiasdietrich.application.models.CertificateModel;
import de.hshannover.inform.matthiasdietrich.ui.input.InputController;
import de.hshannover.inform.matthiasdietrich.application.models.PlayerActor;
import de.hshannover.inform.matthiasdietrich.ui.render.Camera;

public class Semester3Project extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;

	private GameController gameController;

	private PlayerActor player;
	private PlayerController playerController;

	private LevelController levelController;
	private CollisionMapController collisionMapController;
	private CollisionDetectionController collisionDetectionController;

	// Box2D
	private Box2DDebugRenderer debugRenderer;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new Camera();

		// CONTROLLER
		levelController = new LevelController();
		collisionMapController = new CollisionMapController();
		gameController = GameController.getInstance();
		collisionDetectionController = new CollisionDetectionController();

		// Box2D
		debugRenderer = new Box2DDebugRenderer();

		// SETUP CONTROLLER

		// level/map
		levelController.setMap(1);
		collisionMapController.setCollisionMap(levelController.getCollisionData());
		collisionMapController.constructCollisionMap(gameController.getWorld());

		// player
		playerController = PlayerController.getInstance();
		playerController.setInput(InputController.getInstance());

		player = new PlayerActor(gameController.getWorld());
		playerController.setPlayer(player);

		// game
		gameController.setContactListener(collisionDetectionController);

		// @TODO: test
		player.setX(3);
		player.setY(3);
		player.spawn();

		// @TODO: test
		CertificateModel cert = new CertificateModel();
		cert.spawn(gameController.getWorld());
		cert.spawn(gameController.getWorld());
		cert.spawn(gameController.getWorld());
		cert.spawn(gameController.getWorld());
		cert.spawn(gameController.getWorld());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);

		playerController.updatePlayer();

		gameController.getWorld().step(1/60f, 1, 1);
		gameController.destroyBodies();

		if(GameConstants.DEV_MODE) {
			debugRenderer.render(gameController.getWorld(), camera.combined);
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
