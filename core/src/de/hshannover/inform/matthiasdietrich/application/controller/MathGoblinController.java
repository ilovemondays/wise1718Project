package de.hshannover.inform.matthiasdietrich.application.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;
import de.hshannover.inform.matthiasdietrich.application.models.GoblinActor;

import java.util.ArrayList;

/**
 * Updates every goblin in level.
 * Checks in which direction every goblin should fire
 * Spawns projectile object
 */
public class MathGoblinController {
    private static MathGoblinController mathGoblinController = null;
    private ArrayList<GoblinActor> goblins;
    private final float shootIntervalTime = 3f;
    private ProjectileController projectileController = ProjectileController.getInstance();
    private World world;

    private MathGoblinController(){}

    public static MathGoblinController getInstance() {
        if (mathGoblinController == null) {
            mathGoblinController = new MathGoblinController();
        }
        return mathGoblinController;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
        projectileController.setWorld(world);
    }

    public ArrayList<GoblinActor> getGoblins() {
        return goblins;
    }

    public void setGoblins(ArrayList<GoblinActor> goblins) {
        this.goblins = goblins;
    }

    public void update() {
        for (GoblinActor goblin : goblins) {
            // check if goblin can shoot
            if(goblin.getTime() >= shootIntervalTime) {
                goblin.setTime(0);
                // spawn evil math projectile
                projectileController.newProjectile(goblin.getX(), goblin.getY());
            }
            goblin.setTime(goblin.getTime() + Gdx.graphics.getDeltaTime());
        }

        projectileController.update();
    }
}
