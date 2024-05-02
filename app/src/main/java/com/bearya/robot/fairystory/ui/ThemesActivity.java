package com.bearya.robot.fairystory.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bearya.robot.base.BaseApplication;
import com.bearya.robot.base.ui.BaseActivity;
import com.bearya.robot.base.util.MusicUtil;
import com.bearya.robot.databinding.ActivityThemesBinding;
import com.bearya.robot.fairystory.ui.res.ThemeConfig;
import com.bearya.robot.fairystory.walk.car.LoadMgr;

import java.util.concurrent.TimeUnit;

/**
 * 主题乐园,在这里可以选择不同的关卡进行游戏
 */
public class ThemesActivity extends BaseActivity implements View.OnClickListener {
    private ActivityThemesBinding bindView;
    private rx.Subscription subscribe;
    private boolean singleClickLock = false;

    public static void start(Context context) {
        context.startActivity(new Intent(context, ThemesActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView = ActivityThemesBinding.inflate(getLayoutInflater());
        setContentView(bindView.getRoot());
        withClick(bindView.viewA, this);
        withClick(bindView.viewB, this);
        withClick(bindView.viewC, this);
        withClick(bindView.viewD, this);
        withClick(bindView.viewS, this);
    }

    @Override
    public void onClick(View v) {

        if (singleClickLock) {
            return;
        }
        singleClickLock = true;

        if (v.getId() == bindView.viewA.getId()) {
            startGame(ThemeConfig.THEME_QHXB); // 行星的秘密
        } else if (v.getId() == bindView.viewC.getId()) {
            startGame(ThemeConfig.THEME_YXWH); // 了不起的宇航员
        } else if (v.getId() == bindView.viewB.getId()) {
            startGame(ThemeConfig.THEME_MHWH); // 有趣的空间站
        } else if (v.getId() == bindView.viewD.getId()) {
            startGame(ThemeConfig.THEME_CXTD); // 飞天神州
        } else if (v.getId() == bindView.viewS.getId()) {
            startFree(); // 创想模块
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        BaseApplication.getInstance().getHandler().postDelayed(() -> MusicUtil.playAssetsAudio("music/zh/welcome.mp3", mediaPlayer -> {
            MusicUtil.playAssetsAudio("music/zh/delay.mp3");
            MusicUtil.playAssetsBgMusic("music/zh/theme.mp3");
        }), 500);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        singleClickLock = false;

        if (subscribe != null && !subscribe.isUnsubscribed()) {
            subscribe.unsubscribe();
            subscribe = null;
        }

        subscribe = rx.Observable.interval(20, 20, TimeUnit.SECONDS)
                .subscribeOn(rx.schedulers.Schedulers.newThread())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(aLong -> MusicUtil.playAssetsAudio("music/zh/delay.mp3"));

    }

    @Override
    protected void onPause() {
        super.onPause();
        singleClickLock = false;
    }

    @Override
    protected void onStop() {
        super.onStop();

        MusicUtil.stopBgMusic();

        if (subscribe != null && !subscribe.isUnsubscribed()) {
            subscribe.unsubscribe();
        }

    }

    /**
     * 启动游戏介绍页面
     *
     * @param type 开场介绍资源类型
     * @see ThemeConfig
     */
    private void startGame(String type) {
        singleClickLock = false;
        ThemeConfig.CURRENT_THEME = type;
        LoadMgr.getInstance().setThemeEndLoad(type);
        ThemeIntroduceActivity.start(this, type);
    }

    private void startFree(){
        singleClickLock = false;
        ThemeConfig.CURRENT_THEME = ThemeConfig.THEME_FREE;
        LoadMgr.getInstance().setThemeEndLoad(ThemeConfig.THEME_FREE);
        StationActivity.start(this);
    }

}