package com.ds05.mylauncher;

import android.app.Application;
import android.content.Context;

/**
 * Created by Jun.wang on 2018/5/9.
 */

public class LauncherApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}






















