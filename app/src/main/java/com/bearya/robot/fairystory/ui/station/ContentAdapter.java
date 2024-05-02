package com.bearya.robot.fairystory.ui.station;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ContentAdapter extends FragmentStatePagerAdapter {

    private final Fragment imageFragment;
    private final Fragment actionFragment;
    private final Fragment soundFragment;

    public ContentAdapter(FragmentManager fm, String type) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        soundFragment = StationSoundFragment.newInstance(type);
        actionFragment = StationActionFragment.newInstance(type);
        imageFragment = StationImageFragment.newInstance(type);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return imageFragment;
            case 1: return actionFragment;
            case 2: return soundFragment;
            default: return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "图片";
            case 1: return "动作";
            case 2: return "声音";
            default: return "";
        }
    }
}