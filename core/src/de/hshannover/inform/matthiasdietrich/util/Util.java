package de.hshannover.inform.matthiasdietrich.util;

import com.badlogic.gdx.graphics.g2d.Sprite;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;

/**
 * Created by matthiasdietrich on 22.10.17.
 */
public class Util {
    public static float scale(float valueToBeScaled) {
        return valueToBeScaled/ GameConstants.SCALE;
    }

    public static Sprite adjustSize(Sprite imageToBeScaled) {
        imageToBeScaled.setSize(scale(imageToBeScaled.getWidth()), scale(imageToBeScaled.getHeight()));
        return imageToBeScaled;
    }
}
