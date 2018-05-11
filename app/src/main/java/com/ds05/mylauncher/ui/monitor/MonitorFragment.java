package com.ds05.mylauncher.ui.monitor;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.SwitchPreference;
import android.util.Log;
import android.view.View;

import com.ds05.mylauncher.ModuleBaseFragment;
import com.ds05.mylauncher.R;
import com.ds05.mylauncher.common.manager.PrefDataManager;

/**
 * Created by Jun.wang on 2018/5/6.
 */



//import static com.ds05.mylauncher.common.manager.PrefDataManager.AlarmMode.Capture;
//import static com.ds05.mylauncher.common.manager.PrefDataManager.AlarmMode.Recorder;
import static com.ds05.mylauncher.common.manager.PrefDataManager.AutoAlarmSound.Alarm;
import static com.ds05.mylauncher.common.manager.PrefDataManager.AutoAlarmSound.Scream;
import static com.ds05.mylauncher.common.manager.PrefDataManager.AutoAlarmSound.Silence;
import static com.ds05.mylauncher.common.manager.PrefDataManager.MonitorSensitivity.High;
import static com.ds05.mylauncher.common.manager.PrefDataManager.MonitorSensitivity.Low;
//import static com.ds05.mylauncher.service.HWSink.ALARM_INTERVAL_TIME_30SEC;
//import static com.ds05.mylauncher.service.HWSink.AUTO_ALARM_TIME_3SEC;


public class MonitorFragment extends ModuleBaseFragment {

    public static final String KEY_HUMAN_MONIOTOR = "key_human_monitor";
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

        int intervalTime = 0;
        int alarmTime = 0;
        int sens = 0;
        int alarmsound = 0;
        int alarmmode = 0;

        // humanMonitor
        SwitchPreference humanmonitorSwitchPreference =
                (SwitchPreference)findPreference(KEY_HUMAN_MONIOTOR);
        humanmonitorSwitchPreference.setChecked(PrefDataManager.getHumanMonitorState());

        //Auto Alarm TIme
        if((PrefDataManager.getAutoAlarmTime())==3000){
            alarmTime = 0;
        }else if((PrefDataManager.getAutoAlarmTime()) == 8000){
            alarmTime = 1;
        }else if((PrefDataManager.getAutoAlarmTime()) == 15000){
            alarmTime = 2;
        }else if((PrefDataManager.getAutoAlarmTime()) == 25000){
            alarmTime = 3;
        }
        ListPreference alarmListPreference = (ListPreference) findPreference(KEY_INTELL_ALARM_TIME);
        Log.d("PP","############# alarmTime = " + alarmTime);
        alarmListPreference.setValueIndex(alarmTime);//length = 4;

        // Alarm interval time
        if((PrefDataManager.getAlarmIntervalTime()) == 30000){
            intervalTime = 0;
        }else if((PrefDataManager.getAlarmIntervalTime()) == 90000){
            intervalTime = 1;
        }else if((PrefDataManager.getAlarmIntervalTime()) == 180000){
            intervalTime = 2;
        }
        ListPreference intervalListPreference = (ListPreference) findPreference(KEY_ALARM_INTERVAL_TIME);
        intervalListPreference.setValueIndex(intervalTime);

        // Human Monitor Sensi
        if((PrefDataManager.getHumanMonitorSensi()).equals(High)){
            sens = 0;
        }else if((PrefDataManager.getHumanMonitorSensi()).equals(Low)){
            sens = 1;
        }
        ListPreference sensListPreference = (ListPreference) findPreference(KEY_MONITORING_SENS);
        sensListPreference.setValueIndex(sens);

        // alarm sound
/*        if((PrefDataManager.getAlarmSound()).equals(Silence)){
            alarmsound = 0;

        }else if((PrefDataManager.getAlarmSound()).equals(Alarm)){
            alarmsound = 1;
        }else if((PrefDataManager.getAlarmSound()).equals(Scream)){
            alarmsound = 2;
        }
        ListPreferenceExt alarmsoundListPreferenceExt = (ListPreferenceExt)findPreference(KEY_ALARM_SOUND);
        alarmsoundListPreferenceExt.setValueIndex(alarmsound);*/
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






















