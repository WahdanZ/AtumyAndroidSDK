package me.atumy.android.sdk.beta;



import android.content.Context;
import android.content.SharedPreferences;

public class SharedData
{
    static final String PREFS_NAME = "atumyappdata";
    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;
    private static SharedData instance;

    private SharedData(Context context)
    {
        this.appSharedPrefs = context.getSharedPreferences(PREFS_NAME, 0);
        this.prefsEditor = this.appSharedPrefs.edit();
    }

    static SharedData getInstance(Context context) {
        if (instance == null) {
            instance = new SharedData(context);
        }
        return instance;
    }

    static SharedData getInstance() {
        return instance;
    }

    int getPrefInt(String key, int defaultVal) {
        return this.appSharedPrefs.getInt("NotificationIcon", defaultVal);
    }

    void setPrefInt(String key, int value) {
        this.prefsEditor.putInt(key, value);
        this.prefsEditor.commit();
    }

    String getPrefString(String key, String defaultVal) {
        return this.appSharedPrefs.getString(key, defaultVal);
    }

    boolean getPrefBoolean(String key, boolean defaultVal) {
        return this.appSharedPrefs.getBoolean(key, defaultVal);
    }

    void setPrefString(String key, String value) {
        this.prefsEditor.putString(key, value);
        this.prefsEditor.commit();
    }

    void setPrefBoolean(String key, boolean value) {
        this.prefsEditor.putBoolean(key, value);
        this.prefsEditor.commit();
    }

    long getPrefLong(String key, Long defaultValue) {
        return this.appSharedPrefs.getLong(key, defaultValue.longValue());
    }

    void setPrefLong(String key, long value) {
        this.prefsEditor.putLong(key, value);
        this.prefsEditor.commit();
    }
}