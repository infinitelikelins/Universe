package com.bearya.robot.fairystory.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;

import com.bearya.actionlib.utils.KVManager;
import com.bearya.robot.R;
import com.bearya.robot.base.ui.BaseActivity;
import com.bearya.robot.base.util.MusicUtil;
import com.bearya.robot.databinding.ActivityStationBinding;
import com.bearya.robot.fairystory.ui.popup.impl.DeleteConfirmPopup;
import com.bearya.robot.fairystory.ui.station.ContentActivity;
import com.bearya.robot.fairystory.walk.load.StationBlueLoad;
import com.bearya.robot.fairystory.walk.load.StationGreenLoad;
import com.bearya.robot.fairystory.walk.load.StationPinkLoad;
import com.bearya.robot.fairystory.walk.load.StationPurpleLoad;
import com.bearya.robot.fairystory.walk.load.StationRedLoad;
import com.bearya.robot.fairystory.walk.load.StationYellowLoad;

public class StationActivity extends BaseActivity implements View.OnClickListener, View.OnLongClickListener {

    public static void start(Context context) {
        context.startActivity(new Intent(context, StationActivity.class));
    }

    private boolean singleClickLock = false;
    private ActivityStationBinding bindView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView = ActivityStationBinding.inflate(getLayoutInflater());
        setContentView(bindView.getRoot());

        withClick(bindView.stationBlue, this);
        withClick(bindView.stationGreen, this);
        withClick(bindView.stationPink, this);
        withClick(bindView.stationPurple, this);
        withClick(bindView.stationYellow, this);
        withClick(bindView.stationRed, this);

        withLongClick(bindView.stationBlue, this);
        withLongClick(bindView.stationGreen, this);
        withLongClick(bindView.stationPink, this);
        withLongClick(bindView.stationPurple, this);
        withLongClick(bindView.stationYellow, this);
        withLongClick(bindView.stationRed, this);

        withClick(bindView.perform, this);
        withClick(bindView.clear, this);

        MusicUtil.playAssetsAudio("station/zh/station_init.mp3");
    }

    @Override
    public void onClick(View v) {
        if (singleClickLock) {
            return;
        }
        singleClickLock = true;

        if (v.getId() == bindView.clear.getId()) {
            stationClearAll();
        } else if (v.getId() == bindView.stationBlue.getId()) {
            ContentActivity.start(this, StationBlueLoad.NAME);
        } else if (v.getId() == bindView.stationGreen.getId()) {
            ContentActivity.start(this, StationGreenLoad.NAME);
        } else if (v.getId() == bindView.stationRed.getId()) {
            ContentActivity.start(this, StationRedLoad.NAME);
        } else if (v.getId() == bindView.stationYellow.getId()) {
            ContentActivity.start(this, StationYellowLoad.NAME);
        } else if (v.getId() == bindView.stationPink.getId()) {
            ContentActivity.start(this, StationPinkLoad.NAME);
        } else if (v.getId() == bindView.stationPurple.getId()) {
            ContentActivity.start(this, StationPurpleLoad.NAME);
        } else if (v.getId() == bindView.perform.getId()) {
            CardControllerActivity.start(this, null);
        }

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        singleClickLock = false;

        stationSelectedStatus(StationBlueLoad.NAME, bindView.stationBlue);
        stationSelectedStatus(StationGreenLoad.NAME, bindView.stationGreen);
        stationSelectedStatus(StationRedLoad.NAME, bindView.stationRed);
        stationSelectedStatus(StationYellowLoad.NAME, bindView.stationYellow);
        stationSelectedStatus(StationPinkLoad.NAME, bindView.stationPink);
        stationSelectedStatus(StationPurpleLoad.NAME, bindView.stationPurple);
    }

    @Override
    protected void onPause() {
        super.onPause();
        singleClickLock = false;
    }

    private void stationSelectedStatus(String type, AppCompatImageView imageView) {
        boolean status = !TextUtils.isEmpty(KVManager.getInstance().getString("image_" + type)) ||
                !TextUtils.isEmpty(KVManager.getInstance().getString("sound_" + type)) ||
                KVManager.getInstance().getInt("action_" + type + "1", -1) > 0 ||
                KVManager.getInstance().getInt("action_" + type + "2", -1) > 0 ||
                KVManager.getInstance().getInt("action_" + type + "3", -1) > 0;
        imageView.setSelected(status);
    }

    private void stationClearAll() {
        DeleteConfirmPopup popup = new DeleteConfirmPopup(this);
        popup.applyShowTips(getString(R.string.clear_all_station_config));
        popup.applyShowAudio("card/zh/p_delete_station.mp3");
        popup.withConfirm(v -> {
            stationClear(StationBlueLoad.NAME, bindView.stationBlue);
            stationClear(StationGreenLoad.NAME, bindView.stationGreen);
            stationClear(StationRedLoad.NAME, bindView.stationRed);
            stationClear(StationYellowLoad.NAME, bindView.stationYellow);
            stationClear(StationPinkLoad.NAME, bindView.stationPink);
            stationClear(StationPurpleLoad.NAME, bindView.stationPurple);
            Toast.makeText(getApplicationContext(), getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
            singleClickLock = false;
        }, v -> singleClickLock = false);
        popup.showPopupWindow();
    }

    private void stationClear(String type, AppCompatImageView imageView) {

        KVManager.getInstance().remove("image_" + type);
        KVManager.getInstance().remove("sound_" + type);
        KVManager.getInstance().remove("action_" + type + "1");
        KVManager.getInstance().remove("action_" + type + "2");
        KVManager.getInstance().remove("action_" + type + "3");

        imageView.setSelected(false);
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == bindView.stationBlue.getId()) {
            stationClear(StationBlueLoad.NAME, bindView.stationBlue);
        } else if (v.getId() == bindView.stationGreen.getId()) {
            stationClear(StationGreenLoad.NAME, bindView.stationGreen);
        } else if (v.getId() == bindView.stationRed.getId()) {
            stationClear(StationRedLoad.NAME, bindView.stationRed);
        } else if (v.getId() == bindView.stationYellow.getId()) {
            stationClear(StationYellowLoad.NAME, bindView.stationYellow);
        } else if (v.getId() == bindView.stationPink.getId()) {
            stationClear(StationPinkLoad.NAME, bindView.stationPink);
        } else if (v.getId() == bindView.stationPurple.getId()) {
            stationClear(StationPurpleLoad.NAME, bindView.stationPurple);
        }
        return true;
    }

}