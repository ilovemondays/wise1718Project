package de.hshannover.inform.matthiasdietrich.application.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import de.hshannover.inform.matthiasdietrich.Semester3Project;
import de.hshannover.inform.matthiasdietrich.ui.input.InputController;

/**
 * Created by matthiasdietrich on 25.10.17.
 */
public class MainMenuScreen implements Screen {
    final Semester3Project game;

    private InputController input;
    private Sound sound = Gdx.audio.newSound(Gdx.files.internal("music/title.mp3"));


    public MainMenuScreen(final Semester3Project game) {
        this.game = game;
        input = InputController.getInstance();
        sound.play();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.9f, 0.3f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(new Texture("images/title.png"), 0, 0);
        game.fontBold.draw(game.batch, "Study Race", 100f, 380);
        game.fontLight.draw(game.batch, "Start: Drücke Leertaste", 100, 300f);
        game.fontLight.draw(game.batch, "Ende: Drücke CMD+Q ;)", 100, 250f);
        game.batch.end();

        if(input.isJump()) {
            game.setScreen(new GameScreen(game));
            sound.stop();
            sound.dispose();
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
