package com.sawaaid.malltemplate.connection;


import com.sawaaid.malltemplate.connection.response.RespAds;
import com.sawaaid.malltemplate.connection.response.RespProduct;
import com.sawaaid.malltemplate.connection.response.RespSections;
import com.sawaaid.malltemplate.connection.response.RespSubSection;


import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.Path;


public interface API {


    @GET("api/sections")
    Call<RespSections> sections();

    @GET("api/sliders")
    Call<RespAds> ads();

    @GET("api/sectionProducts/{sectionId}/{subsectionId}/{page}")
    Call<RespProduct> sectionProduct(@Path("sectionId") String sectionId,@Path("subsectionId") String subSectionId ,@Path("page") String page_no);

    @GET("api/newProducts")
    Call<RespProduct> newProducts();

    @GET("api/specialProducts")
    Call<RespProduct> specialProducts();

    @GET("api/searchProducts/{word}/{page}")
    Call<RespProduct> search(@Path("word") String word, @Path("page") String page);

    @GET("api/subSections/{sectionId}")
    Call<RespSubSection> subSections(@Path("sectionId") String sectionId);


}
