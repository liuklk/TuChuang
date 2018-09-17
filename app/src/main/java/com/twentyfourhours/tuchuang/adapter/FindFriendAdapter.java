package com.twentyfourhours.tuchuang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.model.bean.StoreBeen;
import com.twentyfourhours.tuchuang.ui.activity.CommunityDetailActivity;
import com.twentyfourhours.tuchuang.ui.activity.CommunityUserDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/8.
 */
public class FindFriendAdapter extends BaseAdapter{

    private Context mContext;

    private ViewGroup mParent;

    private List<StoreBeen> data=new ArrayList<>();

    public void setData(List<StoreBeen> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public FindFriendAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        if (data!=null){
            return data.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (data!=null){
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
        ViewHolder holder=null;
        if (convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_find_friend_view,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.setData(data.get(position),position);
        return convertView;
    }

    class ViewHolder implements View.OnClickListener {
        ImageView img1;
        ImageView img2;
        ImageView img3;
        ImageView img4;
        View lastView;

        public ViewHolder(View view) {
            img1= (ImageView) view.findViewById(R.id.img1);
            img2= (ImageView) view.findViewById(R.id.img2);
            img3= (ImageView) view.findViewById(R.id.img3);
            img4= (ImageView) view.findViewById(R.id.img4);
            lastView= view.findViewById(R.id.last_view);

            img1.setOnClickListener(this);
            img2.setOnClickListener(this);
            img3.setOnClickListener(this);
            img4.setOnClickListener(this);
            view.setOnClickListener(this);
        }

        public void setData(StoreBeen storeBeen,int position) {
            lastView.setVisibility(position==data.size()-1?View.VISIBLE:View.GONE);
        }

        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.img1||v.getId()==R.id.img2||v.getId()==R.id.img3||v.getId()==R.id.img4) {
                Intent intent = new Intent(mContext, CommunityDetailActivity.class);
                mContext.startActivity(intent);
            }else {
                Intent intent = new Intent(mContext, CommunityUserDetailActivity.class);
                mContext.startActivity(intent);
            }
        }
    }
}
