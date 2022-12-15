package com.sawaaid.malltemplate.connection.response;

import com.sawaaid.malltemplate.model.Ads;

import java.io.Serializable;
import java.util.List;

public class RespAds implements Serializable {
    public int code;
    public String message;
    public List<Ads> data;
}
