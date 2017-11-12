package de.hshannover.inform.matthiasdietrich;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
//import com.badlogic.gdx.graphics.g2d.stbtt.TrueTypeFontFactory;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import de.hshannover.inform.matthiasdietrich.application.screens.MainMenuScreen;

public class Semester3Project extends Game {
	public SpriteBatch batch;
	public BitmapFont fontBold;
	public World world;
    //public static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";
    public FreeTypeFontGenerator generator;
    public FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    public BitmapFont fontLight;


    @Override
	public void create () {
        Box2D.init();
		world = new World(new Vector2(0, -10f), true);
		batch = new SpriteBatch();
        //fontBold = TrueTypeFontFactory.createBitmapFont(Gdx.files.internal("fonts/Raleway-Bold.ttf"), FONT_CHARACTERS, 12.5f, 7.5f, 1.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //fontLight = TrueTypeFontFactory.createBitmapFont(Gdx.files.internal("fonts/Raleway-Light.ttf"), FONT_CHARACTERS, 12.5f, 7.5f, 1.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //fontBold.setColor(1f, 0f, 0f, 1f);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Raleway-Light.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 42;
        fontLight = generator.generateFont(parameter);
        fontLight.setColor(1f, 1f, 1f, 1f);
        parameter.size = 62;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Raleway-ExtraBold.ttf"));
        fontBold = generator.generateFont(parameter);
        fontBold.setColor(1f, 1f, 1f, 1f);
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
        fontBold.dispose();
        fontLight.dispose();
        generator.dispose();
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
