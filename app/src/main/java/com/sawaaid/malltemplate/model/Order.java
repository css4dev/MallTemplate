package com.sawaaid.malltemplate.model;

import androidx.annotation.Keep;

import java.io.Serializable;
@Keep
public class Order implements Serializable {
    public int id, status, userId, locationId;
    public double totalPrice;
    public String deliveryDate, orderDate, userNotes;
}
