package com.infyom.adssdk.apiService;


import com.facebook.ads.BuildConfig;
import com.infyom.adssdk.adModel.Datum;
import com.infyom.adssdk.adModel.Response;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET(BuildConfig.APPLICATION_ID)
    Call<Response<ArrayList<Datum>>> getAdsData();

}
