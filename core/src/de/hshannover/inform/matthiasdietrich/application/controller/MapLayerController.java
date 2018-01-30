package de.hshannover.inform.matthiasdietrich.application.controller;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;
import de.hshannover.inform.matthiasdietrich.application.models.GoblinActor;
import de.hshannover.inform.matthiasdietrich.application.models.PlayerActor;

import java.awt.*;
import java.util.ArrayList;

/**
 * Get Map Data from TMX files
 */
class MapLayerController {
    private TiledMapTileLayer playerMap;
    private TiledMapTileLayer mathGoblinMap;
    private TiledMapTileLayer lightMap;

    public TiledMapTileLayer getPlayerMap() {
        return playerMap;
    }

    public void setPlayerMap(TiledMapTileLayer playerMap) {
        this.playerMap = playerMap;
    }

    public TiledMapTileLayer getMathGoblinMap() {
        return mathGoblinMap;
    }

    public void setMathGoblinMap(TiledMapTileLayer mathGoblinMap) {
        this.mathGoblinMap = mathGoblinMap;
    }

    public TiledMapTileLayer getLightMap() {
        return lightMap;
    }

    public void setLightMap(TiledMapTileLayer lightMap) {
        this.lightMap = lightMap;
    }

    public ArrayList<Point> getTiledMapData(TiledMapTileLayer mapData) {
        ArrayList<Point> tiledMapTiles = new ArrayList<Point>();
        for (int collisionHeight = 0; collisionHeight <= mapData.getHeight();  collisionHeight++) {
            for (int collisionWidth = 0; collisionWidth <= mapData.getWidth(); collisionWidth++) {
                if (mapData.getCell(collisionWidth, collisionHeight) != null) {
                    tiledMapTiles.add(new Point(collisionWidth, collisionHeight));
                }
            }
        }
        return tiledMapTiles;
    }

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
                    player.setStartingPoint(new Vector2(tileWidth, tileHeight));
                }
            }
        }
    }

    /**
     * Get goblin position from map tmx data.
     * @param list
     */
    public void setMathGoblinPosition(World world, ArrayList<GoblinActor> list) {
        GoblinActor goblin;
        for (int tileHeight = 0; tileHeight <= getMathGoblinMap().getHeight();  tileHeight++) {
            for (int tileWidth = 0; tileWidth <= getMathGoblinMap().getWidth(); tileWidth++) {
                if (getMathGoblinMap().getCell(tileWidth, tileHeight) != null) {
                    goblin = new GoblinActor(world);
                    goblin.setPosition(tileWidth + GameConstants.TILE_WIDTH/2, tileHeight + GameConstants.TILE_WIDTH/2);
                    list.add(goblin);
                }
            }
        }
    }

    /**
     * Get light position from map tmx data.
     * @param world
     */
    public void setLightPosition(World world, RayHandler rayHandler, Color color) {
        for (int tileHeight = 0; tileHeight <= getLightMap().getHeight();  tileHeight++) {
            for (int tileWidth = 0; tileWidth <= getLightMap().getWidth(); tileWidth++) {
                if (getLightMap().getCell(tileWidth, tileHeight) != null) {
                    new PointLight(rayHandler, 100, color,
                            20f, tileWidth + GameConstants.TILE_WIDTH/2, tileHeight + GameConstants.TILE_WIDTH/2);
                }
            }
        }
    }

}
