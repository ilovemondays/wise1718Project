package de.hshannover.inform.matthiasdietrich.application.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import de.hshannover.inform.matthiasdietrich.Semester3Project;
import de.hshannover.inform.matthiasdietrich.ui.input.InputController;

import java.util.Observable;
import java.util.Observer;

/**
 * "Screen is shown when the game is over" - Capt. Obvious
 */
public class GameOverScreen implements Screen, Observer {
    final Semester3Project game;
    private InputController input;

    public GameOverScreen(final Semester3Project game) {
        this.game = game;
        input = InputController.getInstance();
        game.guiController.addMeAsObserver(this);
    }

    @Override
    public void show() {
        game.guiController.setGameOverStage();
        game.assetManager.stopMusic();
        game.assetManager.playSound("sound-game-over");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.guiController.getActStage().act();
        game.guiController.getActStage().draw();
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

    @Override
    public void update (Observable o, Object arg) {
        if (arg instanceof String) {
            if (arg.equals("button-gameover")) {
                game.setScreen(game.getMainMenuScreen());
                dispose();
            }
        }
    }
}
