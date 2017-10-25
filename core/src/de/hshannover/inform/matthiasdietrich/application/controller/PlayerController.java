package de.hshannover.inform.matthiasdietrich.application.controller;

import de.hshannover.inform.matthiasdietrich.application.models.PlayerActor;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;
import de.hshannover.inform.matthiasdietrich.ui.input.InputController;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by matthiasdietrich on 14.10.17.
 */
public class PlayerController implements Observer {
    private int playerIsOnGround = 0;
    private int playerCanMoveLeft = 0;
    private int playerCanMoveRight = 0;
    private PlayerActor player;
    private InputController input;
    private static PlayerController playerController = null;

    private PlayerController() {}

    public static PlayerController getInstance() {
        if(playerController != null) {
            return playerController;
        }
        playerController = new PlayerController();
        return playerController;
    }

    public PlayerActor getPlayer() {
        return player;
    }

    public void setPlayer(PlayerActor player) {
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
    }

    /**
     * moves the player to the right
     */
    private void moveRight() {
        getPlayer().getBody().applyLinearImpulse(0.12f, 0, 0, 0, true);
    }

    /**
     * moves the player up
     */
    public void jump() {
        getPlayer().getBody().applyForce(0f, 23f, 0f, 1f, true);
    }

    public void setInput(InputController input) {
        this.input = input;
    }

    public void updatePlayer() {
        updatePlayerMovement();
        getPlayer().update();
    }

    private void updatePlayerMovement() {
        if (input.isLeft() && player.getBody().getLinearVelocity().x > -GameConstants.MAX_VELOCITY) {
            if(isPlayerCanMoveLeft() <= 0) {
                moveLeft();
            }
        }
        if (input.isRight() && player.getBody().getLinearVelocity().x < GameConstants.MAX_VELOCITY) {
            if(isPlayerCanMoveRight() <= 0) {
                moveRight();
            }
        }
        if (input.isJump()) {
            if(isPlayerIsOnGround() > 0) {
                jump();
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof String) {
            if (arg.equals("playerOnGround")) {
                playerIsOnGround++;
            }
            if (arg.equals("playerInAir")) {
                playerIsOnGround--;
            }
            if (arg.equals("playerCanMoveLeft")) {
                playerCanMoveLeft++;
            }
            if (arg.equals("playerCanMoveRight")) {
                playerCanMoveRight++;
            }
            if (arg.equals("playerCannotMoveLeft")) {
                playerCanMoveLeft--;
            }
            if (arg.equals("playerCannotMoveRight")) {
                playerCanMoveRight--;
            }
        }
    }
}
