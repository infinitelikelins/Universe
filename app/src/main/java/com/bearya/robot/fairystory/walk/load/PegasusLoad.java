package com.bearya.robot.fairystory.walk.load;

import com.bearya.robot.R;
import com.bearya.robot.base.play.FaceType;

public class PegasusLoad extends EquipmentLoad {

    public static final int START_OID = 48900;//启动点码
    public static final String NAME = "航天员在空间站里生活";

    public PegasusLoad() {
        super(START_OID);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected int loadFacePlay() {
        return R.raw.pegasus;
    }

    @Override
    protected FaceType defineType() {
        return FaceType.Video;
    }

}