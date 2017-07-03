package com.surojam.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Islam on 19/05/2016.
 */
public class Animation {
    public static final int ANIMATION_LOOPING = 0;
    public static final int ANIMATION_NONLOOPING = 1;

    final TextureRegion[] keyFrames;
    final float frameDuration;

    public Animation(float frameDuration, TextureRegion... keyFrames){
        this.keyFrames = keyFrames;
        this.frameDuration = frameDuration;
    }

    public TextureRegion getKeyFrame(float stateTime, int mode){
        int framenumbers =(int)(stateTime/frameDuration);

        if(mode == ANIMATION_LOOPING){
            framenumbers = framenumbers % keyFrames.length;
        }else{
            framenumbers = Math.min(keyFrames.length-1,framenumbers);
        }
        return keyFrames[framenumbers];
    }
}
