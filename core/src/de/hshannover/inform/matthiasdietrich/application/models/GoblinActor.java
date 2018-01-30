package de.hshannover.inform.matthiasdietrich.application.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;
import de.hshannover.inform.matthiasdietrich.util.Util;

/**
 * GoblinActor have time attributes for fire projectiles in intervals
 */
public class GoblinActor extends BaseActor {
    private float time;

    public GoblinActor(World world) {
        super(world);
        time = 0;
    }

    @Override
    public void spawn() {
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(new Vector2(getX(), getY()));

        body = super.world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Util.scale(12f), GameConstants.TILE_WIDTH/2f);

        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = shape;
        fixDef.density = 0.6f;
        fixDef.restitution = 0.1f;
        fixDef.friction = 1f;
        body.createFixture(fixDef).setUserData("goblin");

        shape.dispose();
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }
}
