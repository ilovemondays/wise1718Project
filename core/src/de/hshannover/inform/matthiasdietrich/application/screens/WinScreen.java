package de.hshannover.inform.matthiasdietrich.application.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import de.hshannover.inform.matthiasdietrich.Semester3Project;
import de.hshannover.inform.matthiasdietrich.application.controller.GameController;
import java.util.Observable;
import java.util.Observer;

/**
 * Shows after a player beats the game
 */
public class WinScreen implements Screen, Observer {
    final Semester3Project game;
    private GameController gameController;

    public WinScreen(final Semester3Project game) {
        this.game = game;
        game.guiController.addMeAsObserver(this);
        gameController = GameController.getInstance();
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

        game.guiController.getLabelGameWinBottom().setText("Du hast das Studium in "+ gameController.getOverallTrials()+" Versuchen bestanden.");

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
                gameController.resetGameModel();
                game.setScreen(game.getMainMenuScreen());
                dispose();
            }
        }
    }
}
