package ru.spbau.anastasia.race;

import android.content.res.Resources;

public class mScene {
    public static final int SINGLE_PLAY = 1;
    public static final int PLAY_TOGETHER = 2;

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

    public int width = 0, height = 0;

    public mScene(Resources res, int type) {
        this.res = res;
        for (int i = 0; i < LAY_COUNT; i++) {
            layers[i] = new mLayer(i);
        }
        if (type == SINGLE_PLAY)
        {
            this.type = SINGLE_PLAY;
        } else {
            this.type = PLAY_TOGETHER;
        }
        status = STOPED;
    }

    public void start() {
        status = PLAYED;
    }

    public void stop() {
        status = STOPED;
    }

    public void oneStep(float dx, float dy) {
        if (status != STOPED) {
            add();
            update(dx, dy);
            updateExist();
        }
    }

    public void oneStep(float dx, float dy, float dx2, float dy2) {
        if (status != STOPED) {
            add();
            update(dx, dy, dx2, dy2);
            updateExist();
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
                (player_id == JAKE) ? R.drawable.jake2 : R.drawable.finn2
        );
        live = new mLive(res, SINGLE_PLAY);
    }

    public void initDoubleScene() {
        mBarrierSprite.initBarrier(res);
        mBackgroundSprite.initBarrier(res);
        player = new mPlayerSprite(width/2 - 60 * mSettings.ScaleFactorX, height - 120 * mSettings.ScaleFactorY, res, R.drawable.jake1, R.drawable.jake2);
        live = new mLive(res, SINGLE_PLAY);
        player2 = new mPlayerSprite(width/2 + 60 * mSettings.ScaleFactorX, height - 120 * mSettings.ScaleFactorY, res, R.drawable.finn1, R.drawable.finn2);
        live2 = new mLive(res, PLAY_TOGETHER);
    }

    public void add() {
        addBarrier();
        addBackground();
    }

    public void addBarrier() {
        if (layers[0].tryToAdd())
        {
            mBarrierSprite barrierSprite = new mBarrierSprite(res);
            layers[0].add(barrierSprite);
        }
    }


    public void deleteBarrier(mBasic item) {
        layers[0].delete(item);
    }

    public void addBackground() {
        if (layers[1].tryToAdd()) {
            mBackgroundSprite backgroundSprite = new mBackgroundSprite(res);
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
        mBasic barrier = player.updateExist(layers[0].data);
        deleteBarrier(barrier);
        live.update();
        if (this.type == SINGLE_PLAY){
            if (!player.exist) {
                player.setY(-10);
                endTheGame();
            }
        } else {
            player2.updateExist(layers[0].data);
            mBasic barrier2 = player.updateExist(layers[0].data);
            deleteBarrier(barrier2);
            live2.update();

            if (!player2.exist) {
                //ToDo
                endTheGame();
                player2.setY(-10);
            }
            if (!player.exist) {
                endTheGame();
                player.setY(-10);
            }

        }

    }

    public void endTheGame(){
        for (int i = 0; i < LAY_COUNT; i++) {
            layers[i].restart();
        }
        player.restart();
        live.update();
        if (type != SINGLE_PLAY){
            player2.restart();
            live2.update();
        }
        status = STOPED;
        //Todo
        //Нужно вывести кнопку, при нажатии на котору игра возобноситься.
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
            live2.update(player);
        }
    }


}
