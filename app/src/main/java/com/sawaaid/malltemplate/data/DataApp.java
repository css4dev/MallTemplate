package com.sawaaid.malltemplate.data;

import android.app.Application;

import com.sawaaid.malltemplate.room.AppDatabase;
import com.sawaaid.malltemplate.room.DAO;


public class DataApp extends Application {
    private static Global global;
    private static SharedPref sharedPref;
    private static DAO dao;


    @Override
    public void onCreate() {
        super.onCreate();
        sharedPref = new SharedPref(this);
        dao = AppDatabase.getDb(this).get();
        global = new Global(this);
    }

    public static synchronized SharedPref pref() {
        return sharedPref;
    }

    public static synchronized Global global() {
        return global;
    }



    public static synchronized DAO dao() {
        return dao;
    }
}
