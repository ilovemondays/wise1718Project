package de.hshannover.inform.matthiasdietrich.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.hshannover.inform.matthiasdietrich.Semester3Project;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Study Race";
		config.width = 800;
		config.height = 450;
		config.fullscreen = false;
		config.resizable = false;
		new LwjglApplication(new Semester3Project(), config);
	}
}
