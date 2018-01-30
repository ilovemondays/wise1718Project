package de.hshannover.inform.matthiasdietrich.application.controller;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.physics.box2d.*;
import de.hshannover.inform.matthiasdietrich.Semester3Project;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;
import de.hshannover.inform.matthiasdietrich.application.models.CertificateModel;
import de.hshannover.inform.matthiasdietrich.application.models.GameModel;
import de.hshannover.inform.matthiasdietrich.application.models.PlayerActor;
import de.hshannover.inform.matthiasdietrich.application.screens.GameScreen;
import de.hshannover.inform.matthiasdietrich.ui.input.InputController;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Checks winning/loosing condition, sets up controller for all game objects. Cleans up after world ends.
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
    private ProjectileController projectileController;
    private boolean isCollectingCertificate = false;
    private Semester3Project game;

    private RayHandler rayHandler;
    private boolean isSetup = false;
    private GameScreen gameScreen = null;

    private GameController() {
        gameModel = GameModel.getInstance();
        bodiesToDestroy = new ArrayList<Body>();
        lightsToDestroy = new ArrayList<PointLight>();
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

    public void setGameScreen (GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void setLevelController(LevelController levelController) {
        this.levelController = levelController;
    }

    public void setCollisionDetectionController(CollisionDetectionController collisionDetectionController) {
        this.collisionDetectionController = collisionDetectionController;
    }

    public boolean isSetup () {
        return isSetup;
    }

    public void startWorld(World world) {
        if (GameConstants.DEV_MODE) {
            System.out.println("GameController: START WORLD");
        }

        rayHandler = new RayHandler(getWorld());
        nextLevel();

        // SETUP CONTROLLER

        // level
        levelController.setMap(gameModel.getActLevel());

        // player
        if (GameConstants.DEV_MODE) {
            System.out.println("GameController: SETUP PLAYER");
        }
        playerController = PlayerController.getInstance();
        playerController.setInput(InputController.getInstance());
        player = PlayerActor.getInstance(getWorld());
        playerController.setPlayer(player);
        if (GameConstants.DEV_MODE) {
            System.out.println("GameController: PLAYER WAS SET");
        }

        // player position
        levelController.getMapLayerController().setPlayerPosition(getWorld(), player);
        player.spawn();

        // distribute goblins in level
        levelController.spawnGoblins();
        mathGoblinController = MathGoblinController.getInstance();
        mathGoblinController.setGoblins(LevelController.getGoblins());
        mathGoblinController.setWorld(getWorld());

        // set collision detection
        setContactListener(collisionDetectionController);

        //sound.playMusic();
        levelController.getMapLayerController().setLightPosition(getWorld(), rayHandler, levelController.getLightColor());
        rayHandler.setAmbientLight(levelController.getAmbientColor());
        isSetup = true;
    }

    public void endWorld() {
        isSetup = false;
        player.setTired(0);
        projectileController = ProjectileController.getInstance();
        projectileController.clearProjectiles();
        levelController.clear();
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
        GameController.world = world;
    }

    public LevelController getLevelController () {
        return levelController;
    }

    public static ArrayList<Body> getBodiesToDestroy() {
        return bodiesToDestroy;
    }

    private void setBodiesToDestroy(ArrayList<Body> bodiesToDestroy) {
        GameController.bodiesToDestroy = bodiesToDestroy;
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

    public int getOverallTrials() {
        return gameModel.getOverallTrials();
    }

    public void increaseOverallTrials() {
        gameModel.increaseOverallTrials();
    }

    /**
     * Checks and removes box2d bodies from world.
     * Updates Player and MathGoblins
     * Checks if level needs to restart/end or if player reaches next level
     */
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

        if (playerController.isInTrap()) {
            bodiesToDestroy.add(player.getBody());
            player.setTired(0);
            player.setBody(null);
            player.setBodyDef(null);
            player.setPosition(player.getStartingPoint().x, player.getStartingPoint().y);
            player.spawn();
            gameScreen.setPauseTime(-1f);
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

    public boolean checkLevelCompleteCondition () {
        return (gameModel.getCertificatesFound() >= GameConstants.WIN_CONDITION);
    }

    public boolean checkGameWinCondition () {
        return (gameModel.getActLevel() > GameConstants.MAX_LEVELS);
    }

    public boolean checkGameOverCondition() {
        if (gameModel.getTrials() > GameConstants.MAX_TRIALS) {
            resetGameModel();
            destroyBodies();
            destroyLights();
            return true;
        }
        return false;
    }
}
