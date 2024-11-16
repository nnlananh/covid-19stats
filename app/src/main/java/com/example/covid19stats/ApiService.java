package com.example.covid19stats;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseModel> registerUser(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseModel> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );
}
