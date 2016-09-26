package com.example.hao.gankio;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.hao.gankio.acitivity.HaoSwipeToRefreshActivity;
import com.example.hao.gankio.api.Network;
import com.example.hao.gankio.data.Android;
import com.example.hao.gankio.utils.AndroidResultsToAndroidMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class MainActivity extends HaoSwipeToRefreshActivity {

    private static final int PRELOAD_SIZE = 6;
    @BindView(R.id.recycler_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.week_hot)
    FloatingActionButton mFloatingActionButton;
    private MainAdapter mMainAdapter = new MainAdapter();

    private boolean mIsFirstTimeTouchBottom = true;
    private int mPage = 1;

    Observer<List<Android>> observer = new Observer<List<Android>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            setRefresh(false);
            Toast.makeText(MainActivity.this, R.string.retry, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(List<Android> androids) {
            setRefresh(false);
            mMainAdapter.setItems(androids);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupRecyclerView();

    }

    private void loadData() {
        setRefresh(true);

        unsubscribe();
        subscription = rx.Observable
                .zip(Network.getGankApi().getAndroid(mPage).map(AndroidResultsToAndroidMapper.getInstance()),
                        Network.getGankApi().getMeizhi(mPage).map(AndroidResultsToAndroidMapper.getInstance()),
                                new Func2<List<Android>, List<Android>, List<Android>>() {
                                    @Override
                                    public List<Android> call(List<Android> androidDesc, List<Android> meizhiImg) {
                                        List<Android> androidList = new ArrayList<Android>(androidDesc.size());
                                        for (int i = 0; i < androidDesc.size(); i++) {
                                            Android android = new Android();
                                            android.desc = androidDesc.get(i).desc;
                                            android.url = meizhiImg.get(i).url;
                                            android.publishedAt = meizhiImg.get(i).desc;
                                            androidList.add(android);
                                        }
                                        return androidList;
                                    }
                                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        new Handler().postDelayed(() -> setRefresh(true), 358);
        loadData();
    }

    @Override
    public void requestDataRefresh() {
        super.requestDataRefresh();
        mPage = 1;
        loadData();
    }


    @OnClick(R.id.week_hot)
    public void onFab(View v) {
//        if (mMeizhiList != null && mMeizhiList.size() > 0) {
//            startGankActivity(mMeizhiList.get(0).publishedAt);
//        }
    }

    private void setupRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_list);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mMainAdapter);
        mRecyclerView.addOnScrollListener(getOnBottomListener(layoutManager));

    }

    RecyclerView.OnScrollListener getOnBottomListener(StaggeredGridLayoutManager layoutManager) {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView rv, int dx, int dy) {
                boolean isBottom =
                        layoutManager.findLastCompletelyVisibleItemPositions(new int[2])[1] >=
                                mMainAdapter.getItemCount() - PRELOAD_SIZE;
                if (!mSwipeRefreshLayout.isRefreshing() && isBottom) {
                    if (!mIsFirstTimeTouchBottom) {
                        mSwipeRefreshLayout.setRefreshing(true);
                        mPage += 1;
                        loadData();
                    } else {
                        mIsFirstTimeTouchBottom = false;
                    }
                }
            }
        };
    }

}
