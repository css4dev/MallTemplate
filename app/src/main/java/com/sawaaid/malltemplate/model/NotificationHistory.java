package com.sawaaid.malltemplate.model;

import androidx.annotation.Keep;

import java.io.Serializable;
@Keep
public class NotificationHistory implements Serializable {
    public int id;
    public String message, title, notificationDate;
}
