package de.hshannover.inform.matthiasdietrich.application.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.awt.*;

/**
 * Certificates don't need a physic simulation, so there is a different model and box2d setup
 */
public class CertificateModel {
    private Point position;

    public Point getPosition () {
        return position;
    }

    public void setPosition (Point position) {
        this.position = position;
    }

    public void spawn(World world, float x, float y) {
        // Create our body definition
        BodyDef groundBodyDef = new BodyDef();
        // Set its world position
        groundBodyDef.position.set(new Vector2(x + 0.5f, y+ 0.5f));

        // Create a body from the defintion and add it to the world
        Body body = world.createBody(groundBodyDef);

        // Create a polygon shape
        PolygonShape groundBox = new PolygonShape();
        // Set the polygon shape as a box which is twice the size of our view port and 20 high
        // (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(0.25f, 0.25f);

        // Create a fixture from our polygon shape and add it to our ground body
        FixtureDef groundFix = new FixtureDef();
        groundFix.shape = groundBox;
        groundFix.density = 1.0f;
        groundFix.friction = 1.0f;
        groundFix.isSensor = true;
        body.createFixture(groundFix).setUserData(this);
        // Clean up after ourselves
        groundBox.dispose();
    }
}
