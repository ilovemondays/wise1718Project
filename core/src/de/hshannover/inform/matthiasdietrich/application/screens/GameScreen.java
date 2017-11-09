package de.hshannover.inform.matthiasdietrich.application.screens;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import de.hshannover.inform.matthiasdietrich.Semester3Project;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;
import de.hshannover.inform.matthiasdietrich.application.controller.*;
import de.hshannover.inform.matthiasdietrich.application.models.CertificateModel;
import de.hshannover.inform.matthiasdietrich.application.models.PlayerActor;
import de.hshannover.inform.matthiasdietrich.ui.input.InputController;
import de.hshannover.inform.matthiasdietrich.ui.render.Camera;

// @TODO: Hier ist zu viel Game Logik drin, das gehört in den Game Controller, wird immer schlimmer :o
/**
 * Created by matthiasdietrich on 25.10.17.
 */
public class GameScreen implements Screen {
    final Semester3Project game;

    private GameController gameController;

    private PlayerActor player;
    private PlayerController playerController;

    private LevelController levelController;
    private CollisionDetectionController collisionDetectionController;

    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;
    private RayHandler rayHandler;

    public GameScreen(final Semester3Project game) {
        this.game = game;
        camera = new Camera();

        // CONTROLLER
        levelController = new LevelController(game.world);

        gameController = GameController.getInstance();
        gameController.startWorld();
        collisionDetectionController = new CollisionDetectionController();

        // Box2D
        debugRenderer = new Box2DDebugRenderer();

        // SETUP CONTROLLER
        gameController.setWorld(game.world);

        // level/map
        levelController.setMap(1);
        // collision layer
        levelController.getMapLayerController().constructCollisionMap(gameController.getWorld());
        // trap layer
        levelController.getMapLayerController().constructTrapMap(gameController.getWorld());

        // player
        playerController = PlayerController.getInstance();
        playerController.setInput(InputController.getInstance());
        player = PlayerActor.getInstance(game.world);
        playerController.setPlayer(player);

        // player position
        levelController.getMapLayerController().setPlayerPosition(game.world, player);
        player.spawn();

        // distribute certificates in level
        levelController.getMapLayerController().setCertificatesPosition(game.world, new CertificateModel());

        // distribute goblins in level
        levelController.spawnGoblins();

        // set collision detection
        gameController.setContactListener(collisionDetectionController);

        //player.update();
        //camera.translate(player.getX(), player.getY());
        //camera.update();

        rayHandler = new RayHandler(game.world);

        rayHandler.setAmbientLight(new Color(.1f, .3f, .6f, .5f));
        levelController.getMapLayerController().setLightPosition(game.world, rayHandler);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);

        playerController.updatePlayer();

        camera.position.set(player.getX(), player.getY(), 0);
        camera.update();

        gameController.getWorld().step(1/60f, 1, 1);
        gameController.destroyBodies(); // if there are any to destroy, like collected certificates

        if(GameConstants.DEV_MODE) {
            debugRenderer.render(gameController.getWorld(), camera.combined);
        }
        rayHandler.setCombinedMatrix(camera);
        rayHandler.updateAndRender();

        if (gameController.checkWinCondition()) {
            gameController.resetGameModel();
            levelController.clear();
            game.setScreen(new LevelCompletedScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}
