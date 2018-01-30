package de.hshannover.inform.matthiasdietrich.ui.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;

import java.util.Random;

/**
 * Stores assets which can be accessed global
 */
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
    private Sound soundLevelCompleted;
    private Sound soundCertificate;
    private Sound soundGameover;
    private Sound soundBoom;
    private Sound soundWhistle;
    private Sound soundCheering;
    private Sound soundPartyHorn;

    // TEXTURES
    private Sprite imageTitle;

    public AssetManager() {
        musicTitle = Gdx.audio.newSound(Gdx.files.internal("music/title.mp3"));
        musicStage1 = Gdx.audio.newSound(Gdx.files.internal("music/stage-1.mp3"));
        musicStage2 = Gdx.audio.newSound(Gdx.files.internal("music/stage-2.mp3"));
        musicStage3 = Gdx.audio.newSound(Gdx.files.internal("music/stage-3.mp3"));
        musicStage6 = Gdx.audio.newSound(Gdx.files.internal("music/stage-6.mp3"));

        soundLevelCompleted = Gdx.audio.newSound(Gdx.files.internal("sounds/levelcompleted.wav"));
        soundCertificate = Gdx.audio.newSound(Gdx.files.internal("sounds/certificate.wav"));
        soundGameover = Gdx.audio.newSound(Gdx.files.internal("sounds/gameover.wav"));
        soundBoom = Gdx.audio.newSound(Gdx.files.internal("sounds/boom.wav"));
        soundWhistle = Gdx.audio.newSound(Gdx.files.internal("sounds/whistle.mp3"));
        soundCheering = Gdx.audio.newSound(Gdx.files.internal("sounds/cheering.mp3"));
        soundPartyHorn = Gdx.audio.newSound(Gdx.files.internal("sounds/party-horn.mp3"));

        imageTitle = new Sprite(new Texture("images/title.jpeg"));
    }

    public void playMusic (String music) {
        if (GameConstants.DEV_MODE || GameConstants.NO_MUSIC) {
            return;
        }
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
        if (GameConstants.DEV_MODE) {
            return;
        }
        if (sound.equals("sound-certificate")) {
            soundCertificate.play();
        }
        if (sound.equals("sound-level-completed")) {
            soundLevelCompleted.play();
        }
        if (sound.equals("sound-game-over")) {
            soundGameover.play();
        }
        if (sound.equals("sound-boom")) {
            soundBoom.play();
        }
        if (sound.equals("sound-whistle")) {
            soundWhistle.play();
        }
        if (sound.equals("sound-cheering")) {
            soundCheering.play();
        }
        if (sound.equals("sound-party-horn")) {
            soundPartyHorn.play();
        }
    }

    public Sprite getImage(String img) {
        if (img.equals("image-title")) {
            imageTitle.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            return imageTitle;
        }
        return null;
    }

}
