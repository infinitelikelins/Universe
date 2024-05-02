package com.bearya.robot.fairystory.walk.load;

import com.bearya.robot.R;
import com.bearya.robot.base.play.FaceType;

public class RocketPrincipleLoad extends EquipmentLoad {

    public static final int START_OID = 55200;
    public static final String NAME = "发射原理";

    public RocketPrincipleLoad() {
        super(START_OID);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected int loadFacePlay() {
        return R.raw.purple;
    }

    @Override
    protected FaceType defineType() {
        return FaceType.Video;
    }
}