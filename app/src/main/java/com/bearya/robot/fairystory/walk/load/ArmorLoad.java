package com.bearya.robot.fairystory.walk.load;

import com.bearya.robot.R;
import com.bearya.robot.base.play.FaceType;

public class ArmorLoad extends EquipmentLoad {

    public static final int START_OID = 49800;//启动点码
    public static final String NAME = "航天员的训练";

    public ArmorLoad() {
        super(START_OID);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected int loadFacePlay() {
        return R.raw.armor;
    }

    @Override
    protected FaceType defineType() {
        return FaceType.Video;
    }

}