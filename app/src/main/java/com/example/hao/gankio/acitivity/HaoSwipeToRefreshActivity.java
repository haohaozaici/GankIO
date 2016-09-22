package com.example.hao.gankio.acitivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.hao.gankio.R;
import com.example.hao.gankio.widget.MultiSwipeRefreshLayout;
import com.example.hao.gankio.widget.SwipeRefreshLayer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hao on 2016-09-22.
 */

public abstract class HaoSwipeToRefreshActivity extends BaseActivity implements SwipeRefreshLayer {

    @BindView(R.id.swipe_refresh_layout)
    public MultiSwipeRefreshLayout mSwipeRefreshLayout;
    private boolean mIsRequestDataRefresh = false;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistableBundle) {
        super.onCreate(savedInstanceState, persistableBundle);
        ButterKnife.bind(this);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        trySetupSwipeRefresh();

    }

    private void trySetupSwipeRefresh() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_3,
                    R.color.refresh_progress_2, R.color.refresh_progress_1);
            // Do not use lambda here!
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    requestDataRefresh();
                }
            });
        }
    }

    @Override
    public void requestDataRefresh() {
        mIsRequestDataRefresh = true;
    }

    public void setRefresh(boolean requestDataRefresh) {
        if (mSwipeRefreshLayout == null) {
            return;
        }

        if (!requestDataRefresh) {
            mIsRequestDataRefresh = false;
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 1000);
        }else {
            mSwipeRefreshLayout.setRefreshing(true);
        }

    }

    @Override public void setProgressViewOffset(boolean scale, int start, int end) {
        mSwipeRefreshLayout.setProgressViewOffset(scale, start, end);
    }


    @Override
    public void setCanChildScrollUpCallback(MultiSwipeRefreshLayout.CanChildScrollUpCallback canChildScrollUpCallback) {
        mSwipeRefreshLayout.setCanChildScrollUpCallback(canChildScrollUpCallback);
    }


    public boolean isRequestDataRefresh() {
        return mIsRequestDataRefresh;
    }


}
