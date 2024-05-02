package com.bearya.robot.fairystory.walk.load;

import com.bearya.robot.R;
import com.bearya.robot.base.play.FaceType;

public class CompassLoad extends EquipmentLoad {

    public static final int START_OID = 34500;//启动点码

    public static final String NAME = "八大行星的特点";

    public CompassLoad() {
        super(START_OID);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected int loadFacePlay() {
        return R.raw.compass;
    }
    @Override
    protected FaceType defineType() {
        return FaceType.Video;
    }
}
