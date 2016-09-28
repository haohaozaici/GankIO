package com.example.hao.gankio.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hao.gankio.R;
import com.example.hao.gankio.acitivity.BaseActivity;
import com.example.hao.gankio.acitivity.DetailsActivity;
import com.example.hao.gankio.data.Android;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hao on 2016/9/28.
 */

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.ViewHolder> {

    private List<Android> items;
    private Activity activity;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Android gank = items.get(position);
        if (position == 0) {
            showCategory(holder, position);
        }
        else {
            boolean theCategoryOfLastEqualsToThis = items.get(position - 1).type.equals(items.get(position).type);
            if (!theCategoryOfLastEqualsToThis) {
                showCategory(holder, position);
            }
            else {
                hideCategory(holder);
            }
        }
        holder.itemText.setText(gank.desc);
        holder.detailsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity baseActivity = new BaseActivity();
                baseActivity.openWebsite(activity ,gank.url);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public void setItems(Activity activity ,List<Android> items){
        this.activity = activity;
        this.items = items;
        notifyDataSetChanged();
    }

    private void showCategory(ViewHolder holder, int position) {
        if (!isVisibleOf(holder.category)) {
            holder.category.setVisibility(View.VISIBLE);
            switch (items.get(position).type){
                case "Android":
                    break;
                case "iOS":
                    holder.categoryImage.setImageResource(R.drawable.web);
                    holder.categoryText.setText("iOS");
                    break;
                case "拓展资源":
                    holder.categoryImage.setImageResource(R.drawable.satellite128);
                    holder.categoryText.setText("拓展资源 杂七杂八");
                    break;
                default:
                    holder.category.setVisibility(View.GONE);
                    break;
            }
        }
    }


    private void hideCategory(ViewHolder holder) {
        if (isVisibleOf(holder.category)) holder.category.setVisibility(View.GONE);
    }

    private boolean isVisibleOf(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.category) LinearLayout category;
        @BindView(R.id.details_category_img) ImageView categoryImage;
        @BindView(R.id.details_item) LinearLayout detailsItem;
        @BindView(R.id.details_category_text) TextView categoryText;
        @BindView(R.id.details_item_text) TextView itemText;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
