package com.bearya.robot.fairystory.walk.load;

import com.bearya.robot.R;
import com.bearya.robot.base.play.FaceType;

public class IdeaEndLoad extends EndLoad {

    public static final int START_OID = 1000;// 启动点码 (先于跳跳镇的终点码值重合)
    public static final String NAME = "飞天神州";

    public IdeaEndLoad() {
        super(START_OID);
    }

    @Override
    public String[] getEquipmentLoads() {
        return new String[]{
                RocketPrincipleLoad.NAME, // 发射原理
                RocketLaunchLoad.NAME, // 火箭发射
                RocketUnderstandLoad.NAME // 认识火箭
        };
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected FaceType defineType() {
        return FaceType.Image;
    }

    @Override
    protected int loadSuccessFacePlay() {
        return R.mipmap.introduce_d;
    }

    @Override
    protected String playSound() {
        return "music/zh/end_idea.mp3";
    }

}