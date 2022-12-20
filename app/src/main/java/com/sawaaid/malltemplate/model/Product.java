package com.sawaaid.malltemplate.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "product")
public class Product implements Serializable {

    @PrimaryKey
    public int id1;

    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "quantity")
    public int quantity;

    @ColumnInfo(name = "isNew")
    public int isNew;

    @ColumnInfo(name = "isSpecial")
    public int isSpecial;

    @ColumnInfo(name = "sectionId")
    public int sectionId;

    @ColumnInfo(name = "subSectionId")
    public int subSectionId;

    @ColumnInfo(name = "isDeleted")
    public int isDeleted;

    @ColumnInfo(name = "dollarPrice")
    public double dollarPrice;

    @ColumnInfo(name = "priceAfterSale")
    public double priceAfterSale;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "photo")
    public String photo;

    @ColumnInfo(name = "addedDate")
    public String addedDate;

    @ColumnInfo(name = "purchaseOption")
    public String purchaseOption;

    @ColumnInfo(name = "productDetails")
    public String productDetails;

    @ColumnInfo(name = "editFromPc")
    public String editFromPc;

    @ColumnInfo(name = "barcode")
    public String barcode;

    @ColumnInfo(name = "sectionName")
    public String sectionName;

    @ColumnInfo(name = "subSectionName")
    public String subSectionName;

    @ColumnInfo(name = "code")
    public String code;

    public int getId1() {
        return id1;
    }

    public void setId1(int id1) {
        this.id1 = id1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    public int getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(int isSpecial) {
        this.isSpecial = isSpecial;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public int getSubSectionId() {
        return subSectionId;
    }

    public void setSubSectionId(int subSectionId) {
        this.subSectionId = subSectionId;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public double getDollarPrice() {
        return dollarPrice;
    }

    public void setDollarPrice(double dollarPrice) {
        this.dollarPrice = dollarPrice;
    }

    public double getPriceAfterSale() {
        return priceAfterSale;
    }

    public void setPriceAfterSale(double priceAfterSale) {
        this.priceAfterSale = priceAfterSale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public String getPurchaseOption() {
        return purchaseOption;
    }

    public void setPurchaseOption(String purchaseOption) {
        this.purchaseOption = purchaseOption;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public String getEditFromPc() {
        return editFromPc;
    }

    public void setEditFromPc(String editFromPc) {
        this.editFromPc = editFromPc;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSubSectionName() {
        return subSectionName;
    }

    public void setSubSectionName(String subSectionName) {
        this.subSectionName = subSectionName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
