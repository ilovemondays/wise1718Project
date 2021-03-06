package de.hshannover.inform.matthiasdietrich.application.models;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;

/**
 * Projectiles hav a light attached, flying in given direction. Gravity shouldn't affect it.
 */
public class ProjectileActor extends BaseActor {
    private Vector2 direction;
    private boolean spawned = false;
    private PointLight light;
    private String leftOrRight;

    public ProjectileActor(World world, float x, float y, String leftOrRight) {
        super(world);
        setX(x);
        setY(y);
        type = "Projectile";
        this.leftOrRight = leftOrRight;
    }

    public boolean isSpawned() {
        return spawned;
    }

    public void setSpawned(boolean spawned) {
        this.spawned = spawned;
    }

    public PointLight getLight() {
        return light;
    }

    public void setLight(PointLight light) {
        this.light = light;
    }

    public String getLeftOrRight () {
        return leftOrRight;
    }

    public void setLeftOrRight (String leftOrRight) {
        this.leftOrRight = leftOrRight;
    }

    public void spawn(Vector2 direction, RayHandler rayHandler) {
        this.direction = direction.nor(); // normalisiert, damit geschwindigkeit unabhängig der entfernung/laenge des vectors ist.
        this.direction.x *= GameConstants.PROJECTILE_SPEED;

        spawned = true;

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(new Vector2(getX(), getY()));

        body = world.createBody(bodyDef);
        body.setBullet(true);
        body.setGravityScale(0);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(GameConstants.TILE_WIDTH/2.5f, GameConstants.TILE_WIDTH/2.5f);

        FixtureDef fixDef = new FixtureDef();

        // Ground Sensor
        shape.setAsBox(GameConstants.TILE_WIDTH/6f, GameConstants.TILE_WIDTH/9f, new Vector2(0, -GameConstants.TILE_WIDTH/2.2f), 0);
        fixDef.shape = shape;
        fixDef.isSensor = true;
        fixDef.density = 0;
        body.createFixture(fixDef).setUserData(this);

        shape.dispose();

        light = new PointLight(rayHandler, 10, new Color(1f, .0f, .3f, .8f), 2f, body.getPosition().x, body.getPosition().y);
        body.applyLinearImpulse(direction, body.getPosition(), true);
    }

    @Override
    public void update() {
        super.update();
        light.setPosition(new Vector2(body.getPosition().x, body.getPosition().y - GameConstants.TILE_WIDTH/2));
    }
}
