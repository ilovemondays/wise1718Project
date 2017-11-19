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
import com.badlogic.gdx.utils.viewport.Viewport;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;

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
    private Table tableLevelCompletedMenu;
    // game
    private Label labelTrials;
    private Label labelCertificatesFound;
    private Label labelSemester;
    // main menu
    private Label labelTitle;
    private Label labelSubTitle;
    // level completed
    private Label labelCompletedTop;
    private Label labelCompletedBottom;

    private TextButton buttonStartGame;
    private TextButton buttonExitGame;
    private TextButton buttonShowHelp;
    private TextButton buttonLevelCompletedNext;
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
        if (GameConstants.DEV_MODE) {
            System.out.println("NEW GUI CONTROLLER");
            stage.setDebugAll(true);
        }
        Gdx.input.setInputProcessor(stage);

        tableMainMenu = new Table();
        tableMainMenu.setFillParent(true);

        tableLevelCompletedMenu = new Table();
        tableLevelCompletedMenu.setFillParent(true);

        tableGameHUD = new Table();
        tableGameHUD.setFillParent(true);
        tableGameHUD.top().left();
        if (GameConstants.DEV_MODE) {
            tableMainMenu.setDebug(true);
            tableLevelCompletedMenu.setDebug(true);
            tableGameHUD.setDebug(true);
        }

        // TEXTURES
        Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Skin skin = new Skin();
        skin.add("white", new Texture(pixmap));

        // SETUP FONTS
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/WildYouth-Regular.otf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 16;
        fontSmall = generator.generateFont(parameter);

        parameter.size = 20;
        parameter.shadowColor = new Color(0x333333ff);
        parameter.shadowOffsetX = 2;
        parameter.shadowOffsetY = 2;
        fontBig = generator.generateFont(parameter);
        fontBig.setColor(1f, 1f, 1f, 1f);

        parameter.size = 128;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = 3;
        parameter.color = new Color(0xff3377ff);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Last-Paradise.otf"));
        fontTitle = generator.generateFont(parameter);

        // GUI ELEMENTS
        // Default Button
        skinButton = new Skin();
        skinButton.add("default", fontBig);

        buttonStyleDefault = new TextButton.TextButtonStyle();
        buttonStyleDefault.up = skin.newDrawable("white", new Color(0xff3377ff));
        buttonStyleDefault.over = skin.newDrawable("white", new Color(0x333333ff));
        buttonStyleDefault.font = skinButton.getFont("default");
        skinButton.add("default", buttonStyleDefault);

        // MAIN MENU:
        buttonStartGame = new TextButton("START", skinButton);
        buttonStartGame.pad(10);
        buttonShowHelp = new TextButton("HILFE", skinButton);
        buttonShowHelp.pad(10);
        buttonExitGame = new TextButton("ENDE", skinButton);
        buttonExitGame.pad(10);

        labelTitle = new Label("Study Race", new Label.LabelStyle(fontTitle, Color.WHITE));
        labelSubTitle = new Label("A Jump And Run Game", new Label.LabelStyle(fontBig, Color.WHITE));

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

        // LEVEL COMPLETED
        buttonLevelCompletedNext = new TextButton("WEITER", skinButton);
        buttonLevelCompletedNext.pad(10);
        buttonLevelCompletedNext.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                if (GameConstants.DEV_MODE) {
                    System.out.println("BUTTON NEXT LISTENER ADDED");
                }
                setChanged();
                notifyObservers("button-levelCompleted-next");
            }
        });
        labelCompletedTop = new Label("", new Label.LabelStyle(fontBig, Color.WHITE));
        labelCompletedBottom = new Label("", new Label.LabelStyle(fontBig, Color.WHITE));
        tableLevelCompletedMenu.add(labelCompletedTop).pad(10);
        tableLevelCompletedMenu.row();
        tableLevelCompletedMenu.add(labelCompletedBottom).pad(10);
        tableLevelCompletedMenu.row();
        tableLevelCompletedMenu.add(buttonLevelCompletedNext).pad(10);
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

    public Label getLabelCompletedTop () {
        return labelCompletedTop;
    }

    public Label getLabelCompletedBottom () {
        return labelCompletedBottom;
    }

    public void setGameStage() {
        stage.clear();
        stage.addActor(tableGameHUD);
    }

    public void setMainMenuStage() {
        stage.clear();
        stage.addActor(tableMainMenu);
    }

    public void setLevelCompletedStage() {
        if (GameConstants.DEV_MODE) {
            System.out.println("set level completed stage");
        }
        stage.clear();
        stage.addActor(tableLevelCompletedMenu);
    }

    public Stage getActStage() {
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
