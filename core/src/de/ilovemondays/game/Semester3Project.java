package de.ilovemondays.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.ilovemondays.game.actors.Player;
import de.ilovemondays.game.constants.GameConstants;
import de.ilovemondays.game.input.InputManager;

public class Semester3Project extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Player player;
	private InputManager input;

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
		camera.setToOrtho(false, 384, 216);

		// @TODO: muss eigentlich erst bei klick auf "Spiel Starten" erstellt werden
		player = new Player();
		player.setX(70);
		player.setY(70);
		input = new InputManager();

		// @TODO: In eigene Map-Loading Klasse auslagern
		map = new TmxMapLoader().load("maps/map.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		collision = (TiledMapTileLayer)map.getLayers().get("collision");

		// Box2D
		Box2D.init();
		world = new World(new Vector2(0, -100), true);
		debugRenderer = new Box2DDebugRenderer();

		// @TODO: ugly testing incoming:
		BodyDef playerDef = new BodyDef();
		playerDef.type = BodyDef.BodyType.DynamicBody;
		playerDef.fixedRotation = true;
		playerDef.position.set(new Vector2(player.getX() + 28/GameConstants.SCALE_FACTOR_BOX2D, player.getY()+32/GameConstants.SCALE_FACTOR_BOX2D));
		Body playerBody = world.createBody(playerDef);

		PolygonShape playerBox = new PolygonShape();
		playerBox.setAsBox(28/GameConstants.SCALE_FACTOR_BOX2D, 32/GameConstants.SCALE_FACTOR_BOX2D);
		FixtureDef playerFix = new FixtureDef();
		playerFix.shape = playerBox;
		playerFix.density = 0.0f;
		playerFix.restitution = 0.3f;
		playerBody.createFixture(playerFix);
		playerBox.dispose();




		for (int collisionHeight = 0; collisionHeight <= collision.getHeight();  collisionHeight++) {
			for (int collisionWidth = 0; collisionWidth <= collision.getWidth();  collisionWidth++) {
				if(collision.getCell(collisionWidth, collisionHeight) != null) {
					createCollisionTile(collisionWidth, collisionHeight);
				}
			}
		}
	}

	// @TODO: eigene klasse erstellen:
	private void createCollisionTile(int x,int y) {
		// Create our body definition
		BodyDef groundBodyDef = new BodyDef();
		// Set its world position
		// @TODO: Die Konstanten sind unlesbar:
		groundBodyDef.position.set(new Vector2((x * GameConstants.TILE_WIDTH)+GameConstants.TILE_WIDTH/GameConstants.SCALE_FACTOR_BOX2D, (y*GameConstants.TILE_WIDTH)+GameConstants.TILE_WIDTH/GameConstants.SCALE_FACTOR_BOX2D));

		// Create a body from the defintion and add it to the world
		Body groundBody = world.createBody(groundBodyDef);

		// Create a polygon shape
		PolygonShape groundBox = new PolygonShape();
		// Set the polygon shape as a box which is twice the size of our view port and 20 high
		// (setAsBox takes half-width and half-height as arguments)
		groundBox.setAsBox(GameConstants.TILE_WIDTH/GameConstants.SCALE_FACTOR_BOX2D, GameConstants.TILE_WIDTH/GameConstants.SCALE_FACTOR_BOX2D);
		// Create a fixture from our polygon shape and add it to our ground body
		groundBody.createFixture(groundBox, 0.0f);
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
		batch.draw(player.getTexture(), player.getX(),player.getY());
		batch.end();

		updatePlayer();

		// Box2D
		world.step(1/45f, 6, 2);
		debugRenderer.render(world, camera.combined);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		renderer.dispose();
	}

	private void updatePlayer() {
		if (input.isLeft()) {
			player.moveLeft();
		}
		if (input.isRight()) {
			player.moveRight();
		}
	}
}
