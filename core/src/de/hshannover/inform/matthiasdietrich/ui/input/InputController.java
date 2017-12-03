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
        return Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A);
    }

    public boolean isRight() {
        return Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D);
    }

    public boolean isJump() {
        return Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W);
    }

    public boolean isESC() {
        return Gdx.input.isKeyPressed(Input.Keys.ESCAPE);
    }
}
