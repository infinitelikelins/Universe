package com.bearya.robot.fairystory.walk.load;

public class StationPurpleLoad extends StationLoad {

    public static final int START_OID = 30900;
    public static final String NAME = "station_purple_";
    public static final int STATION_INDEX = 4;

    public StationPurpleLoad() {
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