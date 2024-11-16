package com.example.covid19stats;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import com.example.covid19stats.model.CovidSummary;

public interface CovidApiService {
    @GET("v3/covid-19/countries/{country}")
    Call<CovidSummary> getCovidSummary(@Path("country") String country);

}
