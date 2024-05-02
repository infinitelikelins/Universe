package com.bearya.robot.fairystory.walk.load;

import com.bearya.robot.base.play.Director;
import com.bearya.robot.base.play.FacePlay;
import com.bearya.robot.base.play.FaceType;
import com.bearya.robot.base.play.LoadPlay;
import com.bearya.robot.base.play.PlayData;
import com.bearya.robot.fairystory.walk.load.lock.DirectorPlayLock;

/**
 * 装备地垫
 */
public abstract class EquipmentLoad extends XLoad {

    public EquipmentLoad(int startOid) {
        super(startOid);
        lock = new DirectorPlayLock();
    }

    @Override
    public void registerPlay() {
        LoadPlay loadPlay = new LoadPlay();
        PlayData playData = new PlayData(new FacePlay(loadFacePlay(), defineType()));
        playData.sound = playSound();
        loadPlay.addLoad(playData);
        Director.getInstance().register(ON_NEW_LOAD, loadPlay);

        LoadPlay failPlay = new LoadPlay();
        PlayData failData = new PlayData("music/zh/end_fail.mp3");
        failPlay.addLoad(failData);
        Director.getInstance().register(ON_END_LOAD_FAIL, failPlay);
    }

    protected abstract int loadFacePlay();

    protected abstract FaceType defineType();

    protected String playSound() {
        return "";
    }

}