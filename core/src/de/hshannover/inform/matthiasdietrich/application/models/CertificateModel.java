package de.hshannover.inform.matthiasdietrich.application.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by matthiasdietrich on 24.10.17.
 */
public class CertificateModel {
    // @TODO: zertifikat positionen aus den level daten ziehen
    public void spawn(World world) {
        // Create our body definition
        BodyDef groundBodyDef = new BodyDef();
        // Set its world position
        groundBodyDef.position.set(new Vector2(4, 2));

        // Create a body from the defintion and add it to the world
        Body groundBody = world.createBody(groundBodyDef);

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
        groundBody.createFixture(groundFix).setUserData("certificate");
        // Clean up after ourselves
        groundBox.dispose();
    }
}
