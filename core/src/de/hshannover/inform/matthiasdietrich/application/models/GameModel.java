package de.hshannover.inform.matthiasdietrich.application.models;

/**
 * Holds all data for game progression like level, how many trials, certificates found
 */
public class GameModel {
    private int actLevel = 0;
    private int certificatesFound;
    private int trials = 1;
    private int overallTrials = -1;
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

    public int getOverallTrials () {
        return overallTrials;
    }

    public void setOverallTrials (int overallTrials) {
        this.overallTrials = overallTrials;
    }

    public void increaseLevel() {
        actLevel++;
        increaseOverallTrials();
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
        increaseOverallTrials();
    }
    public void increaseOverallTrials() {
        overallTrials++;
    }

    /**
     * sets all data back to default/start
     */
    public void resetGame() {
        setCertificatesFound(0);
        setTrials(1);
        setActLevel(0);
        setOverallTrials(-1);
    }
}
