package com.sawaaid.malltemplate.model;

import java.io.Serializable;

public class Product implements Serializable {
    public int id, quantity, isNew, isSpecial, sectionId, subSectionId, isDeleted;
    public double dollarPrice, priceAfterSale;
    public String name, photo,
            addedDate, purchaseOption,
            productDetails, editFromPc,
            barcode, sectionName,
            subSectionName, code;
}
