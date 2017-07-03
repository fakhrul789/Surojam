package com.surojam.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Islam on 22/05/2016.
 */
public class WorldRenderer {
    static final float FRUSTUM_WIDTH = 10;
    static final float FRUSTUM_HEIGHT = 15;
    World world;
    OrthographicCamera cam;
    SpriteBatch batch;

    public WorldRenderer(SpriteBatch batch,World world){
        this.world = world;
        this.cam = new OrthographicCamera(FRUSTUM_WIDTH,FRUSTUM_HEIGHT);
        this.cam.position.set(FRUSTUM_WIDTH/2,FRUSTUM_HEIGHT/2,0);
        this.batch = batch;
    }

    public void render(){
        if(world.suro.position.y > cam.position.y)cam.position.y = world.suro.position.y;
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        renderBackground();
        renderObject();
    }

    private void renderObject() {
        batch.enableBlending();
        batch.begin();
        renderSuro();
        renderPlatform();
        renderItem();
        renderEnemy();
        renderCastle();
        batch.end();
    }

    private void renderBackground() {
        batch.disableBlending();
        batch.begin();
        batch.draw(Asset.backgroundRegion,cam.position.x-FRUSTUM_WIDTH/2,cam.position.y-FRUSTUM_HEIGHT/2,
                FRUSTUM_WIDTH,FRUSTUM_HEIGHT);
        batch.end();
    }

    private void renderSuro(){
        TextureRegion keyframe ;

        switch (world.suro.state){
            case Suro.SURO_STATE_FALL :
                keyframe = Asset.suroFall.getKeyFrame(world.suro.stateTime,Animation.ANIMATION_LOOPING);
                break;
            case Suro.SURO_STATE_JUMP :
                keyframe = Asset.suroJump.getKeyFrame(world.suro.stateTime,Animation.ANIMATION_NONLOOPING);
                break;
            case Suro.SURO_STATE_HIT :
            default: keyframe = Asset.suroHit;
        }

        float side = world.suro.velocity.x < 0 ? -1 : 1;
        if(side < 0){
            batch.draw(keyframe,world.suro.position.x + 0.5f, world.suro.position.y - 0.5f, side * 1, 1);
        }else{
            batch.draw(keyframe,world.suro.position.x - 0.5f, world.suro.position.y - 0.5f, side * 1, 1);
        }
    }

    private void renderPlatform(){
        int len = world.platforms.size();
        for(int i=0;i<len;i++){
            Platform platform = world.platforms.get(i);
            TextureRegion keyFrame = Asset.platform1;
            if(platform.number == Platform.PLATFORM_NUM_2) keyFrame = Asset.platform1;
            if(platform.number == Platform.PLATFORM_NUM_3) keyFrame = Asset.platform2;
            if(platform.number == Platform.PLATFORM_NUM_4) keyFrame = Asset.platform3;
            if(platform.state == Platform.PLATFORM_STATE_PULVERIZING){
                keyFrame = Asset.breakingPlatform.getKeyFrame(platform.stateTime,Animation.ANIMATION_NONLOOPING);
            }
            batch.draw(keyFrame,platform.position.x - 1, platform.position.y-0.7f,2,1);
        }
    }

    private void renderItem(){
        int len = world.springs.size();
        for(int i=0;i<len;i++){
            Spring spring = world.springs.get(i);
            batch.draw(Asset.spring,spring.position.x-0.1f,spring.position.y-0.7f,1,1);
        }

        len = world.coins.size();
        for(int i = 0; i<len; i++){
            Coin coin = world.coins.get(i);
            TextureRegion keyframe = Asset.coinAnim.getKeyFrame(coin.stateTime,Animation.ANIMATION_LOOPING);
            batch.draw(keyframe,coin.position.x - 0.5f, coin.position.y - 0.5f,1,1);
        }

        len = world.stars.size();
        for(int i=0;i<len;i++){
            Star star = world.stars.get(i);
            TextureRegion keyframe = Asset.starAnim.getKeyFrame(star.stateTime,Animation.ANIMATION_LOOPING);
            batch.draw(keyframe,star.position.x - 0.5f, star.position.y - 0.5f,1,1);
        }
    }

    private void renderEnemy(){
        int len = world.squirrels.size();
        for(int i=0;i<len;i++){
            Enemy enemy = world.squirrels.get(i);
            TextureRegion keyframe = Asset.enemyFly.getKeyFrame(enemy.stateTime,Animation.ANIMATION_LOOPING);
            float side = enemy.velocity.x  < 0 ? -1 : 1;
            if(side < 0){
                batch.draw(keyframe,enemy.position.x + 0.5f, enemy.position.y - 0.5f,side * 1, 1);
            }else{
                batch.draw(keyframe,enemy.position.x - 0.5f, enemy.position.y - 0.5f, side * 1,1);
            }
        }
    }

    private void renderCastle(){
        Castle castle = world.castle;
        batch.draw(Asset.castle, castle.position.x - 1, castle.position.y - 1,2,2);
    }
}
