package de.hshannover.inform.matthiasdietrich;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.hshannover.inform.matthiasdietrich.application.controller.CollisionMapController;
import de.hshannover.inform.matthiasdietrich.application.controller.LevelController;
import de.hshannover.inform.matthiasdietrich.application.controller.PlayerController;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;
import de.hshannover.inform.matthiasdietrich.ui.input.InputController;
import de.hshannover.inform.matthiasdietrich.application.actors.Player;
import de.hshannover.inform.matthiasdietrich.ui.render.Camera;
import de.hshannover.inform.matthiasdietrich.util.Util;

public class Semester3Project extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;

	private Player player;
	private Sprite playerSprite;
	private PlayerController playerController;

	private LevelController levelController;
	private CollisionMapController collisionMapController;
	private OrthogonalTiledMapRenderer renderer;

	// Box2D
	private World world;
	private Box2DDebugRenderer debugRenderer;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new Camera();

		// CONTROLLER
		levelController = new LevelController();
		collisionMapController = new CollisionMapController();

		// Box2D
		Box2D.init();
		world = new World(new Vector2(0, -10f), true);
		debugRenderer = new Box2DDebugRenderer();

		// SETUP CONTROLLER

		// level/map
		levelController.setMap(1);
		collisionMapController.setCollisionMap(levelController.getCollisionData());
		collisionMapController.constructCollisionMap(world);
		renderer = new OrthogonalTiledMapRenderer(levelController.getMap(), GameConstants.SCALE * 0.001f);

		// player
		playerController = new PlayerController();
		playerController.setInputController(InputController.getInputController());
		world.setContactListener(playerController);

		player = new Player(world);
		playerController.setPlayer(player);

		player.setX(3);
		player.setY(3);
		Texture test = new Texture("actors/dummy/dummy.png");
		playerSprite = Util.adjustSize(new Sprite(test));
		player.spawn();
	}


	@Override
	public void render () {
		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);

		renderer.setView(camera);
		renderer.render();

		batch.begin();
		//batch.draw(player.getSprite(), player.getX(),player.getY());
		playerSprite.setPosition(player.getX()-playerSprite.getWidth()/2, player.getY()-playerSprite.getHeight()/2);
		playerSprite.draw(batch);
		batch.end();

		playerController.updatePlayer();

		// Box2D
		world.step(1/60f, 1, 1);
		debugRenderer.render(world, camera.combined);
	}

	@Override
	public void dispose () {
		batch.dispose();
		renderer.dispose();
	}


}
