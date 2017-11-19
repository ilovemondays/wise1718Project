package de.hshannover.inform.matthiasdietrich.application.constants;

/**
 * Created by matthiasdietrich on 06.10.17.
 */
public class GameConstants {
    public static final float TILE_WIDTH = 1; // 1 = 1 meter for box2D physics
    public static final int SCALE = 32; // tile width in pixels
    public static final int MAX_VELOCITY = 3; // player movement
    public static final int CAMERA_X = 384; // interpolated value for game window width
    public static final int CAMERA_Y = 216; // interpolated value for game window height
    public static final boolean DEV_MODE = true; // show debug information

    public static final int WIN_CONDITION = 5; // collect # of certificates
    public static final int MAX_TRIALS = 3; // max # of tirals before game over

    public enum ActAnimation {
        JUMP, RUN, IDLE
    }

    public enum Direction {
        LEFT, RIGHT
    }
}
