package de.hshannover.inform.matthiasdietrich.application.models;

/**
 * Created by matthiasdietrich on 24.10.17.
 */
public class GameModel {
    private int actLevel;
    private int certificatesFound;
    private int trials;
    private static GameModel gameModel = null;

    private GameModel() {
        resetGame();
    }

    public static GameModel getInstance() {
        if (gameModel != null) {
            return gameModel;
        }
        gameModel= new GameModel();
        return gameModel;
    }

    public int getActLevel() {
        return actLevel;
    }

    public void setActLevel(int actLevel) {
        this.actLevel = actLevel;
    }

    public void increaseLevel() {
        actLevel++;
    }

    public void increaseCertificates() {
        certificatesFound++;
    }

    public int getCertificatesFound() {
        return certificatesFound;
    }

    public void setCertificatesFound(int certificatesFound) {
        this.certificatesFound = certificatesFound;
    }

    public int getTrials() {
        return trials;
    }

    public void setTrials(int trials) {
        this.trials = trials;
    }

    public void increaseTrials() {
        trials++;
    }

    public void resetGame() {
        setActLevel(1);
        setCertificatesFound(0);
        setTrials(0);
    }
}
