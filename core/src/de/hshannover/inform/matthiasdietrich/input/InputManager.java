package de.hshannover.inform.matthiasdietrich.input;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Abstraction layer for handling user input
 * @author Matthias Dietrich (matthias.dietrich@stud.hs-hannover.de)
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

    public boolean isAction() {
        if(Gdx.input.isKeyPressed(Input.Keys.X)) {
            return true;
        }
        return false;
    }

    public boolean isHotkey1() {
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            return true;
        }
        return false;
    }

    public boolean isHotkey2() {
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            return true;
        }
        return false;
    }
}
