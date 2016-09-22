package com.example.hao.gankio.widget;

/**
 * Created by hao on 2016-09-22.
 */

public interface SwipeRefreshLayer {

    void requestDataRefresh();

    void setRefresh(boolean refresh);

    void setProgressViewOffset(boolean scale, int start, int end);

    void setCanChildScrollUpCallback(MultiSwipeRefreshLayout.CanChildScrollUpCallback callback);
}
