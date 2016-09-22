package com.example.hao.gankio.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okio.Timeout;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hao on 2016-09-22.
 */

public class HaohaoRetrofit {

    final GankApi gankService;

    final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .create();

    HaohaoRetrofit(){
        OkHttpClient client = new OkHttpClient.Builder().
                readTimeout(12, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .client(client)
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        gankService = retrofit.create(GankApi.class);
    }

    public GankApi getGankService(){
        return gankService;
    }

}
