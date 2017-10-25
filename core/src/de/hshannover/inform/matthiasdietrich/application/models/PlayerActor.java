package de.hshannover.inform.matthiasdietrich.application.models;

import com.badlogic.gdx.physics.box2d.World;

/**
 * The PlayerActor is controlled by the user
 * @author Matthias Dietrich (matthias.dietrich@stud.hs-hannover.de)
 */
public class PlayerActor extends BaseActor {
    float isTired;

    public PlayerActor(World world) {
        super(world);
    }

    public float getIsTired() {
        return isTired;
    }

    public void setIsTired(float isTired) {
        this.isTired = isTired;
    }
}
