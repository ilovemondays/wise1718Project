package de.hshannover.inform.matthiasdietrich.application.controller;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * Created by matthiasdietrich on 22.10.17.
 */
public class LevelController {
    private TiledMap map;

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

    }

    public TiledMapTileLayer getCollisionData() {
        return (TiledMapTileLayer) getMap().getLayers().get("collision");
    }
}
