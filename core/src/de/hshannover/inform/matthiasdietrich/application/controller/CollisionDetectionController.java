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
        checkCertificatesStart(a);
        checkCertificatesStart(b);

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
        checkProjectiles(a,b);
        if(checkProjectilesWithPlayer(a,b) || checkProjectilesWithPlayer(b,a)) {
            setChanged();
            notifyObservers("player-hit-by-projectile-START");
        }

        // PLAYER MOVEMENT
        if( a.getUserData() != null && a.getUserData().equals("player-ground") ||
                b.getUserData() != null && b.getUserData().equals("player-ground")) {
            setChanged();
            notifyObservers("playerOnGround");

        }
        if( a.getUserData() != null && a.getUserData().equals("player-left") ||
                b.getUserData() != null && b.getUserData().equals("player-left")) {
            setChanged();
            notifyObservers("playerCanMoveLeft");
        }
        if( a.getUserData() != null && a.getUserData().equals("player-right") ||
                b.getUserData() != null && b.getUserData().equals("player-right")) {
            setChanged();
            notifyObservers("playerCanMoveRight");
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        // MOVEMENT
        if( a.getUserData() != null && a.getUserData().equals("player-ground") ||
                b.getUserData() != null && b.getUserData().equals("player-ground")) {
            setChanged();
            notifyObservers("playerInAir");
        }
        if( a.getUserData() != null && a.getUserData().equals("player-left") ||
                b.getUserData() != null && b.getUserData().equals("player-left")) {
            setChanged();
            notifyObservers("playerCannotMoveLeft");
        }
        if( a.getUserData() != null && a.getUserData().equals("player-right") ||
                b.getUserData() != null && b.getUserData().equals("player-right")) {
            setChanged();
            notifyObservers("playerCannotMoveRight");
        }

        // CERTIFICATES
        checkCertificatesEnd(a);
        checkCertificatesEnd(b);

        // PROJECTILES
        checkProjectiles(a,b);
        if(checkProjectilesWithPlayer(a,b) || checkProjectilesWithPlayer(b,a)) {
            setChanged();
            notifyObservers("player-hit-by-projectile-END");
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private void checkCertificatesStart(Fixture obj1) {
        if( obj1.getUserData() instanceof CertificateModel) {
            setChanged();
            notifyObservers(obj1);
            setChanged();
            notifyObservers("certificate-collected-START");
        }
    }

    private void checkCertificatesEnd(Fixture obj) {
        if( obj.getUserData() instanceof CertificateModel) {
            setChanged();
            notifyObservers("certificate-collected-END");
        }
    }

    private void checkProjectiles(Fixture obj1, Fixture obj2) {
        if(obj1.getUserData() instanceof ProjectileActor || obj2.getUserData() instanceof ProjectileActor) {
            // tell projectile controller
            setChanged();
            notifyObservers(obj1);
            setChanged();
            notifyObservers(obj2);
        }
    }
    private boolean checkProjectilesWithPlayer(Fixture obj1, Fixture obj2) {
        if (obj2.getUserData() instanceof ProjectileActor) {
            if(obj1.getUserData() instanceof PlayerActor ||
                    (obj1.getUserData() instanceof String && obj1.getUserData().toString().contains("player"))) {
                return true;
            }
        }
        return false;
    }
}
