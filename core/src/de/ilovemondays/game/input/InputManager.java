package de.ilovemondays.game.input;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Created by matthiasdietrich on 05.10.17.
 */
public class InputManager {

    public boolean isLeft() {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            return true;
        }
        return false;
    }

    public boolean isRight() {
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            return true;
        }
        return false;
    }

    public boolean isJump() {
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            return true;
        }
        return false;
    }
}
