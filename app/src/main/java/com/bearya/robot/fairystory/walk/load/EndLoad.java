package com.bearya.robot.fairystory.walk.load;

import com.bearya.robot.base.play.Director;
import com.bearya.robot.base.play.FacePlay;
import com.bearya.robot.base.play.FaceType;
import com.bearya.robot.base.play.LoadPlay;
import com.bearya.robot.base.play.PlayData;
import com.bearya.robot.fairystory.walk.load.lock.DirectorPlayLock;

public abstract class EndLoad extends XLoad {

    public EndLoad(int startOid) {
        super(startOid);
        lock = new DirectorPlayLock();
    }

    @Override
    public void registerPlay() {
        LoadPlay loadPlay = new LoadPlay();
        Director.getInstance().register(ON_NEW_LOAD, loadPlay);

        LoadPlay successPlay = new LoadPlay();
        PlayData successData = new PlayData(new FacePlay(loadSuccessFacePlay(), defineType()));
        successData.sound = playSound();
        successPlay.addLoad(successData);
        Director.getInstance().register(ON_END_LOAD_SUCCESS, successPlay);

        LoadPlay failPlay = new LoadPlay();
        PlayData failData = new PlayData(new FacePlay(loadSuccessFacePlay(), defineType()));
        failData.sound = "music/zh/end_fail.mp3";
        failPlay.addLoad(failData);
        Director.getInstance().register(ON_END_LOAD_FAIL, failPlay);
    }

    /**
     * 到达终点所需要的装备
     */
    public abstract String[] getEquipmentLoads();

    protected abstract int loadSuccessFacePlay();

    protected abstract FaceType defineType();

    protected String playSound() {
        return "";
    }

}