package de.hshannover.inform.matthiasdietrich.application.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.hshannover.inform.matthiasdietrich.Semester3Project;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;
import de.hshannover.inform.matthiasdietrich.application.controller.*;
import de.hshannover.inform.matthiasdietrich.ui.input.InputController;
import de.hshannover.inform.matthiasdietrich.ui.render.Camera;
import de.hshannover.inform.matthiasdietrich.ui.render.MapRenderer;
import de.hshannover.inform.matthiasdietrich.ui.render.SpriteRenderer;

// @TODO: Hier ist zu viel Game Logik drin, das gehört in den Game Controller, wird immer schlimmer :o
/**
 * Created by matthiasdietrich on 25.10.17.
 */
public class GameScreen implements Screen {
    final Semester3Project game;

    private GameController gameController;

    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;
    private SpriteRenderer spriteRenderer;
    private MapRenderer mapRenderer;
    private InputController input;
    private float cameraRotation = 0;
    private boolean show = false;

    private final float pauseGameTime = 0;
    private float pause = 0;


    public GameScreen(final Semester3Project game) {
        this.game = game;
        camera = game.camera;
        input = InputController.getInstance();

        // Box2D
        debugRenderer = new Box2DDebugRenderer();

        // CONTROLLER
        gameController = GameController.getInstance();
        gameController.setWorld(game.world);
        gameController.setGameScreen(this);
        gameController.setLevelController(new LevelController(game.world));
        gameController.setCollisionDetectionController(new CollisionDetectionController());

        // Render
        spriteRenderer = new SpriteRenderer();
        mapRenderer = new MapRenderer();

    }

    public void setPause (float pause) {
        this.pause = pause;
    }

    @Override
    public void show() {
        if (GameConstants.DEV_MODE) {
            System.out.println("GameScreen: WORLD BODY COUNT: "+game.world.getBodyCount());
            game.clearWorld();
            System.out.println("GameScreen: WORLD BODY COUNT: "+game.world.getBodyCount());
        }
        if (GameConstants.DEV_MODE) {
            System.out.println("GameScreen: SHOW GAME_SCREEN");
        }
        game.guiController.setGameStage();
        if (GameConstants.DEV_MODE) {
            System.out.println("GameScreen: SET GAME STAGE");
        }
        gameController.startWorld(game.world);
        if (GameConstants.DEV_MODE) {
            System.out.println("GameScreen: START WORLD");
        }
        gameController.setGame(game);
        if (GameConstants.DEV_MODE) {
            System.out.println("GameScreen: SET PLAYER TO SPRITE RENDERER");
        }
        spriteRenderer.setPlayer(gameController.getPlayer());

        if (GameConstants.DEV_MODE) {
            System.out.println("GameScreen: MAP RENDERER");
        }

        mapRenderer.setMapTiles(gameController.getLevelController().getCollisionTiles());
        mapRenderer.setTraps(gameController.getLevelController().getTrapTiles());
        mapRenderer.setGoblins(gameController.getLevelController().getGoblinsAsPoints());
        mapRenderer.setCertificates(gameController.getLevelController().getCertificates());

        switch (gameController.getLevel()){
            case 1: game.assetManager.playMusic("music-stage-1");
                break;
            case 2: game.assetManager.playMusic("music-stage-2");
                break;
            case 3: game.assetManager.playMusic("music-stage-3");
                break;
            default: game.assetManager.playMusic("music-stage-6");
                break;
        }
        show = true;
    }

    @Override
    public void render(float delta) {
        if(pause < pauseGameTime) {
            pause += Gdx.graphics.getDeltaTime();
        }

        if((show && gameController.isSetup())) {
            Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            game.batch.setProjectionMatrix(camera.combined);


            if (GameConstants.DEV_MODE) {
                debugRenderer.render(gameController.getWorld(), camera.combined);
            }

            // @TODO: verschieben in render/lighting class
            gameController.getRayHandler().setCombinedMatrix(camera);
            gameController.getRayHandler().updateAndRender();

            if(pause >= pauseGameTime) {
                // @TODO: das hier in camera class
                camera.position.set(gameController.getPlayer().getX(), gameController.getPlayer().getY(), 0);
                camera.update();

                gameController.loop();

                // @TODO: der screen sollte nichts über win bedingungen wissen
                // auch braucht er nicht wissen was der nächste screen ist
                if (gameController.checkWinCondition()) {
                    gameController.endWorld();
                    game.setScreen(game.getLevelCompletedScreen());
                    dispose();
                }

                if (gameController.checkGameOverCondition()) {
                    gameController.endWorld();
                    game.setScreen(game.getGameOverScreen());
                    dispose();
                }
            }

            game.batch.begin();
            mapRenderer.render(game.batch, (Camera) camera);
            game.batch.end();

            game.batch.begin();
            game.guiController.getActStage().act();
            game.guiController.getLabelTrials().setText("VERSUCH: " + gameController.getTrials());
            game.guiController.getLabelCertificatesFound().setText("SCHEINE: " + gameController.getCertificates());
            game.guiController.getLabelSemester().setText("SEMESTER: " + gameController.getLevel());
            game.guiController.setPlayerHealth(gameController.getPlayer().getTired());
            game.guiController.getActStage().draw();
            game.batch.end();

            spriteRenderer.render(game.batch);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        show = false;
    }

    @Override
    public void resume() {
        show = true;
    }

    @Override
    public void hide() {
        show = false;
    }

    @Override
    public void dispose() {
    }
}
