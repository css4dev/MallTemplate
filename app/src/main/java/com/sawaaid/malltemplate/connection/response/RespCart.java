package com.sawaaid.malltemplate.connection.response;

import com.sawaaid.malltemplate.model.Product;

import java.io.Serializable;
import java.util.List;

public class RespCart implements Serializable {
    public List<Product> data;
    public int code;
    public String message;

}
