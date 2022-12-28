package com.sawaaid.malltemplate.model;

import androidx.annotation.Keep;

import java.io.Serializable;

@Keep
public class Address implements Serializable {
    public int id, userId;
    public String locationString, locationName, latitude, longitude;
}
