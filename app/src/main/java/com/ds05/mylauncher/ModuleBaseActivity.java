package com.ds05.mylauncher;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class ModuleBaseActivity extends Activity {
    private TitleBarManager mTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_layout);

        mTitleBar = new TitleBarManager(this);

        onInit(savedInstanceState); //  为什么设计这个需要再研究
    }

    public void replaceFragment(ModuleBaseFragment frag) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, frag);
        fragmentTransaction.addToBackStack(frag.getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    public void jumpToFragment(ModuleBaseFragment frag) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, frag);
        fragmentTransaction.addToBackStack(frag.getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    public void jumpToFragment(Fragment frag) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, frag);
        fragmentTransaction.addToBackStack(frag.getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else {
            getFragmentManager().popBackStack();
            super.onBackPressed();
        }
    }

    protected void onInit(Bundle savedInstanceState) {
    }

    public void showTitleBar() {
        mTitleBar.showTitleBar();
    }

    public void hideTitleBar() {
        mTitleBar.hideTitleBar();
    }

    public void setTitle(int resId) {
        mTitleBar.setTitle(resId);
    }

    public void setTitle(String title) {
        mTitleBar.setTitle(title);
    }
}



























