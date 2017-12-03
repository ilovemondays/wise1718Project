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
    private PlayerActor player;
    private Animation playerRunAnimation;
    private Animation playerIdleAnimation;
    private Animation playerJumpAnimation;
    private TextureRegion playerCurrentFrame;
    private boolean playerFlip;

    private Texture test = new Texture("actors/dummy/dummy.png");

    public SpriteRenderer() {
        playerRunAnimation = SpriteAnimation.create("actors/player/run.png", 1, 7, 0.08f);
        playerIdleAnimation = SpriteAnimation.create("actors/player/idle.png", 1, 6, 0.08f);
        playerJumpAnimation = SpriteAnimation.create("actors/player/jump.png", 1, 1, 0.1f);
    }

    public void setPlayer (PlayerActor player) {
        this.player = player;
    }

    public void render(Batch batch) {
        batch.begin();
        stateTime += Gdx.graphics.getDeltaTime();

       //batch.draw(test, player.getX() , player.getY(), 1, 1);

        // RENDER PLAYER
        playerCurrentFrame = getActPlayerAnimation();
        playerFlip = (playerController.getActDirection() == GameConstants.Direction.LEFT);
         if(player != null) {
            batch.draw(playerCurrentFrame,
                    playerFlip ? player.getX()+0.5f : player.getX() - 0.5f,
                    player.getY() - 0.35f,
                    playerFlip ? -1*1 : 1,
                    1);
        }


        batch.end();
    }

    private TextureRegion getActPlayerAnimation() {
        // RUN
        if (playerController.getActAnimation() == GameConstants.ActAnimation.RUN) {
            return (TextureRegion) playerRunAnimation.getKeyFrame(stateTime, true);
        }
        // JUMP
        if (playerController.getActAnimation() == GameConstants.ActAnimation.JUMP) {
           return (TextureRegion) playerJumpAnimation.getKeyFrame(stateTime, true);
        }
        // IDLE
        if (playerController.getActAnimation() == GameConstants.ActAnimation.IDLE) {
            return (TextureRegion) playerIdleAnimation.getKeyFrame(stateTime, true);
        }
        // DEFAULT IDLE
        return (TextureRegion) playerIdleAnimation.getKeyFrame(stateTime, true);
    }
}
