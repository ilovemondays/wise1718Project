package de.hshannover.inform.matthiasdietrich.application.controller;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import de.hshannover.inform.matthiasdietrich.Semester3Project;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;
import de.hshannover.inform.matthiasdietrich.application.models.CertificateModel;
import de.hshannover.inform.matthiasdietrich.application.models.GameModel;
import de.hshannover.inform.matthiasdietrich.application.models.PlayerActor;
import de.hshannover.inform.matthiasdietrich.ui.input.InputController;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by matthiasdietrich on 24.10.17.
 */
public class GameController implements Observer {
    private static World world;
    private static ArrayList<Body> bodiesToDestroy;
    private static ArrayList<PointLight> lightsToDestroy;
    private static GameController gameController = null;
    private GameModel gameModel;
    private PlayerActor player;
    private PlayerController playerController;
    private LevelController levelController;
    private CollisionDetectionController collisionDetectionController;
    private MathGoblinController mathGoblinController;
    private boolean isCollectingCertificate = false;
    private Semester3Project game;
    // @TODO: Das hier in eine render/light class verschieben
    private RayHandler rayHandler;

    private GameController() {
        world = new World(new Vector2(0, -2f), true);
        gameModel = GameModel.getInstance();
        this.game = game;
    }

    public static GameController getInstance() {
        if (gameController != null) {
            return gameController;
        }
        gameController = new GameController();
        return gameController;
    }

    public void resetGameModel() {
        gameModel.resetGame();

    }
    public int getTrials(){
        return gameModel.getTrials();
    }
    public int getCertificates(){
        return gameModel.getCertificatesFound();
    }
    public int getLevel(){
        return gameModel.getActLevel();
    }

    public void setGame (Semester3Project game) {
        this.game = game;
    }

    public void startWorld(World world) {
        setWorld(world);
        bodiesToDestroy = new ArrayList<Body>();
        lightsToDestroy = new ArrayList<PointLight>();

        rayHandler = new RayHandler(getWorld());
        rayHandler.setAmbientLight(new Color(.1f, .3f, .7f, .4f));

        nextLevel();

        levelController = new LevelController(world);
        collisionDetectionController = new CollisionDetectionController();

        // SETUP CONTROLLER
        // level/map
        levelController.setMap(gameModel.getActLevel());
        // trap layer
        //levelController.getMapLayerController().constructTrapMap(getWorld());

        // player
        playerController = PlayerController.getInstance();
        playerController.setInput(InputController.getInstance());
        player = PlayerActor.getInstance(getWorld());
        playerController.setPlayer(player);

        // player position
        levelController.getMapLayerController().setPlayerPosition(getWorld(), player);
        player.spawn();

        // distribute certificates in level
       // levelController.getMapLayerController().setCertificatesPosition(getWorld(), new CertificateModel());

        // distribute goblins in level
        // @TODO: levelController sollte nicht die goblins spawnen
        levelController.spawnGoblins();
        mathGoblinController = MathGoblinController.getInstance();
        mathGoblinController.setGoblins(LevelController.getGoblins());
        mathGoblinController.setWorld(getWorld());

        // set collision detection
        setContactListener(collisionDetectionController);

        //sound.playMusic();
        levelController.getMapLayerController().setLightPosition(getWorld(), rayHandler);
    }

    public void endWorld() {
        if (GameConstants.DEV_MODE) {
            System.out.println("END WORLD");
        }
        player.setTired(0);
        world.destroyBody(player.getBody());
        levelController.clear();
        //sound.stop();
       // rayHandler.dispose();
        //sound.dispose();
    }

    public void nextLevel() {
        gameModel.increaseLevel();
        gameModel.setTrials(1);
        gameModel.setCertificatesFound(0);
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public LevelController getLevelController () {
        return levelController;
    }

    public static ArrayList<Body> getBodiesToDestroy() {
        return bodiesToDestroy;
    }

    private void setBodiesToDestroy(ArrayList<Body> bodiesToDestroy) {
        this.bodiesToDestroy = bodiesToDestroy;
    }

    public static ArrayList<PointLight> getLightsToDestroy() {
        return lightsToDestroy;
    }

    public static void setLightsToDestroy(ArrayList<PointLight> lightsToDestroy) {
        GameController.lightsToDestroy = lightsToDestroy;
    }

    public RayHandler getRayHandler() {
        return rayHandler;
    }

    public void setRayHandler(RayHandler rayHandler) {
        this.rayHandler = rayHandler;
    }

    public PlayerActor getPlayer() {
        return player;
    }

    public void setPlayer(PlayerActor player) {
        this.player = player;
    }

    public void loop() {
        getWorld().step(1/60f, 1, 1);
        destroyBodies(); // if there are any to destroy, like collected certificates, projectiles...
        destroyLights();
        playerController.updatePlayer();
        mathGoblinController.update();

        if(playerController.playerIsTired()) {
            gameModel.increaseTrials();
            bodiesToDestroy.add(player.getBody());

            player.setTired(0);
            player.setBody(null);
            player.setBodyDef(null);
            player.setPosition(player.getStartingPoint().x, player.getStartingPoint().y);
            player.spawn();

        }

        if(gameModel.getTrials() > 3) {
            endWorld();
        }

    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Fixture && ((Fixture)arg).getUserData() instanceof CertificateModel) {
            Body b = ((Fixture) arg).getBody();
            if(!bodiesToDestroy.contains(b)) {
                bodiesToDestroy.add(b);
            }
            levelController.getCertificates().remove(((Fixture)arg).getUserData());
        }

        if(arg instanceof String) {
            if (arg.equals("certificate-collected-START")) {
                isCollectingCertificate = true;
            }
            if (arg.equals("certificate-collected-END")) {
                if (isCollectingCertificate == true) {
                    isCollectingCertificate = false;
                    gameModel.increaseCertificates();
                    game.assetManager.playSound("sound-certificate");
                }
            }
        }
    }

    public void setContactListener(ContactListener contactListener) {
        getWorld().setContactListener(contactListener);
    }

    public void destroyBodies() {
        for(Body b : getBodiesToDestroy()) {
            getWorld().destroyBody(b);
        }
        getBodiesToDestroy().clear();
    }

    public void destroyLights() {
        for(PointLight p : getLightsToDestroy()) {
            p.remove();
        }
        getLightsToDestroy().clear();
    }

    public boolean checkWinCondition() {
        return (gameModel.getCertificatesFound() >= GameConstants.WIN_CONDITION);
    }

    public boolean checkGameOverCondition() {
        return (gameModel.getTrials() > GameConstants.MAX_TRIALS);
    }
}
