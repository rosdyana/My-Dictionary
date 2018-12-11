/*
 * Copyright (c) 2018. Rosdyana Kusuma.
 */

package mydictionary.sleepybear.com.mydictionary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import mydictionary.sleepybear.com.mydictionary.R;

public class AppPreference {
    SharedPreferences sharedPreferences;
    Context context;

    public AppPreference(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setFirstRun(boolean isFirstRun) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String key = context.getResources().getString(R.string.app_first_run);
        editor.putBoolean(key, isFirstRun);
        editor.commit();
    }

    public Boolean isFirstRun() {
        String key = context.getResources().getString(R.string.app_first_run);
        return sharedPreferences.getBoolean(key, true);
    }

    public void setENtoID(boolean isENtoID) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String key = context.getResources().getString(R.string.is_en_to_id);
        editor.putBoolean(key, isENtoID);
        editor.commit();
    }

    public Boolean isENToID() {
        String key = context.getResources().getString(R.string.is_en_to_id);
        return sharedPreferences.getBoolean(key, true);
    }
}
