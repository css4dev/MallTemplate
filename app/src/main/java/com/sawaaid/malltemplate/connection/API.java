package com.sawaaid.malltemplate.connection;




import com.sawaaid.malltemplate.connection.response.RespSections;
import com.sawaaid.malltemplate.connection.response.RespUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface API {

    String token = "Cache-Control: max-age=0";


    @FormUrlEncoded
    @POST("Routing.php")
    Call<RespUser> login(@FieldMap HashMap<String, String> map);

    @GET("api/sections")
    Call<RespSections> sections();



}
