package com.bearya.robot.fairystory.walk.load;

import com.bearya.robot.R;
import com.bearya.robot.base.play.FaceType;

public class CrystalShoesLoad extends EquipmentLoad {

    public static final int START_OID = 47100;//启动点码

    public static final String NAME = "空间站的作用";

    public CrystalShoesLoad() {
        super(START_OID);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected int loadFacePlay() {
        return R.raw.shoes;
    }

    @Override
    protected FaceType defineType() {
        return FaceType.Video;
    }
}