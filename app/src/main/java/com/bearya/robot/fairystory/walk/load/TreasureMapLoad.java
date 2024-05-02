package com.bearya.robot.fairystory.walk.load;

import com.bearya.robot.R;
import com.bearya.robot.base.play.FaceType;

public class TreasureMapLoad extends EquipmentLoad {
    public static final int START_OID = 33600;//启动点码
    public static final String NAME = "行星的运动";

    public TreasureMapLoad() {
        super(START_OID);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected int loadFacePlay() {
        return R.raw.treasure;
    }

    @Override
    protected FaceType defineType() {
        return FaceType.Video;
    }

}