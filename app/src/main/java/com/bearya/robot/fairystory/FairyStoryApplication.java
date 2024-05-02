package com.bearya.robot.fairystory;

import com.bearya.robot.BuildConfig;
import com.bearya.robot.base.BaseApplication;
import com.bearya.robot.base.load.ILoadMgr;
import com.bearya.robot.base.util.DebugUtil;
import com.bearya.robot.fairystory.walk.car.LoadMgr;

public class FairyStoryApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        DebugUtil.setDebugMode(BuildConfig.DEBUG);
    }

    @Override
    protected ILoadMgr createLoadMgr() {
        return LoadMgr.getInstance();
    }

}