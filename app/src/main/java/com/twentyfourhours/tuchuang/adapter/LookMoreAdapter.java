package com.twentyfourhours.tuchuang.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.model.bean.HotBeen;
import com.twentyfourhours.tuchuang.ui.activity.ShoppingDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/2/2.
 */
public class LookMoreAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<HotBeen> data = new ArrayList<>();

    public void setData(List<HotBeen> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public LookMoreAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recommend_like_view, parent, false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder= (ViewHolder) holder;
        viewHolder.setData(data.get(position));
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.mark_tv)
        TextView mMarkTv;
        @BindView(R.id.mark_tv2)
        TextView mMarkTv2;
        @BindView(R.id.mark_tv3)
        TextView mMarkTv3;
        @BindView(R.id.limit_time)
        TextView mLimitTime;
        @BindView(R.id.select_view)
        ImageView mSelectView;
        HotBeen mHotBeen;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mHotBeen.getType()==1){
                        mHotBeen.setSelect(!mHotBeen.isSelect());
                        notifyDataSetChanged();
                    }else {
                        Intent intent = new Intent(mContext, ShoppingDetailActivity.class);
                        intent.putExtra("limit_time", mHotBeen.isShowLimitTime());
                        mContext.startActivity(intent);
                    }
                }
            });
        }

        public void setData(HotBeen hotBeen) {
            mHotBeen=hotBeen;
            mSelectView.setVisibility(hotBeen.isSelect()?View.VISIBLE:View.GONE);
            mMarkTv.setVisibility(hotBeen.isShow1()?View.VISIBLE:View.GONE);
            mMarkTv2.setVisibility(hotBeen.isShow2()?View.VISIBLE:View.GONE);
            mMarkTv3.setVisibility(hotBeen.isShow3()?View.VISIBLE:View.GONE);
            mLimitTime.setVisibility(hotBeen.isShowLimitTime()?View.VISIBLE:View.GONE);
        }
    }
}
