package de.hshannover.inform.matthiasdietrich.application.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.utils.Array;
import de.hshannover.inform.matthiasdietrich.Semester3Project;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;
import de.hshannover.inform.matthiasdietrich.application.controller.GameController;
import de.hshannover.inform.matthiasdietrich.ui.input.InputController;

import java.util.Observable;
import java.util.Observer;

/**
 * Is displayed after each level
 */
public class LevelCompletedScreen implements Screen, Observer {
    final Semester3Project game;
    private InputController input;
    private boolean shown = false;
    private GameController gameController = GameController.getInstance();

    private ParticleEffect konfetti = null;
    private ParticleEffectPool konfettiPool;
    private Array<ParticleEffectPool.PooledEffect> kenfettis = new Array();
    private ParticleEffectPool.PooledEffect pooledKonfetti;

    public LevelCompletedScreen(final Semester3Project game) {
        this.game = game;
        input = InputController.getInstance();
        game.guiController.addMeAsObserver(this);
        konfetti = new ParticleEffect();
        konfetti.load(Gdx.files.internal("particles/konfetti.particle"), Gdx.files.internal("particles"));
        konfetti.scaleEffect(0.005f);
        konfettiPool = new ParticleEffectPool(konfetti, 1,9);
    }

    @Override
    public void show() {
        if (GameConstants.DEV_MODE) {
            System.out.println("show level completed screen");
        }
        shown = true;
        game.guiController.setLevelCompletedStage();
        kenfettis.clear();

        if (gameController.getLevel() == 0) {
            game.guiController.getLabelCompletedTop().setText("Semester 1");
            game.guiController.getLabelCompletedBottom().setText("Du hast 3 Versuche");
        } else {
            game.assetManager.playSound("sound-level-completed");
            game.assetManager.stopMusic();
            game.guiController.getLabelCompletedTop().setText("Semester "+gameController.getLevel());
            game.guiController.getLabelCompletedBottom().setText("Im "+gameController.getTrials() + ". Versuch bestanden!");
        }

        game.world.step(1/60f, 1, 1);
        game.clearWorld();
    }

    @Override
    public void render(float delta) {
        game.world.step(1/60f, 1, 1);
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        // Update and draw effects:
        for (int i = kenfettis.size - 1; i >= 0; i--) {
            ParticleEffectPool.PooledEffect effect = kenfettis.get(i);
            effect.draw(game.batch, Gdx.graphics.getDeltaTime());
            if (effect.isComplete()) {
                effect.free();
                kenfettis.removeIndex(i);
            }
        }

        if(shown) {
            game.guiController.getActStage().act();
            game.guiController.getActStage().draw();

            if(input.isJump() && kenfettis.size == 0 && gameController.getLevel() >= 0) {
                pooledKonfetti = konfettiPool.obtain();
                pooledKonfetti.setPosition(game.batch.getProjectionMatrix().getScaleX()+0.5f, game.batch.getProjectionMatrix().getScaleY() + 1f);
                kenfettis.add(pooledKonfetti);
                //game.assetManager.playSound("sound-cheering");
                game.assetManager.playSound("sound-party-horn");
            }
        }

        game.batch.end();
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
                game.setScreen(game.getGameScreen());
                dispose();
            }
        }
    }
}
