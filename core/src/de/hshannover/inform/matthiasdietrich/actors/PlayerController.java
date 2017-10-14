package de.hshannover.inform.matthiasdietrich.actors;

import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by matthiasdietrich on 14.10.17.
 */
public class PlayerController implements ContactListener {
    private boolean playerIsOnGround;
    private boolean playerCanMoveLeft = true;
    private boolean playerCanMoveRight = true;

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if( a.getUserData() != null && a.getUserData().equals("ground") ||
            b.getUserData() != null && b.getUserData().equals("ground")) {
            playerIsOnGround = true;
        }
        if( a.getUserData() != null && a.getUserData().equals("left") ||
            b.getUserData() != null && b.getUserData().equals("left")) {
            playerCanMoveLeft = false;
        }
        if( a.getUserData() != null && a.getUserData().equals("right") ||
            b.getUserData() != null && b.getUserData().equals("right")) {
            playerCanMoveRight = false;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if( a.getUserData() != null && a.getUserData().equals("ground") ||
            b.getUserData() != null && b.getUserData().equals("ground")) {
            playerIsOnGround = false;
        }
        if( a.getUserData() != null && a.getUserData().equals("left") ||
            b.getUserData() != null && b.getUserData().equals("left")) {
            playerCanMoveLeft = true;
        }
        if( a.getUserData() != null && a.getUserData().equals("right") ||
            b.getUserData() != null && b.getUserData().equals("right")) {
            playerCanMoveRight = true;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean isPlayerIsOnGround() {
        return playerIsOnGround;
    }

    public boolean isPlayerCanMoveLeft() {
        return playerCanMoveLeft;
    }

    public boolean isPlayerCanMoveRight() {
        return playerCanMoveRight;
    }
}
