package de.hshannover.inform.matthiasdietrich.ui.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

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
    private TextButton buttonStartGame;
    private TextButton buttonExitGame;
    private TextButton buttonShowHelp;
    private TextButton.TextButtonStyle buttonStyleDefault;
    private Skin skinButton;
    private Rectangle healthBar;

    // Fonts
    private BitmapFont fontBold;
    private BitmapFont fontLight;
    public FreeTypeFontGenerator generator;
    public FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    public GUIController() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        tableMainMenu = new Table();
        tableMainMenu.setFillParent(true);

        tableGameHUD = new Table();
        tableGameHUD.setFillParent(true);
        tableGameHUD.top().left();

        // SETUP FONTS
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;

        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Raleway-ExtraBold.ttf"));
        fontBold = generator.generateFont(parameter);
        fontBold.setColor(1f, 1f, 1f, 1f);

        parameter.size = 16;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Raleway-Light.ttf"));
        fontLight = generator.generateFont(parameter);

        // GUI ELEMENTS
        // MAIN MENU:
        skinButton = new Skin();
        skinButton.add("default", fontBold);

        buttonStyleDefault = new TextButton.TextButtonStyle();
        buttonStyleDefault.font = skinButton.getFont("default");
        buttonStyleDefault.fontColor = new Color(0xffffffcc);
        buttonStyleDefault.overFontColor = new Color(0xffffffff);
        skinButton.add("default", buttonStyleDefault);

        buttonStartGame = new TextButton("START", skinButton);
        buttonShowHelp = new TextButton("HILFE", skinButton);
        buttonExitGame = new TextButton("ENDE", skinButton);

        tableMainMenu.add(buttonStartGame).pad(10);
        tableMainMenu.row();
        tableMainMenu.add(buttonShowHelp).pad(10);
        tableMainMenu.row();
        tableMainMenu.add(buttonExitGame).pad(10);

        // GAME HUD:
        labelTrials = new Label("", new Label.LabelStyle(fontLight, Color.WHITE));
        labelCertificatesFound = new Label("", new Label.LabelStyle(fontLight, Color.WHITE));
        labelSemester = new Label("", new Label.LabelStyle(fontLight, Color.WHITE));

        healthBar = new Rectangle();
        healthBar.setSize(100, 10);

        Pixmap pixmap = new Pixmap(100, 10, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLUE);
        pixmap.fillRectangle(0, 0, 100, 10);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        tableGameHUD.add(labelTrials).padTop(10).padLeft(10);
        tableGameHUD.add(labelCertificatesFound).padTop(10).padLeft(10);
        tableGameHUD.add(labelSemester).padTop(10).padLeft(10);
        // tableGameHUD.add(texture).padTop(10).padLeft(10);

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

    public void addMeAsObserver(Observer obj) {
        addObserver(obj);
    }
}
