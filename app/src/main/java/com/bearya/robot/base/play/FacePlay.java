package com.bearya.robot.base.play;

public class FacePlay {
    private final String face;
    private final FaceType faceType;

    private final int resId;

    public FacePlay(String face, FaceType faceType) {
        this.face = face;
        this.faceType = faceType;
        resId = -1;
    }

    public FacePlay(int res, FaceType faceType) {
        this.resId = res;
        this.faceType = faceType;
        face = null;
    }

    public String getFace() {
        return face;
    }

    public FaceType getFaceType() {
        return faceType;
    }

    public int getResource(){ return resId;}

}
