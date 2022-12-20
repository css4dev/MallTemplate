package com.sawaaid.malltemplate.connection.response;

import com.sawaaid.malltemplate.model.SubSection;

import java.io.Serializable;
import java.util.List;

public class RespSubSection implements Serializable {
    public int code;
    public String message;
    public List<SubSection> data;
}
