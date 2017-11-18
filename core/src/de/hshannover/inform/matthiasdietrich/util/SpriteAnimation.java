package de.hshannover.inform.matthiasdietrich.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Matthias on 18.11.2017.
 */
public class SpriteAnimation {

    public static Animation create(String image, int frameCols, int frameRows, float duration) {
        Texture spriteSheet;

        spriteSheet = new Texture(Gdx.files.internal(image));

        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet,
                spriteSheet.getWidth() / frameCols,
                spriteSheet.getHeight() / frameRows);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] spriteFrames = new TextureRegion[frameCols * frameRows];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
                spriteFrames[index++] = tmp[i][j];
            }
        }
        return new Animation(duration, spriteFrames);
    }
}