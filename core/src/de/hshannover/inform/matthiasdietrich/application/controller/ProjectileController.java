package de.hshannover.inform.matthiasdietrich.application.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import de.hshannover.inform.matthiasdietrich.application.models.ProjectileActor;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by matthiasdietrich on 10.11.17.
 */
public class ProjectileController implements Observer {
    private static ProjectileController projectileController = new ProjectileController();
    private ArrayList<ProjectileActor> projectiles = new ArrayList<ProjectileActor>();
    private GameController gameController = GameController.getInstance();

    private ProjectileController () {}

    public static ProjectileController getInstance() {
        return projectileController;
    }

    public ArrayList<ProjectileActor> getProjectiles() {
        return projectiles;
    }

    public void update() {
        for (ProjectileActor projectile : projectiles) {
            if (projectile.isSpawned() == false) {
                Vector2 playerPosition = new Vector2(gameController.getPlayer().getX(), gameController.getPlayer().getY());

                projectile.spawn(
                        // richtungsvektor zum spieler
                        //new Vector2(playerPosition.x - projectile.getX(), playerPosition.y - projectile.getY()),
                        new Vector2(5,0),
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
            projectiles.remove(((Fixture)arg).getUserData());
        }
    }
}
