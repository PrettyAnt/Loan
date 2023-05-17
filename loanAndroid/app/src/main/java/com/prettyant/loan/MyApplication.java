package com.prettyant.loan;

import android.app.Application;

import com.prettyant.loan.util.LogUtil;

/**
 *Created by chenyu on 2023-05-08
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.isShowLog = true;
    }
}
