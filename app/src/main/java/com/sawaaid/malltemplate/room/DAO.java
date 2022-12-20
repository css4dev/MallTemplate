package com.sawaaid.malltemplate.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.sawaaid.malltemplate.model.Ads;
import com.sawaaid.malltemplate.model.Product;
import com.sawaaid.malltemplate.model.Section;

import java.util.List;

@Dao
public interface DAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAds(List<Ads> ads);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProduct(List<Product> products);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSections(List<Section> sections);

    @Query("SELECT * FROM ads")
    List<Ads> getAds();

    @Query("SELECT * FROM sections")
    List<Section> getSections();

    @Query("SELECT * FROM product WHERE isNew = 1")
    List<Product> getProductsIsNew();

    @Query("SELECT * FROM product WHERE isSpecial = 1")
    List<Product> getProductsIsSpecial();

    @Query("DELETE FROM ads")
    void deleteAds();

    @Query("DELETE FROM sections")
    void deleteSections();

    @Query("DELETE FROM product WHERE IsNew = 1")
    void deleteProductsIsNew();

    @Query("DELETE FROM product WHERE IsSpecial = 1")
    void deleteProductsIsSpecial();

}
