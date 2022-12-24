package com.sawaaid.malltemplate.connection.response;



import com.sawaaid.malltemplate.model.User;

import java.io.Serializable;
import java.util.List;

public class RespUser implements Serializable {
    public User data;
    public int code;
    public String message;

}
