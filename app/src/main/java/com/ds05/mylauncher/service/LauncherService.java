package com.ds05.mylauncher.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Jun.wang on 2018/5/22.
 */

public class LauncherService extends Service {

    private SoundManager mSoundManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSoundManager = new SoundManager(this);
    }
}
