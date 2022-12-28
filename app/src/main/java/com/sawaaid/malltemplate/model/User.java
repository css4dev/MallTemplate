package com.sawaaid.malltemplate.model;


import androidx.annotation.Keep;

import java.io.Serializable;
@Keep
public class User implements Serializable {
    public int id;
    public String name, phoneNumber, token, email, password, date;
}
