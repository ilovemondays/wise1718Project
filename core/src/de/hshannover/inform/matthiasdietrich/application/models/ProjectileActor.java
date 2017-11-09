package de.hshannover.inform.matthiasdietrich.application.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;

/**
 * Created by matthiasdietrich on 07.11.17.
 */
public class ProjectileActor extends BaseActor {
    public ProjectileActor(World world) {
        super(world);
    }

    public void spawn() {
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(new Vector2(getX(), getY()));

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(GameConstants.TILE_WIDTH/2.5f, GameConstants.TILE_WIDTH/2.5f);

        FixtureDef fixDef = new FixtureDef();

        // Ground Sensor
        shape.setAsBox(GameConstants.TILE_WIDTH/6f, GameConstants.TILE_WIDTH/9f, new Vector2(0, -GameConstants.TILE_WIDTH/2.2f), 0);
        fixDef.shape = shape;
        fixDef.isSensor = true;
        body.createFixture(fixDef).setUserData("ground");

        shape.dispose();
    }
}
