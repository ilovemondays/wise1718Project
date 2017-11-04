package de.hshannover.inform.matthiasdietrich.application.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;

import static com.badlogic.gdx.physics.box2d.BodyDef.*;

/**
 * A base model for every object/actor in game
 * @author Matthias Dietrich (matthias.dietrich@stud.hs-hannover.de)
 */
public class BaseActor extends Actor {
    protected BodyDef bodyDef;
    protected Body body;
    protected World world;

    public BaseActor(World world) {
        this.world = world;
        bodyDef = new BodyDef();
        body = null;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public void spawn() {
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(new Vector2(getX(), getY()));

        body = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(GameConstants.TILE_WIDTH/2.5f, GameConstants.TILE_WIDTH/2.5f);
        circle.setRadius(GameConstants.TILE_WIDTH/2.5f);

        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = circle;
        fixDef.density = 0.6f;
        fixDef.restitution = 0.1f;
        fixDef.friction = 1f;
        body.createFixture(fixDef).setUserData("body");

        // Ground Sensor
        shape.setAsBox(GameConstants.TILE_WIDTH/6f, GameConstants.TILE_WIDTH/9f, new Vector2(0, -GameConstants.TILE_WIDTH/2.2f), 0);
        fixDef.shape = shape;
        fixDef.isSensor = true;
        body.createFixture(fixDef).setUserData("ground");

        // Left Sensor
        shape.setAsBox(GameConstants.TILE_WIDTH/40f, GameConstants.TILE_WIDTH/10f, new Vector2(-GameConstants.TILE_WIDTH/2.4f, 0), 0);
        fixDef.shape = shape;
        fixDef.isSensor = true;
        body.createFixture(fixDef).setUserData("left");

        // Right Sensor
        shape.setAsBox(GameConstants.TILE_WIDTH/40f, GameConstants.TILE_WIDTH/10f, new Vector2(GameConstants.TILE_WIDTH/2.4f, 0), 0);
        fixDef.shape = shape;
        fixDef.isSensor = true;
        body.createFixture(fixDef).setUserData("right");

        shape.dispose();
    }


    public void update() {
        if(body != null) {
            Vector2 position = body.getPosition();
            setPosition(position.x, position.y);
        }
    }






}
