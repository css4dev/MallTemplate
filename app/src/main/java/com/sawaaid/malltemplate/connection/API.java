package com.sawaaid.malltemplate.connection;


import com.sawaaid.malltemplate.connection.response.RespAds;
import com.sawaaid.malltemplate.connection.response.RespSections;


import retrofit2.Call;

import retrofit2.http.GET;


public interface API {


    @GET("api/sections")
    Call<RespSections> sections();

    @GET("api/sliders")
    Call<RespAds> ads();


}
