package com.sawaaid.malltemplate.data;

import android.content.Context;

import com.sawaaid.malltemplate.model.User;


public class Global {

    private Context context;
    private User user;
    private String tempEmail = "";

    public Global(Context context) {
        this.context = context;
        this.user = DataApp.pref().getUser();


    }

    public void logout() {
        user = null;
        DataApp.pref().clearUser();
    }

    public boolean isLogin() {
        return user != null;
    }

    public User getUser() {
        if (user == null) user = DataApp.pref().getUser();
        return user;
    }

    public String getTempEmail() {
        return tempEmail;
    }


    public void setTempEmail(String tempEmail) {
        this.tempEmail = tempEmail;
    }

    public void setUser(User user) {
        this.user = user;
        DataApp.pref().saveUser(user);
    }


}
