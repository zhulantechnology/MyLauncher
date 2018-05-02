package com.ds05.mylauncher.ui.setting;

import android.os.Bundle;

import com.ds05.mylauncher.ModuleBaseActivity;
import com.ds05.mylauncher.R;

public class SettingsActivity extends ModuleBaseActivity {

    @Override
    protected void onInit(Bundle savedInstanceState) {
        showTitleBar();
        setTitle(R.string.string_system_settings);
        replaceFragment(new SettingsFragment());
    }
}






















