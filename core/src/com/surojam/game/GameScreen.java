package com.surojam.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.surojam.game.World.WorldListener;

/**
 * Created by Islam on 21/05/2016.
 */
public class GameScreen extends ScreenAdapter {
    static final int GAME_READY = 0;
    static final int GAME_RUNNING = 1;
    static final int GAME_PAUSED = 2;
    static final int GAME_LEVEL_END = 3;
    static final int GAME_OVER = 4;
    static final int GAME_TOUCH_STAR = 5;
    static final int GAME_SHOW_QUESTION = 6;

    SuroJam game;

    int state;
    OrthographicCamera guiCam;
    Vector3 touchPoint;
    World world;
    WorldListener worldListener;
    WorldRenderer renderer;
    Rectangle pauseBounds;
    Rectangle resumeBounds;
    Rectangle quitBounds;
    Rectangle bener;
    Rectangle salah;
    int lastScore;
    String scoreString;

    GlyphLayout glyphLayout = new GlyphLayout();

    public GameScreen (SuroJam game) {
        this.game = game;

        state = GAME_READY;

        guiCam = new OrthographicCamera(320, 480);
        guiCam.position.set(320 / 2, 480 / 2, 0);
        touchPoint = new Vector3();
        worldListener = new WorldListener() {
            @Override
            public void jump () {
                Asset.playSound(Asset.jumpSound);
            }

            @Override
            public void highJump () {
                Asset.playSound(Asset.highJumpSound);
            }

            @Override
            public void hit () {

            }

            @Override
            public void coin () {
                Asset.playSound(Asset.coinSound);
            }

            @Override
            public void star() {
                Asset.playSound(Asset.bintangSound);
            }
        };
        world = new World(worldListener);
        renderer = new WorldRenderer(game.batch, world);
        pauseBounds = new Rectangle(320 - 64, 480 - 64, 64, 64);
        resumeBounds = new Rectangle(160 - 96, 240, 192, 36);
        quitBounds = new Rectangle(160 - 96, 240 - 36, 192, 36);

        lastScore = 0;
        scoreString = "SCORE: 0";
    }

    public void update (float deltaTime) {
        if (deltaTime > 0.1f) deltaTime = 0.1f;

        switch (state) {
            case GAME_READY:
                updateReady();
                break;
            case GAME_RUNNING:
                updateRunning(deltaTime);
                break;
            case GAME_PAUSED:
                updatePaused();
                break;
            case GAME_TOUCH_STAR:
                updateInfo();
                break;
            case GAME_LEVEL_END:
                updateLevelEnd();
                break;
            case GAME_OVER:
                updateGameOver();
                break;
        }
    }

    private void updateInfo() {
        if(Gdx.input.justTouched()){
            guiCam.unproject(touchPoint.set(Gdx.input.getX(),Gdx.input.getY(),0));
            Gdx.app.log("state",String.valueOf(world.touchStar));

            if(world.touchStar == 1 || world.touchStar == 3 || world.touchStar == 5 || world.touchStar == 7 || world.touchStar == 9){
                state = GAME_RUNNING;
                world.state = World.WORLD_STATE_RUNNING;
                return;
            }

            if(world.touchStar == 2 || world.touchStar == 4 || world.touchStar == 8 || world.touchStar == 10 || world.touchStar == 6){
                if(bener.contains(touchPoint.x,touchPoint.y)){
                    Asset.playSound(Asset.benarSound);
                    state = GAME_RUNNING;
                    world.state = World.WORLD_STATE_RUNNING;
                    return;
                }

                if(salah.contains(touchPoint.x,touchPoint.y)){
                    Asset.playSound(Asset.hitSound);
                    state = GAME_OVER;
                    world.state = World.WORLD_STATE_GAME_OVER;
                    return;
                }
            }
        }
    }

    private void updateReady () {
        if (Gdx.input.justTouched()) {
            state = GAME_RUNNING;
        }
    }

    private void updateRunning (float deltaTime) {
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (pauseBounds.contains(touchPoint.x, touchPoint.y)) {
                Asset.playSound(Asset.clickSound);
                state = GAME_PAUSED;
                return;
            }
        }

        Application.ApplicationType appType = Gdx.app.getType();

