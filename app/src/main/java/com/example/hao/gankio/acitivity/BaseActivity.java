package com.example.hao.gankio.acitivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;

import com.example.hao.gankio.R;

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

    public void openWebsite(Activity context, String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(context.getResources().getColor(R.color.colorAccent))
                .setShowTitle(true);
        builder.build().launchUrl(context, Uri.parse(url));
    }

    @Override
    public void onStart() {
        super.onStart();
        CustomTabsClient.connectAndInitialize(getApplicationContext(), "com.android.chrome");
    }
}
