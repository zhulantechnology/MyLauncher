package com.ds05.mylauncher.ui.setting;

import android.os.Bundle;

import com.ds05.mylauncher.ModuleBaseFragment;
import com.ds05.mylauncher.R;

/**
 * Created by Jun.wang on 2018/5/2.
 */

public class SettingsFragment extends ModuleBaseFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_fragment);
    }
}




















