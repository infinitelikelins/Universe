package com.bearya.robot.fairystory.ui.station;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.bearya.robot.R;
import com.bearya.robot.base.ui.BaseActivity;
import com.bearya.robot.base.util.MusicUtil;
import com.bearya.robot.databinding.ActivityContentBinding;

import java.util.Objects;

public class ContentActivity extends BaseActivity {

    public static void start(Context context, String type) {
        MusicUtil.playAssetsAudio("station/zh/" + type + ".mp3",
                mp -> context.startActivity(new Intent(context, ContentActivity.class).putExtra("type", type)));
    }

    private ActivityContentBinding bindView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView = ActivityContentBinding.inflate(getLayoutInflater());
        setContentView(bindView.getRoot());

        String type = getIntent().getStringExtra("type");

        bindView.viewPager.setAdapter(new ContentAdapter(getSupportFragmentManager(), type));
        bindView.viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bindView.contentBackground.setBackgroundResource(R.mipmap.station_bg1);
                        MusicUtil.playAssetsAudio("station/zh/station_image.mp3");
                        break;
                    case 1:
                        bindView.contentBackground.setBackgroundResource(R.mipmap.station_bg2);
                        MusicUtil.playAssetsAudio("station/zh/station_action.mp3");
                        break;
                    case 2:
                        bindView.contentBackground.setBackgroundResource(R.mipmap.station_bg3);
                        MusicUtil.playAssetsAudio("station/zh/station_sound.mp3");
                        break;
                }
            }
        });
        bindView.tabs.setupWithViewPager(bindView.viewPager);

        Objects.requireNonNull(bindView.tabs.getTabAt(0)).setIcon(R.drawable.station_tab_image_selector);
        Objects.requireNonNull(bindView.tabs.getTabAt(1)).setIcon(R.drawable.station_tab_action_selector);
        Objects.requireNonNull(bindView.tabs.getTabAt(2)).setIcon(R.drawable.station_tab_sound_selector);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        MusicUtil.playAssetsAudio("station/zh/station_image.mp3");
    }

}