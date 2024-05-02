package com.bearya.robot.base.play;

import static com.bearya.robot.base.play.PlayData.ONLY_ACTION;

import android.media.MediaPlayer;
import android.text.TextUtils;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bearya.actionlib.utils.RobotActionManager;
import com.bearya.robot.R;
import com.bearya.robot.base.BaseApplication;
import com.bearya.robot.base.util.CodeUtils;
import com.bearya.robot.base.util.DebugUtil;
import com.bearya.robot.base.util.MusicUtil;
import com.bearya.robot.fairystory.ui.stage.LottieFragment;
import com.bearya.robot.fairystory.ui.stage.PictureFragment;
import com.bearya.robot.fairystory.ui.stage.VideoFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Director implements MediaPlayer.OnCompletionListener {
    private FrameLayout view;
    private FragmentManager supportFragmentManager;
    private LoadPlay loadPlay;
    private PlayData playData;
    private PlayListener listener;
    private final Map<String, LoadPlay> loadPlayMap = new HashMap<>();

    private final List<Runnable> actionRunnable = new ArrayList<>();

    private static Director instance;
    private String soundParam;

    private Director() {

    }

    public static Director getInstance() {
        if (instance == null) {
            instance = new Director();
        }
        return instance;
    }

    public void register(String key, LoadPlay play) {
        if (!loadPlayMap.containsKey(key)) {
            loadPlayMap.put(key, play);
        }
    }

    public void director(String key, PlayListener listener) {
        director(key, null, listener);
    }

    public void director(String key, String soundParam, PlayListener listener) {
        this.listener = listener;
        this.soundParam = soundParam;
        DebugUtil.debug("获取key=%s ,数据", key);
        this.loadPlay = loadPlayMap.remove(key);
        if (loadPlay != null) {
            directNext();
        }
    }

    private void directNext() {
        if (loadPlay != null) {
            playData = loadPlay.getPlay();
        }
        if (playData == null && listener != null) {
            loadPlay = null;
            listener.onComplete();
            listener = null;
        } else {
            directPlay(playData);
        }
    }

    private void directPlay(PlayData playData) {
        if (playData != null) {
            playData.countCompleteCondition();
            playFace(playData.getFacePlay());
            playSound(playData.getSound());
            playLight(playData.getColor(), playData.getMode());
            playAction(playData.getRobotAction());
        }
    }

    public void setContainer(FragmentManager supportFragmentManager, FrameLayout view) {
        this.view = view;
        this.supportFragmentManager = supportFragmentManager;
    }

    private void playLottie(String fileName) {

        Fragment fragment = LottieFragment.newInstance(fileName);

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commitNowAllowingStateLoss();

        view.postDelayed(() -> complete(PlayData.ONLY_IMAGE), 3000);
    }

    private void playImage(int fileName) {
        Fragment fragment = PictureFragment.newInstance(fileName);

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commitNowAllowingStateLoss();

        view.postDelayed(() -> complete(PlayData.ONLY_IMAGE), 3000);
    }

    private void playVideo(int fileName) {

        VideoFragment fragment = VideoFragment.newInstance(fileName);

        fragment.setOnVideoCompletedListener(mp -> view.postDelayed(() -> complete(PlayData.ONLY_VIDEO), 3000));

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commitNowAllowingStateLoss();
    }

    private void playFace(final FacePlay facePlay) {
        if (facePlay != null && (!TextUtils.isEmpty(facePlay.getFace()) || facePlay.getResource() > 0)) {
            DebugUtil.debug("playFace type=%s,file=%s", facePlay.getFaceType().name(), facePlay.getFace());
            BaseApplication.getInstance().getHandler().post(() -> {
                switch (facePlay.getFaceType()) {
                    case Lottie:
                        playLottie(facePlay.getFace());
                        break;
                    case Image:
                        playImage(facePlay.getResource());
                        break;
                    case Video:
                        playVideo(facePlay.getResource());
                        break;
                }
            });
        }
    }

    private void playSound(String file) {
        if (!TextUtils.isEmpty(file)) {
            MusicUtil.stopMusic();
            if (!TextUtils.isEmpty(soundParam) && file.contains("%s")) {
                file = String.format(file, soundParam);
            }
            DebugUtil.debug("playSound=%s", file);

            if (!TextUtils.isEmpty(file)) {
                if (file.startsWith("/storage/")) {
                    MusicUtil.play(file, this);
                } else {
                    MusicUtil.playAssetsAudio(file, this);
                }
            }
        }
    }

    private void playAction(TimeAction[] actions) {
        removeActionRunnable();
        if (actions != null && actions.length > 0) {
            int during = 0;
            for (TimeAction action : actions) {
                if (action == null) {
                    continue;
                }
                ActionRunnable runnable = new ActionRunnable(action.getAction());
                actionRunnable.add(runnable);
                BaseApplication.getInstance().getHandler().postDelayed(runnable, during);
                during += (action.getTime() * 1000);
            }
            Runnable stopActionRunnable = () -> {
                RobotActionManager.reset();
                complete(ONLY_ACTION);
            };
            actionRunnable.add(stopActionRunnable);
            BaseApplication.getInstance().getHandler().postDelayed(stopActionRunnable, during);
        }

    }

    private void playLight(RobotActionManager.LightColor color, RobotActionManager.LightMode mode) {
        if (color != null) {
            DebugUtil.debug("颜色:%s,模式:%s", color.name(), mode.name());
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        complete(PlayData.ONLY_SOUND);
    }

    private void complete(int contidion) {
        if (playData != null) {
            playData.complete(contidion);
            if (playData.isComplete()) {
                directNext();
            }
        } else {
            directNext();
        }
    }

    public void reset() {
        playData = null;
        loadPlay = null;
        listener = null;
        loadPlayMap.clear();
    }

    public void release() {
        loadPlayMap.clear();
        view = null;
    }

    public void stop() {
        MusicUtil.stopMusic();
        removeActionRunnable();
        RobotActionManager.reset();
        removeActionRunnable();
    }

    private void removeActionRunnable() {
        if (!CodeUtils.isEmpty(actionRunnable)) {
            for (Runnable runnable : actionRunnable) {
                BaseApplication.getInstance().getHandler().removeCallbacks(runnable);
            }
        }
        actionRunnable.clear();
    }

    private static class ActionRunnable implements Runnable {
        private final int action;

        ActionRunnable(int action) {
            this.action = action;
        }

        @Override
        public void run() {
            RobotActionManager.reset();
            BaseApplication.getInstance().getHandler().postDelayed(this::doAction, 1000);
        }

        private void doAction() {
            if (action == 1) {
                RobotActionManager.handShake(50);
            } else if (action == 2) {
                RobotActionManager.ctrlLeftHand(0, 50, 10);
            } else if (action == 3) {
                RobotActionManager.ctrlRighttHand(0, 50, 10);
            } else if (action == 4) {
                RobotActionManager.headerShake((byte) 10);
            } else if (action == 5) {
                RobotActionManager.turnHead(8, 50, 10);
            } else if (action == 6) {
                RobotActionManager.turnHead(0, 50, 10);
            }
        }
    }

    public void playMovingEmotion(String emotion) {
        playFace(new FacePlay(emotion, FaceType.Lottie));
    }

}