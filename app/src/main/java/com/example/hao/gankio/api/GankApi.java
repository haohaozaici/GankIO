package com.example.hao.gankio.api;

import com.example.hao.gankio.data.AndroidResult;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by hao on 2016-09-22.
 */

public interface GankApi {

    @GET("data/Android/30/{page}" )
    Observable<AndroidResult> getAndroid(@Path("page")int page );

    @GET("data/福利/30/{page}")
    Observable<AndroidResult> getMeizhi(@Path("page")int page);


}
