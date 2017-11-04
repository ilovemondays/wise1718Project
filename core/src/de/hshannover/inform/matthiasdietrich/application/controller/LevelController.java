package de.hshannover.inform.matthiasdietrich.application.controller;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Created by matthiasdietrich on 22.10.17.
 */
public class LevelController {
    private TiledMap map;
    private MapLayerController mapLayerController = new MapLayerController();

    public TiledMap getMap() {
        return map;
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
            default: this.map = new TmxMapLoader().load("maps/map.tmx");
        }
        mapLayerController.setCollisionMap(getMapData("collision"));
        mapLayerController.setPlayerMap(getMapData("player"));
        mapLayerController.setCertificatesMap(getMapData("certificates"));
        mapLayerController.setMathGoblinMap(getMapData("goblin"));
        mapLayerController.setTrapMap(getMapData("trap"));
    }

    public MapLayerController getMapLayerController() {
        return mapLayerController;
    }

    public TiledMapTileLayer getMapData(String layer) {
        return (TiledMapTileLayer) getMap().getLayers().get(layer);
    }
}
