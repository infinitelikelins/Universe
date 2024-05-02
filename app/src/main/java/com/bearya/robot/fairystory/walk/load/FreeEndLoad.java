package com.bearya.robot.fairystory.walk.load;

import com.bearya.robot.base.play.Director;
import com.bearya.robot.base.play.FaceType;
import com.bearya.robot.base.play.LoadPlay;

public class FreeEndLoad extends EndLoad {

    public static final int START_OID = 31800;// 启动点码 (先于跳跳镇的终点码值重合)
    public static final String NAME = "通用";

    public FreeEndLoad() {
        super(START_OID);
    }

    @Override
    public void registerPlay() {
        Director.getInstance().register(ON_NEW_LOAD, new LoadPlay());
        Director.getInstance().register(ON_END_LOAD_SUCCESS, new LoadPlay());
        Director.getInstance().register(ON_END_LOAD_FAIL, new LoadPlay());
    }

    @Override
    public String[] getEquipmentLoads() {
        return new String[0];
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected FaceType defineType() {
        return null;
    }

    @Override
    protected int loadSuccessFacePlay() {
        return 0;
    }

}