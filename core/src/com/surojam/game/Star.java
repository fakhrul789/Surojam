package com.surojam.game;

/**
 * Created by Islam on 23/05/2016.
 */
public class Star extends GameObject{
    public static final float STAR_WIDTH = 0.5f;
    public static final float STAR_HEIGHT = 0.8f;

    float stateTime;

    public Star(float x, float y){
        super (x,y,STAR_WIDTH,STAR_HEIGHT);
        stateTime = 0;
    }

    public void update(float deltaTime){
        stateTime += deltaTime;
    }
}
