package com.ds05.mylauncher.ui.home;

import android.app.Fragment;
import android.content.Context;

/**
 * Created by jun.wang on 2018/4/28.
 */

public class BaseFragment extends Fragment {
    public Context getContext() {
        return getActivity();
    }
}
