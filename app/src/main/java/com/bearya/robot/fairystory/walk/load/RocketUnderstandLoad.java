package com.bearya.robot.fairystory.walk.load;

import com.bearya.robot.R;
import com.bearya.robot.base.play.FaceType;

public class RocketUnderstandLoad extends EquipmentLoad {

    public static final int START_OID = 54300;
    public static final String NAME = "认识火箭";

    public RocketUnderstandLoad() {
        super(START_OID);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected int loadFacePlay() {
        return R.raw.green;
    }

    @Override
    protected FaceType defineType() {
        return FaceType.Video;
    }

}