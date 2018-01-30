package de.hshannover.inform.matthiasdietrich.util;

import com.badlogic.gdx.graphics.g2d.Sprite;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;

/**
 * Helps to scale between pixel and the units box2d uses. Box2d 1px = 1 meter
 */
public class Util {
    public static float scale(float valueToBeScaled) {
        return valueToBeScaled/ GameConstants.SCALE;
    }

    public static float scaleUp(float valueToBeScaled) {
        return valueToBeScaled* GameConstants.SCALE;
    }

    public static Sprite adjustSize(Sprite imageToBeScaled) {
        imageToBeScaled.setSize(scale(imageToBeScaled.getWidth()), scale(imageToBeScaled.getHeight()));
        return imageToBeScaled;
    }
}
