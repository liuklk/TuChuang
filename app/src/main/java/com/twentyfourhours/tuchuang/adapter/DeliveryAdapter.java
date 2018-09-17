package com.twentyfourhours.tuchuang.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.model.bean.StoreBeen;
import com.twentyfourhours.tuchuang.ui.activity.LogisticsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/9.
 */
public class DeliveryAdapter extends BaseAdapter{
    private Context mContext;

    private List<StoreBeen> data=new ArrayList<>();

    public void setData(List<StoreBeen> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public DeliveryAdapter(Context context) {
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.delivery_item_view,parent,false);
            holder=new DeliveryAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.setData(data.get(position));
        return convertView;
    }

    class ViewHolder implements View.OnClickListener {
        TextView phoneNumber;
        Button logistics;

        public ViewHolder(View view) {
            phoneNumber= (TextView) view.findViewById(R.id.phone_number_tv);
            logistics= (Button) view.findViewById(R.id.logistics);
            view.setOnClickListener(this);
            phoneNumber.setOnClickListener(this);
            logistics.setOnClickListener(this);
        }

        public void setData(StoreBeen storeBeen) {

        }

        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.phone_number_tv){
                Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "95546"));//跳转到拨号界面，同时传递电话号码
                mContext.startActivity(dialIntent);
            }else {
                Intent intent=new Intent(mContext, LogisticsActivity.class);
                mContext.startActivity(intent);
            }
        }
    }
}
