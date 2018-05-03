package com.ds05.mylauncher.ui.setting;

import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.ListPreference;
import android.util.DisplayMetrics;
import android.util.Log;

import com.ds05.mylauncher.ModuleBaseFragment;
import com.ds05.mylauncher.R;

import java.util.Locale;

/**
 * Created by Jun.wang on 2018/5/2.
 */

public class SettingsFragment extends ModuleBaseFragment {

    public static final String KEY_LANGUAGE = "key_Settings_language";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_fragment);

        initLanguage();
    }

    private void initLanguage() {
        Configuration config = getResources().getConfiguration();
        String currLang = config.locale.getLanguage() + "_" + config.locale.getCountry();
        Log.e("XXX", "currLang------:" + currLang);

        if(!currLang.equals("zh_CN") && !currLang.equals("en_US")) {
            String defLang = getResources().getString(R.string.def_language);
            Locale defLocale = Locale.SIMPLIFIED_CHINESE;
            if(defLang.equals("en_US")) {
                defLocale = Locale.ENGLISH;
            }
            config.locale = defLocale;
            DisplayMetrics dm = getResources().getDisplayMetrics();
            getResources().updateConfiguration(config, dm);
            currLang = defLang;
        }

        ListPreference langPref = (ListPreference)findPreference(KEY_LANGUAGE);
        int index = 0;
        if(currLang.equals("zh_CN")) {
            index = 0;
        } else if(currLang.equals("en_US")) {
            index = 1;
        }

        langPref.setValue("" + index);
        langPref.setSummary(langPref.getEntries()[index]);
        //langPref.setOnPreferenceChangeListener(this);
    }
}




















