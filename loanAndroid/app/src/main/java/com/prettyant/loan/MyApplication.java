package com.prettyant.loan;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.prettyant.loan.util.LogUtil;
import com.tencent.mmkv.MMKV;

/**
 *Created by chenyu on 2023-05-08
 */
public class MyApplication extends Application {
    public static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
        String rootDir = MMKV.initialize(this);
        LogUtil.v(LogUtil.LOG+" dir---------->>>>>  "+rootDir);
    }

}
