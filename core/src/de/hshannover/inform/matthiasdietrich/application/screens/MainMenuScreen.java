package de.hshannover.inform.matthiasdietrich.application.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import de.hshannover.inform.matthiasdietrich.Semester3Project;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by matthiasdietrich on 25.10.17.
 */
public class MainMenuScreen implements Screen, Observer {
    final Semester3Project game;

    public MainMenuScreen(final Semester3Project game) {
        this.game = game;
        game.guiController.addMeAsObserver(this);
        game.guiController.setMainMenuStage();
        game.assetManager.playMusic("music-title");
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            if (arg.equals("button-start-game")) {
                game.setScreen(game.getLevelCompletedScreen());
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
       game.batch.draw(new Texture("images/title.jpeg"), 0, 0);
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


}
