package com.surojam.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.sun.scenario.Settings;

/**
 * Created by Islam on 19/05/2016.
 */
public class MainMenuScreen extends ScreenAdapter {
    SuroJam game;
    OrthographicCamera guiCam;
    Rectangle playBounds;
    Rectangle aboutBounds;
    Rectangle soundBounds;
    Rectangle bantuanBounds;
    Vector3 touchPoint;

    public MainMenuScreen(SuroJam game){
        this.game = game;

        guiCam = new OrthographicCamera(320,480);
        guiCam.position.set(320/2,480/2,0);

        soundBounds = new Rectangle(0,0,64,64);
        playBounds = new Rectangle(160-150,250,300,45);
        aboutBounds = new Rectangle(160-150,250-50,300,45);
        bantuanBounds = new Rectangle(160-150,250-50-50,300,45);
        touchPoint = new Vector3();
    }

    public void update(){
        if(Gdx.input.justTouched()){
            guiCam.unproject(touchPoint.set(Gdx.input.getX(),Gdx.input.getY(),0));

            if(playBounds.contains(touchPoint.x,touchPoint.y)){
                Asset.playSound(Asset.clickSound);
                Gdx.app.log("Start","Start klik");
                game.setScreen(new GameScreen(game));
                dispose();
            }
            if(aboutBounds.contains(touchPoint.x,touchPoint.y)){
                Asset.playSound(Asset.clickSound);
                game.setScreen(new HighScoreScreen(game));
                dispose();
            }
            if(bantuanBounds.contains(touchPoint.x,touchPoint.y)){
                Asset.playSound(Asset.clickSound);
                game.setScreen(new Bantuan(game));
                dispose();
            }
            if(soundBounds.contains(touchPoint.x,touchPoint.y)){
                Asset.playSound(Asset.clickSound);
                Setting.soundEnabled = !Setting.soundEnabled;
                if (Setting.soundEnabled)
                    Asset.music.play();
                else
                    Asset.music.pause();
            }
        }
    }

    public void draw(){
        GL20 gl = Gdx.gl;
        gl.glClearColor(1,0,0,1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        guiCam.update();
        game.batch.setProjectionMatrix(guiCam.combined);

        game.batch.disableBlending();
        game.batch.begin();
        game.batch.draw(Asset.backgroundRegion,0,0,320,480);
        game.batch.end();

        game.batch.enableBlending();
        game.batch.begin();
        game.batch.draw(Asset.logo,160-274/2,480-10-142,274,142);
        game.batch.draw(Asset.mainMenu,70,254-100,200,150);
        game.batch.draw(Setting.soundEnabled ? Asset.soundOn : Asset.soundOff, 0, 0, 66 , 64);
        game.batch.end();
    }

    @Override
    public void render(float delta) {
        update();
        draw();
    }

    @Override
    public void pause() {
        Setting.save();
    }
}
