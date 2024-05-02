package com.bearya.robot.fairystory.ui.popup.impl;

import android.content.Context;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.bearya.robot.R;
import com.bearya.robot.base.util.MusicUtil;
import com.bearya.robot.fairystory.ui.popup.AbsBasePopup;

public class ResultSuccessPopup extends AbsBasePopup {

    public ResultSuccessPopup(Context context) {
        super(context);
    }

    @Override
    protected int inflateLayoutId() {
        return R.layout.popup_result_success_view;
    }

    @Override
    protected void onViewInflated() {

    }

    @Override
    protected void onPopupShow() {
        super.onPopupShow();
        MusicUtil.playAssetsAudio("tts/zh/gold_effect.mp3", mediaPlayer -> {
            showResultData();
            setPerformMode();
        });
    }

    @Override
    protected int onPopupShowDuration() {
        return 100;
    }

    @Override
    protected int onPopupDismissDuration() {
        return 100;
    }

    private void setPerformMode() {
        findViewById(R.id.exit_game).setVisibility(View.VISIBLE);
        findViewById(R.id.update_controller).setVisibility(View.VISIBLE);
    }

    private void showResultData() {
        // 动画作用的Root布局
        ConstraintLayout constraintLayout = findViewById(R.id.popup_result_success_view);

        // 动画集合,提取Root布局的约束信息，放到ConstraintSet中
        ConstraintSet set1 = new ConstraintSet();
        set1.clone(constraintLayout);

        // 提供结束的时候动画的参数
        ConstraintSet set2 = new ConstraintSet();
        set2.clone(getContext(), R.layout.popup_result_success_view_set);

        // 动画动作
        TransitionSet transitionSet = new TransitionSet();
        // 淡入动画效果
        Fade fadeIn = new Fade(Fade.IN);
        fadeIn.setDuration(1000);
        fadeIn.setInterpolator(new AccelerateDecelerateInterpolator());
        fadeIn.setStartDelay(1000);
        // 添加到动画的集合中
        transitionSet.addTransition(fadeIn);

        // 位移动作
        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.addTarget(R.id.ic_light);
        changeBounds.setDuration(1000);
        changeBounds.setInterpolator(new LinearInterpolator());
        // 添加到动画集合中
        transitionSet.addTransition(changeBounds);
        // 动画执行的顺序
        transitionSet.setOrdering(TransitionSet.ORDERING_TOGETHER);
        // 动画的时间插值器
        transitionSet.setInterpolator(new LinearInterpolator());
        // 动画应用
        TransitionManager.beginDelayedTransition(constraintLayout, transitionSet);

        // 最终的动画结果推送到布局中
        set2.applyTo(constraintLayout);
    }

    public void withEvent(final View.OnClickListener exitGame, final View.OnClickListener updateController) {
        // 退出游戏
        withClick(R.id.exit_game, exitGame);
        // 优化指令
        withClick(R.id.update_controller, updateController);
    }

}