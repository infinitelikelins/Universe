package com.bearya.robot.fairystory.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.bearya.robot.R;
import com.bearya.robot.base.BaseApplication;
import com.bearya.robot.base.card.Additional;
import com.bearya.robot.base.load.BaseLoad;
import com.bearya.robot.base.play.Director;
import com.bearya.robot.base.protocol.DriveResult;
import com.bearya.robot.base.ui.BaseActivity;
import com.bearya.robot.base.util.DebugUtil;
import com.bearya.robot.base.util.MusicUtil;
import com.bearya.robot.base.walk.Section;
import com.bearya.robot.base.walk.action.Action;
import com.bearya.robot.base.walk.action.ActionSet;
import com.bearya.robot.base.walk.action.BackwardAction;
import com.bearya.robot.base.walk.action.ForwardAction;
import com.bearya.robot.base.walk.action.LeftAction;
import com.bearya.robot.base.walk.action.RightAction;
import com.bearya.robot.fairystory.ui.popup.impl.ResultFailPopup;
import com.bearya.robot.fairystory.ui.popup.impl.ResultSuccessPopup;
import com.bearya.robot.fairystory.ui.res.CardChildAction;
import com.bearya.robot.fairystory.ui.res.CardParentAction;
import com.bearya.robot.fairystory.ui.res.CardType;
import com.bearya.robot.fairystory.ui.res.ThemeConfig;
import com.bearya.robot.fairystory.walk.action.RobotCarAction;
import com.bearya.robot.fairystory.walk.car.ICar;
import com.bearya.robot.fairystory.walk.car.LoadMgr;
import com.bearya.robot.fairystory.walk.car.RobotCar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 指令执行运行状态，执行结果
 */
public class RuntimeActivity extends BaseActivity implements ICar.DriveListener {

    // 这个类初始化了数据Can的读取和驱动控制
    private final RobotCar robotCar = new RobotCar(this);

    // 指令集合
    private ArrayList<CardParentAction> data;

    /**
     * 启动执行内容
     *
     * @param actions 这个必须是List<CardParentAction>转换成的JSON字符串，或者直接为空
     */
    public static void start(Context context, String actions) {
        context.startActivity(new Intent(context, RuntimeActivity.class).putExtra("actions", actions));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runtime);

        FrameLayout fragmentContainer = findViewById(R.id.fragment_container);
        Director.getInstance().setContainer(getSupportFragmentManager(), fragmentContainer);

        try {
            // 执行行动的数据
            String actions = getIntent().getStringExtra("actions");
            Type type = new TypeToken<List<CardParentAction>>() {
            }.getType();
            data = new Gson().fromJson(actions, type);
        } catch (Exception e) {
            DebugUtil.debug("-- 动作序列化失败 -- ");
        }

