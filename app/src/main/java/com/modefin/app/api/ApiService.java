package com.modefin.app.api;

import com.modefin.app.model.MakeupResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("api/v1/products.json")
    Call<List<MakeupResponse>> getMakeupList(@Query("brand") String brand);

}
