package com.example.hao.gankio;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.hao.gankio.acitivity.HaoSwipeToRefreshActivity;
import com.example.hao.gankio.api.GankApi;
import com.example.hao.gankio.data.Gank;
import com.example.hao.gankio.data.GankBean;
import com.example.hao.gankio.data.Meizhi;
import com.example.hao.gankio.data.休息视频Data;
import com.example.hao.gankio.utils.Dates;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.model.ConflictAlgorithm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends HaoSwipeToRefreshActivity {

    private static final int PRELOAD_SIZE = 6;
    private static final String DB_NAME = "gank.db";
    public static LiteOrm sDb;
    @BindView(R.id.recycler_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.week_hot)
    FloatingActionButton mFloatingActionButton;
    private MainAdapter mMainAdapter;
    private List<Gank> mMeizhiList;

    private boolean mIsFirstTimeTouchBottom = true;


    private int mPage = 1;
    private int mIndex = (mPage - 1) * 10;
    private boolean addComplete = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sDb = LiteOrm.newSingleInstance(this, DB_NAME);

        mMeizhiList = new ArrayList<>();
        QueryBuilder query = new QueryBuilder(Meizhi.class);
        query.appendOrderDescBy("publishedAt");
        query.limit(0, 10);
//        mMeizhiList.addAll(sDb.query(query));

        setupRecyclerView();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        new Handler().postDelayed(() -> setRefresh(true), 358);
        getData(true);
    }

    @Override
    public void requestDataRefresh() {
        super.requestDataRefresh();
        mPage = 1;
        getData(true);
    }

    private void loadVideo(int mPage) {
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        GankApi gankApi = retrofit2.create(GankApi.class);

        Call<GankBean> call = gankApi.get休息视频Data2(mPage);
        call.enqueue(new Callback<GankBean>() {
            @Override
            public void onResponse(Call<GankBean> call, Response<GankBean> response) {
                GankBean gankBean = response.body();
                for (int i = 0; i < gankBean.results.size(); i++) {
                    mMeizhiList.add(mIndex + i, gankBean.results.get(i));
                }
                addComplete = true;
            }

            @Override
            public void onFailure(Call<GankBean> call, Throwable t) {
                Log.d("cylog", "Error" + t.toString());
            }
        });
    }

    private void loadMeizhi(int mPage) {
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        GankApi gankApi = retrofit2.create(GankApi.class);

        Call<GankBean> call = gankApi.getMeizhiData2(mPage);
        call.enqueue(new Callback<GankBean>() {
            @Override
            public void onResponse(Call<GankBean> call, Response<GankBean> response) {
                GankBean gankBean = response.body();
                if (addComplete && mMeizhiList.size() != 0) {
                    for (int i = 0; i < gankBean.results.size(); i++) {
                        mMeizhiList.get(mIndex + i).setUrl(gankBean.results.get(i).url);
                        Log.e("cylog", mMeizhiList.get(i).toString());
                    }
                    addComplete = false;
                }

                mMainAdapter.notifyDataSetChanged();
                setRefresh(false);
//                saveMeizhis(mMeizhiList);
            }

            @Override
            public void onFailure(Call<GankBean> call, Throwable t) {
                Log.d("cylog", "Error" + t.toString());
            }
        });


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
        mMainAdapter = new MainAdapter(this, mMeizhiList);
        mRecyclerView.setAdapter(mMainAdapter);

        mRecyclerView.addOnScrollListener(getOnBottomListener(layoutManager));

    }

    private void getData(boolean clean) {
        if (clean) {
            mMeizhiList.clear();
            mPage = 1;
            mIndex = (mPage - 1) * 10;
        }
        loadVideo(mPage);
        loadMeizhi(mPage);

    }


//    private void loadData() {
//        loadData(/* clean */false);
//    }
//    /**
//     * 获取服务数据
//     * 2016-9-22 23:40:31 by haohao
//     * @param clean 清除来自数据库缓存或者已有数据。
//     */
//    private void loadData(boolean clean) {
//        mLastVideoIndex = 0;
//
//        Subscription s = Observable
//                .zip(sGankIO.getMeizhiData(mPage), sGankIO.get休息视频Data(mPage), this::createMeizhiDataWith休息视频Desc)
//                .map(meizhiData -> meizhiData.results)
//                .flatMap(Observable::from)
//                .toSortedList(((meizhi, meizhi2) -> meizhi2.publishedAt.compareTo(meizhi.publishedAt)))
//                .doOnNext(this::saveMeizhis)
//                .observeOn(AndroidSchedulers.mainThread())
//                .finallyDo(() -> setRefresh(false))
//                .subscribe(meizhiList -> {
//                    if (clean) {
//                        mMeizhiList.clear();
//                        mMeizhiList.addAll(meizhiList);
//                        mMainAdapter.notifyDataSetChanged();
//                        setRefresh(false);
//                    }
//                }, throwable -> loadError(throwable));
//
//        addSubscription(s);
//    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        Log.e("netWork", "111", throwable);

        Snackbar.make(mRecyclerView, R.string.snap_load_fail, Snackbar.LENGTH_LONG)
                .setAction(R.string.retry, v -> {
                    requestDataRefresh();
                })
                .show();
    }

    private void saveMeizhis(List<Gank> meizhis) {
        sDb.insert(meizhis, ConflictAlgorithm.Replace);
    }

//    private MeizhiData createMeizhiDataWith休息视频Desc(MeizhiData data, 休息视频Data love) {
//        for (Meizhi meizhi : data.results) {
//            meizhi.desc = meizhi.desc + " " +
//                    getFirstVideoDesc(meizhi.publishedAt, love.results);
//        }
//        return data;
//    }

//    private String getFirstVideoDesc(Date publishedAt, List<Gank> results) {
//        String videoDesc = "";
//        for (int i = mLastVideoIndex; i < results.size(); i++) {
//            Gank video = results.get(i);
//            if (video.publishedAt == null) video.publishedAt = video.createdAt;
//            if (Dates.isTheSameDay(publishedAt, video.publishedAt)) {
//                videoDesc = video.desc;
//                mLastVideoIndex = i;
//                break;
//            }
//        }
//        return videoDesc;
//    }

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
                        mIndex = (mPage - 1) * 10;
                        getData(false);
                    } else {
                        mIsFirstTimeTouchBottom = false;
                    }
                }
            }
        };
    }

}
