package de.hshannover.inform.matthiasdietrich;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import de.hshannover.inform.matthiasdietrich.application.screens.MainMenuScreen;

import java.util.ArrayList;

public class Semester3Project extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public World world;

	@Override
	public void create () {
        Box2D.init();
		world = new World(new Vector2(0, -10f), true);
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		world.dispose();
	}

	public void clearWorld() {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
		for (Body body: bodies) {
		    world.destroyBody(body);
        }
	}
}
