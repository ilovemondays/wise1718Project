package de.hshannover.inform.matthiasdietrich.ui.render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;
import de.hshannover.inform.matthiasdietrich.util.Util;

/**
 * Viewport for the game. It moves with the player
 */
public class Camera extends OrthographicCamera {

    public Camera() {
        setToOrtho(false, Util.scale(GameConstants.CAMERA_X), Util.scale(GameConstants.CAMERA_Y));
    }
}
