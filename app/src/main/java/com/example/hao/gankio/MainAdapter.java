package com.example.hao.gankio;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.example.hao.gankio.acitivity.BaseActivity;
import com.example.hao.gankio.acitivity.DetailsActivity;
import com.example.hao.gankio.data.Android;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hao on 2016-09-22.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<Android> androids;
    private MainActivity activity;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meizhi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Android android = androids.get(position);
        String url = android.url;
        Glide.with(holder.itemView.getContext())
                .load(url)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.meizhi_img)
                .getSize(new SizeReadyCallback() {
                    @Override
                    public void onSizeReady(int width, int height) {
                        if (!holder.card.isShown()) {
                            holder.card.setVisibility(View.VISIBLE);
                        }
                    }
                });

        int limit = 48;
        String text = android.desc.length() > limit ? android.desc.substring(0, limit) + "..." : android.desc;
        String desc = android.publishedAt.substring(5, 10) + " " + text;
        holder.title.setText(desc);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = android.publishedAt;
                String year, month, day;
                year = date.substring(0, 4);
                month = date.substring(5, 7);
                day = date.substring(8, 10);
                String url = "http://gank.io/" + year + "/" + month + "/" + day;
//                activity.openWebsite(activity, url);
                Intent intent = new Intent(activity, DetailsActivity.class);
                intent.putExtra("year", year);
                intent.putExtra("month", month);
                intent.putExtra("day", day);
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return androids == null ? 0 : androids.size();
    }

    public void setItems(MainActivity activitys, List<Android> androids) {
        this.androids = androids;
        this.activity = activitys;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.meizhi_img)
        ImageView meizhi_img;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.card)
        LinearLayout card;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
