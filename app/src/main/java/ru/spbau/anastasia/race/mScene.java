package ru.spbau.anastasia.race;

import android.content.res.Resources;

public class mScene {
    public float speed = 1;

    public boolean isNewRound = false;
    public static final int TIME_OF_ROUND = 30;
    public static final double DELTE_COUNT = 0.1;
    public static final double PAUSE_ON_NEW_ROUND = 10;
    public double count = 0;
    public int round = 0;
    private int lastRound = 0;

    public static final int SINGLE_PLAY = 1;
    public static final int PLAY_TOGETHER = 2;
    public static final double DELTE_SPEED = 0.1;
    public static final double DELTE_ADDING_BARRIERS = 0.2;


    public static final int PLAYED= 1;
    public static final int STOPED = 2;

    public static final int FINN = 0;
    public static final int JAKE = 1;

    public int status;
    public int type;
    public int player_id;

    public static final int LAY_COUNT = 2;
    mLayer[] layers = new mLayer[LAY_COUNT];
    Resources res;

    mPlayerSprite player;
    mPlayerSprite player2;
    mLive live;
    mLive live2;

    interface SceneListener {
        void onGameOver();
    }

    SceneListener sceneListener;

    public int width = 0, height = 0;

    public mScene(Resources res, int type_) {
        this.res = res;
        for (int i = 0; i < LAY_COUNT; i++) {
            layers[i] = new mLayer(i);
        }
        type = type_;
        status = STOPED;
    }

    public void start() {
        status = PLAYED;
    }

    public void stop() {
        status = STOPED;
    }

    public void oneStep(float dx, float dy) {
        recalcNewRound();
        if (status != STOPED && !isNewRound) {
            add();
            update(dx, dy);
            updateExist();
            recalcParametrs();
        }
    }

    private void recalcParametrs(){
        count += DELTE_COUNT;
        if (status == STOPED && sceneListener != null) {
            sceneListener.onGameOver();
        }
    }

    private void recalcNewRound (){
        if ((int) count % TIME_OF_ROUND == 0 && count > TIME_OF_ROUND){
            newRound();
            count++;
        }
        if (lastRound < PAUSE_ON_NEW_ROUND){
            lastRound++;
        }
        if (lastRound == PAUSE_ON_NEW_ROUND && isNewRound){
            for (mLayer l : layers){
                if (l.frequencyOfAdding > 2) {
                    l.frequencyOfAdding -= DELTE_ADDING_BARRIERS;
                }
                l.isDamaged = false;
            }
            speed += DELTE_SPEED;
            isNewRound = false;
        }
    }

    private void newRound(){
        round++;
        for (mLayer l : layers){
            l.isDamaged = true;
        }
        lastRound = 0;
        isNewRound = true;
    }

    public void oneStep(float dx, float dy, float dx2, float dy2) {
        recalcNewRound();
        if (status != STOPED) {
            add();
            update(dx, dy, dx2, dy2);
            updateExist();
            recalcParametrs();
        }
    }

    public void setWH(int w, int h) {
        width = w;
        height = h;
    }

    public void initScene() {
        switch (type) {
            case SINGLE_PLAY :
                initSingleScene();
                break;
            case PLAY_TOGETHER :
                initDoubleScene();
                break;
        }
    }

    public void initSingleScene() {
        mBarrierSprite.initBarrier(res);
        mBackgroundSprite.initBarrier(res);
        player = new mPlayerSprite(width/2, height - 120 * mSettings.ScaleFactorY, res,
                (player_id == JAKE) ? R.drawable.jake1 : R.drawable.finn1,
                (player_id == JAKE) ? R.drawable.jake2 : R.drawable.finn2,
                (player_id == JAKE) ? R.drawable.jake3 : R.drawable.finn3,
                (player_id == JAKE) ? R.drawable.jake4 : R.drawable.finn4);

        live = new mLive(res, SINGLE_PLAY);
    }

    public void initDoubleScene() {
        mBarrierSprite.initBarrier(res);
        mBackgroundSprite.initBarrier(res);
        player = new mPlayerSprite(width/2 - 60 * mSettings.ScaleFactorX, height - 120 * mSettings.ScaleFactorY, res,
                R.drawable.jake1, R.drawable.jake2, R.drawable.jake3, R.drawable.jake4);
        live = new mLive(res, mLive.FIRST_PLAYER);
        player2 = new mPlayerSprite(width/2 + 60 * mSettings.ScaleFactorX, height - 120 * mSettings.ScaleFactorY, res,
                R.drawable.finn1, R.drawable.finn2, R.drawable.finn3, R.drawable.finn4);
        live2 = new mLive(res, mLive.SECOND_PLAYER);
    }

    public void add() {
        addBarrier();
        addBackground();
    }

    public void addBarrier() {
        if (layers[0].tryToAdd())
        {
            mBarrierSprite barrierSprite = new mBarrierSprite(res, speed);
            layers[0].add(barrierSprite);
        }
    }


    public void deleteBarrier(mBasic item) {
        layers[0].delete(item);
    }

    public void addBackground() {
        if (layers[1].tryToAdd()) {
            mBackgroundSprite backgroundSprite = new mBackgroundSprite(res, speed);
            layers[1].add(backgroundSprite);
        }
    }

    public mPlayerSprite getPlayer() {
        return player;
    }

    public mLayer getBarrier() {
        return layers[0];
    }

    public mLayer getBackground() {
        return layers[1];
    }

    public void updateExist() {
        for (int i = 0; i < LAY_COUNT; i++) {
            layers[i].updateExist();
        }
        mBasic barrier = player.updateExist(layers);
        deleteBarrier(barrier);
        live.update();
        if (!player.exist){
            status = STOPED;
        }
        if (this.type == PLAY_TOGETHER){
            player2.updateExist(layers);
            mBasic barrier2 = player.updateExist(layers);
            deleteBarrier(barrier2);
            live2.update();
            if (!player2.exist){
                status = STOPED;
            }

        }

    }

    public void restart(){
        speed = 1;
        for (mLayer l : layers){
            l.frequencyOfAdding = 5;
        }
        isNewRound = false;
        count = 0;
        for (int i = 0; i < LAY_COUNT; i++) {
            layers[i].restart();
        }
        player.restart();
        live.update();
        if (type != SINGLE_PLAY){
            player2.restart();
            live2.update();
        }
        status = PLAYED;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void update(float dx, float dy) {
        for (mLayer l : layers) {
            l.update();
        }
        player.update(dx, dy);
        live.update(player);
    }

    public void update(float dx, float dy, float dx2, float dy2) {
        update(dx, dy);
        if(type == PLAY_TOGETHER){
            player2.update(dx2, dy2);
            live2.update(player2);
        }
    }


}
