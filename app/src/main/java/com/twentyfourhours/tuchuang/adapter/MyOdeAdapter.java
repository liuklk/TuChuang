package com.twentyfourhours.tuchuang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.model.bean.MyOderBean;
import com.twentyfourhours.tuchuang.ui.activity.EvaluateActivity;
import com.twentyfourhours.tuchuang.ui.activity.FirmOrderActivity;
import com.twentyfourhours.tuchuang.ui.activity.LogisticsActivity;
import com.twentyfourhours.tuchuang.common.util.AnimUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/7 0007.
 */

public class MyOdeAdapter extends BaseAdapter {
    private Context mContext;
    private List<MyOderBean> mData = new ArrayList<>();

    public List<MyOderBean> getData() {
        return mData;
    }

    public void setData(List<MyOderBean> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public MyOdeAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if (mData != null) {
            return mData.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_my_oder, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.setData(mData.get(i), i);
        return view;
    }

    class ViewHolder implements View.OnClickListener {
        ImageView icon;
        TextView name;
        TextView statu;
        TextView number;
        TextView account;
        TextView totalPrice;
        TextView price;
        TextView style;
        TextView size;
        Button logistics;
        Button action;
        MyOderBean mMyOderBean;
        int position;
        private View mView;

        public ViewHolder(View view) {
            this.mView = view;
            icon = (ImageView) view.findViewById(R.id.shop_iv);
            name = (TextView) view.findViewById(R.id.name);
            statu = (TextView) view.findViewById(R.id.statu);
            number = (TextView) view.findViewById(R.id.shop_number);
            account = (TextView) view.findViewById(R.id.account);
            price = (TextView) view.findViewById(R.id.new_price);
            totalPrice = (TextView) view.findViewById(R.id.price);
            logistics = (Button) view.findViewById(R.id.logistics);
            action = (Button) view.findViewById(R.id.action);
            style = (TextView) view.findViewById(R.id.style_tv);
            size = (TextView) view.findViewById(R.id.size_tv);
            initListener(view);
        }

        private void initListener(View view) {
            view.setOnClickListener(this);
            logistics.setOnClickListener(this);
            action.setOnClickListener(this);
        }

        public void setData(MyOderBean myOderBean, int position) {
            this.mMyOderBean = myOderBean;
            this.position = position;
            //TODO:设置数据

            action.setVisibility(myOderBean.isShowAction()?View.VISIBLE:View.GONE);
            action.setText(myOderBean.getAction());
            statu.setText(myOderBean.getStatu());
            logistics.setText(myOderBean.getLogistics());
        }

        @Override
        public void onClick(View view) {
            String logistics = this.logistics.getText().toString().trim();
            String action = this.action.getText().toString().trim();
            if (view.getId() == R.id.logistics) {
                if ("取消订单".equals(logistics)) {
//                    AnimUtils.startAnim(position,mData,MyOdeAdapter.this,true,mView);
//                    mData.remove(position);
//                    notifyDataSetChanged();

                } else if ("查看物流".equals(logistics)){
                    //跳转到地图配送物流
                    Intent intent = new Intent(mContext, LogisticsActivity.class);
                    mContext.startActivity(intent);
                }else {
                    //跳转到评价
                    Intent intent = new Intent(mContext, EvaluateActivity.class);
                    mContext.startActivity(intent);
                }
            }
            if (view.getId() == R.id.action) {
                if ("付款".equals(action)) {
                    //跳转到评价
                    Intent intent = new Intent(mContext, FirmOrderActivity.class);
                    mContext.startActivity(intent);
                }else {
                    //跳转到评价
                    AnimUtils.startAnim(position,mData,MyOdeAdapter.this,false,mView);
                }
            }
//            else {
//                if ("评价".equals(action)) {
//                    //跳转到评价
////                    Intent intent = new Intent(mContext, DealDoneActivity.class);
////                    mContext.startActivity(intent);
//                } else if ("提醒配送".equals(action)) {
//                    //跳转到订单详情
////                    Intent intent = new Intent(mContext, MyOderDetailActivity.class);
////                    mContext.startActivity(intent);
//                }
//            }
        }

    }


}
