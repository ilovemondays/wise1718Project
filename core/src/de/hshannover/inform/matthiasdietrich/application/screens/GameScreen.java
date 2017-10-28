package de.hshannover.inform.matthiasdietrich.application.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import de.hshannover.inform.matthiasdietrich.Semester3Project;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;
import de.hshannover.inform.matthiasdietrich.application.controller.*;
import de.hshannover.inform.matthiasdietrich.application.models.CertificateModel;
import de.hshannover.inform.matthiasdietrich.application.models.PlayerActor;
import de.hshannover.inform.matthiasdietrich.ui.input.InputController;
import de.hshannover.inform.matthiasdietrich.ui.render.Camera;

/**
 * Created by matthiasdietrich on 25.10.17.
 */
public class GameScreen implements Screen {
    final Semester3Project game;

    private GameController gameController;

    private PlayerActor player;
    private PlayerController playerController;

    private LevelController levelController;
    private CollisionMapController collisionMapController;
    private CollisionDetectionController collisionDetectionController;

    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;

    public GameScreen(final Semester3Project game) {
        this.game = game;

        camera = new Camera();

        // CONTROLLER
        levelController = new LevelController();
        collisionMapController = new CollisionMapController();
        gameController = GameController.getInstance();
        gameController.startWorld();
        collisionDetectionController = new CollisionDetectionController();

        // Box2D
        debugRenderer = new Box2DDebugRenderer();

        // SETUP CONTROLLER
        gameController.setWorld(game.world);

        // level/map
        levelController.setMap(1);
        collisionMapController.setCollisionMap(levelController.getCollisionData());
        collisionMapController.constructCollisionMap(gameController.getWorld());

        // player
        playerController = PlayerController.getInstance();
        playerController.setInput(InputController.getInstance());

        player = PlayerActor.getInstance(gameController.getWorld());
        playerController.setPlayer(player);

        // game
        gameController.setContactListener(collisionDetectionController);

        // @TODO: test
        player.setX(3);
        player.setY(3);
        player.spawn();

        // @TODO: test
        CertificateModel cert = new CertificateModel();
        cert.spawn(gameController.getWorld(), 4, 2);
        cert.spawn(gameController.getWorld(), 4, 3);
        cert.spawn(gameController.getWorld(), 4, 4);
        cert.spawn(gameController.getWorld(), 5, 4);
        cert.spawn(gameController.getWorld(), 6, 4);
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

        gameController.getWorld().step(1/60f, 1, 1);
        gameController.destroyBodies(); // if there are any to destroy, like collected certificates

        if(GameConstants.DEV_MODE) {
            debugRenderer.render(gameController.getWorld(), camera.combined);
        }

        if (gameController.checkWinCondition()) {
            gameController.resetGameModel();
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
