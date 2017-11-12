package de.hshannover.inform.matthiasdietrich.application.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * The PlayerActor is controlled by the user
 * @author Matthias Dietrich (matthias.dietrich@stud.hs-hannover.de)
 */
public class PlayerActor extends BaseActor {
    private float tired;
    private static PlayerActor player = null;
    private Vector2 startingPoint;

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

    public float getTired() {
        return tired;
    }

    public void setTired(float tired) {
        this.tired = tired;
    }

    public Vector2 getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(Vector2 startingPoint) {
        this.startingPoint = startingPoint;
    }
}
