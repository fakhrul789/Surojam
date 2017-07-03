package com.surojam.game;

/**
 * Created by Islam on 21/05/2016.
 */
public class Suro extends DynamicGameObject {
    public static final int SURO_STATE_JUMP = 0;
    public static final int SURO_STATE_FALL = 1;
    public static final int SURO_STATE_HIT = 2;
    public static final float SURO_JUMP_VELOCITY = 11;
    public static final float SURO_MOVE_VELOCITY = 20;
    public static final float SURO_WIDTH = 0.8f;
    public static final float SURO_HEIGHT = 0.8f;

    int state;
    float stateTime;

    public Suro(float x, float y) {
        super(x, y, SURO_WIDTH, SURO_HEIGHT);
        state = SURO_STATE_FALL;
        stateTime = 0;
    }

    public void update (float deltaTime) {
        velocity.add(World.gravity.x * deltaTime, World.gravity.y * deltaTime);
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        bounds.x = position.x - bounds.width / 2;
        bounds.y = position.y - bounds.height / 2;

        if (velocity.y > 0 && state != SURO_STATE_HIT) {
            if (state != SURO_STATE_JUMP) {
                state = SURO_STATE_JUMP;
                stateTime = 0;
            }
        }

        if (velocity.y < 0 && state != SURO_STATE_HIT) {
            if (state != SURO_STATE_FALL) {
                state = SURO_STATE_FALL;
                stateTime = 0;
            }
        }

        if (position.x < 0) position.x = World.WORLD_WIDTH;
        if (position.x > World.WORLD_WIDTH) position.x = 0;

        stateTime += deltaTime;
    }

    public void hitSquirrel () {
        velocity.set(0, 0);
        state = SURO_STATE_HIT;
        stateTime = 0;
    }

    public void hitPlatform () {
        velocity.y = SURO_JUMP_VELOCITY;
        state = SURO_STATE_JUMP;
        stateTime = 0;
    }

    public void hitSpring () {
        velocity.y = SURO_JUMP_VELOCITY * 1.5f;
        state = SURO_STATE_JUMP;
        stateTime = 0;
    }
}
