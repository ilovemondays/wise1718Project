package de.ilovemondays.game.actors;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * A base model for every object/actor in game
 * @author Matthias Dietrich (matthias.dietrich@stud.hs-hannover.de)
 */
public class BaseActor extends Actor {
    BodyDef box2dBody = new BodyDef();

    public BaseActor() {
        box2dBody.type = BodyDef.BodyType.DynamicBody;
        box2dBody.position.set(getX(), getY());
        box2dBody.fixedRotation = true;
    }

    public BodyDef getBox2dBody() {
        return box2dBody;
    }

    public void setBox2dBody(BodyDef box2dBody) {
        this.box2dBody = box2dBody;
    }


}
