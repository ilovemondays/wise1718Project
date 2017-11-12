package de.hshannover.inform.matthiasdietrich.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.hshannover.inform.matthiasdietrich.Semester3Project;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "BIN S3 PROJECT";
		config.width = 800;
		config.height = 450;
		config.fullscreen = false;
		new LwjglApplication(new Semester3Project(), config);
	}
}
