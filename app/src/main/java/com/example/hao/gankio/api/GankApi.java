package com.example.hao.gankio.api;

import com.example.hao.gankio.data.GankData;
import com.example.hao.gankio.data.MeizhiData;
import com.example.hao.gankio.data.休息视频Data;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by hao on 2016-09-22.
 */

public interface GankApi {

    @GET("data/福利/" + HaohaoFactory.meizhiSize + "/{page}") Observable<MeizhiData> getMeizhiData(
            @Path("page") int page);

    @GET("day/{year}/{month}/{day}") Observable<GankData> getGankData(
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day);

    @GET("data/休息视频/" + HaohaoFactory.meizhiSize + "/{page}") Observable<休息视频Data> get休息视频Data(
            @Path("page") int page);

}