        if (data != null && data.size() > 0) {
            // 前进指令起始范围
            AtomicInteger startId = new AtomicInteger(1);

            for (int i = 0; i < data.size(); i++) {
                CardParentAction cardParentAction = data.get(i);

                if (cardParentAction.parentActionId == CardType.ACTION_FORWARD) { // 如果是前进卡
                    // 根据步数设置反馈id的范围值，闭区间 [开始，结束]
                    cardParentAction.idSection = new Section(startId.get(), startId.addAndGet(cardParentAction.stepCount) - 1);
                }

                // 运行前,所有指令状态修改为正确
                cardParentAction.status = true;

                if (cardParentAction.childAction != null) {
                    if (cardParentAction.childAction.childActionId == CardType.ACTION_DEFAULT) {
                        cardParentAction.childAction = null;
                    } else {
                        cardParentAction.childAction.status = true;
                    }
                }
            }
        }
        prepare();
    }

    private void prepare() {
        // 显示出哼歌的表情后开始行走
        if (ThemeConfig.CURRENT_THEME.equals(ThemeConfig.THEME_YXWH)) {
            Director.getInstance().playMovingEmotion("sq");
        } else if (ThemeConfig.CURRENT_THEME.equals(ThemeConfig.THEME_QHXB)) {
            Director.getInstance().playMovingEmotion("ja");
        } else if (ThemeConfig.CURRENT_THEME.equals(ThemeConfig.THEME_MHWH)) {
            Director.getInstance().playMovingEmotion("axy");
        } else if (ThemeConfig.CURRENT_THEME.equals(ThemeConfig.THEME_CXTD)) {
            Director.getInstance().playMovingEmotion("kx");
        } else if (ThemeConfig.CURRENT_THEME.equals(ThemeConfig.THEME_FREE)) {
            Director.getInstance().playMovingEmotion("hs");
        }

        // 出发了
        MusicUtil.playAssetsAudio("music/zh/w_ready_treasure3.mp3", mediaPlayer -> runOnUiThread(this::doRun));

    }

    /**
     * 动起来
     */
    private void doRun() {
        // 这是行动指令对接集合
        RobotCarAction robotCarAction = new RobotCarAction();
        ActionSet actionSet = new ActionSet();
        actionSet.clear();
        int loopIndex = -1;
        int loopCount = -1;
        for (int i = 0; i < data.size(); i++) {
            CardParentAction parentAction = data.get(i);
            // 将行动指令转换成行走可识别的指令
            if (parentAction.parentActionId == CardType.ACTION_CLOSURE) {
                loopCount--;
                if (loopCount > 0) {
                    i = loopIndex;
                }
            } else if (parentAction.parentActionId == CardType.ACTION_LOOP) {
                loopCount = parentAction.stepCount;
                loopIndex = i;
            } else if (parentAction.parentActionId != CardType.ACTION_DEFAULT) {
                actionSet.add(createParentAction(parentAction));
            }
        }

        while (actionSet.getActionList().size() > 0) {
            Action action = actionSet.getActionList().get(0);
            if (action instanceof ForwardAction) {
                break;
            } else {
                actionSet.getActionList().remove(0);
            }
        }

        // 驱动行驶数据指向
        robotCarAction.set(actionSet);

        // 设置规划好的行动指令转换
        robotCar.setRobotAction(robotCarAction);

        // 启动
        robotCar.drive();

    }

    /**
     * 创建方向行动指令
     *
     * @param action 行动指令的位置
     */
    private List<Action> createParentAction(CardParentAction action) {
        List<Action> actions = new ArrayList<>();
        switch (action.parentActionId) {
            case CardType.ACTION_BACKWARD: // 后退
                actions.add(new BackwardAction(1));
                break;
            case CardType.ACTION_FORWARD: // 前进
                int id = action.idSection.getStart();
                // 根据前进指令步数，直接转化成步数为1的前进指令告诉RobotCar
                for (int i = 0; i < action.stepCount; i++) {
                    actions.add(new ForwardAction(id++, 1, null));
                }
                break;
            case CardType.ACTION_LEFT: //  左转
                actions.add(new LeftAction(1));
                break;

            case CardType.ACTION_RIGHT: // 右转
                actions.add(new RightAction(1));
                break;
            default:
                return null;
        }
        return actions;
    }


    /**
     * 运行的时候 ， 发生的一些异常情况
     *
     * @param exception 异常原因
     * @param param     异常信息
     */
    @Override
    public void onException(final ICar.DriveException exception, Object param) {
        runOnUiThread(() -> {
            if (exception == ICar.DriveException.OutOfLoad) { // 小贝不在地垫上,可能是行走过程中走出去的或者是人为的抱离地垫
                showResultErrorPopup(getApplicationContext());
            }
        });
    }

    /**
     * 运行时，发生的一些结果处理，包含错误处理 ， 终点处理 ， 装备处理
     *
     * @param result    结果
     * @param stepIndex 出错的指令位置
     * @param load      当前道路
     * @param param     1.可以是装备的状态List ,  2.可以是正确的终点道路名称
     */
    @Override
    public void onDriveResult(final DriveResult result, final int stepIndex, final BaseLoad load, @Nullable final Object param) {
        DebugUtil.debug("得到结果%s,当前地垫:%s,前进步子:%d,装备状态%s", result.name(), load == null ? "null" : load.getName(), stepIndex, param != null ? param.toString() : "null");

        runOnUiThread(() -> {

            switch (result) {

                case Success:
                    DebugUtil.debug("Success 到达终点目标终点显示动画");
                    showResultSuccessPopup(getApplicationContext());
                    break;

                case FailLessAction:
                    // TODO: 2020/6/18 根据当前最新需要 ， 将 终点地点的逻辑 设置为 主题对应的终点地点才算到达
                    DebugUtil.debug("FailLessAction");
                    onLoadAnimation(stepIndex, load, param);
                    showResultErrorPopup(getApplicationContext());
                    break;

                case FailMoreAction:
                    DebugUtil.debug("FailMoreAction");
                    onLoadAnimation(stepIndex, load, param);
                    showResultErrorPopup(getApplicationContext());
                    break;

                case FailObstacleAdditionalLost:
                    DebugUtil.debug("FailObstacleAdditionalLost");
                    onLoadAnimation(stepIndex, load, new Object());
                    showResultErrorPopup(getApplicationContext());
                    break;

                case FailObstacleAdditionalUnMatch:
                    DebugUtil.debug("FailObstacleAdditionalUnMatch");
                    onLoadAnimation(stepIndex, load, param);
                    showResultErrorPopup(getApplicationContext());
                    break;

                case FailNoEntry:
                    DebugUtil.debug("FailNoEntry");
                    onLoadAnimation(stepIndex, load, param);
                    showResultErrorPopup(getApplicationContext());
                    break;

                case FailEndLoadUnMatch:
                    if (param instanceof String) {
                        DebugUtil.debug("FailEndLoadUnMatch -- 3");
                        showResultErrorPopup(getApplicationContext());
                    }

                    if (load != null) {
                        String wrongEndLoad = load.getName();// 当前走到的不匹配道路的名称
                        DebugUtil.debug("wrongEndLoad = %s", wrongEndLoad);
                    }

                    break;

                case FailLostEquipmentLoads: // 到达终点但缺少装备地垫
                    DebugUtil.debug("FailLostEquipmentLoads");
                    List<String> lostEquipmentLoads = new ArrayList<>(); // 缺少的装备地垫列表
                    if (param instanceof List) {
                        // 实际在运行的时候收到与主题匹配终点但还缺少的装备地垫列表
                        List params = (List) param;
                        if (params.size() > 0) {
                            for (int i = 0; i < params.size(); i++) {
                                Object paramObj = params.get(i);
                                if (paramObj instanceof String) {
                                    String lostEquipmentLoad = (String) paramObj;
                                    lostEquipmentLoads.add(lostEquipmentLoad);
                                }
                            }
                        }
                    }
                    // 当丢失/未收集的装备为0时，就是正确的
                    if (lostEquipmentLoads.size() == 0) {
                        //发现终点地垫所需的装备都收集完成，显示正确的动画
                        showResultSuccessPopup(getApplicationContext());
                    } else {
                        // 发现未收集的装备大于0,那么应该就是显示错误的未收集齐装备的动画
                        showResultErrorPopup(getApplicationContext());
                    }
                    break;
            }
        });
    }

    /**
     * 根据步数反馈的id,判断前进指令在哪一个卡片上
     *
     * @param id 步数反馈的id
     */
    private CardParentAction findCardParentActionByForwardId(int id) {
        if (id > 0) {
            for (CardParentAction action : data) {
                if (action.idSection != null && action.idSection.in(id)) {
                    return action;
                }
            }
        }
        return null;
    }

    /**
     * 加载动画内容识别,卡片异常
     *
     * @param stepIndex  卡片位置
     * @param baseLoad   道路
     * @param additional 道具卡片
     */
    private void onLoadAnimation(int stepIndex, @Nullable BaseLoad baseLoad, @Nullable Object additional) {
        DebugUtil.error("stepIndex = " + stepIndex);
        DebugUtil.error("BaseLoad name = " + (baseLoad != null ? baseLoad.getName() : "null"));
        DebugUtil.error("Additional class = %s , name = %s",
                additional != null ? additional.getClass().getSimpleName() : "null",
                additional instanceof Additional ? ((Additional) additional).getCards().get(0).getOid() : "null");

        if (stepIndex == -1) {
            return;
        }

        // 在所有的前进路径中找找错误的那个指令
        CardParentAction action = findCardParentActionByForwardId(stepIndex);

        if (action != null) {
            // 行动指令卡片
            if (additional != null) {
                // 当additional不为空的时候 就是 道具卡错误 , 但是同时如果是在障碍地垫上的话,也判断一下道具卡是没有使用
                if (action.childAction == null) {
                    action.childAction = new CardChildAction(CardType.ACTION_DEFAULT);
                }
                // 道具卡的状态修改成false
                action.childAction.status = false;
            } else {
                // 当additional为空的时候 就是 行动指令错误/或者是没有添加道具指令
                action.status = false;
            }
        }
    }

    /**
     * 结果页面，显示错误结果
     *
     * @param context 上下文
     */
    private void showResultErrorPopup(final Context context) {
        // 先取消Lottie动画置空
        ResultFailPopup popup = new ResultFailPopup(this, "真遗憾，\n\n我没有完成任务。", null);
        popup.withEvent(v -> {
            CardControllerActivity.start(context, new Gson().toJson(data));
            MusicUtil.stopMusic();
            // 返回刷卡指令页面需清除本次记录的信息
            LoadMgr.getInstance().clear();
            finish();
        }, v -> {
            // 返回主页需清除本次记录的信息
            ThemesActivity.start(getApplicationContext());
            LoadMgr.getInstance().clear();
            finish();
        });
        popup.showPopupWindow();
    }

    /**
     * 正确到达终点时显示成功完成游戏的数据统计
     */
    private void showResultSuccessPopup(final Context context) {
        // 先取消Lottie动画置空
        ResultSuccessPopup popup = new ResultSuccessPopup(this);
        popup.withEvent(v -> {
            LoadMgr.getInstance().clear();
            BaseApplication.getInstance().release();
        }, v -> {
            CardControllerActivity.start(context, new Gson().toJson(data));
            LoadMgr.getInstance().clear();
            finish();
        });
        popup.showPopupWindow();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Director.getInstance().setContainer(null, null);
        robotCar.release();
    }

}