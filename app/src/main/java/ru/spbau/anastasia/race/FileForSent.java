package ru.spbau.anastasia.race;

import java.nio.ByteBuffer;
import java.util.Random;

public class FileForSent {

    public static final Random RND = new Random();
    private float dx, dy;
    public int row = -1, numOfImage = -1;
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

    public FileForSent(byte [] bytes) {
        byte[] dxBytes = {bytes[0], bytes[1], bytes[2], bytes[3]};
        byte[] dyBytes = {bytes[4], bytes[5], bytes[6], bytes[7]};

        byte[] rowBytes = {bytes[8], bytes[9], bytes[10], bytes[11]};
        byte[] numOfImageBytes = {bytes[12], bytes[13], bytes[14], bytes[15]};

        dx = ByteBuffer.wrap(dxBytes).getFloat();
        dy = ByteBuffer.wrap(dyBytes).getFloat();

        row = ByteBuffer.wrap(rowBytes).getInt();
        numOfImage = ByteBuffer.wrap(numOfImageBytes).getInt();

        isJumping = bytes[16] != 0;
    }

    public byte [] toMsg() {

        byte[] bytes = new byte[17];

        byte[] bytesDX = ByteBuffer.allocate(4).putFloat(dx).array();

        bytes[0] = bytesDX[0];
        bytes[1] = bytesDX[1];
        bytes[2] = bytesDX[2];
        bytes[3] = bytesDX[3];

        byte[] bytesDY = ByteBuffer.allocate(4).putFloat(dy).array();

        bytes[4] = bytesDY[0];
        bytes[5] = bytesDY[1];
        bytes[6] = bytesDY[2];
        bytes[7] = bytesDY[3];

        byte[] bytesRow = ByteBuffer.allocate(4).putInt(row).array();

        bytes[8] = bytesRow[0];
        bytes[9] = bytesRow[1];
        bytes[10] = bytesRow[2];
        bytes[11] = bytesRow[3];

        byte[] bytesNumOfImage = ByteBuffer.allocate(4).putInt(numOfImage).array();

        bytes[12] = bytesNumOfImage[0];
        bytes[13] = bytesNumOfImage[1];
        bytes[14] = bytesNumOfImage[2];
        bytes[15] = bytesNumOfImage[3];

        bytes[16] = (byte) (isJumping ? 1 : 0);

        return bytes;
    }

    public static FileForSent genServer() {
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
