package de.hshannover.inform.matthiasdietrich.application.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import de.hshannover.inform.matthiasdietrich.Semester3Project;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;
import de.hshannover.inform.matthiasdietrich.application.controller.GameController;
import de.hshannover.inform.matthiasdietrich.ui.input.InputController;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by matthiasdietrich on 25.10.17.
 */
public class LevelCompletedScreen implements Screen, Observer {
    final Semester3Project game;
    private InputController input;
    private boolean shown = false;
    private GameController gameController = GameController.getInstance();

    public LevelCompletedScreen(final Semester3Project game) {
        this.game = game;
        input = InputController.getInstance();
        game.guiController.addMeAsObserver(this);
    }

    @Override
    public void show() {
        shown = true;
        game.clearWorld();
        game.guiController.setLevelCompletedStage();

        if (GameConstants.DEV_MODE) {
            System.out.println("show level completed screen");
        }

        if (gameController.getLevel() == 0) {
            game.guiController.getLabelCompletedTop().setText("Semester 1");
            game.guiController.getLabelCompletedBottom().setText("Du hast 3 Versuche");
        } else {
            game.assetManager.playSound("sound-level-completed");
            game.assetManager.stopMusic();
            game.guiController.getLabelCompletedTop().setText("Semester "+gameController.getLevel());
            game.guiController.getLabelCompletedBottom().setText("Im "+gameController.getTrials() + ". Versuch bestanden!");
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(shown) {
            game.batch.begin();
            game.guiController.getActStage().act();
            game.guiController.getActStage().draw();
            game.batch.end();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        shown = false;
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

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            if (arg.equals("button-levelCompleted-next")) {
                if (GameConstants.DEV_MODE) {
                    System.out.println("BUTTON NEXT LEVEL CLICKED");
                }
                game.setScreen(new GameScreen(game));
                dispose();
            }
        }
    }
}
