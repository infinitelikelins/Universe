package com.bearya.robot.fairystory.walk.load;

import com.bearya.robot.R;
import com.bearya.robot.base.play.FaceType;

public class MineEndLoad extends EndLoad {

    public static final int START_OID = 10900;//启动点码
    public static final String NAME = "行星的秘密";

    public MineEndLoad() {
        super(START_OID);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String[] getEquipmentLoads() {
        return new String[]{
                TreasureMapLoad.NAME, // 行星的运动
                CompassLoad.NAME, // 八大行星的特点
                KeyLoad.NAME // 认识八大行星
        };
    }

    @Override
    protected int loadSuccessFacePlay() {
        return R.mipmap.introduce_a;
    }

    @Override
    protected FaceType defineType() {
        return FaceType.Image;
    }

    @Override
    protected String playSound() {
        return "music/zh/end_mine.mp3";
    }
}