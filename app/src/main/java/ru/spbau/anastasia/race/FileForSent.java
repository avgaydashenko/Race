package ru.spbau.anastasia.race;

import java.util.Random;

public class FileForSent {

    public static final Random RND = new Random();

    private static float dx, dy;
    private static int row = 0, numOfImage = 0;
    private boolean isJumping;

    public FileForSent(float dx_, float dy_, int row_, int numOfImage_, boolean isJumping_) {
        dx = dx_;
        dy = dy_;
        row = row_;
        numOfImage = numOfImage_;
        isJumping = isJumping_;
    }

    public FileForSent(float dx_, float dy_, boolean isJumping_) {
        dx = dx_;
        dy = dy_;
        isJumping = isJumping_;
    }

    public static FileForSent genClient() {
        return new FileForSent(0, 0, RND.nextInt(5), RND.nextInt(5), false);
    }

    public float getDX(){
        return dx;
    }

    public boolean getIsJumping(){
        return isJumping;
    }

    public float getDY(){
        return dy;
    }

    public int getRow(){
        return row;
    }

    public int getNumOfImage(){
        return numOfImage;
    }
}
