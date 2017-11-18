package de.hshannover.inform.matthiasdietrich.ui.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by matthiasdietrich on 13.11.17.
 */
public class GUIController extends Observable {
    // Stage & Elements
    private Stage stage;
    private Table tableGameHUD;
    private Table tableMainMenu;
    private Label labelTrials;
    private Label labelCertificatesFound;
    private Label labelSemester;
    private Label labelTitle;
    private Label labelSubTitle;
    private TextButton buttonStartGame;
    private TextButton buttonExitGame;
    private TextButton buttonShowHelp;
    private TextButton.TextButtonStyle buttonStyleDefault;
    private Skin skinButton;
    private ProgressBar healthBar;
    private ProgressBar.ProgressBarStyle healthBarStyle;

    // Fonts
    private BitmapFont fontBig;
    private BitmapFont fontSmall;
    private BitmapFont fontTitle;
    public FreeTypeFontGenerator generator;
    public FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    public GUIController() {
        stage = new Stage();
        // stage.setDebugAll(true);
        Gdx.input.setInputProcessor(stage);

        tableMainMenu = new Table();
        tableMainMenu.setFillParent(true);

        tableGameHUD = new Table();
        tableGameHUD.setFillParent(true);
        tableGameHUD.top().left();

        // TEXTURES
        Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Skin skin = new Skin();
        skin.add("white", new Texture(pixmap));

        // SETUP FONTS
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 20;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/WildYouth-Regular.otf"));
        fontBig = generator.generateFont(parameter);
        fontBig.setColor(1f, 1f, 1f, 1f);

        parameter.size = 16;
        fontSmall = generator.generateFont(parameter);

        parameter.size = 128;
        parameter.color = new Color(0xff3377ff);
        parameter.shadowColor = new Color(0x333333ff);
        parameter.shadowOffsetX = 1;
        parameter.shadowOffsetY = 1;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Last-Paradise.otf"));
        fontTitle = generator.generateFont(parameter);

        // GUI ELEMENTS
        // MAIN MENU:
        skinButton = new Skin();
        skinButton.add("default", fontBig);

        buttonStyleDefault = new TextButton.TextButtonStyle();
        buttonStyleDefault.up = skin.newDrawable("white", new Color(0xff3377ff));
        buttonStyleDefault.over = skin.newDrawable("white", new Color(0x333333ff));
        buttonStyleDefault.font = skinButton.getFont("default");
        buttonStyleDefault.fontColor = new Color(0x333333ff);
        buttonStyleDefault.overFontColor = new Color(0xff3377ff);
        skinButton.add("default", buttonStyleDefault);

        buttonStartGame = new TextButton("START", skinButton);
        buttonStartGame.pad(10);
        buttonShowHelp = new TextButton("HILFE", skinButton);
        buttonShowHelp.pad(10);
        buttonExitGame = new TextButton("ENDE", skinButton);
        buttonExitGame.pad(10);

        labelTitle = new Label("Study Race", new Label.LabelStyle(fontTitle, Color.WHITE));
        labelSubTitle = new Label("A Jump And Run Game", new Label.LabelStyle(fontSmall, Color.WHITE));

        // tableMainMenu.setDebug(true);
        tableMainMenu.add(labelTitle).colspan(3);
        tableMainMenu.row();
        tableMainMenu.add(labelSubTitle).colspan(3).padBottom(50).left();
        tableMainMenu.row();
        tableMainMenu.add(buttonStartGame).pad(10);
        tableMainMenu.add(buttonShowHelp).pad(10);
        tableMainMenu.add(buttonExitGame).pad(10);

        // GAME HUD:
        labelTrials = new Label("", new Label.LabelStyle(fontSmall, Color.WHITE));
        labelCertificatesFound = new Label("", new Label.LabelStyle(fontSmall, Color.WHITE));
        labelSemester = new Label("", new Label.LabelStyle(fontSmall, Color.WHITE));

        TextureRegionDrawable textureBar = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("testHandle.png"))));

        healthBarStyle = new ProgressBar.ProgressBarStyle(skin.newDrawable("white", Color.GRAY), textureBar);
        healthBarStyle.knobBefore = healthBarStyle.knob;
        healthBar = new ProgressBar(0, 1f, 0.1f,false, healthBarStyle);
        healthBar.setSize(100f, 16);
        healthBar.setAnimateDuration(0.3f);

        tableGameHUD.add(labelTrials).padTop(10).padLeft(10);
        tableGameHUD.add(labelCertificatesFound).padTop(10).padLeft(10);
        tableGameHUD.add(labelSemester).padTop(10).padLeft(10);
        tableGameHUD.add(healthBar).padTop(10).padLeft(10);

        // ADD MAIN MENU LISTENER
        buttonStartGame.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                setChanged();
                notifyObservers("button-start-game");
            }
        });
        buttonShowHelp.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                setChanged();
                notifyObservers("button-show-help");
            }
        });
        buttonExitGame.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                setChanged();
                notifyObservers("button-exit-game");
            }
        });
    }

    public Label getLabelTrials() {
        return labelTrials;
    }

    public Label getLabelCertificatesFound() {
        return labelCertificatesFound;
    }

    public Label getLabelSemester() {
        return labelSemester;
    }

    public Stage getGameStage() {
        stage.clear();
        stage.addActor(tableGameHUD);
        return stage;
    }

    public Stage getMainMenuStage() {
        stage.clear();
        stage.addActor(tableMainMenu);
        return stage;
    }

    public ProgressBar getHealthBar() {
        return healthBar;
    }

    public void setPlayerHealth(float playerHealth) {
        healthBar.setValue(playerHealth);
    }

    public void addMeAsObserver(Observer obj) {
        addObserver(obj);
    }
}
