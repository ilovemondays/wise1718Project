package de.hshannover.inform.matthiasdietrich.application.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;

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

    public void spawn() {
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(new Vector2(getX(), getY()));

        body = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(GameConstants.TILE_WIDTH/2.5f, GameConstants.TILE_WIDTH/2.5f);
        circle.setRadius(GameConstants.TILE_WIDTH/2.5f);

        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = circle;
        fixDef.density = 0.5f;
        fixDef.restitution = 0;
        fixDef.friction = 1f;
        body.createFixture(fixDef).setUserData(this);

        // Ground Sensor
        shape.setAsBox(GameConstants.TILE_WIDTH/6f, GameConstants.TILE_WIDTH/9f, new Vector2(0, -GameConstants.TILE_WIDTH/2.2f), 0);
        fixDef.shape = shape;
        fixDef.isSensor = true;
        body.createFixture(fixDef).setUserData("player-ground");

        // Left Sensor
        shape.setAsBox(GameConstants.TILE_WIDTH/40f, GameConstants.TILE_WIDTH/10f, new Vector2(-GameConstants.TILE_WIDTH/2.4f, 0), 0);
        fixDef.shape = shape;
        fixDef.isSensor = true;
        body.createFixture(fixDef).setUserData("player-left");

        // Right Sensor
        shape.setAsBox(GameConstants.TILE_WIDTH/40f, GameConstants.TILE_WIDTH/10f, new Vector2(GameConstants.TILE_WIDTH/2.4f, 0), 0);
        fixDef.shape = shape;
        fixDef.isSensor = true;
        body.createFixture(fixDef).setUserData("player-right");

        shape.dispose();
    }
}
