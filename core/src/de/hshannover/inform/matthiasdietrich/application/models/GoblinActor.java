package de.hshannover.inform.matthiasdietrich.application.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;

/**
 * Created by matthiasdietrich on 04.11.17.
 */
public class GoblinActor extends BaseActor {

    public GoblinActor(World world) {
        super(world);
    }

    @Override
    public void spawn() {
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(new Vector2(getX(), getY()));

        body = super.world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(GameConstants.TILE_WIDTH/2f, GameConstants.TILE_WIDTH/2f);

        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = shape;
        fixDef.density = 0.6f;
        fixDef.restitution = 0.1f;
        fixDef.friction = 1f;
        body.createFixture(fixDef).setUserData("goblin");

        shape.dispose();
    }
}
