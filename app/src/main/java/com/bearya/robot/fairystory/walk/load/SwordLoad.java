package com.bearya.robot.fairystory.walk.load;

import com.bearya.robot.R;
import com.bearya.robot.base.play.FaceType;

public class SwordLoad extends EquipmentLoad {

    public static final int START_OID = 50700;//启动点码
    public static final String NAME = "航天员";

    public SwordLoad() {
        super(START_OID);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected int loadFacePlay() {
        return R.raw.sword;
    }

    @Override
    protected FaceType defineType() {
        return FaceType.Video;
    }

}