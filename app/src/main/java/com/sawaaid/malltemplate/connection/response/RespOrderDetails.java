package com.sawaaid.malltemplate.connection.response;

import com.sawaaid.malltemplate.model.Ads;
import com.sawaaid.malltemplate.model.OrderDetails;

import java.io.Serializable;
import java.util.List;

public class RespOrderDetails implements Serializable {
    public int code;
    public String message;
    public List<OrderDetails> data;
}
