package de.hshannover.inform.matthiasdietrich.application.controller;

import com.badlogic.gdx.physics.box2d.*;
import de.hshannover.inform.matthiasdietrich.application.models.CertificateModel;
import de.hshannover.inform.matthiasdietrich.application.models.PlayerActor;
import de.hshannover.inform.matthiasdietrich.application.models.ProjectileActor;

import java.util.Observable;

/**
 * Created by matthiasdietrich on 25.10.17.
 */
public class CollisionDetectionController extends Observable implements ContactListener {

    public CollisionDetectionController() {
        this.addObserver(GameController.getInstance());
        this.addObserver(PlayerController.getInstance());
        this.addObserver(ProjectileController.getInstance());
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        // CERTIFICATES
        if( a.getUserData() != null && a.getUserData() instanceof CertificateModel) {
            setChanged();
            notifyObservers(a);
        }
        if( b.getUserData() != null && b.getUserData() instanceof CertificateModel) {
            setChanged();
            notifyObservers(b);
        }

        // TRAP
        if( a.getUserData() != null && a.getUserData().equals("trap")) {
            setChanged();
            notifyObservers("player-hit-trap");
        }
        if( b.getUserData() != null && b.getUserData().equals("trap")) {
            setChanged();
            notifyObservers("player-hit-trap");
        }

        // PROJECTILES
        if(a.getUserData() instanceof ProjectileActor) {
            // tell projectile controller
            setChanged();
            notifyObservers(a);
            if(b.getUserData() instanceof PlayerActor) {
                // tell player controller
                setChanged();
                notifyObservers("player-hit-by-projectile");
            }
        }
        if(b.getUserData() instanceof ProjectileActor) {
            // tell projectile controller
            setChanged();
            notifyObservers(b);
            if(a.getUserData() instanceof PlayerActor) {
                // tell player controller
                setChanged();
                notifyObservers("player-hit-by-projectile");
            }
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
