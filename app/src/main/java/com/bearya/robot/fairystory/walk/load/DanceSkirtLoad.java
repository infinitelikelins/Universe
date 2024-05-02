package com.bearya.robot.fairystory.walk.load;

import com.bearya.robot.R;
import com.bearya.robot.base.play.FaceType;

public class DanceSkirtLoad extends EquipmentLoad {

    public static final int START_OID = 46200;//启动点码
    public static final String NAME = "空间站的建造";

    public DanceSkirtLoad() {
        super(START_OID);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected int loadFacePlay() {
        return R.raw.skirt;
    }

    @Override
    protected FaceType defineType() {
        return FaceType.Video;
    }
}