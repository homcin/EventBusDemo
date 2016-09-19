package com.grace.eventbusdemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.grace.eventbusdemo.R;
import com.grace.eventbusdemo.entity.Girl;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Administrator on 2016/9/2.
 */
public class GirlsAdapter extends RecyclerView.Adapter<GirlsAdapter.ViewHolder> {

    private Context mContext;
    private List<Girl> girls;

    public GirlsAdapter(Context context, List<Girl> stories) {
        mContext = context;
        this.girls = stories;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivGirl;
        public ViewHolder(View itemView){
            super(itemView);
            ivGirl = (ImageView) itemView.findViewById(R.id.iv_girl);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View girlView = LayoutInflater.from(mContext).inflate(R.layout.item_girl, parent, false);
        final ViewHolder viewHolder = new ViewHolder(girlView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GirlsAdapter.ViewHolder holder, int position) {
        Girl girl = girls.get(position);
        Picasso.with(mContext).load(girl.getUrl()).config(Bitmap.Config.RGB_565).into(holder.ivGirl);
    }

    @Override
    public int getItemCount() {
        return girls.size();
    }

}
