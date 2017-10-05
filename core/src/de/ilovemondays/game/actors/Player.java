package de.ilovemondays.game.actors;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by matthiasdietrich on 05.10.17.
 */
public class Player extends BaseActor {
    private Texture texture;

    public Player() {
        texture = new Texture("actors/dummy/dummy.png");
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void moveLeft() {
        setX(getX() - 3);
    }

    public void moveRight() {
        setX(getX() + 3);
    }
}
