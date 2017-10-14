package de.hshannover.inform.matthiasdietrich.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import de.hshannover.inform.matthiasdietrich.constants.GameConstants;

import static com.badlogic.gdx.physics.box2d.BodyDef.*;

/**
 * A base model for every object/actor in game
 * @author Matthias Dietrich (matthias.dietrich@stud.hs-hannover.de)
 */
public class BaseActor extends Actor {
    private BodyDef bodyDef;
    private Body body;
    private World world;
    private Texture texture;
    private boolean isOnGround;

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    private Sprite sprite;

    public BaseActor(World world) {
        this.world = world;
        bodyDef = new BodyDef();
        texture = null;
        body = null;
        sprite = null;
    }
    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
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
        fixDef.density = 0.5f;
        fixDef.restitution = 0.0f;
        fixDef.friction = 1f;
        body.createFixture(fixDef).setUserData("body");

        // Ground Sensor
        shape.setAsBox(GameConstants.TILE_WIDTH/7f, GameConstants.TILE_WIDTH/7f, new Vector2(0, -GameConstants.TILE_WIDTH/2.2f), 0);
        fixDef.shape = shape;
        fixDef.isSensor = true;
        body.createFixture(fixDef).setUserData("ground");

        // Left Seonsor
        shape.setAsBox(GameConstants.TILE_WIDTH/7f, GameConstants.TILE_WIDTH/7f, new Vector2(-GameConstants.TILE_WIDTH/2.2f, 0), 0);
        fixDef.shape = shape;
        fixDef.isSensor = true;
        body.createFixture(fixDef).setUserData("left");

        // Left Seonsor
        shape.setAsBox(GameConstants.TILE_WIDTH/7f, GameConstants.TILE_WIDTH/7f, new Vector2(GameConstants.TILE_WIDTH/2.2f, 0), 0);
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
