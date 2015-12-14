package ru.spbau.anastasia.race;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by alex on 12.12.2015.
 */
public class Sound {
    public static final int MUSIC_TIME = 1000;
    public static final int HERT = 1;
    public static final int DIE = 2;
    public static final int JUMP = 3;
    private int mDie, mHert, mJump, mTheme;
    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mStreamID;
    public boolean isStoped;
    public int theme;

    class SceneTask extends TimerTask {
        @Override
        public void run() {
            if (isStoped)
                return;
            playSound(mTheme);
        }
    }

    SceneTask task;
    Timer timer;

    public Sound(AssetManager asset, int theme_, int menu) {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }
        if (menu == GameMenu.MENU_ACTIVITY) {
            task = new SceneTask();
            timer = new Timer();
            timer.schedule(task, 0, MUSIC_TIME);
            theme = theme_;
        }
        isStoped = false;
        mAssetManager = asset;
        mDie = loadSound("lose.mp3");
        mHert = loadSound("crash.mp3");
        if (theme == GameMenu.NOT_IS_CHECKED){
            mJump = loadSound("jump.mp3");
        } else {
            mJump = loadSound("jump_in_snow.mp3");
        }
        mTheme = loadSound("race.mp3");
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    public void createOldSoundPool() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    }

    public void play(int sound){
        if (isStoped)
            return;
        switch (sound) {
            case DIE:
                playSound(mDie);
                break;
            case JUMP:
               playSound(mJump);
                break;
            case HERT:
                playSound(mHert);
                break;
        }

    }

    private int playSound(int sound) {
        if (sound > 0) {
            mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return mStreamID;
    }

    private int loadSound(String fileName) {
        AssetFileDescriptor afd;
        try {
            afd = mAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }
}