        // should work also with Gdx.input.isPeripheralAvailable(Peripheral.Accelerometer)
        if (appType == Application.ApplicationType.Android || appType == Application.ApplicationType.iOS) {
            world.update(deltaTime, Gdx.input.getAccelerometerX());
        } else {
            float accel = 0;
            if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) accel = 5f;
            if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) accel = -5f;
            world.update(deltaTime, accel);
        }
        if (world.score != lastScore) {
            lastScore = world.score;
            scoreString = "SCORE: " + lastScore;
        }
        if(world.state == World.WORLD_STATE_TOUCH_STAR){
            state = GAME_TOUCH_STAR;
        }
        if(world.state == World.WORLD_STATE_SHOW_QUESTION){
            state = GAME_SHOW_QUESTION;
        }
        if (world.state == World.WORLD_STATE_NEXT_LEVEL) {
            Asset.playSound(Asset.menangSound);
            state = GAME_LEVEL_END;
        }
        if (world.state == World.WORLD_STATE_GAME_OVER) {
            state = GAME_OVER;
            Asset.playSound(Asset.hitSound);
            if (lastScore >= Setting.highscores[4])
                scoreString = "NEW HIGHSCORE: " + lastScore;
            else
                scoreString = "SCORE: " + lastScore;
            Setting.addScore(lastScore);
            Setting.save();
        }
    }

    private void updatePaused () {
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (resumeBounds.contains(touchPoint.x, touchPoint.y)) {
                Asset.playSound(Asset.clickSound);
                state = GAME_RUNNING;
                return;
            }

            if (quitBounds.contains(touchPoint.x, touchPoint.y)) {
                Asset.playSound(Asset.clickSound);
                game.setScreen(new MainMenuScreen(game));
                return;
            }
        }
    }

    private void updateLevelEnd () {
        if (Gdx.input.justTouched()) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    private void updateGameOver () {
        if (Gdx.input.justTouched()) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    public void draw () {
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        guiCam.update();
        game.batch.setProjectionMatrix(guiCam.combined);
        game.batch.enableBlending();
        game.batch.begin();
        switch (state) {
            case GAME_READY:
                presentReady();
                break;
            case GAME_RUNNING:
                presentRunning();
                break;
            case GAME_PAUSED:
                presentPaused();
                break;
            case GAME_TOUCH_STAR :
                presentInfo();
                break;
            case GAME_SHOW_QUESTION :
                showQuest();
                break;
            case GAME_LEVEL_END:
                presentLevelEnd();
                break;
            case GAME_OVER:
                presentGameOver();
                break;
        }
        game.batch.end();
    }

    private void showQuest() {
        game.batch.draw(Asset.soal[0],5,100,300,275);
    }

    private void presentInfo() {
        switch (world.touchStar){
            case 1 : game.batch.draw(Asset.info[0],22,100,275,275);break;
            case 2 : game.batch.draw(Asset.soal[0],22,140,274,275);
                     game.batch.draw(Asset.jawab[0],22,95,275,75);
                bener = new Rectangle(22,95,137,75);
                salah = new Rectangle(159,95,137,75);
                break;
            case 3 : game.batch.draw(Asset.info[1],22,100,275,275);break;
            case 4 : game.batch.draw(Asset.soal[1],22,140,274,275);
                     game.batch.draw(Asset.jawab[1],22,95,270,75);
                salah = new Rectangle(22,95,137,75);
                bener = new Rectangle(159,95,137,75);
                break;
            case 5 : game.batch.draw(Asset.info[2],22,100,275,275);break;
            case 6 : game.batch.draw(Asset.soal[2],22,140,275,275);
                     game.batch.draw(Asset.jawab[2],22,95,270,75);
                salah = new Rectangle(22,95,137,275);
                bener = new Rectangle(159,95,137,275);
                break;
            case 7 : game.batch.draw(Asset.info[3],22,100,275,275);break;
            case 8 : game.batch.draw(Asset.soal[3],22,140,275,275);
                     game.batch.draw(Asset.jawab[3],22,95,270,75);
                bener = new Rectangle(22,95,137,75);
                salah = new Rectangle(159,95,137,75);
                break;
            case 9 : game.batch.draw(Asset.info[4],5,100,275,275);break;
            case 10 : game.batch.draw(Asset.soal[4],22,145,275,275);
                      game.batch.draw(Asset.jawab[4],22,95,270,75);
                bener = new Rectangle(22,95,137,75);
                salah = new Rectangle(159,95,137,75);
                break;
            default:world.touchStar = 1;
        }
    }

    private void presentReady () {
       game.batch.draw(Asset.siap,10,200,300,67);
    }

    private void presentRunning () {
        game.batch.draw(Asset.pause, 320 - 64, 480 - 64, 64, 64);
        Asset.font.draw(game.batch, scoreString, 16, 480 - 20);
    }

    private void presentPaused () {
        game.batch.draw(Asset.pauseMenu, 160 - 192 / 2, 240 - 96 / 2, 192, 96);
        Asset.font.draw(game.batch, scoreString, 16, 480 - 20);
    }

    private void presentLevelEnd () {
        game.batch.draw(Asset.menang,160-160/2,240-96/2,160,200);
        glyphLayout.setText(Asset.font,scoreString);
        Asset.font.draw(game.batch,scoreString,160-glyphLayout.width/2,480-20);
    }

    private void presentGameOver () {
        game.batch.draw(Asset.kalah, 160 - 160 / 2, 240 - 96 / 2, 160, 200);
        glyphLayout.setText(Asset.font, scoreString);
        Asset.font.draw(game.batch, scoreString, 160 - glyphLayout.width / 2, 480 - 20);
    }

    @Override
    public void render (float delta) {
        update(delta);
        draw();
    }

    @Override
    public void pause () {
        if (state == GAME_RUNNING) state = GAME_PAUSED;
    }
}