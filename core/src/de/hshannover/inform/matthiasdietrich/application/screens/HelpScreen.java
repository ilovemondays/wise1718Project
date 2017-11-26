package de.hshannover.inform.matthiasdietrich.application.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import de.hshannover.inform.matthiasdietrich.Semester3Project;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;
import de.hshannover.inform.matthiasdietrich.ui.input.InputController;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by matthiasdietrich on 25.10.17.
 */
public class HelpScreen implements Screen, Observer {
    final Semester3Project game;
    private InputController input;
    private Sprite backgroundImage;

    public HelpScreen(final Semester3Project game) {
        this.game = game;
        input = InputController.getInstance();
        game.guiController.addMeAsObserver(this);
    }

    @Override
    public void show() {
        game.guiController.setHelpScreenStage();
        backgroundImage = game.assetManager.getImage("image-title");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.4f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        backgroundImage.draw(game.batch);
        game.batch.end();

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
            if (arg.equals("button-help-back")) {
                game.setScreen(game.getMainMenuScreen());
                dispose();
            }
        }
    }
}
