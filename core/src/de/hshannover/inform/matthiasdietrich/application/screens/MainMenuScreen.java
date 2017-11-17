package de.hshannover.inform.matthiasdietrich.application.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import de.hshannover.inform.matthiasdietrich.Semester3Project;
import de.hshannover.inform.matthiasdietrich.ui.input.InputController;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by matthiasdietrich on 25.10.17.
 */
public class MainMenuScreen implements Screen, Observer {
    final Semester3Project game;
    private Stage stage;
    private InputController input;
    //private Sound sound = Gdx.audio.newSound(Gdx.files.internal("music/title.mp3"));


    public MainMenuScreen(final Semester3Project game) {
        this.game = game;
        input = InputController.getInstance();
        stage = game.guiController.getMainMenuStage();
        game.guiController.addMeAsObserver(this);
        //sound.play();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            if (arg.equals("button-start-game")) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
            if (arg.equals("button-exit-game")) {
                dispose();
                System.exit(0);
            }
            if (arg.equals("button-show-help")) {
                System.out.println("HILFE SCREEN ANZEIGEN");
            }
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(new Texture("images/title.png"), 0, 0);
        game.batch.end();

        game.batch.begin();
        stage.act();
        stage.draw();
        game.batch.end();

        if(input.isJump()) {
            //game.setScreen(new GameScreen(game));
            //sound.stop();
            //sound.dispose();
            //dispose();
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
