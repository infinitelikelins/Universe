package com.bearya.robot.fairystory.walk.load;

import com.bearya.robot.R;
import com.bearya.robot.base.play.FaceType;

public class RocketLaunchLoad extends EquipmentLoad {

    public static final int START_OID = 56100;
    public static final String NAME = "火箭发射";

    public RocketLaunchLoad() {
        super(START_OID);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected int loadFacePlay() {
        return R.raw.blue;
    }

    @Override
    protected FaceType defineType() {
        return FaceType.Video;
    }
}