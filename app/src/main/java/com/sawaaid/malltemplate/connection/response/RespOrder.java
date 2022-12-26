package com.sawaaid.malltemplate.connection.response;

import com.sawaaid.malltemplate.model.Order;

import java.io.Serializable;
import java.util.List;

public class RespOrder implements Serializable {
    public int code;
    public String message;
    public List<Order> data;
}
