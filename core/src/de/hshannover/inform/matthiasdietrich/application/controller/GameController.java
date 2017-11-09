package de.hshannover.inform.matthiasdietrich.application.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;
import de.hshannover.inform.matthiasdietrich.application.models.GameModel;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by matthiasdietrich on 24.10.17.
 */
public class GameController implements Observer {
    private static World world;
    private static ArrayList<Body> bodiesToDestroy;
    private static GameController gameController = null;
    private GameModel gameModel;

    private GameController() {
        world = new World(new Vector2(0, -10f), true);
    }

    public static GameController getInstance() {
        if (gameController != null) {
            return gameController;
        }
        gameController = new GameController();
        return gameController;
    }

    public void resetGameModel() {
        gameModel.resetGame();
    }

    public void startWorld() {
        bodiesToDestroy = new ArrayList<Body>();
        gameModel = GameModel.getInstance();
        gameModel.resetGame();
    }


    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public ArrayList<Body> getBodiesToDestroy() {
        return bodiesToDestroy;
    }

    private void setBodiesToDestroy(ArrayList<Body> bodiesToDestroy) {
        this.bodiesToDestroy = bodiesToDestroy;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Fixture) {
            if (((Fixture)arg).getUserData().equals("certificate")) {
                gameModel.setCertificatesFound(gameModel.getCertificatesFound() + 1);
            }
            Body b = ((Fixture) arg).getBody();
            if(!bodiesToDestroy.contains(b)) {
                bodiesToDestroy.add(b);
            }
        }
    }

    public void setContactListener(ContactListener contactListener) {
        getWorld().setContactListener(contactListener);
    }

    public void destroyBodies() {
        for(Body b : getBodiesToDestroy()) {
            getWorld().destroyBody(b);
        }
        getBodiesToDestroy().clear();
    }

    public boolean checkWinCondition() {
        return (gameModel.getCertificatesFound() >= GameConstants.WIN_CONDITION);
    }
}
