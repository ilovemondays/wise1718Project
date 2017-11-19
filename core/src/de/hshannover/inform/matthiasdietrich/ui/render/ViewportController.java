package de.hshannover.inform.matthiasdietrich.ui.render;

import com.badlogic.gdx.utils.viewport.Viewport;

public class ViewportController {
    private static Viewport viewport = new Viewport() {
        @Override
        public void apply () {
            super.apply();
        }
    };

    public static Viewport getViewport () {
        return viewport;
    }
}
