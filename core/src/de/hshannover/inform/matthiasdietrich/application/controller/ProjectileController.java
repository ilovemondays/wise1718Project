package de.hshannover.inform.matthiasdietrich.application.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import de.hshannover.inform.matthiasdietrich.application.models.ProjectileActor;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by matthiasdietrich on 10.11.17.
 */
public class ProjectileController extends Observable implements Observer {
    private static ProjectileController projectileController = new ProjectileController();
    private ArrayList<ProjectileActor> projectiles = new ArrayList<ProjectileActor>();
    private GameController gameController = GameController.getInstance();
    private World world;

    private ProjectileController () {
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public static ProjectileController getInstance() {
        return projectileController;
    }

    public ArrayList<ProjectileActor> getProjectiles() {
        return projectiles;
    }

    public void newProjectile(float x, float y) {
        Vector2 playerPosition = getPlayerPosition();
        String leftOrRight;
        int spawnLeftOrRight = 0;
        if (x < playerPosition.x) {
            spawnLeftOrRight = 1;
            leftOrRight = "right";
        } else {
            spawnLeftOrRight = -1;
            leftOrRight = "left";
        }
        projectiles.add(new ProjectileActor(world, x + spawnLeftOrRight, y+0.5f, leftOrRight));
    }

    public void clearProjectiles() {
        projectiles = null;
        projectiles = new ArrayList<ProjectileActor>();
    }

    public void addMeAsObserver(Observer o) {
        addObserver(o);
    }

    public void update() {
        for (ProjectileActor projectile : projectiles) {
            if (projectile.isSpawned() == false) {
                Vector2 playerPosition = getPlayerPosition();
                int xVal = 1;
                if (projectile.getLeftOrRight().equals("left")) {
                    xVal = -1;
                }

                projectile.spawn(
                        // richtungsvektor zum spieler
                        //new Vector2(playerPosition.x - projectile.getX(), playerPosition.y - projectile.getY()),

                        // left or right
                        new Vector2(xVal, 0),
                        gameController.getRayHandler());
            }
            projectile.update();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Fixture && ((Fixture)arg).getUserData() instanceof ProjectileActor) {
            ProjectileActor projectile = (ProjectileActor)((Fixture)arg).getUserData();
            if (!projectile.getType().equals("Projectile")) return;
            Body b = ((Fixture) arg).getBody();
            if(!gameController.getBodiesToDestroy().contains(b)) {
                gameController.getBodiesToDestroy().add(b);
            }
            if(!gameController.getLightsToDestroy().contains(projectile.getLight())) {
                gameController.getLightsToDestroy().add(projectile.getLight());
            }
            setChanged();
            notifyObservers(new Vector2(projectile.getX(), projectile.getY()));
            projectiles.remove(((Fixture)arg).getUserData());
        }
    }

    private Vector2 getPlayerPosition() {
        return new Vector2(gameController.getPlayer().getX(), gameController.getPlayer().getY());
    }
}
