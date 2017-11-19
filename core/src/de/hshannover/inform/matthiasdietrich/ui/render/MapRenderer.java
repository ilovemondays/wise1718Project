package de.hshannover.inform.matthiasdietrich.ui.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;

import java.awt.*;
import java.util.ArrayList;


public class MapRenderer {
    private float stateTime = 0f;
    private ArrayList<Point> collisionTiles = new ArrayList<Point>();
    private boolean matrixIsSetup = false;
    public Pixmap pixmap;

    public MapRenderer() {
        pixmap = new Pixmap((int)GameConstants.TILE_WIDTH, (int)GameConstants.TILE_WIDTH, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();
    }

    public void setCollisionTiles (ArrayList<Point> collisionTiles) {
        this.collisionTiles = collisionTiles;
    }

    public void render(Batch batch) {
        if (!matrixIsSetup) {
            System.out.println("MATRIX SETUP");
            matrixIsSetup = true;
        }
        for (Point tile: collisionTiles) {
            batch.begin();
            batch.draw(new Texture(pixmap), tile.x, tile.y);
            batch.end();
        }

    }
}
