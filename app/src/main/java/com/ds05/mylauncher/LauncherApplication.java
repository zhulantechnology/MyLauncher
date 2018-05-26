package com.ds05.mylauncher;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.ds05.mylauncher.common.ConnectUtils;
import com.ds05.mylauncher.service.LauncherService;

/**
 * Created by Jun.wang on 2018/5/9.
 */

public class LauncherApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

        startService(new Intent(LauncherApplication.getContext(), LauncherService.class));

        ConnectUtils.NETWORK_IS_OK = ConnectUtils.isNetAvailable(mContext);
    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}






















