package com.sawaaid.malltemplate.connection.response;

import com.sawaaid.malltemplate.model.Section;

import java.io.Serializable;
import java.util.List;

public class RespSections implements Serializable {
    public int code;
    public String message;
    public List<Section> data;
}
