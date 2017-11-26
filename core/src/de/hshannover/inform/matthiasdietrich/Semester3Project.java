package de.hshannover.inform.matthiasdietrich;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import de.hshannover.inform.matthiasdietrich.application.screens.*;
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
	private MainMenuScreen mainMenuScreen;
	private GameScreen gameScreen;
	private HelpScreen helpScreen;


	public LevelCompletedScreen getLevelCompletedScreen () {
		return levelCompletedScreen;
	}

	public HelpScreen getHelpScreen () {
		return helpScreen;
	}

    public GameOverScreen getGameOverScreen () {
        return gameOverScreen;
    }

	public MainMenuScreen getMainMenuScreen () {
		return mainMenuScreen;
	}

    public GameScreen getGameScreen () {
        return gameScreen;
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
        mainMenuScreen = new MainMenuScreen(this);
        gameScreen = new GameScreen(this);
        helpScreen = new HelpScreen(this);

        this.setScreen(mainMenuScreen);
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
        int bodyCount = world.getBodyCount();
        for (int i = 0; i<bodyCount; i++) {
            world.destroyBody(bodies.get(i));
        }
	}
}
