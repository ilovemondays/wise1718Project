package de.hshannover.inform.matthiasdietrich.application.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import de.hshannover.inform.matthiasdietrich.Semester3Project;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;
import de.hshannover.inform.matthiasdietrich.application.controller.*;
import de.hshannover.inform.matthiasdietrich.ui.input.InputController;
import de.hshannover.inform.matthiasdietrich.ui.render.Camera;
import de.hshannover.inform.matthiasdietrich.ui.render.MapRenderer;
import de.hshannover.inform.matthiasdietrich.ui.render.SpriteRenderer;

import java.util.Observable;
import java.util.Observer;

// @TODO: Hier ist zu viel Game Logik drin, das gehört in den Game Controller, wird immer schlimmer :o
/**
 * Created by matthiasdietrich on 25.10.17.
 */
public class GameScreen implements Screen, Observer {
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
    private float pauseTime = 0;
    private boolean pause = false;

    public GameScreen(final Semester3Project game) {
        this.game = game;
        game.guiController.addMeAsObserver(this);
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

    public void setPauseTime (float pauseTime) {
        this.pauseTime = pauseTime;
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
        if(isPause()) {
            pauseTime += Gdx.graphics.getDeltaTime();
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

            if(!isPause()) {
                // @TODO: das hier in camera class
                camera.position.set(gameController.getPlayer().getX(), gameController.getPlayer().getY(), 0);
                camera.update();

                gameController.loop();

                if (gameController.checkLevelCompleteCondition()) {
                    gameController.endWorld();
                    game.setScreen(game.getLevelCompletedScreen());
                    dispose();
                }

                if (gameController.checkGameOverCondition()) {
                    gameController.endWorld();
                    game.setScreen(game.getGameOverScreen());
                    dispose();
                }

                if (gameController.checkGameWinCondition()) {
                    gameController.endWorld();
                    game.setScreen(game.getWinScreen());
                    dispose();
                }
            }

            game.batch.begin();
            mapRenderer.render(game.batch, (Camera) camera);
            game.batch.end();

            spriteRenderer.render(game.batch);

            game.batch.begin();
            game.guiController.getActStage().act();
            game.guiController.getLabelTrials().setText("VERSUCH: " + gameController.getTrials());
            game.guiController.getLabelCertificatesFound().setText("SCHEINE: " + gameController.getCertificates());
            game.guiController.getLabelSemester().setText("SEMESTER: " + gameController.getLevel());
            game.guiController.setPlayerHealth(gameController.getPlayer().getTired());
            game.guiController.getActStage().draw();
            game.batch.end();

            if (input.isESC() && !pause) {
                pause = true;
                game.guiController.getButtonGameExit().setVisible(true);
                game.guiController.getButtonGameResume().setVisible(true);
            }
        }
    }

    private boolean isPause() {
        return ((pauseTime < pauseGameTime) || pause);
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

    @Override
    public void update (Observable o, Object arg) {
        if (arg instanceof String) {
            if (arg.equals("button-game-exit-to-menu")) {
                pause = false;
                gameController.endWorld();
                gameController.resetGameModel();
                game.setScreen(game.getMainMenuScreen());
                dispose();
            }
            if (arg.equals("button-game-resume")) {
                game.guiController.getButtonGameExit().setVisible(false);
                game.guiController.getButtonGameResume().setVisible(false);
                pause = false;
            }
        }
    }
}
