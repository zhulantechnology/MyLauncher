package com.ds05.mylauncher.common.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ds05.mylauncher.LauncherApplication;
import com.ds05.mylauncher.R;
import com.ds05.mylauncher.ui.monitor.MonitorFragment;

/**
 * Created by Jun.wang on 2018/5/8.
 */

public class PrefDataManager {
    public static final String COMMON_PREF_DATA = "common_pref_data";
    public static final String APP_PREF_SETTINGS = "com.ds05.myLauncher_preferences";

    public static final String ALARM_SOUND_VOLUME = "alarm_sound_volume";
    public static final String DOORBELL_VOLUME = "doorbell_volume";
    public static final String SERVER_TYPE = "server_type";

    //AutoAlarmTime begin
    public enum AutoAlarmTime {
        Time_3sec(3000), Time_8sec(8000),  Time_15sec(15000),  Time_25sec(25000);
        private int time;
        AutoAlarmTime(int t) {
            time = t;
        }

        public static AutoAlarmTime get(int t) {
            switch (t) {
                case 3000:
                    return Time_3sec;
                case 8000:
                    return Time_8sec;
                case 15000:
                    return Time_15sec;
                case 25000:
                    return Time_25sec;
                default:
                    return null;
            }
        }

        public int time() {
            return time;
        }
    }
    //AutoAlarmTime end

    //AlarmIntervalTime start
    public enum AlarmIntervalTime{
        Time_30sec(30000),Time_90sec(90000),Time_180sec(180000);

        private int time;
        AlarmIntervalTime(int t){ time = t;}

        public static AlarmIntervalTime get(int t){
            switch(t){
                case 30000:
                    return Time_30sec;
                case 90000:
                    return Time_90sec;
                case 180000:
                    return Time_180sec;
                default:
                    return null;
            }
        }
        public int time(){return time;}
    }
    //AlarmIntervalTime end

    //MonitorSensitivity start
    public enum MonitorSensitivity {
        High(1), Low(2);

        int sensi;
        MonitorSensitivity(int sensi) {
            this.sensi = sensi;
        }
        public int sensitivity() {
            return sensi;
        }
        public static MonitorSensitivity get(int sensi) {
            switch(sensi) {
                case 1:
                    return High;
                case 2:
                    return Low;
                default:
                    return null;
            }
        }
    }
    //MonitorSensitivity end

    //AlarmMode start
    public enum AlarmMode {
        Recorder(1), Capture(0);

        private int mode;
        AlarmMode(int mode) {
            this.mode = mode;
        }
        public int mode() {
            return mode;
        }
        public static AlarmMode get(int i) {
            switch(i) {
                case 0:
                    return Capture;
                case 1:
                    return Recorder;
                default:
                    return null;
            }
        }
    }
    //AlarmMode end

    //AutoAlarmSound start
    public enum AutoAlarmSound {
        Silence(0), Alarm(1), Scream(2);
        private int sound;
        AutoAlarmSound(int i) {
            sound = i;
        }
        public static AutoAlarmSound get(int i) {
            switch(i) {
                case 0:
                    return Silence;
                case 1:
                    return Alarm;
                case 2:
                    return Scream;
                default:
                    return null;
            }
        }
        public int sound() {
            return sound;
        }
    }
    //AutoAlarmSound end

    //DoorbellSound start
    public enum DoorbellSound{
        ding_dang(0),ding_dong(1),dong_dong(2),ji_cu_qiao_men(3);
        private int sound_door;
        DoorbellSound(int i){
            sound_door = i;
        }
        public static DoorbellSound get(int i){
            switch (i){
                case 0:
                    return ding_dang;
                case 1:
                    return ding_dong;
                case 2:
                    return dong_dong;
                case 3:
                    return ji_cu_qiao_men;
                default:
                    return null;
            }
        }
        public int sound_door(){return sound_door;}
    }
    //DoorbellSound end

    public static boolean getHumanMonitorState() {
        boolean def = LauncherApplication.getContext()
                .getResources().getBoolean(R.bool.def_human_monitor_value);
        return getBoolean(APP_PREF_SETTINGS, MonitorFragment.KEY_HUMAN_MONIOTOR, def);
    }

