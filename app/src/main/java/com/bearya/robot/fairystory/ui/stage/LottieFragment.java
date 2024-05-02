package com.bearya.robot.fairystory.ui.stage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bearya.robot.databinding.StageLottieBinding;

public class LottieFragment extends Fragment {

    private StageLottieBinding bindView;

    public static LottieFragment newInstance(String fileName) {
        Bundle args = new Bundle();
        args.putString("fileName", fileName);
        LottieFragment fragment = new LottieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bindView = StageLottieBinding.inflate(inflater,container,false);
        return bindView.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        String emotion = getArguments().getString("fileName");
        bindView.lottieView.loop(true);
        bindView.lottieView.setAnimation(String.format("emotion/%s.json", emotion));
        bindView.lottieView.playAnimation();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bindView.lottieView.cancelAnimation();
    }

}
