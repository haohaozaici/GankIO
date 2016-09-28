package com.example.hao.gankio.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hao on 2016/9/28.
 */

public class Gank {

    public @SerializedName("Android") List<Android> Androids;
    public @SerializedName("iOS") List<Android> iOS;
    public @SerializedName("休息视频") List<Android> 休息视频;
    public @SerializedName("前端") List<Android> 前端;
    public @SerializedName("拓展资源") List<Android> 拓展资源;
    public @SerializedName("瞎推荐") List<Android> 瞎推荐;
    public @SerializedName("福利") List<Android> 福利;
}
