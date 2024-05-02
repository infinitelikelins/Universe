package com.bearya.robot.fairystory.ui.stage;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bearya.robot.databinding.StageVideoBinding;

public class VideoFragment extends Fragment {

    private StageVideoBinding bindView;

    private MediaPlayer.OnCompletionListener completedListener;

    public static VideoFragment newInstance(int fileName) {
        Bundle args = new Bundle();
        args.putInt("video", fileName);
        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bindView = StageVideoBinding.inflate(inflater, container, false);
        return bindView.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        int videoPath = getArguments().getInt("video", 0);
        bindView.video.setVideoPath("android.resource://" + requireContext().getPackageName() + "/" + videoPath);
        bindView.video.setOnCompletionListener(completedListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        bindView.video.requestFocus();
        bindView.video.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void setOnVideoCompletedListener(MediaPlayer.OnCompletionListener completedListener) {
        this.completedListener = completedListener;
    }

    @Override
    public void onStop() {
        super.onStop();
        bindView.video.stopPlayback();
    }
}