    public static int getAlarmSoundIndex() {
        Context ctx = LauncherApplication.getContext();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        String def = ctx.getString(R.string.def_auto_alarm_sound);
        String index = pref.getString(MonitorFragment.KEY_ALARM_SOUND, def);
        return Integer.parseInt(index);
    }


    public static float getAlarmSoundVolume() {
        return getFloat(COMMON_PREF_DATA, ALARM_SOUND_VOLUME, 1.0f);
    }
    public static void setAlarmSoundVolume(float val) {
        if(val < 0) {
            throw new IllegalArgumentException("Invalide volume: " + val);
        }
        setFloat(COMMON_PREF_DATA, ALARM_SOUND_VOLUME, val);
    }
    public static float getDoorbellVolume() {
        return getFloat(COMMON_PREF_DATA, DOORBELL_VOLUME, 1.0f);
    }
    public static void setDoorbellVolume(float val) {
        if(val < 0) {
            throw new IllegalArgumentException("Invalide volume: " + val);
        }
        setFloat(COMMON_PREF_DATA, DOORBELL_VOLUME, val);
    }
    public static int getShootNumber() {
        return getInt(COMMON_PREF_DATA, "common_shoot_number", 3);
    }
    public static void setShootNumber(int number) {
        setInt(COMMON_PREF_DATA, "common_shoot_number", number);
    }


    /************ Base Method **********/
    public static float getFloat(String file, String key, float def) {
        SharedPreferences sp = LauncherApplication.getContext()
                .getSharedPreferences(file, Context.MODE_PRIVATE);
        return sp.getFloat(key, def);
    }
    public static void setFloat(String file, String key, float val) {
        SharedPreferences sp = LauncherApplication.getContext()
                .getSharedPreferences(file, Context.MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.putFloat(key, val);
        spe.commit();
    }
    public static long getLong(String file, String key, long def) {
        SharedPreferences sp = LauncherApplication.getContext()
                .getSharedPreferences(file, Context.MODE_PRIVATE);
        return sp.getLong(key, def);
    }
    public static int getInt(String file, String key, int def) {
        SharedPreferences sp = LauncherApplication.getContext()
                .getSharedPreferences(file, Context.MODE_PRIVATE);
        return sp.getInt(key, def);
    }
    public static String getString(String file, String key, String def) {
        SharedPreferences sp = LauncherApplication.getContext()
                .getSharedPreferences(file, Context.MODE_PRIVATE);
        return sp.getString(key, def);
    }
    public static void setLong(String file, String key, long val) {
        SharedPreferences sp = LauncherApplication.getContext()
                .getSharedPreferences(file, Context.MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.putLong(key, val);
        spe.commit();
    }
    public static void setLong(SharedPreferences sp, String key, long val) {
        SharedPreferences.Editor spe = sp.edit();
        spe.putLong(key, val);
        spe.commit();
    }
    public static void setInt(String file, String key, int val) {
        SharedPreferences sp = LauncherApplication.getContext()
                .getSharedPreferences(file, Context.MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.putInt(key, val);
        spe.commit();
    }
    public static void setInt(SharedPreferences sp, String key, int val) {
        SharedPreferences.Editor spe = sp.edit();
        spe.putInt(key, val);
        spe.commit();
    }
    public static void setString(String file, String key, String val) {
        SharedPreferences sp = LauncherApplication.getContext()
                .getSharedPreferences(file, Context.MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.putString(key, val);
        spe.commit();
    }
    public static void setString(SharedPreferences sp, String key, String val) {
        SharedPreferences.Editor spe = sp.edit();
        spe.putString(key, val);
        spe.commit();
    }
    public static void setBoolean(String file, String key, boolean val) {
        SharedPreferences sp = LauncherApplication.getContext()
                .getSharedPreferences(file, Context.MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.putBoolean(key, val);
        spe.commit();
    }
    public static boolean getBoolean(String file, String key, boolean def) {
        SharedPreferences sp = LauncherApplication.getContext()
                .getSharedPreferences(file, Context.MODE_PRIVATE);
        return sp.getBoolean(key, def);
    }

    public static int getServerType() {
        return getInt(COMMON_PREF_DATA, SERVER_TYPE, 0);
    }
    public static void setServerType(int val) {
        if(val < 0 || val > 1) {
            throw new IllegalArgumentException("Invalide volume: " + val);
        }
        setInt(COMMON_PREF_DATA, SERVER_TYPE, val);
    }
}































