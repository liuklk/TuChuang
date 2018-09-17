package com.twentyfourhours.tuchuang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.model.bean.StoreBeen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/8.
 */
public class CouponAdapter extends BaseAdapter{

    private Context mContext;

    private List<StoreBeen> data=new ArrayList<>();

    public List<StoreBeen> getData() {
        return data;
    }

    public void setData(List<StoreBeen> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public CouponAdapter(Context context) {
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_coupon_view,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.setData(data.get(position),position);
        return convertView;
    }

    class ViewHolder{

        public ViewHolder(View view) {

        }

        public void setData(StoreBeen storeBeen,int position) {

        }

    }
}
