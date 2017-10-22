package de.hshannover.inform.matthiasdietrich.ui.render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;
import de.hshannover.inform.matthiasdietrich.util.Util;

/**
 * Created by matthiasdietrich on 22.10.17.
 */
public class Camera extends OrthographicCamera {

    public Camera() {
        setToOrtho(false, Util.scale(GameConstants.CAMERA_X), Util.scale(GameConstants.CAMERA_Y));
    }
}
