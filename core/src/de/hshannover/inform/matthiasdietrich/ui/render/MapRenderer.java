package de.hshannover.inform.matthiasdietrich.ui.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;
import de.hshannover.inform.matthiasdietrich.application.controller.ProjectileController;
import de.hshannover.inform.matthiasdietrich.application.models.CertificateModel;
import de.hshannover.inform.matthiasdietrich.application.models.ProjectileActor;
import de.hshannover.inform.matthiasdietrich.util.Util;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class MapRenderer implements Observer {
    private ArrayList<Point> mapTiles = new ArrayList<Point>();
    private ArrayList<Point> traps = new ArrayList<Point>();
    private ArrayList<Point> goblins = new ArrayList<Point>();
    private ArrayList<CertificateModel> certificates = new ArrayList<CertificateModel>();
    private ProjectileController projectileController = ProjectileController.getInstance();
    private Pixmap pixmap;
    private Texture titleTexture;
    private Sprite trapTexture;
    private Sprite goblinTexture;
    private Sprite certificateTexture;
    private Sprite projectileTexture;
    private ParticleEffect explosion = null;

    public MapRenderer() {
        projectileController.addMeAsObserver(this);
        pixmap = new Pixmap((int)GameConstants.TILE_WIDTH, (int)GameConstants.TILE_WIDTH, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();
        titleTexture = new Texture(pixmap);
        //@TODO: FILE ASSET MANAGER
        trapTexture = Util.adjustSize(new Sprite(new Texture("images/map/trap.png")));
        goblinTexture = Util.adjustSize(new Sprite(new Texture("actors/goblin/goblin.png")));
        certificateTexture = Util.adjustSize(new Sprite(new Texture("images/map/certificate.png")));
        projectileTexture = Util.adjustSize(new Sprite(new Texture("actors/projectiles/sqrt.png")));
//        explosion = new ParticleEffect();
//        explosion.load(Gdx.files.internal("particles/explosion.particle"), Gdx.files.internal("particles"));
//        explosion.scaleEffect(0.01f);
    }

    public void setMapTiles (ArrayList<Point> mapTiles) {
        this.mapTiles = null;
        this.mapTiles = mapTiles;
    }

    public void setTraps (ArrayList<Point> traps) {
        this.traps = null;
        this.traps = traps;
    }

    public void setGoblins (ArrayList<Point> goblins) {
        this.goblins = null;
        this.goblins = goblins;
    }

    public void setCertificates (ArrayList<CertificateModel> certificates) {
        this.certificates = null;
        this.certificates = certificates;
    }

    /**
     * Draws all map parts;:map tiles, traps, goblins, projectiles and certificates
     *
     * @param batch
     * @param camera
     */
    public void render(Batch batch, Camera camera) {
        for (Point tile: mapTiles) {
            if (isTileVisible(camera, tile)) {
                batch.draw(titleTexture, tile.x, tile.y);
            }
        }
        for (Point tile: traps) {
            if (isTileVisible(camera, tile)) {
                    trapTexture.setPosition(tile.x, tile.y);
                    trapTexture.draw(batch);
            }
        }
        for (Point tile: goblins) {
            if (isTileVisible(camera, tile)) {
                goblinTexture.setPosition(tile.x  + 0.15f, tile.y);
                goblinTexture.draw(batch);
            }
        }
        for (CertificateModel certificate: certificates) {
            Point point = certificate.getPosition();
            if (isTileVisible(camera, point.x, point.y)) {
                certificateTexture.setPosition(point.x + 0.25f, point.y  + 0.25f);
                certificateTexture.draw(batch);
            }
        }
        for (ProjectileActor projectile : projectileController.getProjectiles()) {
            if (isTileVisible(camera, projectile.getX(), projectile.getY())) {
                projectileTexture.setPosition(projectile.getX() - 0.25f, projectile.getY() - 0.55f);
                projectileTexture.draw(batch);
            }
        }

        if (explosion != null) {
            explosion.update(Gdx.graphics.getDeltaTime());
            explosion.draw(batch);
        }

    }

    /**
     * Checks if point is visible on camera view
     *
     * @param camera
     * @param tile
     * @return
     */
    private boolean isTileVisible(Camera camera, Point tile) {
        return (camera.frustum.pointInFrustum(tile.x + 2, tile.y + 2, 0) ||
        camera.frustum.pointInFrustum(tile.x + 2, tile.y - 2, 0) ||
        camera.frustum.pointInFrustum(tile.x - 2, tile.y + 2, 0) ||
        camera.frustum.pointInFrustum(tile.x - 2, tile.y - 2, 0));
    }
    private boolean isTileVisible(Camera camera, float x, float y) {
        return (camera.frustum.pointInFrustum(x + 2, y + 2, 0) ||
                camera.frustum.pointInFrustum(x + 2, y - 2, 0) ||
                camera.frustum.pointInFrustum(x - 2, y + 2, 0) ||
                camera.frustum.pointInFrustum(x - 2, y - 2, 0));
    }

    @Override
    public void update (Observable o, Object arg) {
//        if (arg instanceof Vector2) {
//            explosion.getEmitters().first().setPosition(((Vector2) arg).x ,((Vector2) arg).y);
//            explosion.start();
//        }
    }
}
