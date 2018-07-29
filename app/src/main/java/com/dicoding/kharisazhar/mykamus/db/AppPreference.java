package com.dicoding.kharisazhar.mykamus.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dicoding.kharisazhar.mykamus.R;

public class AppPreference {

    Context context;
    SharedPreferences pref;

    public AppPreference(Context context) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setFirstRun(Boolean input) {
        SharedPreferences.Editor editor = pref.edit();
        String key = context.getResources().getString(R.string.app_first_run);
        editor.putBoolean(key, input);
        editor.commit();
    }

    public Boolean getFirstRun() {
        String key = context.getResources().getString(R.string.app_first_run);
        return pref.getBoolean(key, true);
    }
}
