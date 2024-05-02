package com.bearya.robot.fairystory.walk.load;

import com.bearya.robot.R;
import com.bearya.robot.base.play.FaceType;

public class FatTonnyLoad extends EquipmentLoad {
    public static final int START_OID = 48000;//启动点码
    public static final String NAME = "空间站的组成";

    public FatTonnyLoad() {
        super(START_OID);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected int loadFacePlay() {
        return R.raw.carriage;
    }

    @Override
    protected FaceType defineType() {
        return FaceType.Video;
    }

}