package de.hshannover.inform.matthiasdietrich.ui.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.Random;

public class AssetManager {
    private Random random = new Random();

    // MUSIC
    private Sound musicTitle;
    private Sound musicStage1;
    private Sound musicStage2;
    private Sound musicStage3;
    private Sound musicStage6;
    private Sound actMusic = null;

    // SOUNDS
    private Sound levelCompleted;
    private Sound certificate;
    private Sound gameover;

    public AssetManager() {
        musicTitle = Gdx.audio.newSound(Gdx.files.internal("music/title.mp3"));
        musicStage1 = Gdx.audio.newSound(Gdx.files.internal("music/stage-1.mp3"));
        musicStage2 = Gdx.audio.newSound(Gdx.files.internal("music/stage-2.mp3"));
        musicStage3 = Gdx.audio.newSound(Gdx.files.internal("music/stage-3.mp3"));
        musicStage6 = Gdx.audio.newSound(Gdx.files.internal("music/stage-6.mp3"));

        levelCompleted = Gdx.audio.newSound(Gdx.files.internal("sounds/levelcompleted.wav"));
        certificate = Gdx.audio.newSound(Gdx.files.internal("sounds/certificate.wav"));
        gameover = Gdx.audio.newSound(Gdx.files.internal("sounds/gameover.wav"));
    }

    public void playMusic (String music) {
        if (actMusic != null) {
            actMusic.stop();
        }
        if (music.equals("music-title")) {
            actMusic = musicTitle;
        }
        if (music.equals("music-stage-1")) {
            actMusic = musicStage1;
        }
        if (music.equals("music-stage-2")) {
            actMusic = musicStage2;
        }
        if (music.equals("music-stage-3")) {
            actMusic = musicStage3;
        }
        if (music.equals("music-stage-6")) {
            actMusic = musicStage6;
        }

        actMusic.loop();
    }

    public void stopMusic() {
        if (actMusic != null)
        actMusic.stop();
    }

    public void playSound(String sound) {
        if (sound.equals("sound-certificate")) {
            certificate.play();
        }
        if (sound.equals("sound-level-completed")) {
            levelCompleted.play();
        }
        if (sound.equals("sound-game-over")) {
            gameover.play();
        }
    }

}
