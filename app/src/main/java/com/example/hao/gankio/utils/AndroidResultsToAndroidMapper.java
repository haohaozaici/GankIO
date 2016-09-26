package com.example.hao.gankio.utils;

import com.example.hao.gankio.data.Android;
import com.example.hao.gankio.data.AndroidResult;

import java.util.List;

import rx.functions.Func1;

/**
 * Created by hao on 2016/9/26.
 */

public class AndroidResultsToAndroidMapper implements Func1<AndroidResult, List<Android>> {

    private static AndroidResultsToAndroidMapper instance = new AndroidResultsToAndroidMapper();

    private AndroidResultsToAndroidMapper() {
    }

    public static AndroidResultsToAndroidMapper getInstance() {
        return instance;
    }

    @Override
    public List<Android> call(AndroidResult androidResult) {
        List<Android> results = androidResult.MainResults;
        return results;
    }
}
