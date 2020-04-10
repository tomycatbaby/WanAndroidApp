package com.lzf.wanandroidapp.base;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SettingUtil {
    private static SharedPreferences setting;
    private static final String NIGHT_MODE = "night_mode";

    public static boolean getIsNightMode() {
        checkNull();
        return setting.getBoolean(NIGHT_MODE, false);
    }

    public static void checkNull() {
        if (setting == null) {
            setting = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        }
    }

    public static void setNightMode(boolean b) {
        setting.edit().putBoolean(NIGHT_MODE, b).apply();
    }
}
