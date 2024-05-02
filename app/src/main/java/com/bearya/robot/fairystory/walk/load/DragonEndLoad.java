package com.bearya.robot.fairystory.walk.load;

import com.bearya.robot.R;
import com.bearya.robot.base.play.FaceType;

public class DragonEndLoad extends EndLoad {

    public static final int START_OID = 57000;//启动点码
    public static final String NAME = "了不起的宇航员";

    public DragonEndLoad() {
        super(START_OID);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String[] getEquipmentLoads() {
        return new String[]{
                ArmorLoad.NAME, // 航天员的训练
                PegasusLoad.NAME, // 航天员在空间站里生活
                SwordLoad.NAME // 航天员
        };
    }

    @Override
    protected FaceType defineType() {
        return FaceType.Image;
    }

    @Override
    protected int loadSuccessFacePlay() {
        return R.mipmap.introduce_c;
    }

    @Override
    protected String playSound() {
        return "music/zh/end_dragon.mp3";
    }
}