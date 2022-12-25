package com.sawaaid.malltemplate.connection.response;

import com.sawaaid.malltemplate.model.Address;

import java.io.Serializable;
import java.util.List;

public class RespAddress implements Serializable {
    public int code;
    public String message;
    public List<Address> data;
}
