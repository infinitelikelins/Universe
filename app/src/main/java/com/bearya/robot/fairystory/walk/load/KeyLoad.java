package com.bearya.robot.fairystory.walk.load;

import com.bearya.robot.R;
import com.bearya.robot.base.play.FaceType;

public class KeyLoad extends EquipmentLoad {

    public static final int START_OID = 35400;//启动点码
    public static final String NAME = "认识八大行星";

    public KeyLoad() {
        super(START_OID);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected int loadFacePlay() {
        return R.raw.key;
    }

    @Override
    protected FaceType defineType() {
        return FaceType.Video;
    }

}