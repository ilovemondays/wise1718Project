package de.hshannover.inform.matthiasdietrich;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import de.hshannover.inform.matthiasdietrich.application.screens.GameOverScreen;
import de.hshannover.inform.matthiasdietrich.application.screens.LevelCompletedScreen;
import de.hshannover.inform.matthiasdietrich.application.screens.MainMenuScreen;
import de.hshannover.inform.matthiasdietrich.ui.assets.AssetManager;
import de.hshannover.inform.matthiasdietrich.ui.render.Camera;
import de.hshannover.inform.matthiasdietrich.ui.render.GUIController;
import de.hshannover.inform.matthiasdietrich.ui.render.SpriteRenderer;

public class Semester3Project extends Game {
	public SpriteBatch batch;
	public World world;
	public GUIController guiController;
	public SpriteRenderer spriteRenderer;
	public Camera camera;
	public Vector3 cameraDefaultPosition;
	public AssetManager assetManager;

	private LevelCompletedScreen levelCompletedScreen;
	private GameOverScreen gameOverScreen;

	public LevelCompletedScreen getLevelCompletedScreen () {
		return levelCompletedScreen;
	}

    public GameOverScreen getGameOverScreen () {
        return gameOverScreen;
    }

    @Override
	public void create () {
        Box2D.init();
		world = new World(new Vector2(0, -10f), true);
		batch = new SpriteBatch();
        camera = new Camera();
        cameraDefaultPosition = camera.position;
        guiController = new GUIController();
		spriteRenderer = new SpriteRenderer();
		assetManager = new AssetManager();

		// setup screens
        levelCompletedScreen = new LevelCompletedScreen(this);
        gameOverScreen = new GameOverScreen(this);

        this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		world.dispose();
	}

	public void clearWorld() {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
		for (Body body: bodies) {
		    world.destroyBody(body);
        }
	}
}
