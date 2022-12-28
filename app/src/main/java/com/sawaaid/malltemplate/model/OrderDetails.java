package com.sawaaid.malltemplate.model;

import androidx.annotation.Keep;

import java.io.Serializable;
@Keep
public class OrderDetails implements Serializable {
    public int id, productId, orderId;
    public String name;
    public double allPrice, onePrice, quantity;
}
