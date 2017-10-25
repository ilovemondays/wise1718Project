package de.hshannover.inform.matthiasdietrich.application.models;

import com.badlogic.gdx.physics.box2d.World;

/**
 * The PlayerActor is controlled by the user
 * @author Matthias Dietrich (matthias.dietrich@stud.hs-hannover.de)
 */
public class PlayerActor extends BaseActor {
    private float isTired;
    private static PlayerActor player = null;

    private PlayerActor(World world) {
        super(world);
    }

    public static PlayerActor getInstance(World world) {
        if (player != null) {
            return player;
        }
        player = new PlayerActor(world);
        return player;
    }

    public float getIsTired() {
        return isTired;
    }

    public void setIsTired(float isTired) {
        this.isTired = isTired;
    }
}
