package com.bearya.robot.fairystory.walk.load;

import com.bearya.robot.base.car.TravelPath;
import com.bearya.robot.base.play.Director;
import com.bearya.robot.base.play.LoadPlay;
import com.bearya.robot.base.play.PlayData;
import com.bearya.robot.base.walk.Travel;
import com.bearya.robot.base.walk.TravelCrossOver;
import com.bearya.robot.base.walk.TravelFace;

public class StartLoad extends XLoad {
    public static final int START_OID = 26476;//启动点码
    public static final String NAME = "起点";

    public StartLoad() {
        super(START_OID);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void registerPlay() {
        LoadPlay failPlay = new LoadPlay();
        PlayData failData = new PlayData("music/zh/end_fail.mp3");
        failPlay.addLoad(failData);
        Director.getInstance().register(ON_END_LOAD_FAIL, failPlay);
    }

    public TravelPath<Travel> getInitTravelPath(int strategy) {
        TravelPath<Travel> travelPath = new TravelPath<>();
        if (strategy == XLoad.TRAVEL_PATH_STRATEGY_MOVE_ONLY) {
            travelPath.addTravel(new TravelCrossOver(27121));
        } else {
            travelPath.addTravel(new TravelFace(26701));
        }
        travelPath.addTravel(new TravelCrossOver(26701));
        return travelPath;
    }

    public int getFaceOid() {
        return 26491;
    }

}