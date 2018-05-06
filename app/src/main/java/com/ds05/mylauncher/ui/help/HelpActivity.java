package com.ds05.mylauncher.ui.help;

import android.os.Bundle;

import com.ds05.mylauncher.ModuleBaseActivity;
import com.ds05.mylauncher.R;

/**
 * Created by Jun.wang on 2018/5/6.
 */

public class HelpActivity extends ModuleBaseActivity {
    @Override
    protected void onInit(Bundle savedInstanceState) {
        showTitleBar();
        setTitle(R.string.string_help_info);
        replaceFragment(new HelpFragment());
    }
}
