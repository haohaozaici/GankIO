package com.example.hao.gankio.acitivity;

import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.hao.gankio.R;

import moe.feng.alipay.zerosdk.AlipayZeroSdk;

/**
 * Created by hao on 2016-08-21.
 */
public class AboutMe extends BaseActivity {

    private LinearLayout github;
    private LinearLayout Alipay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_me);

        github = (LinearLayout) findViewById(R.id.github);
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebsite(AboutMe.this, getString(R.string.GitHub));
            }
        });

        Alipay = (LinearLayout) findViewById(R.id.Alipay);
        Alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean b = AlipayZeroSdk.hasInstalledAlipayClient(getApplicationContext());
                if (b) {
                    AlipayZeroSdk.startAlipayClient(AboutMe.this, getString(R.string.Alipay));
                }
            }
        });
    }

    @Override
    public void onStart() {
        CustomTabsClient.connectAndInitialize(AboutMe.this, "com.android.chrome");
        super.onStart();
    }

}
