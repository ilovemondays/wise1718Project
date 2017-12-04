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
import com.badlogic.gdx.utils.Align;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;

import java.util.Observable;
import java.util.Observer;

/**
 * GUI Controller is responsible for all UI Elements the user sees and can click
 */
public class GUIController extends Observable {
    // Stage & Elements
    private Stage stage;

    private Table tableGameHUD;
    private Table tableMainMenu;
    private Table tableLevelCompletedMenu;
    private Table tableGameOverMenu;
    private Table tableGameWinMenu;
    private Table tableHelpScreen;

    private TextButton.TextButtonStyle buttonStyleDefault;
    private Skin skinButton;
    Skin skin = new Skin();

    // game
    private Label labelTrials;
    private Label labelCertificatesFound;
    private Label labelSemester;
    private ProgressBar healthBar;
    private ProgressBar.ProgressBarStyle healthBarStyle;
    private TextButton buttonGameResume;
    private TextButton buttonGameExit;

    // main menu
    private Label labelTitle;
    private Label labelSubTitle;
    private TextButton buttonStartGame;
    private TextButton buttonExitGame;
    private TextButton buttonShowHelp;

    // help screen
    private Label labelHelpTitle;
    private Label labelHelpText;
    private TextButton buttonHelpBack;

    // level completed
    private Label labelCompletedTop;
    private Label labelCompletedBottom;
    private TextButton buttonLevelCompletedNext;

    // game over
    private Label labelGameOverTop;
    private Label labelGameOverBottom;
    private TextButton buttonGameOver;

    // game win
    private Label labelGameWinTop;
    private Label labelGameWinBottom;
    private TextButton buttonGameWin;

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

        tableGameOverMenu = new Table();
        tableGameOverMenu.setFillParent(true);

        tableGameWinMenu = new Table();
        tableGameWinMenu.setFillParent(true);

        tableHelpScreen = new Table();
        tableHelpScreen.setFillParent(true);

        tableGameHUD = new Table();
        tableGameHUD.setFillParent(true);
        tableGameHUD.columnDefaults(5);
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
        parameter.color = new Color(0xff0071ff);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Last-Paradise.otf"));
        fontTitle = generator.generateFont(parameter);

        // GUI ELEMENTS
        // Default Button
        skinButton = new Skin();
        skinButton.add("default", fontBig);

        buttonStyleDefault = new TextButton.TextButtonStyle();
        buttonStyleDefault.up = skin.newDrawable("white", new Color(0xff0071ff));
        buttonStyleDefault.over = skin.newDrawable("white", new Color(0x333333ff));
        buttonStyleDefault.font = skinButton.getFont("default");
        skinButton.add("default", buttonStyleDefault);

        // MAIN MENU:
       setupMainMenu();

       // HELP SCREEN
        setupHelpUi();

        // GAME HUD:
        setupGameGui();

        // LEVEL COMPLETED
        setupLevelCompletedUi();

        // GAME OVER
        setupGameOverUi();

