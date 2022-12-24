package com.sawaaid.malltemplate.room.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "entity_basket")
public class EntityBasket {

    @PrimaryKey(autoGenerate = true)
    public Integer id1;

    @ColumnInfo(name = "product_id")
    public Integer productId;

    @ColumnInfo(name = "quantity")
    public Double quantity;

    @ColumnInfo(name = "price")
    public Double price;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
