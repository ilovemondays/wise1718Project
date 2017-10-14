package de.hshannover.inform.matthiasdietrich;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.hshannover.inform.matthiasdietrich.actors.PlayerController;
import de.hshannover.inform.matthiasdietrich.constants.GameConstants;
import de.hshannover.inform.matthiasdietrich.input.InputManager;
import de.hshannover.inform.matthiasdietrich.actors.Player;

public class Semester3Project extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private InputManager input;

	private Player player;
	private Sprite playerSprite;
	private PlayerController playerController;

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;

	// Box2D
	private TiledMapTileLayer collision;
	private World world;
	private Box2DDebugRenderer debugRenderer;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, scale(384), scale(216));

		input = new InputManager();

		// @TODO: In eigene Map-Loading Klasse auslagern
		map = new TmxMapLoader().load("maps/map.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		collision = (TiledMapTileLayer)map.getLayers().get("collision");

		// Box2D
		Box2D.init();
		world = new World(new Vector2(0, -10f), true);
		debugRenderer = new Box2DDebugRenderer();

		// @TODO: ugly testing incoming:
		for (int collisionHeight = 0; collisionHeight <= collision.getHeight();  collisionHeight++) {
			for (int collisionWidth = 0; collisionWidth <= collision.getWidth();  collisionWidth++) {
				if(collision.getCell(collisionWidth, collisionHeight) != null) {
					createCollisionTile(collisionWidth, collisionHeight);
				}
			}
		}

		// @TODO: muss eigentlich erst bei klick auf "Spiel Starten" erstellt werden
		playerController = new PlayerController();
		world.setContactListener(playerController);
		player = new Player(world);
		player.setX(3);
		player.setY(3);
		Texture test = new Texture("actors/dummy/dummy.png");
		playerSprite = adjustSize(new Sprite(test));
		player.spawn();
	}

	// @TODO: eigene klasse erstellen:
	private void createCollisionTile(int x,int y) {
		// Create our body definition
		BodyDef groundBodyDef = new BodyDef();
		// Set its world position
		// @TODO: Die Konstanten sind unlesbar:
		groundBodyDef.position.set(new Vector2(x * GameConstants.TILE_WIDTH, y*GameConstants.TILE_WIDTH));

		// Create a body from the defintion and add it to the world
		Body groundBody = world.createBody(groundBodyDef);

		// Create a polygon shape
		PolygonShape groundBox = new PolygonShape();
		// Set the polygon shape as a box which is twice the size of our view port and 20 high
		// (setAsBox takes half-width and half-height as arguments)
		groundBox.setAsBox(GameConstants.TILE_WIDTH/2, GameConstants.TILE_WIDTH/2);

		// Create a fixture from our polygon shape and add it to our ground body
		FixtureDef groundFix = new FixtureDef();
		groundFix.shape = groundBox;
		groundFix.density = 1.0f;
		groundFix.friction = 1.0f;
		groundBody.createFixture(groundFix);
		// Clean up after ourselves
		groundBox.dispose();
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

		player.update();
		playerMovement();

		// Box2D
		world.step(1/60f, 1, 1);
		debugRenderer.render(world, camera.combined);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		renderer.dispose();
	}

	private void playerMovement() {
		if (input.isLeft() && player.getBody().getLinearVelocity().x > -GameConstants.MAX_VELOCITY) {
			if(playerController.isPlayerCanMoveLeft()) {
				player.moveLeft();
			}
		}
		if (input.isRight() && player.getBody().getLinearVelocity().x < GameConstants.MAX_VELOCITY) {
			if(playerController.isPlayerCanMoveRight()) {
				player.moveRight();
			}
		}
		if (input.isJump()) {
			System.out.println("can jump: "+playerController.isPlayerIsOnGround());
			if(playerController.isPlayerIsOnGround()) {
				System.out.println("jump");
				player.jump();
			}
		}
	}

	// @TODO: das hier in helper/utility class auslagern
	public float scale(float valueToBeScaled) {
		return valueToBeScaled/GameConstants.SCALE;
	}

	public Sprite adjustSize(Sprite imageToBeScaled) {
		imageToBeScaled.setSize(scale(imageToBeScaled.getWidth()), scale(imageToBeScaled.getHeight()));
		return imageToBeScaled;
	}
}
