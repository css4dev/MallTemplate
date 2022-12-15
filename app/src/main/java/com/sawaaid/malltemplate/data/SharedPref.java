package com.sawaaid.malltemplate.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.sawaaid.malltemplate.model.User;

public class SharedPref {

    private static SharedPref mInstance;

    public static synchronized SharedPref get() {
        return mInstance;
    }

    private static String default_ringtone_url = "content://settings/system/notification_sound";
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences prefs;

    public SharedPref(Context context) {
        mInstance = this;
        this.context = context;
        sharedPreferences = context.getSharedPreferences("MAIN_PREF", Context.MODE_PRIVATE);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    // Preference for User Login
    public void saveUser(User user) {
        String str = new Gson().toJson(user);
        sharedPreferences.edit().putString("USER_PREF_KEY", str).apply();
    }


    public User getUser() {
        String str = sharedPreferences.getString("USER_PREF_KEY", null);
        if (str != null) {
            return new Gson().fromJson(str, User.class);
        }
        return null;
    }

    public void clearUser() {
        sharedPreferences.edit().putString("USER_PREF_KEY", null).apply();
    }


    // set city id
    public void setCityId(Long id) {
        sharedPreferences.edit().putLong("CITY_ID", id).apply();
    }

    public Long getCityId() {
        return sharedPreferences.getLong("CITY_ID", 0);
    }

    // Preference for Fcm register
    public void setFcmRegId(String fcmRegId) {
        sharedPreferences.edit().putString("FCM_PREF_KEY", fcmRegId).apply();
    }

    public String getFcmRegId() {
        return sharedPreferences.getString("FCM_PREF_KEY", null);
    }

    public boolean isFcmRegIdEmpty() {
        return TextUtils.isEmpty(getFcmRegId());
    }

    public void setSubscibeNotif(boolean value) {
        sharedPreferences.edit().putBoolean("SUBSCRIBE_NOTIF", value).apply();
    }

    public void setDuesStatus(boolean value) {
        sharedPreferences.edit().putBoolean("DUES", value).apply();
    }

    public boolean isDuesOpen() {
        return sharedPreferences.getBoolean("DUES", false);
    }

    public boolean isSubscibeNotif() {
        return sharedPreferences.getBoolean("SUBSCRIBE_NOTIF", false);
    }

    public boolean isClassTeacher() {
        return sharedPreferences.getBoolean("CLASS_TEACHER", false);
    }

    public void setClassTeacher(boolean value) {
        sharedPreferences.edit().putBoolean("CLASS_TEACHER", value).apply();
    }

    // Preference for first launch
    public void setFirstLaunch(boolean flag) {
        sharedPreferences.edit().putBoolean("FIRST_LAUNCH", flag).apply();
    }

    public void setLoginDate(String date) {
        sharedPreferences.edit().putString("LOGIN_DATE", date).apply();
    }

    public String getLoginDate() {
        return sharedPreferences.getString("LOGIN_DATE", "0");
    }

    public boolean isFirstLaunch() {
        return sharedPreferences.getBoolean("FIRST_LAUNCH", true);
    }

    // Preference for settings
    public void setPushNotification(boolean value) {
        sharedPreferences.edit().putBoolean("SETTINGS_PUSH_NOTIF", value).apply();
    }

    public boolean getPushNotification() {
        return sharedPreferences.getBoolean("SETTINGS_PUSH_NOTIF", true);
    }

    public void setVibration(boolean value) {
        sharedPreferences.edit().putBoolean("SETTINGS_VIBRATION", value).apply();
    }

    public boolean getVibration() {
        return sharedPreferences.getBoolean("SETTINGS_VIBRATION", true);
    }

    public String getRingtone() {
        return sharedPreferences.getString("SETTINGS_RINGTONE", default_ringtone_url);
    }

    public void setImageCache(boolean value) {
        sharedPreferences.edit().putBoolean("SETTINGS_IMG_CACHE", value).apply();
    }

    public boolean getImageCache() {
        return sharedPreferences.getBoolean("SETTINGS_IMG_CACHE", true);
    }

    public String getAppStatus() {
        return sharedPreferences.getString("APP_STATUS", "");
    }

    public void setAppStatus(String status) {
        sharedPreferences.edit().putString("APP_STATUS", status).apply();
    }

    // Preference for User Login


    // Preference for first launch
    public void setIntersCounter(int counter) {
        sharedPreferences.edit().putInt("INTERS_COUNT", counter).apply();
    }

    public int getIntersCounter() {
        return sharedPreferences.getInt("INTERS_COUNT", 0);
    }

    public void clearIntersCounter() {
        sharedPreferences.edit().putInt("INTERS_COUNT", 0).apply();
    }

}
