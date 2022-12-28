package com.sawaaid.malltemplate.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "entity_favorite")
public class EntityFavorite {

    @PrimaryKey(autoGenerate = true)
    public int id1;

    @ColumnInfo(name = "product_id")
    public Integer productId;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
