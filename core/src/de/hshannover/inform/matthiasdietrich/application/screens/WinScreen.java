package de.hshannover.inform.matthiasdietrich.application.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import de.hshannover.inform.matthiasdietrich.Semester3Project;
import de.hshannover.inform.matthiasdietrich.ui.input.InputController;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by matthiasdietrich on 25.10.17.
 */
public class WinScreen implements Screen, Observer {
    final Semester3Project game;

    public WinScreen(final Semester3Project game) {
        this.game = game;
        game.guiController.addMeAsObserver(this);
    }

    @Override
    public void show() {
        game.guiController.setGameWinStage();
        game.assetManager.stopMusic();
        game.assetManager.playSound("sound-game-win");
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
            if (arg.equals("button-gamewin")) {
                game.setScreen(game.getMainMenuScreen());
                dispose();
            }
        }
    }
}
