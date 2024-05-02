package com.bearya.robot.fairystory.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bearya.robot.R;
import com.bearya.robot.base.ui.BaseActivity;
import com.bearya.robot.base.util.MusicUtil;
import com.bearya.robot.databinding.ActivityThemeIntroduceBinding;
import com.bearya.robot.fairystory.ui.res.IntroduceAudio;
import com.bearya.robot.fairystory.ui.res.ThemeConfig;

/**
 * 场景动画 起始背景介绍
 */
public class ThemeIntroduceActivity extends BaseActivity {
    private ActivityThemeIntroduceBinding bindView;

    public static void start(Context context, String type) {
        context.startActivity(new Intent(context, ThemeIntroduceActivity.class).putExtra("type", type));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView = ActivityThemeIntroduceBinding.inflate(getLayoutInflater());
        setContentView(bindView.getRoot());

        // 获取播放主题的资源类型
        String types = getIntent().getStringExtra("type");
        assert types != null;

        bindView.introduceView.setBackgroundResource(playStartImageArrays(types));

        // 音频的播放事件
        MusicUtil.playAssetsAudio(playStartAudioRes(types), mediaPlayer -> {
            CardControllerActivity.start(ThemeIntroduceActivity.this, null);
            finish();
        });

        // 跳过场景介绍动画
        withClick(bindView.introduceView, view -> {
            CardControllerActivity.start(ThemeIntroduceActivity.this, null);
            finish();
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        MusicUtil.stopMusic();
    }

    /**
     * 点击屏幕上的游戏关卡，选择播放的动画
     *
     * @param type 点击游戏关卡类型
     */
    private int playStartImageArrays(String type) {
        switch (type) {
            case ThemeConfig.THEME_QHXB:
                return R.mipmap.introduce_a;
            case ThemeConfig.THEME_YXWH:
                return R.mipmap.introduce_c;
            case ThemeConfig.THEME_MHWH:
                return R.mipmap.introduce_b;
            case ThemeConfig.THEME_CXTD:
            default:
                return R.mipmap.introduce_d;
        }
    }

    /**
     * 播放的背景配音
     *
     * @param type 点击游戏关卡类型
     */
    private String playStartAudioRes(String type) {
        switch (type) {
            case ThemeConfig.THEME_YXWH:
                return IntroduceAudio.hero;
            case ThemeConfig.THEME_QHXB:
                return IntroduceAudio.treasure;
            case ThemeConfig.THEME_MHWH:
                return IntroduceAudio.ball;
            case ThemeConfig.THEME_CXTD:
                return IntroduceAudio.station;
            default:
                return "";
        }
    }

}