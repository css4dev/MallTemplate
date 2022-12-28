package com.sawaaid.malltemplate.connection;


import com.sawaaid.malltemplate.connection.response.RespAddress;
import com.sawaaid.malltemplate.connection.response.RespAds;
import com.sawaaid.malltemplate.connection.response.Resp;
import com.sawaaid.malltemplate.connection.response.RespNotificationHistory;
import com.sawaaid.malltemplate.connection.response.RespOrder;
import com.sawaaid.malltemplate.connection.response.RespOrderDetails;
import com.sawaaid.malltemplate.connection.response.RespProduct;
import com.sawaaid.malltemplate.connection.response.RespSections;
import com.sawaaid.malltemplate.connection.response.RespSubSection;
import com.sawaaid.malltemplate.connection.response.RespUser;
import com.sawaaid.malltemplate.room.entity.EntityBasket;


import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface API {


    @GET("api/sections")
    Call<RespSections> sections();

    @GET("api/sliders")
    Call<RespAds> ads();

    @GET("api/sectionProducts/{sectionId}/{subsectionId}/{page}")
    Call<RespProduct> sectionProduct(@Path("sectionId") String sectionId, @Path("subsectionId") String subSectionId, @Path("page") String page_no);

    @GET("api/newProducts")
    Call<RespProduct> newProducts();

    @GET("api/specialProducts")
    Call<RespProduct> specialProducts();

    @GET("api/basketProducts/{id}")
    Call<RespProduct> cartProducts(@Path("id") String id);

    @GET("api/favoriteProducts/{id}/{page}")
    Call<RespProduct> favoriteProducts(@Path("id") String id, @Path("page") String page);

    @GET("api/searchProducts/{word}/{page}")
    Call<RespProduct> search(@Path("word") String word, @Path("page") String page);

    @GET("api/notification/{page}")
    Call<RespNotificationHistory> notificationHistory(@Path("page") String page);


    @GET("api/subSections/{sectionId}")
    Call<RespSubSection> subSections(@Path("sectionId") String sectionId);

    @GET("api/location/{userId}")
    Call<RespAddress> addresses(@Path("userId") String userId);

    @FormUrlEncoded
    @POST("api/signIn")
    Call<RespUser> login(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("api/addLocation")
    Call<Resp> insertAddress(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("api/userOrder")
    Call<RespOrder> orders(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("api/signUp")
    Call<RespUser> signUp(@FieldMap HashMap<String, String> map);

    @DELETE("api/deleteLocation/{id}")
    Call<Resp> deleteAddress(@Path("id") String id);

    @GET("api/ordersDetails/{orderId}")
    Call<RespOrderDetails> orderDetails(@Path("orderId") String orderId);

    @POST("api/addOrder")
    Call<Resp> insertOrder(@Body HashMap<String, Object> map);
}
