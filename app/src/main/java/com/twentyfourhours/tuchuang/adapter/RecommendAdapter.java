package com.twentyfourhours.tuchuang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.model.bean.HotBeen;
import com.twentyfourhours.tuchuang.ui.activity.ShoppingDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/4.
 */

public class RecommendAdapter extends BaseAdapter {

    private Context mContext;

    public RecommendAdapter(Context context) {
        mContext = context;
    }

    private List<HotBeen> data = new ArrayList<>();

    public void setData(List<HotBeen> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (data != null) {
            return data.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_recommend_view, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setData(data.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView mTextView1;
        TextView mLimitTime;
        HotBeen mHotBeen;

        public ViewHolder(View view) {
            mTextView1 = (TextView) view.findViewById(R.id.mark_tv);
            mLimitTime = (TextView) view.findViewById(R.id.limit_time);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, ShoppingDetailActivity.class);
                    intent.putExtra("limit_time",mHotBeen.isShowLimitTime());
                    mContext.startActivity(intent);
                }
            });
        }


        public void setData(HotBeen hotBeen) {
            mHotBeen=hotBeen;
            mTextView1.setVisibility(hotBeen.isShow()?View.VISIBLE:View.GONE);
            mLimitTime.setVisibility(hotBeen.isShowLimitTime()?View.VISIBLE:View.GONE);
        }
    }
}
