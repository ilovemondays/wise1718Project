package de.hshannover.inform.matthiasdietrich.application.models;

/**
 * Created by matthiasdietrich on 24.10.17.
 */
public class GameModel {
    private int actLevel;
    private int certificatesFound;
    private int trials;

    public int getActLevel() {
        return actLevel;
    }

    public void setActLevel(int actLevel) {
        this.actLevel = actLevel;
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
}
