package de.hshannover.inform.matthiasdietrich.application.controller;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;
import de.hshannover.inform.matthiasdietrich.application.models.BaseActor;
import de.hshannover.inform.matthiasdietrich.application.models.CertificateModel;
import de.hshannover.inform.matthiasdietrich.application.models.GoblinActor;
import de.hshannover.inform.matthiasdietrich.application.models.PlayerActor;

import java.util.ArrayList;

/**
 * Created by matthiasdietrich on 22.10.17.
 */
public class MapLayerController {
    TiledMapTileLayer collisionMap;
    TiledMapTileLayer playerMap;
    TiledMapTileLayer certificatesMap;
    TiledMapTileLayer MathGoblinMap;
    TiledMapTileLayer TrapMap;

    public TiledMapTileLayer getCollisionMap() {
        return collisionMap;
    }

    public void setCollisionMap(TiledMapTileLayer collisionMap) {
        this.collisionMap = collisionMap;
    }

    public TiledMapTileLayer getPlayerMap() {
        return playerMap;
    }

    public void setPlayerMap(TiledMapTileLayer playerMap) {
        this.playerMap = playerMap;
    }

    public TiledMapTileLayer getCertificatesMap() {
        return certificatesMap;
    }

    public void setCertificatesMap(TiledMapTileLayer certificatesMap) {
        this.certificatesMap = certificatesMap;
    }

    public TiledMapTileLayer getMathGoblinMap() {
        return MathGoblinMap;
    }

    public void setMathGoblinMap(TiledMapTileLayer mathGoblinMap) {
        MathGoblinMap = mathGoblinMap;
    }

    public TiledMapTileLayer getTrapMap() {
        return TrapMap;
    }

    public void setTrapMap(TiledMapTileLayer trapMap) {
        TrapMap = trapMap;
    }

    /**
     * Takes the map tmx data and translates it into box2d bodies
     * @param world
     */
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
        BodyDef groundBodyDef = new BodyDef();

        // set its world position
        groundBodyDef.position.set(new Vector2(x * GameConstants.TILE_WIDTH+0.5f, y*GameConstants.TILE_WIDTH+0.5f));
        Body groundBody = world.createBody(groundBodyDef);

        // create a polygon shape
        PolygonShape groundBox = new PolygonShape();
        // achtung halbe-hoehe und halbe-weite:
        groundBox.setAsBox(GameConstants.TILE_WIDTH/2, GameConstants.TILE_WIDTH/2);

        FixtureDef groundFix = new FixtureDef();
        groundFix.shape = groundBox;
        groundFix.density = 1.0f;
        groundFix.friction = 1.0f;
        groundBody.createFixture(groundFix);
        groundBox.dispose();
    }

    /**
     * Takes the map tmx data and translates it into box2d bodies
     * @param world
     */
    public void constructTrapMap(World world) {
        for (int collisionHeight = 0; collisionHeight <= getTrapMap().getHeight();  collisionHeight++) {
            for (int collisionWidth = 0; collisionWidth <= getTrapMap().getWidth(); collisionWidth++) {
                if (getTrapMap().getCell(collisionWidth, collisionHeight) != null) {
                    createTrapTile(world, collisionWidth, collisionHeight);
                }
            }
        }
    }

    private void createTrapTile(World world, int x,int y) {
        BodyDef groundBodyDef = new BodyDef();

        // set its world position
        groundBodyDef.position.set(new Vector2(x * GameConstants.TILE_WIDTH+0.5f, y*GameConstants.TILE_WIDTH+0.5f));
        Body groundBody = world.createBody(groundBodyDef);

        // create a polygon shape
        PolygonShape groundBox = new PolygonShape();
        // achtung halbe-hoehe und halbe-weite:
        groundBox.setAsBox(GameConstants.TILE_WIDTH/2, GameConstants.TILE_WIDTH/2);

        FixtureDef groundFix = new FixtureDef();
        groundFix.shape = groundBox;
        groundFix.density = 1.0f;
        groundFix.friction = 1.0f;
        groundBody.createFixture(groundFix).setUserData("trap");;
        groundBox.dispose();
    }


    // @TODO: Das hier unten vereinheitlichen, alles redundanter code

    /**
     * Get player position from map tmx data. There should be only on player tile.
     * But if there are more the last found position is used
     * @param world
     * @param player
     */
    public void setPlayerPosition(World world, PlayerActor player) {
        for (int tileHeight = 0; tileHeight <= getPlayerMap().getHeight();  tileHeight++) {
            for (int tileWidth = 0; tileWidth <= getPlayerMap().getWidth(); tileWidth++) {
                if (getPlayerMap().getCell(tileWidth, tileHeight) != null) {
                    player.setPosition(tileWidth, tileHeight);
                }
            }
        }
    }

    /**
     * Get cert position from map tmx data.
     * @param world
     * @param cert
     */
    public void setCertificatesPosition(World world, CertificateModel cert) {
        for (int tileHeight = 0; tileHeight <= getCertificatesMap().getHeight();  tileHeight++) {
            for (int tileWidth = 0; tileWidth <= getCertificatesMap().getWidth(); tileWidth++) {
                if (getCertificatesMap().getCell(tileWidth, tileHeight) != null) {
                    cert.spawn(world, tileWidth + GameConstants.TILE_WIDTH/2, tileHeight + GameConstants.TILE_WIDTH/2);
                }
            }
        }
    }

    /**
     * Get goblin position from map tmx data. There should be only on player tile.
     * @param goblin
     * @param list
     */
    public void setMathGoblinPosition(GoblinActor goblin, ArrayList<GoblinActor> list) {
        for (int tileHeight = 0; tileHeight <= getMathGoblinMap().getHeight();  tileHeight++) {
            for (int tileWidth = 0; tileWidth <= getMathGoblinMap().getWidth(); tileWidth++) {
                if (getMathGoblinMap().getCell(tileWidth, tileHeight) != null) {
                    goblin.setPosition(tileWidth + GameConstants.TILE_WIDTH/2, tileHeight + GameConstants.TILE_WIDTH/2);
                    list.add(goblin);
                }
            }
        }
    }

}
