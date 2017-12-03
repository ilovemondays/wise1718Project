package de.hshannover.inform.matthiasdietrich.application.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.MassData;
import de.hshannover.inform.matthiasdietrich.application.models.PlayerActor;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;
import de.hshannover.inform.matthiasdietrich.application.models.ProjectileActor;
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
    private boolean isHittingProjectile = false;
    private GameConstants.ActAnimation actAnimation = GameConstants.ActAnimation.IDLE;
    private GameConstants.Direction actDirection = GameConstants.Direction.LEFT;
    private boolean isInTrap = false;

    private PlayerController() {
    }

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

    public GameConstants.ActAnimation getActAnimation () {
        return actAnimation;
    }

    public void setActAnimation (GameConstants.ActAnimation actAnimation) {
        this.actAnimation = actAnimation;
    }

    public GameConstants.Direction getActDirection () {
        return actDirection;
    }

    public void setActDirection (GameConstants.Direction actDirection) {
        this.actDirection = actDirection;
    }

    public boolean isInTrap () {
        return isInTrap;
    }

    public void setInTrap (boolean inTrap) {
        isInTrap = inTrap;
    }

    /**
     * moves the player to the left
     */
    private void moveLeft() {
        getPlayer().getBody().applyLinearImpulse(-0.07f, 0, 0, 0, true);
        if (playerIsOnGround > 0) {
            actAnimation = GameConstants.ActAnimation.RUN;
        }
        actDirection = GameConstants.Direction.LEFT;
    }

    /**
     * moves the player to the right
     */
    private void moveRight() {
        getPlayer().getBody().applyLinearImpulse(0.07f, 0, 0, 0, true);
        if (playerIsOnGround > 0) {
            actAnimation = GameConstants.ActAnimation.RUN;
        }
        actDirection = GameConstants.Direction.RIGHT;
    }

    /**
     * moves the player up
     */
    public void jump() {
        getPlayer().getBody().applyForce(0f, 20f, 0f, 1f, true);
        actAnimation = GameConstants.ActAnimation.JUMP;
    }

    public void setInput(InputController input) {
        this.input = input;
    }

    public void updatePlayer() {
        updatePlayerMovement();
        player.update();
        if(!player.getBody().isAwake()) {
            if (playerIsOnGround > 0) {
                actAnimation = GameConstants.ActAnimation.IDLE;
            }
        }
    }

    public boolean playerIsTired() {
        return (player.getTired() >= 1);
    }

    private void updatePlayerMovement() {
        if (input.isLeft() && player.getBody().getLinearVelocity().x > -GameConstants.MAX_VELOCITY) {
            if(playerCanMoveLeft <= 0) {
                moveLeft();
            }
        }
        if (input.isRight() && player.getBody().getLinearVelocity().x < GameConstants.MAX_VELOCITY) {
            if(playerCanMoveRight <= 0) {
                moveRight();
            }
        }
        if (input.isJump() && player.getBody().getLinearVelocity().y < GameConstants.MAX_JUMP_VELOCITY) {
            if (playerIsOnGround > 0) {
                jump();
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {

        if(arg instanceof String) {
            // TRAPS
            if (arg.equals("player-hit-trap")) {
                isInTrap = true;
            }
            if (arg.equals("player-hit-trap-END")) {
                if(isInTrap) {
                    isInTrap = false;
                    player.setTired(1);
                }
            }
            if (arg.equals("player-hit-by-projectile-START")) {
                isHittingProjectile = true;
            }
            if (arg.equals("player-hit-by-projectile-END")) {
                if(isHittingProjectile == true) {
                    isHittingProjectile = false;
                    player.setTired(player.getTired() + 0.2f);
                }
            }
            // MOVEMENT
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
