package de.hshannover.inform.matthiasdietrich.application.controller;

import com.badlogic.gdx.physics.box2d.*;
import de.hshannover.inform.matthiasdietrich.application.actors.Player;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;
import de.hshannover.inform.matthiasdietrich.ui.input.InputController;

/**
 * Created by matthiasdietrich on 14.10.17.
 */
public class PlayerController implements ContactListener {
    private int playerIsOnGround = 0;
    private int playerCanMoveLeft = 0;
    private int playerCanMoveRight = 0;
    private Player player;
    private InputController inputController;

    /**
     * Checks starting collision with player sensors
     * @param contact
     */
    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if( a.getUserData() != null && a.getUserData().equals("ground") ||
            b.getUserData() != null && b.getUserData().equals("ground")) {
            playerIsOnGround++;

        }
        if( a.getUserData() != null && a.getUserData().equals("left") ||
            b.getUserData() != null && b.getUserData().equals("left")) {
            playerCanMoveLeft++;
        }
        if( a.getUserData() != null && a.getUserData().equals("right") ||
            b.getUserData() != null && b.getUserData().equals("right")) {
            playerCanMoveRight++;
        }
    }

    /**
     * Checks ending collision with player sensors
     * @param contact
     */
    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if( a.getUserData() != null && a.getUserData().equals("ground") ||
            b.getUserData() != null && b.getUserData().equals("ground")) {
            playerIsOnGround--;
        }
        if( a.getUserData() != null && a.getUserData().equals("left") ||
            b.getUserData() != null && b.getUserData().equals("left")) {
            playerCanMoveLeft--;
        }
        if( a.getUserData() != null && a.getUserData().equals("right") ||
            b.getUserData() != null && b.getUserData().equals("right")) {
            playerCanMoveRight--;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * checks if player is on ground
     * @return
     */
    private int isPlayerIsOnGround() {
        return playerIsOnGround;
    }

    private int isPlayerCanMoveLeft() {
        return playerCanMoveLeft;
    }

    private int isPlayerCanMoveRight() {
        return playerCanMoveRight;
    }

    /**
     * moves the player to the left
     */
    private void moveLeft() {
        getPlayer().getBody().applyLinearImpulse(-0.12f, 0, 0, 0, true);
        //getBody().applyForce(-10f, -2f, 1f, 0f, true);
    }

    /**
     * moves the player to the right
     */
    private void moveRight() {
        getPlayer().getBody().applyLinearImpulse(0.12f, 0, 0, 0, true);
        //getBody().applyForce(10f, -2f, 1f, 0f, true);
    }

    /**
     * moves the player up
     */
    public void jump() {
        getPlayer().getBody().applyForce(0f, 23f, 0f, 1f, true);
    }

    public void setInputController(InputController inputController) {
        this.inputController = inputController;
    }

    public void updatePlayer() {
        updatePlayerMovement();
        getPlayer().update();
    }

    private void updatePlayerMovement() {
        if (inputController.isLeft() && player.getBody().getLinearVelocity().x > -GameConstants.MAX_VELOCITY) {
            if(isPlayerCanMoveLeft() <= 0) {
                moveLeft();
            }
        }
        if (inputController.isRight() && player.getBody().getLinearVelocity().x < GameConstants.MAX_VELOCITY) {
            if(isPlayerCanMoveRight() <= 0) {
                moveRight();
            }
        }
        if (inputController.isJump()) {
            if(isPlayerIsOnGround() > 0) {
                jump();
            }
        }
    }
}
