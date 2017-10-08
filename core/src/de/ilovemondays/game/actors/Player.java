package de.ilovemondays.game.actors;

import com.badlogic.gdx.graphics.Texture;

/**
 * The Player is controlled by the user
 * @author Matthias Dietrich (matthias.dietrich@stud.hs-hannover.de)
 */
public class Player extends BaseActor {
    private final int MOVEMENT_SPEED = 3;
    private Texture texture;

    public Player() {
        super();
        texture = new Texture("actors/dummy/dummy.png");
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void moveLeft() {
        setX(getX() - MOVEMENT_SPEED);
    }

    public void moveRight() {
        setX(getX() + MOVEMENT_SPEED);
    }
}
