package com.bearya.robot.fairystory.walk.load;

import com.bearya.robot.R;
import com.bearya.robot.base.play.FaceType;

public class CastleEndLoad extends EndLoad {

    public static final int START_OID = 57900;//启动点码

    public static final String NAME = "有趣的空间站";

    public CastleEndLoad() {
        super(START_OID);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String[] getEquipmentLoads() {
        return new String[]{
                CrystalShoesLoad.NAME, // 空间站的作用
                DanceSkirtLoad.NAME, // 空间站的建造
                FatTonnyLoad.NAME // 空间站的组成
        };
    }

    @Override
    protected FaceType defineType() {
        return FaceType.Image;
    }

    @Override
    protected int loadSuccessFacePlay() {
        return R.mipmap.introduce_b;
    }

    @Override
    protected String playSound() {
        return "music/zh/end_castle.mp3";
    }

}