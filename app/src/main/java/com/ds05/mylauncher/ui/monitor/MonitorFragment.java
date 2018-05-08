package com.ds05.mylauncher.ui.monitor;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.util.Log;
import android.view.View;

import com.ds05.mylauncher.ModuleBaseFragment;
import com.ds05.mylauncher.R;

/**
 * Created by Jun.wang on 2018/5/6.
 */

public class MonitorFragment extends ModuleBaseFragment {
    
    public static final String KEY_INTELL_ALARM_TIME = "key_intelligent_alarm_time";
    public static final String KEY_ALARM_INTERVAL_TIME = "key_alarm_interval_time";
    public static final String KEY_MONITORING_SENS = "key_monitoring_sensitivity";
    public static final String KEY_ALARM_SOUND = "key_auto_alarm_sound";
    public static final String KEY_ALARM_VOLUME = "key_auto_alarm_volume";
    public static final String KEY_SHOOTING_MODE = "key_shooting_mode";

    private static final String[] KEYS = {
            KEY_INTELL_ALARM_TIME,
            KEY_MONITORING_SENS,
            KEY_SHOOTING_MODE,
            KEY_ALARM_INTERVAL_TIME
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.monitor_fragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(R.string.string_monitor);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Preference preference;
        preference = findPreference(KEY_INTELL_ALARM_TIME);
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            Log.e("XXX", "Integer.parseInt(listPreference.getValue())---:" + Integer.parseInt(listPreference.getValue()));
            preference.setSummary(listPreference.getEntries()
                    [Integer.parseInt(listPreference.getValue())]);

        }
    }
}






















