package de.hshannover.inform.matthiasdietrich.application.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by matthiasdietrich on 24.10.17.
 */
public class GameController implements Observer {
    private World world;
    private static ArrayList<Body> bodiesToDestroy;

    public GameController() {
        Box2D.init();
        world = new World(new Vector2(0, -10f), true);
        bodiesToDestroy = new ArrayList<Body>();
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

    public void setBodiesToDestroy(ArrayList<Body> bodiesToDestroy) {
        this.bodiesToDestroy = bodiesToDestroy;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Fixture) {
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
}
