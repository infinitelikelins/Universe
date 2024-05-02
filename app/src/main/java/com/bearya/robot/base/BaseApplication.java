package com.bearya.robot.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

import com.bearya.actionlib.utils.RobotActionManager;
import com.bearya.robot.BuildConfig;
import com.bearya.robot.base.can.CanManager;
import com.bearya.robot.base.can.Messager;
import com.bearya.robot.base.load.ILoadMgr;
import com.bearya.robot.base.play.Director;
import com.bearya.robot.base.ui.BaseActivity;
import com.bearya.robot.base.util.ActionDefine;
import com.bearya.robot.base.util.DebugUtil;
import com.bearya.robot.base.util.DeviceUtil;
import com.bearya.robot.base.util.MusicUtil;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mmkv.MMKV;

public abstract class BaseApplication extends Application {

    private static BaseApplication mInstance;
    private final Handler handler = new Handler();
    private long lastMoveTimeStamp = 0;
    private ILoadMgr mLoadMgr;

    public static BaseApplication getInstance() {
        return mInstance;
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            DebugUtil.error("-- application do kill -- ");
            release();
        }
    };

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mInstance = this;
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    public void onCreate() {
        super.onCreate();

        MusicUtil.init();
        MMKV.initialize(this);

        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getInstance());
        strategy.setAppPackageName("com.bearya.robot.universe");
        strategy.setAppVersion(DeviceUtil.getVersionName(getApplicationContext()) + "--" + DeviceUtil.getVersionCode(getApplicationContext()));
        CrashReport.initCrashReport(this, "9857a3fbe3", BuildConfig.DEBUG, strategy);
        CrashReport.setUserId(DeviceUtil.getRKBroadProductCode());
        registerReceiver(receiver, new IntentFilter("bearya.intent.action.KILL_APP"));
        mLoadMgr = createLoadMgr();
        getHandler().postDelayed(() -> CanManager.getInstance().startScan(), 3000);
        ActionDefine.sendMessagerToServices(getInstance(), new Messager(Messager.APP_RESUME));

    }

    public Handler getHandler() {
        return handler;
    }

    /**
     * 移动一下小贝
     */
    public void moveALittle(boolean isGoAhead) {
        DebugUtil.debug("小贝感觉不到OID自己移动了一下");
        if (System.currentTimeMillis() - lastMoveTimeStamp > 500) {
            lastMoveTimeStamp = System.currentTimeMillis();
            if (isGoAhead) {
                RobotActionManager.goAhead(10, 10, "");
            } else {
                RobotActionManager.goBack(10, 10, "");
            }
            handler.postDelayed(RobotActionManager::stopWheel, 500);
        }
    }

    public void release() {
        unregisterReceiver(receiver);
        RobotActionManager.reset();
        Director.getInstance().release();
        CanManager.getInstance().release();
        BaseActivity.finishAllActivity();
        MusicUtil.stopMusic();
        MusicUtil.stopBgMusic();
    }

    public ILoadMgr getLoadMgr() {
        return mLoadMgr;
    }

    protected abstract ILoadMgr createLoadMgr();

}