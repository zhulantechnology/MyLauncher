package com.ds05.mylauncher.ui.monitor;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.ds05.mylauncher.ModuleBaseActivity;
import com.ds05.mylauncher.R;

/**
 * Created by Jun.wang on 2018/5/6.
 */

public class MonitorActivity extends ModuleBaseActivity {

    @Override
    protected void onInit(Bundle savedInstanceState) {
        showTitleBar();
        setTitle(R.string.string_monitor);

        replaceFragment(new MonitorFragment());
    }
}
