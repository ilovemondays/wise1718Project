package de.hshannover.inform.matthiasdietrich.application.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import de.hshannover.inform.matthiasdietrich.Semester3Project;
import de.hshannover.inform.matthiasdietrich.ui.input.InputController;

/**
 * Created by matthiasdietrich on 25.10.17.
 */
public class LevelCompletedScreen implements Screen {
    final Semester3Project game;
    private InputController input;

    public LevelCompletedScreen(final Semester3Project game) {
        this.game = game;
        input = InputController.getInstance();
        game.clearWorld();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.9f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.font.draw(game.batch, "Level Erfolgreich beendet. wow.", 100f, 400f);
        game.font.draw(game.batch, "Start: Nochmal!", 100, 350f);
        game.font.draw(game.batch, "Ende: Drücke CMD+Q ;)", 100, 300f);
        game.batch.end();

        if(input.isJump()) {
            game.setScreen(new GameScreen(game));
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
