package com.modefin.app.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;

    public static OkHttpClient getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(Integer.parseInt(ApplicationProperties.getInstance().getProperty("server_time_out")), TimeUnit.SECONDS)
                .readTimeout(Integer.parseInt(ApplicationProperties.getInstance().getProperty("server_time_out")), TimeUnit.SECONDS)
                .writeTimeout(Integer.parseInt(ApplicationProperties.getInstance().getProperty("server_time_out")), TimeUnit.SECONDS);
        httpClient.addInterceptor(interceptor);

        return httpClient.build();
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .client(getClient())
                    .baseUrl(ApplicationProperties.getInstance().getProperty("server_url"))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
