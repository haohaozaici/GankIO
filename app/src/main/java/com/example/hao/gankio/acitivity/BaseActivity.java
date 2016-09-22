package com.example.hao.gankio.acitivity;

import android.support.v7.app.AppCompatActivity;

import com.example.hao.gankio.api.GankApi;
import com.example.hao.gankio.api.HaohaoFactory;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by hao on 2016-09-22.
 */

public class BaseActivity extends AppCompatActivity {

    public static final GankApi sGankIO = HaohaoFactory.getGankIOSingleton();

    private CompositeSubscription mCompositeSubscription;

    public CompositeSubscription getCompositeSubscription() {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        return this.mCompositeSubscription;
    }

    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }
}
