package com.example.hao.gankio.acitivity;

import android.support.v7.app.AppCompatActivity;

import rx.Subscription;

/**
 * Created by hao on 2016-09-22.
 */

public class BaseActivity extends AppCompatActivity {

    protected Subscription subscription;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unsubscribe();
    }

    protected void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
