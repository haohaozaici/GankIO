package com.example.hao.gankio.api;

import com.example.hao.gankio.data.GankBean;
import com.example.hao.gankio.data.GankData;
import com.example.hao.gankio.data.休息视频Data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hao on 2016-09-22.
 */

public interface GankApi {

    @GET("data/福利/" + HaohaoFactory.meizhiSize + "/{page}")
    Call<GankBean> getMeizhiData2(@Path("page")int page);


    @GET("day/{year}/{month}/{day}")
    Observable<GankData> getGankData(
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day);

    @GET("data/休息视频/" + HaohaoFactory.meizhiSize + "/{page}")
    Call<GankBean> get休息视频Data2(@Path("page") int page);

//    @GET("{username}") Call(String name)

}
