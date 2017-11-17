package de.hshannover.inform.matthiasdietrich.application.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.hshannover.inform.matthiasdietrich.Semester3Project;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;
import de.hshannover.inform.matthiasdietrich.application.controller.*;
import de.hshannover.inform.matthiasdietrich.ui.render.Camera;

// @TODO: Hier ist zu viel Game Logik drin, das gehört in den Game Controller, wird immer schlimmer :o
/**
 * Created by matthiasdietrich on 25.10.17.
 */
public class GameScreen implements Screen {
    final Semester3Project game;

    private GameController gameController;

    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;
    private Stage stage;



    public GameScreen(final Semester3Project game) {
        this.game = game;
        camera = new Camera();
        stage = game.guiController.getGameStage();

        // CONTROLLER
        gameController = GameController.getInstance();
        gameController.startWorld(game.world);

        // Box2D
        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);

        // @TODO: das hier in camera class
        camera.position.set(gameController.getPlayer().getX(), gameController.getPlayer().getY(), 0);
        camera.update();

        gameController.loop();

        if(GameConstants.DEV_MODE) {
            debugRenderer.render(gameController.getWorld(), camera.combined);
        }

        // @TODO: verschieben in render/lighting class
        gameController.getRayHandler().setCombinedMatrix(camera);
        gameController.getRayHandler().updateAndRender();

        // @TODO: der screen sollte nichts über win bedingungen wissen
        // auch braucht er nicht wissen was der nächste screen ist
        if (gameController.checkWinCondition()) {
            gameController.endWorld();
            gameController.nextLevel();
            game.setScreen(new LevelCompletedScreen(game));
            dispose();
        }

        if (gameController.checkGameOverCondition()) {
            gameController.endWorld();
            game.setScreen(new GameOverScreen(game));
            dispose();
        }

        game.batch.begin();
        stage.act();
        game.guiController.getLabelTrials().setText("VERSUCHE: "+gameController.getTrials());
        game.guiController.getLabelCertificatesFound().setText("SCHEINE: "+gameController.getCertificates());
        game.guiController.getLabelSemester().setText("SEMESTER: "+gameController.getLevel());
        stage.draw();
        game.batch.end();
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
