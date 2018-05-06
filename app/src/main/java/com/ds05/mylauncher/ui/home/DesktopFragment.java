package com.ds05.mylauncher.ui.home;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ds05.mylauncher.R;
import com.ds05.mylauncher.ui.help.HelpActivity;
import com.ds05.mylauncher.ui.setting.SettingsActivity;

public class DesktopFragment extends BaseFragment implements View.OnClickListener{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.desktop_frag, container,false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View settings, monitor, photo, help;
        settings = view.findViewById(R.id.id_settings);
        monitor = view.findViewById(R.id.id_monitor);
        photo = view.findViewById(R.id.id_photo);
        help = view.findViewById(R.id.id_help);

        settings.setOnClickListener(this);
        monitor.setOnClickListener(this);
        photo.setOnClickListener(this);
        help.setOnClickListener(this);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = manager.findFragmentById(R.id.fragment);
        if (fragment == null) {
            transaction.add(R.id.fragment, new HomeDisplayFragment());
            transaction.commit();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_settings:
                startActivity(new Intent(getContext(), SettingsActivity.class));
                break;
            case R.id.id_help:
                startActivity(new Intent(getContext(), HelpActivity.class));
                break;
            case R.id.id_monitor:
                //startActivity(new Intent(getContext(), MonitorActivity.class));
                break;
            case R.id.id_photo:
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName( "com.yancy.photobrowser", "com.yancy.photobrowser.GalleryPicker");
                intent.setComponent(componentName);
                startActivity(intent);
                break;
        }
    }
}
