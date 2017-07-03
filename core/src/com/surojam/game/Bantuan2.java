package com.surojam.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Islam on 27/05/2016.
 */
public class Bantuan2 extends ScreenAdapter {
    SuroJam game;

    OrthographicCamera guiCam;
    Rectangle nextBounds;
    Vector3 touchPoint;
    Texture helpImage;
    TextureRegion helpRegion;

    public Bantuan2(SuroJam game) {
        this.game = game;
        guiCam = new OrthographicCamera(320,480);
        guiCam.position.set(320/2,480/2,0);
        nextBounds = new Rectangle(320 - 64, 0, 64,64);
        touchPoint = new Vector3();
        helpImage = Asset.loadTexture("bantuan/bantuan2.png");
        helpRegion = new TextureRegion(helpImage,0,0,317,221);
    }

    public void update(){
        if(Gdx.input.justTouched()){
            guiCam.unproject(touchPoint.set(Gdx.input.getX(),Gdx.input.getY(),0));

            if(nextBounds.contains(touchPoint.x,touchPoint.y)){
                Asset.playSound(Asset.clickSound);
                game.setScreen(new Bantuan3(game));
            }
        }
    }

    public void draw () {
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        guiCam.update();

        game.batch.disableBlending();
        game.batch.begin();
        game.batch.draw(Asset.backgroundRegion,0,0,320,480);
        game.batch.draw(helpRegion, 10, 150,300,250);
        game.batch.end();

        game.batch.enableBlending();
        game.batch.begin();
        game.batch.draw(Asset.arrow, 320, 0, -64, 64);
        game.batch.end();
    }

    @Override
    public void render (float delta) {
        draw();
        update();
    }

    @Override
    public void hide () {
        helpImage.dispose();
    }
}
