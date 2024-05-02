package com.bearya.robot.fairystory.walk.load;

import android.text.TextUtils;
import android.util.ArrayMap;

import com.bearya.actionlib.utils.KVManager;
import com.bearya.robot.base.play.Director;
import com.bearya.robot.base.play.FacePlay;
import com.bearya.robot.base.play.FaceType;
import com.bearya.robot.base.play.LoadPlay;
import com.bearya.robot.base.play.PlayData;
import com.bearya.robot.base.play.TimeAction;
import com.bearya.robot.base.util.ResourceUtil;
import com.bearya.robot.fairystory.walk.load.lock.DirectorPlayLock;

public abstract class StationLoad extends XLoad {

    public StationLoad(int startOid) {
        super(startOid);
        lock = new DirectorPlayLock();
    }

    @Override
    public void registerPlay() {
        LoadPlay newLoadPlay = new LoadPlay();
        PlayData playData = new PlayData();

        String face = KVManager.getInstance().getString("image_" + getName());
        if (!TextUtils.isEmpty(face)) {
            playData.facePlay = new FacePlay(ResourceUtil.getMipmapId(face), FaceType.Image);
        }

        String sound = KVManager.getInstance().getString("sound_" + getName());
        if (!TextUtils.isEmpty(sound)) {
            playData.sound = sound;
        }

        int action1 = KVManager.getInstance().getInt("action_" + getName() + "1", 0);
        if (action1 > 0) {
            if (playData.actions == null) {
                playData.actions = new ArrayMap<>();
            }
            playData.actions.put(1, new TimeAction(action1, 3));
        }

        int action2 = KVManager.getInstance().getInt("action_" + getName() + "2", 0);
        if (action2 > 0) {
            if (playData.actions == null) {
                playData.actions = new ArrayMap<>();
            }
            playData.actions.put(2, new TimeAction(action2, 3));
        }

        int action3 = KVManager.getInstance().getInt("action_" + getName() + "3", 0);
        if (action3 > 0) {
            if (playData.actions == null) {
                playData.actions = new ArrayMap<>();
            }
            playData.actions.put(3, new TimeAction(action3, 3));
        }

        if (!playData.isEmpty()) {
            newLoadPlay.addLoad(playData);
        }
        Director.getInstance().register(ON_NEW_LOAD, newLoadPlay);
    }

    public abstract int getStationIndex();

}