package com.surojam.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by Islam on 19/05/2016.
 */
public class Asset {
    public static Texture background;
    public static TextureRegion backgroundRegion;

    public static Texture item;
    public static Texture newItem;
    public static Texture suro;
    public static Texture mCoin;
    public static Texture mTikus;
    public static Texture mTugu;
    public static Texture platformRegion;
    public static Texture mSpring;
    public static Texture mJudul;
    public static Texture mPause;
    public static Texture mMain;
    public static Texture mInfo;
    public static Texture mSoal;
    public static Texture mJawab;
    public static Texture mSimbol;
    public static Texture mKalah;
    public static Texture mMenang;
    public static Texture mSiap;

    public static TextureRegion siap;
    public static TextureRegion mainMenu;
    public static TextureRegion pauseMenu;
    public static TextureRegion ready;
    public static TextureRegion logo;
    public static TextureRegion soundOn;
    public static TextureRegion soundOff;
    public static TextureRegion arrow;
    public static TextureRegion pause;
    public static TextureRegion spring;
    public static TextureRegion castle;
    public static TextureRegion suroHit;
    public static TextureRegion platform;
    public static TextureRegion platform1;
    public static TextureRegion platform2;
    public static TextureRegion platform3;
    public static TextureRegion about;
    public static TextureRegion menang;
    public static TextureRegion kalah;
    public static TextureRegion[] info = new TextureRegion[5];
    public static TextureRegion[] soal = new TextureRegion[5];
    public static TextureRegion[] jawab = new TextureRegion[5];

    public static BitmapFont font;
    public static FreeTypeFontGenerator generator;
    public static FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    public static Animation starAnim;
    public static Animation coinAnim;
    public static Animation suroJump;
    public static Animation suroFall;
    public static Animation enemyFly;
    public static Animation breakingPlatform;

    public static Music music;
    public static Sound jumpSound;
    public static Sound highJumpSound;
    public static Sound hitSound;
    public static Sound coinSound;
    public static Sound clickSound;
    public static Sound benarSound;
    public static Sound bintangSound;
    public static Sound menangSound;

    public static Texture loadTexture(String file){
        return new Texture(Gdx.files.internal(file));
    }

