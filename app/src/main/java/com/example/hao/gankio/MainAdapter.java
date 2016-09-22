package com.example.hao.gankio;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.example.hao.gankio.data.Meizhi;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hao on 2016-09-22.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    public final static String TAG = "MeizhiList";

    private List<Meizhi> mMeizhiList;
    private Context mContext;

    public MainAdapter(Context context, List<Meizhi> meizhiList) {
        this.mMeizhiList = meizhiList;
        this.mContext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_meizhi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final int positions = holder.getAdapterPosition();

        Meizhi meizhi = mMeizhiList.get(positions);
        int limit = 48;
        String text = meizhi.desc.length() > limit ? meizhi.desc.substring(0, limit) + "..." : meizhi.desc;
        holder.title.setText(text);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGankActivity(mMeizhiList.get(positions).publishedAt);
            }
        });

        Glide.with(mContext)
                .load(meizhi.url)
                .centerCrop()
                .into(holder.meizhi_img)
                .getSize(new SizeReadyCallback() {
                    @Override
                    public void onSizeReady(int width, int height) {
                        if (!holder.card.isShown()) {
                            holder.card.setVisibility(View.VISIBLE);
                        }
                    }
                });

    }

    private void startGankActivity(Date publishedAt) {
        Intent intent = new Intent(mContext, GankActivity.class);
        intent.putExtra(GankActivity.EXTRA_GANK_DATE, publishedAt);
        mContext.startActivity(intent);
    }

    @Override
    public void onViewRecycled(ViewHolder holder){
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return mMeizhiList.size();
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
