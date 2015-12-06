package com.project.distractless;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Andreas Schrade on 19.02.2015.
 */
public class PrefUtils {
    private static final String PREF_KIOSK_MODE = "pref_kiosk_mode";
    private static final String PREF_FROM_ALARM = "pref_from_alarm";
    private static final String PREF_RUN_TIME = "pref_run_time";

    public static boolean isKioskModeActive(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_KIOSK_MODE, false);
    }

    public static void setKioskModeActive(final boolean active, final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_KIOSK_MODE, active).commit();
    }

    public static void setRunTime(final Calendar runTime, final Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putLong(PREF_RUN_TIME, runTime.getTimeInMillis()).commit();
    }

    public static boolean isLaunchInRange(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Calendar def = Calendar.getInstance();
        Long runTime = sp.getLong(PREF_RUN_TIME, def.getTimeInMillis());
        if (runTime <= Calendar.getInstance().getTimeInMillis())
            return true;
        else
            return false;
    }

}
