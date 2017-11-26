package de.hshannover.inform.matthiasdietrich.ui.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Abstraction layer for handling user input
 * @author Matthias Dietrich (matthias.dietrich@stud.hs-hannover.de)
 */
public class InputController {
    private static InputController inputController = null;

    private InputController() {}

    public static InputController getInstance() {
        if (inputController != null) {
            return inputController;
        }
        inputController = new InputController();
        return inputController;
    }

    public boolean isLeft() {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            return true;
        }
        return false;
    }

    public boolean isRight() {
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            return true;
        }
        return false;
    }

    public boolean isJump() {
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            return true;
        }
        return false;
    }

    public boolean isESC() {
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            return true;
        }
        return false;
    }
}
