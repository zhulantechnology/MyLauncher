package com.ds05.mylauncher;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Jun.wang on 2018/5/1.
 */

public class TitleBarManager implements View.OnClickListener{
    private Activity mAct;
    private View mTitleBarView;
    private View mBackView;
    private TextView mTitleView;

    public TitleBarManager(Activity act) {
        mAct = act;
        onInit();
    }

    private void onInit() {
        mTitleBarView = mAct.findViewById(R.id.id_titlebar);
        mBackView = mAct.findViewById(R.id.back_bar);
        mTitleView = (TextView)mAct.findViewById(R.id.title);

        mBackView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_bar:
                mAct.onBackPressed();
                break;
        }
    }

    public void showTitleBar() {
        if(mTitleBarView.getVisibility() != View.VISIBLE) {
            mTitleBarView.setVisibility(View.VISIBLE);
        }
    }

    public void hideTitleBar() {
        if(mTitleBarView.getVisibility() != View.GONE) {
            mTitleBarView.setVisibility(View.GONE);
        }
    }

    public void setTitle(int resId) {
        if(resId == 0) return;

        mTitleView.setText(resId);
    }

    public void setTitle(String title) {
        mTitleView.setText(title);
    }
}
