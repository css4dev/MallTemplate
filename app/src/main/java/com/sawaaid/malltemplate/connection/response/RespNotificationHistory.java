package com.sawaaid.malltemplate.connection.response;

import com.sawaaid.malltemplate.model.Address;
import com.sawaaid.malltemplate.model.NotificationHistory;

import java.io.Serializable;
import java.util.List;

public class RespNotificationHistory implements Serializable {

    public int code;
    public String message;
    public List<NotificationHistory> data;
}
