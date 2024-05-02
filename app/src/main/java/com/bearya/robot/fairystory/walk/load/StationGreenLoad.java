package com.bearya.robot.fairystory.walk.load;

public class StationGreenLoad extends StationLoad {

    public static final int START_OID = 32700;
    public static final String NAME = "station_green_";
    public static final int STATION_INDEX = 3;

    public StationGreenLoad() {
        super(START_OID);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getStationIndex() {
        return STATION_INDEX;
    }

}