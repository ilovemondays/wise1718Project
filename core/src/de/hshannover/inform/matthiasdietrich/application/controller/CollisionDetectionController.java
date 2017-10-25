package de.hshannover.inform.matthiasdietrich.application.controller;

import com.badlogic.gdx.physics.box2d.*;

import java.util.Observable;

/**
 * Created by matthiasdietrich on 25.10.17.
 */
public class CollisionDetectionController extends Observable implements ContactListener {

    public CollisionDetectionController() {
        this.addObserver(GameController.getInstance());
        this.addObserver(PlayerController.getInstance());
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        // CERTIFICATES
        if( a.getUserData() != null && a.getUserData().equals("certificate")) {
            setChanged();
            notifyObservers(a);
        }
        if( b.getUserData() != null && b.getUserData().equals("certificate")) {
            setChanged();
            notifyObservers(b);
        }

        // SPIKES
        if( a.getUserData() != null && a.getUserData().equals("spike") ||
                b.getUserData() != null && b.getUserData().equals("spike")) {
                System.out.println("AUA!!1");
        }

        // PLAYER MOVEMENT
        if( a.getUserData() != null && a.getUserData().equals("ground") ||
                b.getUserData() != null && b.getUserData().equals("ground")) {
            setChanged();
            notifyObservers("playerOnGround");

        }
        if( a.getUserData() != null && a.getUserData().equals("left") ||
                b.getUserData() != null && b.getUserData().equals("left")) {
            setChanged();
            notifyObservers("playerCanMoveLeft");
        }
        if( a.getUserData() != null && a.getUserData().equals("right") ||
                b.getUserData() != null && b.getUserData().equals("right")) {
            setChanged();
            notifyObservers("playerCanMoveRight");
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        // MOVEMENT
        if( a.getUserData() != null && a.getUserData().equals("ground") ||
                b.getUserData() != null && b.getUserData().equals("ground")) {
            setChanged();
            notifyObservers("playerInAir");
        }
        if( a.getUserData() != null && a.getUserData().equals("left") ||
                b.getUserData() != null && b.getUserData().equals("left")) {
            setChanged();
            notifyObservers("playerCannotMoveLeft");
        }
        if( a.getUserData() != null && a.getUserData().equals("right") ||
                b.getUserData() != null && b.getUserData().equals("right")) {
            setChanged();
            notifyObservers("playerCannotMoveRight");
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
