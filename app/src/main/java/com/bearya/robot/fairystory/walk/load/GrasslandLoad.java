package com.bearya.robot.fairystory.walk.load;

import com.bearya.robot.base.play.Director;
import com.bearya.robot.base.play.LoadPlay;
import com.bearya.robot.base.play.PlayData;

public class GrasslandLoad extends XLoad {

    public static final int START_OID = 11800;//启动点码
    public static final String NAME = "星空";

    public GrasslandLoad() {
        super(START_OID);
    }

    @Override
    public void registerPlay() {
        LoadPlay failPlay = new LoadPlay();
        PlayData failData = new PlayData("music/zh/end_fail.mp3");
        failPlay.addLoad(failData);
        Director.getInstance().register(ON_END_LOAD_FAIL, failPlay);
    }

    @Override
    public String getName() {
        return NAME;
    }

}
