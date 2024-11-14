package com.example.covid19stats;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://10.0.2.2/covid19statapp/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            // Cấu hình Gson với setLenient() để chấp nhận JSON không đúng định dạng
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            // Khởi tạo Retrofit với GsonConverterFactory
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson)) // Thêm Gson vào Retrofit
                    .build();
        }
        return retrofit;
    }
}
