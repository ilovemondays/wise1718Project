package de.hshannover.inform.matthiasdietrich.application.controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;
import de.hshannover.inform.matthiasdietrich.application.models.CertificateModel;
import de.hshannover.inform.matthiasdietrich.application.models.GoblinActor;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by matthiasdietrich on 22.10.17.
 */
public class LevelController {
    private TiledMap map;
    private MapLayerController mapLayerController = new MapLayerController();
    private World world;
    private static ArrayList<GoblinActor> goblins = new ArrayList<GoblinActor>();
    private ArrayList<Point> collisionTiles = new ArrayList<Point>();
    private ArrayList<Point> trapTiles = new ArrayList<Point>();
    private ArrayList<CertificateModel> certificates = new ArrayList<CertificateModel>();
    private Color ambientColor;
    private Color lightColor;

    public LevelController(World world) {
        this.world = world;
    }

    public TiledMap getMap() {
        return map;
    }

    public static ArrayList<GoblinActor> getGoblins() {
        return goblins;
    }

    public Color getAmbientColor () {
        return ambientColor;
    }

    public Color getLightColor () {
        return lightColor;
    }

    /**
     * load given level. range 1-6, default is 1;
     * @param level
     */
    public void setMap(int level) {
        if (GameConstants.DEV_MODE) {
            System.out.println("LevelController: LOAD MAP");
        }
        switch (level) {
            case 1:
                ambientColor = new Color(0x24C2CB99);
                lightColor = new Color(0xEF525B99);

                this.map = new TmxMapLoader().load("maps/level-1.tmx");
                break;
            case 2:
                ambientColor = new Color(0x86DDB299);
                lightColor = new Color(0xF8E7A299);;
                this.map = new TmxMapLoader().load("maps/level-2.tmx");
                break;
            case 3:
                ambientColor = new Color(0x2E112D99);
                lightColor = new Color(0xF0433A99);
                this.map = new TmxMapLoader().load("maps/level-3.tmx");
                break;
            case 4:
                ambientColor = new Color(0xEFFC2199);
                lightColor = new Color(0xF835A499);
                this.map = new TmxMapLoader().load("maps/level-4.tmx");
                break;
            case 5:
                ambientColor = new Color(0x4835E855);
                lightColor = new Color(0xFF005899);
                this.map = new TmxMapLoader().load("maps/level-5.tmx");
                break;
            case 6:
                ambientColor = new Color(0x99002255);
                lightColor = new Color(0x00ff3399);
                this.map = new TmxMapLoader().load("maps/level-6.tmx");
                break;
        }

        mapLayerController.setPlayerMap(getMapData("player"));
        mapLayerController.setMathGoblinMap(getMapData("goblin"));
        mapLayerController.setLightMap(getMapData("light"));

        buildCollisionMap();
        buildTrapMap();
        buildCertificatesMap();
    }

    public MapLayerController getMapLayerController() {
        return mapLayerController;
    }

    public TiledMapTileLayer getMapData(String layer) {
        return (TiledMapTileLayer) getMap().getLayers().get(layer);
    }

    public ArrayList<Point> getCollisionTiles () {
        return collisionTiles;
    }

    public ArrayList<Point> getTrapTiles () {
        return trapTiles;
    }

    public ArrayList<CertificateModel> getCertificates () {
        return certificates;
    }

    public ArrayList<Point> getGoblinsAsPoints () {
        ArrayList<Point> list = new ArrayList<Point>();
        for (GoblinActor goblin : goblins) {
            list.add(new Point((int)goblin.getX(), (int)goblin.getY()));
        }
        return list;
    }

    public void spawnGoblins() {
        getMapLayerController().setMathGoblinPosition(world, goblins);
        for (GoblinActor goblin : goblins) {
            goblin.spawn();
        }
    }

    public void clear() {
        goblins.clear();
        collisionTiles.clear();
        certificates.clear();
        trapTiles.clear();
    }

    // COLLISION TILES
    private void buildCollisionMap() {
        collisionTiles = mapLayerController.getTiledMapData(getMapData("collision"));
        for (Point tile : collisionTiles) {
            createCollisionTile(tile.x, tile.y);
        }
    }

    private void createCollisionTile(int x,int y) {
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

    // TRAP TILES
    private void buildTrapMap() {
        trapTiles= mapLayerController.getTiledMapData(getMapData("trap"));
        for (Point tile : trapTiles) {
            createTrapTile(tile.x, tile.y);
        }
    }
    private void createTrapTile(int x,int y) {
        BodyDef groundBodyDef = new BodyDef();

        // set its world position
        groundBodyDef.position.set(new Vector2(x * GameConstants.TILE_WIDTH+0.5f, y*GameConstants.TILE_WIDTH+0.5f));
        Body groundBody = world.createBody(groundBodyDef);

        // create a polygon shape
        PolygonShape groundBox = new PolygonShape();
        // achtung halbe-hoehe und halbe-weite:
        groundBox.setAsBox(GameConstants.TILE_WIDTH/2, GameConstants.TILE_WIDTH/4f);

        FixtureDef groundFix = new FixtureDef();
        groundFix.shape = groundBox;
        groundFix.density = 1.0f;
        groundFix.friction = 1.0f;
        groundBody.createFixture(groundFix).setUserData("trap");
        groundBox.dispose();
    }

    // CERTIFICATES TILES
    private void buildCertificatesMap() {
        ArrayList<Point> list = mapLayerController.getTiledMapData(getMapData("certificates"));
        CertificateModel temp;
        for (Point point : list) {
            temp = new CertificateModel();
            temp.setPosition(point);
            certificates.add(temp);
            temp.spawn(world, temp.getPosition().x, temp.getPosition().y);
        }
    }

}
