package de.hshannover.inform.matthiasdietrich.ui.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.hshannover.inform.matthiasdietrich.application.constants.GameConstants;
import de.hshannover.inform.matthiasdietrich.application.controller.PlayerController;
import com.badlogic.gdx.graphics.g2d.Animation;
import de.hshannover.inform.matthiasdietrich.application.models.PlayerActor;
import de.hshannover.inform.matthiasdietrich.util.SpriteAnimation;

public class SpriteRenderer {
    private float stateTime = 0f;

    // Player Animation
    private PlayerController playerController = PlayerController.getInstance();
    private PlayerActor player = playerController.getPlayer();
    private Animation playerRunAnimation;
    private TextureRegion playerCurrentFrame;
    private boolean playerFlip;

    private Texture test = new Texture("actors/dummy/dummy.png");

    public SpriteRenderer() {
        playerRunAnimation = SpriteAnimation.create("actors/player/run.png", 1, 3, 0.1f);
    }

    public void render(Batch batch) {
        batch.begin();
        stateTime += Gdx.graphics.getDeltaTime();

       //batch.draw(test, player.getX() , player.getY(), 1, 1);

        // RENDER PLAYER
        playerCurrentFrame = (TextureRegion) playerRunAnimation.getKeyFrame(stateTime, true);
        playerFlip = (playerController.getActDirection() == GameConstants.Direction.LEFT);
        batch.draw(playerCurrentFrame,
                playerFlip ? player.getX()+0.5f : player.getX() - 0.5f,
                player.getY() - 0.5f,
                playerFlip ? -1*1 : 1,
                1);

        batch.end();
    }
}
