package de.hshannover.inform.matthiasdietrich.actors;

import com.badlogic.gdx.physics.box2d.World;

/**
 * The Player is controlled by the user
 * @author Matthias Dietrich (matthias.dietrich@stud.hs-hannover.de)
 */
public class Player extends BaseActor {

    public Player(World world) {
        super(world);
    }

    public void moveLeft() {
        getBody().applyLinearImpulse(-0.3f, 0, 0, 0, true);
        //getBody().applyForce(-10f, -2f, 1f, 0f, true);

    }

    public void moveRight() {
        getBody().applyLinearImpulse(0.3f, 0, 0, 0, true);
        //getBody().applyForce(10f, -2f, 1f, 0f, true);

    }

    public void jump() {
        getBody().applyForce(0f, 40f, 0f, 1f, true);

    }
}
