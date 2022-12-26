package com.sawaaid.malltemplate.model;

import java.io.Serializable;

public class Order implements Serializable {
    public int id, status, userId, locationId;
    public double totalPrice;
    public String deliveryDate, orderDate, userNotes;
}
