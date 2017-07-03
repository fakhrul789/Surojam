package com.surojam.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Islam on 27/05/2016.
 */
public class HighScoreScreen extends ScreenAdapter {
    SuroJam game;
    OrthographicCamera guiCam;
    Rectangle backBounds;
    Vector3 touchPoint;
    String[] highScore;
    float xOffset = 0;
    GlyphLayout glyphLayout = new GlyphLayout();

    public HighScoreScreen(SuroJam game){
        this.game = game;

        guiCam = new OrthographicCamera(320,480);
        guiCam.position.set(320/2,480/2,0);
        backBounds = new Rectangle(0,0,64,64);
        touchPoint = new Vector3();
        highScore = new String[5];
        for (int i = 0; i < 5; i++){
            highScore[i] = i + 1 + ". " + Setting.highscores[i];
            glyphLayout.setText(Asset.font, highScore[i]);
            xOffset = Math.max(glyphLayout.width, xOffset);
        }
        xOffset = 160 - xOffset / 2 + Asset.font.getSpaceWidth() / 2;
    }

    public void update(){
        if(Gdx.input.justTouched()){
            guiCam.unproject(touchPoint.set(Gdx.input.getX(),Gdx.input.getY(),0));

            if (backBounds.contains(touchPoint.x, touchPoint.y)) {
                Asset.playSound(Asset.clickSound);
                game.setScreen(new MainMenuScreen(game));
                return;
            }
        }
    }

    public void draw(){
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        guiCam.update();

        game.batch.setProjectionMatrix(guiCam.combined);
        game.batch.disableBlending();
        game.batch.begin();
        game.batch.draw(Asset.backgroundRegion, 0, 0, 320, 480);
        game.batch.end();

        game.batch.enableBlending();
        game.batch.begin();

        float y = 230;
        for (int i = 4; i >= 0; i--) {
            Asset.font.draw(game.batch, highScore[i], xOffset, y);
            y += Asset.font.getLineHeight();
        }

        game.batch.draw(Asset.arrow, 0, 0, 64, 64);
        game.batch.end();
    }

    @Override
    public void render(float delta) {
        update();
        draw();
    }
}
