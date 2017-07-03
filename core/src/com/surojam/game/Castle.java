package com.surojam.game;

/**
 * Created by Islam on 22/05/2016.
 */
public class Castle extends GameObject{
    public static float CASTLE_WIDTH = 1.7f;
    public static float CASTLE_HEIGHT = 1.7f;

    public Castle(float x, float y) {
        super(x, y, CASTLE_WIDTH, CASTLE_HEIGHT);
    }
}
