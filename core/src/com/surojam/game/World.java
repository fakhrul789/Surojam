package com.surojam.game;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Islam on 21/05/2016.
 */
public class World {
    public interface WorldListener {
        public void jump ();

        public void highJump ();

        public void hit ();

        public void coin ();

        public void star();
    }

    public static final float WORLD_WIDTH = 10;
    public static final float WORLD_HEIGHT = 15 * 20;
    public static final int WORLD_STATE_RUNNING = 0;
    public static final int WORLD_STATE_NEXT_LEVEL = 1;
    public static final int WORLD_STATE_GAME_OVER = 2;
    public static final int WORLD_STATE_TOUCH_STAR = 3;
    public static final int WORLD_STATE_SHOW_QUESTION = 4;
    public static final Vector2 gravity = new Vector2(0, -12);

    public final Suro suro;
    public final List<Platform> platforms;
    public final List<Spring> springs;
    public final List<Enemy> squirrels;
    public final List<Coin> coins;
    public final List<Star> stars;
    public Castle castle;
    public final WorldListener listener;
    public final Random rand;

    public float heightSoFar;
    public int score;
    public int state;
    public int touchStar = 0;

    public World (WorldListener listener) {
        this.suro = new Suro(5, 1);
        this.platforms = new ArrayList<Platform>();
        this.springs = new ArrayList<Spring>();
        this.squirrels = new ArrayList<Enemy>();
        this.coins = new ArrayList<Coin>();
        this.stars = new ArrayList<Star>();
        this.listener = listener;
        rand = new Random();
        generateLevel();

        this.heightSoFar = 0;
        this.score = 0;
        this.state = WORLD_STATE_RUNNING;
    }

    private void generateLevel () {
        float y = Platform.PLATFORM_HEIGHT / 2;
        float maxJumpHeight = Suro.SURO_JUMP_VELOCITY * Suro.SURO_JUMP_VELOCITY / (2 * -gravity.y);
        while (y < WORLD_HEIGHT - WORLD_WIDTH / 2) {
            int num = 0;
            if(rand.nextFloat() > 0.25f) num = Platform.PLATFORM_NUM_1;
            if(rand.nextFloat() > 0.5f) num = Platform.PLATFORM_NUM_2;
            if(rand.nextFloat() > 0.75f) num = Platform.PLATFORM_NUM_3;
            if(rand.nextFloat() > 0.9f) num = Platform.PLATFORM_NUM_4;
            int type = rand.nextFloat() > 0.8f ? Platform.PLATFORM_TYPE_MOVING : Platform.PLATFORM_TYPE_STATIC;
            float x = rand.nextFloat() * (WORLD_WIDTH - Platform.PLATFORM_WIDTH) + Platform.PLATFORM_WIDTH / 2;

            Platform platform = new Platform(num,type, x, y);
            platforms.add(platform);

            if (rand.nextFloat() > 0.9f && type != Platform.PLATFORM_TYPE_MOVING) {
                Spring spring = new Spring(platform.position.x, platform.position.y + Platform.PLATFORM_HEIGHT);
                springs.add(spring);
            }

            if (y > WORLD_HEIGHT / 3 && rand.nextFloat() > 0.8f) {
                Enemy enemy = new Enemy(platform.position.x + rand.nextFloat(), platform.position.y
                        + Enemy.ENEMY_HEIGHT + rand.nextFloat() * 2);
                squirrels.add(enemy);
            }

            if(y > WORLD_HEIGHT / 10 && rand.nextFloat() > 0.8f){
                Star star = new Star(platform.position.x + rand.nextFloat(), platform.position.y + Star.STAR_HEIGHT
                + rand.nextFloat() * 3);
                stars.add(star);
            }

            if (rand.nextFloat() > 0.3f) {
                Coin coin = new Coin(platform.position.x + rand.nextFloat(), platform.position.y + Coin.COIN_HEIGHT
                        + rand.nextFloat() * 3);
                coins.add(coin);
            }

            y += (maxJumpHeight - 0.5f);
            y -= rand.nextFloat() * (maxJumpHeight / 3);
        }

        castle = new Castle(WORLD_WIDTH / 2, y);
    }

