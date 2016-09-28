package com.example.hao.gankio.acitivity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hao.gankio.R;
import com.example.hao.gankio.adapter.DetailsAdapter;
import com.example.hao.gankio.api.Network;
import com.example.hao.gankio.data.Android;
import com.example.hao.gankio.data.Gank;
import com.example.hao.gankio.utils.GankResultsToAndroidMapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hao on 2016/9/28.
 */

public class DetailsActivity extends BaseActivity {

    @BindView(R.id.details_toolbar) Toolbar mToolbar;
    @BindView(R.id.recycler_details) RecyclerView mRecyclerView;
    @BindView(R.id.details_img) ImageView details_img;

    private ActionBar mActionBar;
    private DetailsAdapter detailsAdapter = new DetailsAdapter();

    Observer<Gank> observer = new Observer<Gank>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(DetailsActivity.this, R.string.retry, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(Gank gank) {
            Glide.with(DetailsActivity.this).load(gank.福利.get(0).url).centerCrop().into(details_img);
            detailsAdapter.setItems(DetailsActivity.this ,addAllResults(gank));
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(detailsAdapter);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mActionBar = getSupportActionBar();
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

        loadData();
    }

    private void loadData() {
        unsubscribe();
        String year, month, day;
        year = getIntent().getStringExtra("year");
        month = getIntent().getStringExtra("month");
        day = getIntent().getStringExtra("day");

        mActionBar.setTitle(month.substring(1, 2) + "-" + day);

        subscription = Network.getGankApi().getDayAll(year, month, day)
                .map(GankResultsToAndroidMapper.getInstance())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private List<Android> addAllResults(Gank gank) {
        List<Android> mList = new ArrayList<>();
        if (gank.Androids != null) mList.addAll(0, gank.Androids);
        if (gank.iOS != null) mList.addAll(gank.iOS);
        if (gank.前端 != null) mList.addAll(gank.前端);
        if (gank.拓展资源 != null) mList.addAll(gank.拓展资源);
        if (gank.瞎推荐 != null) mList.addAll(gank.瞎推荐);
        if (gank.休息视频 != null) mList.addAll(gank.休息视频);
        return mList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