        // GAME WIN
        setupGameWinUi();
    }

    public Label getLabelTrials() {
        return labelTrials;
    }

    public Label getLabelGameWinBottom() {
        return labelGameWinBottom;
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

    public TextButton getButtonGameResume () {
        return buttonGameResume;
    }

    public TextButton getButtonGameExit () {
        return buttonGameExit;
    }

    public void setGameStage() {
        stage.clear();
        stage.addActor(tableGameHUD);
    }

    public void setMainMenuStage() {
        stage.clear();
        stage.addActor(tableMainMenu);
    }

    public void setHelpScreenStage() {
        stage.clear();
        stage.addActor(tableHelpScreen);
    }

    public void setLevelCompletedStage() {
        if (GameConstants.DEV_MODE) {
            System.out.println("set level completed stage");
        }
        stage.clear();
        stage.addActor(tableLevelCompletedMenu);
    }

    public void setGameOverStage() {
        stage.clear();
        stage.addActor(tableGameOverMenu);
    }
    public void setGameWinStage() {
        stage.clear();
        stage.addActor(tableGameWinMenu);
    }

    /**
     * Returns the set stage object
     * @return Stage
     */
    public Stage getActStage() {
        return stage;
    }

    public ProgressBar getHealthBar() {
        return healthBar;
    }

    /**
     * Sets ProgressBar to given value
     * @param playerHealth
     */
    public void setPlayerHealth(float playerHealth) {
        healthBar.setValue(playerHealth);
    }

    public void addMeAsObserver(Observer obj) {
        addObserver(obj);
    }

    /**
     * GameOver Screen UI
     */
    private void setupGameOverUi () {
        buttonGameOver = new TextButton("ZUM HAUPTMENÜ", skinButton);
        buttonGameOver.pad(10);
        buttonGameOver.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                setChanged();
                notifyObservers("button-gameover");
            }
        });
        labelGameOverTop = new Label("Exmatrikuliert", new Label.LabelStyle(fontBig, Color.WHITE));
        labelGameOverBottom = new Label("Das war dein letzter Versuch.", new Label.LabelStyle(fontBig, Color.WHITE));
        tableGameOverMenu.add(labelGameOverTop).pad(10);
        tableGameOverMenu.row();
        tableGameOverMenu.add(labelGameOverBottom).pad(10);
        tableGameOverMenu.row();
        tableGameOverMenu.add(buttonGameOver).pad(10);
    }

    /**
     * Win Screen UI
     */
    private void setupGameWinUi () {
        buttonGameWin = new TextButton("ZUM HAUPTMENÜ", skinButton);
        buttonGameWin.pad(10);
        buttonGameWin.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                setChanged();
                notifyObservers("button-gamewin");
            }
        });
        labelGameWinTop = new Label("Glüsckwunsch, das war der letzte Schein!", new Label.LabelStyle(fontBig, Color.WHITE));
        labelGameWinBottom = new Label("", new Label.LabelStyle(fontBig, Color.WHITE));
        tableGameWinMenu.add(labelGameWinTop).pad(10);
        tableGameWinMenu.row();
        tableGameWinMenu.add(labelGameWinBottom).pad(10);
        tableGameWinMenu.row();
        tableGameWinMenu.add(buttonGameWin).pad(10);
    }

    /**
     * Main Menu UI
     */
    private void setupMainMenu() {
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

        // ADD MAIN MENU LISTENER
        buttonStartGame.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                if (GameConstants.DEV_MODE) {
                    System.out.println("BUTTON START GAME LISTENER ADDED");
                }
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

    /**
     * GameScreen UI
     */
    private void setupGameGui() {
        Label labelTired = new Label("Erschöpfung:", new Label.LabelStyle(fontSmall, Color.WHITE));
        labelTrials = new Label("", new Label.LabelStyle(fontSmall, Color.WHITE));
        labelCertificatesFound = new Label("", new Label.LabelStyle(fontSmall, Color.WHITE));
        labelSemester = new Label("", new Label.LabelStyle(fontSmall, Color.WHITE));

        TextureRegionDrawable textureBar = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("testHandle.png"))));

        healthBarStyle = new ProgressBar.ProgressBarStyle(skin.newDrawable("white", Color.GRAY), textureBar);
        healthBarStyle.knobBefore = healthBarStyle.knob;
        healthBar = new ProgressBar(0, 1f, 0.1f,false, healthBarStyle);
        healthBar.setSize(100f, 16);
        healthBar.setAnimateDuration(0.3f);

        buttonGameResume = new TextButton("ZURÜCK ZUM SPIEL", buttonStyleDefault);
        buttonGameResume.pad(10).setVisible(false);
        buttonGameExit = new TextButton("SPIEL BEENDEN", buttonStyleDefault);
        buttonGameExit.pad(10).setVisible(false);

        tableGameHUD.add(labelTrials).padTop(10).padLeft(10);
        tableGameHUD.add(labelCertificatesFound).padTop(10).padLeft(10);
        tableGameHUD.add(labelSemester).padTop(10).padLeft(10);
        tableGameHUD.add(labelTired).padTop(10).padLeft(10).align(Align.right);
        tableGameHUD.add(healthBar).padTop(10).padLeft(10).align(Align.left);
        tableGameHUD.row().expandX();
        tableGameHUD.add(buttonGameResume).colspan(5).padTop(100);
        tableGameHUD.row();
        tableGameHUD.add(buttonGameExit).colspan(5).padTop(20);

        buttonGameResume.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                setChanged();
                notifyObservers("button-game-resume");
            }
        });
        buttonGameExit.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                setChanged();
                notifyObservers("button-game-exit-to-menu");
            }
        });
    }

    /**
     * Level Completed UI
     */
    private void setupLevelCompletedUi() {
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

    /**
     * Help Screen UI
     */
    private void setupHelpUi() {
        Label.LabelStyle style = new Label.LabelStyle(fontSmall, Color.BLACK);
        Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0xffffff99));
        pixmap.fill();

        labelHelpTitle = new Label("Anleitung", new Label.LabelStyle(fontBig, new Color(0xff0071ff)));
        labelHelpText = new Label("In diesem Spiel geht es darum in jedem Semester (Level) 5 Scheine zu sammeln.\n\n " +
                "Dafür hat man 3 Versuche je Semester. Fallen und Mathekobolden gilt es auszuweichen,\n\n " +
                "denn diese erschöpfen die Spielfigur. Ist die Erschöpfungsleiste komplett gefüllt,\n\n" +
                " startet ein neuer Versuch, solange dies nicht der dritte war.\n\n" +
                "Einmal eingesammelte Scheine müssen nicht erneut gesammelt werden.\n\n" +
                "Es gibt 6 Semester. Gesteuert wird mit den Pfeiltasten.\n\n " +
                "Mit ESC gelangt man wieder in das Hauptmenu. Spielfortschritt geht dabei verloren.", style);
        buttonHelpBack = new TextButton("ZURÜCK", skinButton);
        buttonHelpBack.pad(10);
        tableHelpScreen.add(labelHelpTitle).padBottom(20);
        tableHelpScreen.row();
        tableHelpScreen.setBackground(new Image(new Texture(pixmap)).getDrawable());
        tableHelpScreen.add(labelHelpText).padBottom(20);
        tableHelpScreen.row();
        tableHelpScreen.add(buttonHelpBack);

        buttonHelpBack.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                setChanged();
                notifyObservers("button-help-back");
            }
        });
    }
}
