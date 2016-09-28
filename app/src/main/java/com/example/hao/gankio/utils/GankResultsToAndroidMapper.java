package com.example.hao.gankio.utils;

import com.example.hao.gankio.data.Android;
import com.example.hao.gankio.data.AndroidResult;
import com.example.hao.gankio.data.Gank;
import com.example.hao.gankio.data.GankResult;

import java.util.List;

import rx.functions.Func1;

/**
 * Created by hao on 2016/9/26.
 */

public class GankResultsToAndroidMapper implements Func1<GankResult, Gank> {

    private static GankResultsToAndroidMapper instance = new GankResultsToAndroidMapper();

    private GankResultsToAndroidMapper() {
    }

    public static GankResultsToAndroidMapper getInstance() {
        return instance;
    }

    @Override
    public Gank call(GankResult gankResult) {
        Gank results = gankResult.DetailsResults;
        return results;
    }
}
