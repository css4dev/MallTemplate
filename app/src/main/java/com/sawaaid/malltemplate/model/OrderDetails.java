package com.sawaaid.malltemplate.model;

import java.io.Serializable;

public class OrderDetails implements Serializable {
    public int id, productId, orderId;
    public String name;
    public double allPrice, onePrice, quantity;
}
