package com.example.hao.gankio;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hao.gankio.data.Android;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hao on 2016-09-22.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<Android> androids;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meizhi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Android android = androids.get(position);
        if (android.images != null) {
            String url = android.images.get(0);

            Glide.with(holder.itemView.getContext())
                    .load(url)
                    .fitCenter()
                    .into(holder.meizhi_img);
        }

        int limit = 48;
        String text = android.desc.length() > limit ? android.desc.substring(0, limit) + "..." : android.desc;
        holder.title.setText(text);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startGankActivity(mMeizhiList.get(positions).publishedAt);
            }
        });

    }

    @Override
    public int getItemCount() {
        return androids == null ? 0 : androids.size();
    }

    public void setItems(List<Android> androids) {
        this.androids = androids;
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
