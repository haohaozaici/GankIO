package com.example.hao.gankio.api;

/**
 * Created by hao on 2016-09-22.
 */

public class HaohaoFactory {

    public static final int meizhiSize = 10;
    protected static final Object monitor = new Object();
    static GankApi sGankIOSingleton = null;

    public static GankApi getGankIOSingleton() {
        synchronized (monitor) {
            if (sGankIOSingleton == null) {
                sGankIOSingleton = new HaohaoRetrofit().getGankService();
            }
            return sGankIOSingleton;
        }
    }
}
