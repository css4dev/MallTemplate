package com.sawaaid.malltemplate.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.sawaaid.malltemplate.model.Ads;
import com.sawaaid.malltemplate.model.Product;
import com.sawaaid.malltemplate.model.Section;
import com.sawaaid.malltemplate.model.SubSection;
import com.sawaaid.malltemplate.room.entity.EntityBasket;

import java.util.List;

@Dao
public interface DAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAds(List<Ads> ads);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProduct(List<Product> products);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSections(List<Section> sections);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSubSections(List<SubSection> sections);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEntityBasket(EntityBasket productsIds);

    @Query("SELECT * FROM ads")
    List<Ads> getAds();

    @Query("SELECT * FROM entity_basket")
    List<EntityBasket> getEntityBasket();

    @Query("SELECT count(product_id) FROM entity_basket WHERE product_id = :productId")
    Integer checkProduct(Integer productId);

    @Query("UPDATE entity_basket SET quantity = :quantity WHERE product_id = :productId")
    Integer updateProductQuantity(Integer productId, Double quantity);

    @Query("SELECT COUNT(*) FROM entity_basket")
    int getProductBasketCount();

    @Query("SELECT * FROM sections")
    List<Section> getSections();

    @Query("SELECT * FROM sub_section WHERE sectionId=:sectionId")
    List<SubSection> getSubSections(String sectionId);

    @Query("SELECT * FROM product WHERE isNew = 1")
    List<Product> getProductsIsNew();

    @Query("SELECT * FROM product WHERE isSpecial = 1")
    List<Product> getProductsIsSpecial();

    @Query("SELECT * FROM product WHERE sectionId = :sectionId AND subSectionId = :subSectionId")
    List<Product> getSectionProducts(String sectionId, String subSectionId);

    @Query("DELETE FROM ads")
    void deleteAds();

    @Query("DELETE FROM sections")
    void deleteSections();

    @Query("DELETE FROM product WHERE IsNew = 1")
    void deleteProductsIsNew();

    @Query("DELETE FROM entity_basket WHERE product_id = :productId")
    void deleteProductFromBasket(String productId);

    @Query("DELETE FROM product WHERE sectionId = :sectionId AND subSectionId = :subSectionId")
    void deleteSectionProducts(String sectionId, String subSectionId);

    @Query("DELETE FROM product WHERE IsSpecial = 1")
    void deleteProductsIsSpecial();


    @Query("DELETE FROM sub_section WHERE sectionId = :sectionId")
    void deleteSubSection(String sectionId);

}
