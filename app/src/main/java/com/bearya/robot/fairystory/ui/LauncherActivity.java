package com.bearya.robot.fairystory.ui;

import com.bearya.robot.R;
import com.bearya.robot.base.ui.BaseLauncherActivity;
import com.bearya.robot.base.ui.LauncherData;

/**
 * 启动页面 ， 点点屏幕
 */
public class LauncherActivity extends BaseLauncherActivity {

    @Override
    protected LauncherData getLauncherData() {
        LauncherData launcherData = new LauncherData();
        launcherData.jumpToActivity = ThemesActivity.class;
        launcherData.bg = R.mipmap.bg_splash;
        launcherData.bgMp3 = "music/zh/touch.mp3";
        launcherData.tipMp3 = "music/zh/bgm.mp3";
        return launcherData;
    }

}