    public static void load(){
        background = loadTexture("background.jpg");
        backgroundRegion = new TextureRegion(background,0,0,480,800);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/upheavtt.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 1;
        font = generator.generateFont(parameter);
        generator.dispose();

        mSimbol = loadTexture("image/simbol.png");
        mMain = loadTexture("image/main menu.png");
        item = loadTexture("image/judul.png");
        newItem = loadTexture("image/item.png");
        suro = loadTexture("image/sura.png");
        mCoin = loadTexture("image/coin.png");
        mTikus = loadTexture("image/tikus.png");
        mTugu = loadTexture("image/tugu.png");
        mSpring = loadTexture("image/spring.png");
        mJudul = loadTexture("image/judul.png");
        mPause = loadTexture("image/pause menu.png");
        mInfo = loadTexture("image/dialog/info.png");
        mSoal = loadTexture("image/dialog/soal.png");
        mJawab = loadTexture("image/dialog/jawab.png");
        platformRegion = loadTexture("image/pijakan.png");
        mKalah = loadTexture("image/kalah.png");
        mMenang = loadTexture("image/menang.png");
        mSiap = loadTexture("image/ready.png");

        info[0] = new TextureRegion(mInfo,0,0,355,285);
        info[1] = new TextureRegion(mInfo,355,0,355,285);
        info[2] = new TextureRegion(mInfo,710,0,355,285);
        info[3] = new TextureRegion(mInfo,1065,0,355,285);
        info[4] = new TextureRegion(mInfo,1420,0,355,285);

        soal[0] = new TextureRegion(mSoal,0,0,355,285);
        soal[1] = new TextureRegion(mSoal,355,0,355,285);
        soal[2] = new TextureRegion(mSoal,710,0,355,285);
        soal[3] = new TextureRegion(mSoal,1065,0,355,285);
        soal[4] = new TextureRegion(mSoal,1420,0,355,285);

        jawab[0] = new TextureRegion(mJawab,0,0,312,89);
        jawab[1] = new TextureRegion(mJawab,312,0,312,89);
        jawab[2] = new TextureRegion(mJawab,624,0,312,89);
        jawab[3] = new TextureRegion(mJawab,936,0,312,89);
        jawab[4] = new TextureRegion(mJawab,1248,0,312,89);

        siap = new TextureRegion(mSiap,0,0,340,67);
        mainMenu = new TextureRegion(mMain, 0, 0, 200, 232);
        pauseMenu = new TextureRegion(mPause, 0, 0, 192, 96);
        ready = new TextureRegion(item, 320, 224, 236, 32);
        about = new TextureRegion(Asset.item, 0, 257, 300, 110 / 3);
        logo = new TextureRegion(mJudul, 0, 0, 274, 172);
        soundOff = new TextureRegion(mSimbol, 335-3, 0, 66, 64);
        soundOn = new TextureRegion(mSimbol, 269-3, 0, 66, 64);
        arrow = new TextureRegion(mSimbol, 0, 0, 66, 64);
        pause = new TextureRegion(mSimbol, 137-3, 0, 66, 64);

        menang = new TextureRegion(mMenang,0,0,1880,2537);
        kalah = new TextureRegion(mKalah,0,0,1880,2537);

        spring = new TextureRegion(mSpring, 0, 0, 40, 38);
        castle = new TextureRegion(mTugu, 0, 0, 64, 78);
        coinAnim = new Animation(0.3f, new TextureRegion(mCoin, 0, 0, 25, 32), new TextureRegion(mCoin, 25, 0, 25, 32),
                new TextureRegion(mCoin, 50, 0, 25, 32), new TextureRegion(mCoin, 75, 0, 25, 32));
        starAnim = new Animation(0.2f, new TextureRegion(newItem,0,18,32,32), new TextureRegion(newItem,32,18,32,32),
                new TextureRegion(newItem,64,18,32,32),new TextureRegion(newItem,96,18,32,32));

        suroJump = new Animation(0.2f, new TextureRegion(suro, 65, 0, 65, 75));
        suroFall = new Animation(0.2f, new TextureRegion(suro, 135, 0, 65, 75));
        suroHit = new TextureRegion(suro, 0, 0, 65, 75);

        enemyFly = new Animation(0.2f, new TextureRegion(mTikus, 0, 0, 32, 64), new TextureRegion(mTikus, 32, 0, 32, 64),
                new TextureRegion(mTikus,64,0,32,64));
        platform3 = new TextureRegion(platformRegion,0,0,67,64);
        platform1 = new TextureRegion(platformRegion,67,0,67,64);
        platform2 = new TextureRegion(platformRegion,134,0,67,64);
        platform = new TextureRegion(platformRegion,201,0,67,64);

        breakingPlatform = new Animation(0.2f, platform);

        music = Gdx.audio.newMusic(Gdx.files.internal("sound/music.mp3"));
        music.setLooping(true);
        music.setVolume(0.5f);
        if (Setting.soundEnabled) music.play();
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("sound/jump.wav"));
        highJumpSound = Gdx.audio.newSound(Gdx.files.internal("sound/highJump.wav"));
        hitSound = Gdx.audio.newSound(Gdx.files.internal("sound/hit.mp3"));
        coinSound = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));
        clickSound = Gdx.audio.newSound(Gdx.files.internal("click.wav"));
        benarSound = Gdx.audio.newSound(Gdx.files.internal("sound/benar.ogg"));
        menangSound = Gdx.audio.newSound(Gdx.files.internal("sound/menang.mp3"));
        bintangSound = Gdx.audio.newSound(Gdx.files.internal("sound/bintang.mp3"));
    }

    public static void playSound (Sound sound) {
        if (Setting.soundEnabled) sound.play(1);
    }

}
