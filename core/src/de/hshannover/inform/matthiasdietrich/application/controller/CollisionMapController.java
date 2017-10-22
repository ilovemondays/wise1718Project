package de.hshannover.inform.matthiasdietrich.application.controller;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;

/**
 * Created by matthiasdietrich on 22.10.17.
 */
public class CollisionMapController {
    TiledMapTileLayer collisionMap;

    public TiledMapTileLayer getCollisionMap() {
        return collisionMap;
    }

    public void setCollisionMap(TiledMapTileLayer collisionMap) {
        this.collisionMap = collisionMap;
    }

    public void constructCollisionMap(World world) {
        for (int collisionHeight = 0; collisionHeight <= getCollisionMap().getHeight();  collisionHeight++) {
            for (int collisionWidth = 0; collisionWidth <= getCollisionMap().getWidth(); collisionWidth++) {
                if (getCollisionMap().getCell(collisionWidth, collisionHeight) != null) {
                    createCollisionTile(world, collisionWidth, collisionHeight);
                }
            }
        }
    }

    private void createCollisionTile(World world, int x,int y) {
        // Create our body definition
        BodyDef groundBodyDef = new BodyDef();
        // Set its world position
        groundBodyDef.position.set(new Vector2(x * GameConstants.TILE_WIDTH, y*GameConstants.TILE_WIDTH));

        // Create a body from the defintion and add it to the world
        Body groundBody = world.createBody(groundBodyDef);

        // Create a polygon shape
        PolygonShape groundBox = new PolygonShape();
        // Set the polygon shape as a box which is twice the size of our view port and 20 high
        // (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(GameConstants.TILE_WIDTH/2, GameConstants.TILE_WIDTH/2);

        // Create a fixture from our polygon shape and add it to our ground body
        FixtureDef groundFix = new FixtureDef();
        groundFix.shape = groundBox;
        groundFix.density = 1.0f;
        groundFix.friction = 1.0f;
        groundBody.createFixture(groundFix);
        // Clean up after ourselves
        groundBox.dispose();
    }
}
