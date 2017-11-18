package de.hshannover.inform.matthiasdietrich;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import de.hshannover.inform.matthiasdietrich.application.screens.MainMenuScreen;
import de.hshannover.inform.matthiasdietrich.ui.render.GUIController;
import de.hshannover.inform.matthiasdietrich.ui.render.SpriteRenderer;

public class Semester3Project extends Game {
	public SpriteBatch batch;
	public World world;
	public GUIController guiController;
	public SpriteRenderer spriteRenderer;

    // UI TEST
    public Skin skin;
    public Table table;
    public TextButton.TextButtonStyle textButtonStyle;
    public TextButton button, button2;
    public Pixmap pixmap;

    @Override
	public void create () {
        Box2D.init();
		world = new World(new Vector2(0, -10f), true);
		batch = new SpriteBatch();
        guiController = new GUIController();
		spriteRenderer = new SpriteRenderer();

        this.setScreen(new MainMenuScreen(this));

		/*
        skin = new Skin();
        // Generate a 1x1 white texture and store it in the skin named "white".
        pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        // Store the default libgdx font under the name "default".
        skin.add("default", new BitmapFont());

        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        // Create a table that fills the screen. Everything else will go inside this table.
        table = new Table();
        table.setFillParent(true);
        table.setWidth(800f);
        table.setHeight(450f);
        table.left().top();
        Gdx.input.setInputProcessor(stage);
        // Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
        button = new TextButton("Click me! 111", skin);
        button2 = new TextButton("Click me! 222", skin);
        //table.add(button);
        //table.row();
       // table.add(button2);
        //table.row();
        */


	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
        //fontBold.dispose();
        //fontLight.dispose();
        //generator.dispose();
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