    public void update (float deltaTime, float accelX) {
        updateBob(deltaTime, accelX);
        updatePlatforms(deltaTime);
        updateSquirrels(deltaTime);
        updateCoins(deltaTime);
        updateStar(deltaTime);
        if (suro.state != Suro.SURO_STATE_HIT) checkCollisions();
        checkGameOver();
    }

    private void updateStar(float deltaTime) {
        int len = stars.size();
        for (int i = 0; i < len; i++) {
            Star star = stars.get(i);
            star.update(deltaTime);
        }
    }

    private void updateBob (float deltaTime, float accelX) {
        if (suro.state != Suro.SURO_STATE_HIT && suro.position.y <= 0.5f) suro.hitPlatform();
        if (suro.state != Suro.SURO_STATE_HIT) suro.velocity.x = -accelX / 10 * suro.SURO_MOVE_VELOCITY;
        suro.update(deltaTime);
        heightSoFar = Math.max(suro.position.y, heightSoFar);
    }

    private void updatePlatforms (float deltaTime) {
        int len = platforms.size();
        for (int i = 0; i < len; i++) {
            Platform platform = platforms.get(i);
            platform.update(deltaTime);
            if (platform.state == Platform.PLATFORM_STATE_PULVERIZING && platform.stateTime > Platform.PLATFORM_PULVERIZING_TIME) {
                platforms.remove(platform);
                len = platforms.size();
            }
        }
    }

    private void updateSquirrels (float deltaTime) {
        int len = squirrels.size();
        for (int i = 0; i < len; i++) {
            Enemy squirrel = squirrels.get(i);
            squirrel.update(deltaTime);
        }
    }

    private void updateCoins (float deltaTime) {
        int len = coins.size();
        for (int i = 0; i < len; i++) {
            Coin coin = coins.get(i);
            coin.update(deltaTime);
        }
    }

    private void checkCollisions () {
        checkPlatformCollisions();
        checkSquirrelCollisions();
        checkItemCollisions();
        checkCastleCollisions();
    }

    private void checkPlatformCollisions () {
        if (suro.velocity.y > 0) return;

        int len = platforms.size();
        for (int i = 0; i < len; i++) {
            Platform platform = platforms.get(i);
            if (suro.position.y > platform.position.y) {
                if (suro.bounds.overlaps(platform.bounds)) {
                    suro.hitPlatform();
                    listener.jump();
                    if (rand.nextFloat() > 0.5f) {
                        platform.pulverize();
                    }
                    break;
                }
            }
        }
    }

    private void checkSquirrelCollisions () {
        int len = squirrels.size();
        for (int i = 0; i < len; i++) {
            Enemy squirrel = squirrels.get(i);
            if (squirrel.bounds.overlaps(suro.bounds)) {
                suro.hitSquirrel();
                listener.hit();
            }
        }
    }

    private void checkItemCollisions () {
        int len = coins.size();
        for (int i = 0; i < len; i++) {
            Coin coin = coins.get(i);
            if (suro.bounds.overlaps(coin.bounds)) {
                coins.remove(coin);
                len = coins.size();
                listener.coin();
                score += Coin.COIN_SCORE;
            }

        }

        len = stars.size();
        for(int i=0;i<len;i++){
            Star star = stars.get(i);
            if(suro.bounds.overlaps(star.bounds)){
                stars.remove(star);
                len = stars.size();
                listener.star();
                touchStar += 1;
                state = WORLD_STATE_TOUCH_STAR;
            }
        }

        if (suro.velocity.y > 0) return;

        len = springs.size();
        for (int i = 0; i < len; i++) {
            Spring spring = springs.get(i);
            if (suro.position.y > spring.position.y) {
                if (suro.bounds.overlaps(spring.bounds)) {
                    suro.hitSpring();
                    listener.highJump();
                }
            }
        }
    }

    private void checkCastleCollisions () {
        if (castle.bounds.overlaps(suro.bounds)) {
            state = WORLD_STATE_NEXT_LEVEL;
        }
    }

    private void checkGameOver () {
        if (heightSoFar - 7.5f > suro.position.y) {
            state = WORLD_STATE_GAME_OVER;
        }
    }
}
