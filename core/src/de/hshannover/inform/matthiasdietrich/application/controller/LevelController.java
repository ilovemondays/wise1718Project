package de.hshannover.inform.matthiasdietrich.application.controller;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.physics.box2d.World;
import de.hshannover.inform.matthiasdietrich.application.models.GoblinActor;

import java.util.ArrayList;

/**
 * Created by matthiasdietrich on 22.10.17.
 */
public class LevelController {
    private TiledMap map;
    private MapLayerController mapLayerController = new MapLayerController();
    private World world;
    private static ArrayList<GoblinActor> goblins = new ArrayList<GoblinActor>();

    public LevelController(World world) {
        this.world = world;
    }

    public TiledMap getMap() {
        return map;
    }

    public static ArrayList<GoblinActor> getGoblins() {
        return goblins;
    }

    /**
     * load given level. range 1-6, default is 1;
     * @param level
     */
    public void setMap(int level) {
        switch (level) {
            case 1:
                this.map = new TmxMapLoader().load("maps/map.tmx");
                break;
            case 2:
                this.map = new TmxMapLoader().load("maps/level-2.tmx");
                break;
            default: this.map = new TmxMapLoader().load("maps/map.tmx");
        }
        mapLayerController.setCollisionMap(getMapData("collision"));
        mapLayerController.setPlayerMap(getMapData("player"));
        mapLayerController.setCertificatesMap(getMapData("certificates"));
        mapLayerController.setMathGoblinMap(getMapData("goblin"));
        mapLayerController.setTrapMap(getMapData("trap"));
        mapLayerController.setLightMap(getMapData("light"));
    }

    public MapLayerController getMapLayerController() {
        return mapLayerController;
    }

    public TiledMapTileLayer getMapData(String layer) {
        return (TiledMapTileLayer) getMap().getLayers().get(layer);
    }

    public void spawnGoblins() {
        getMapLayerController().setMathGoblinPosition(new GoblinActor(world), goblins);
        for (GoblinActor goblin : goblins) {
            goblin.spawn();
        }
    }

    public void clear() {
        goblins.clear();
    }
}
