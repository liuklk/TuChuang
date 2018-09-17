package com.twentyfourhours.tuchuang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.model.bean.AddressManageBean;
import com.twentyfourhours.tuchuang.ui.activity.AddressManagerActivity;
import com.twentyfourhours.tuchuang.ui.activity.NewAddressActivity;
import com.twentyfourhours.tuchuang.common.util.AnimUtils;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017/11/6 0006.
 */

public class AddressManageAdapter extends BaseAdapter {
    private List<AddressManageBean> mData = new ArrayList<>();

    public List<AddressManageBean> getData() {
        return mData;
    }

    private Context mContext;

    public AddressManageAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<AddressManageBean> data) {
        mData = data;
        notifyDataSetChanged();
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_address_manage, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.setData(mData.get(i), i);
        return view;
    }

    class ViewHolder implements View.OnClickListener {
        TextView name;
        TextView phoneNumber;
        TextView address;
        TextView edit;
        TextView delete;
        CheckBox select;
        int position;
        AddressManageBean addressManageBean;
        View mView;

        public ViewHolder(View view) {
            this.mView=view;
            name = (TextView) view.findViewById(R.id.name);
            phoneNumber = (TextView) view.findViewById(R.id.phone_number);
            address = (TextView) view.findViewById(R.id.address);
            edit = (TextView) view.findViewById(R.id.edit);
            delete = (TextView) view.findViewById(R.id.delete);
            select = (CheckBox) view.findViewById(R.id.select);
            initListener(view);
        }

        private void initListener(View view) {
            view.setOnClickListener(this);
            select.setOnClickListener(this);
            edit.setOnClickListener(this);
            delete.setOnClickListener(this);
        }

        public void setData(AddressManageBean addressManageBean, int position) {
            this.addressManageBean=addressManageBean;
            this.position = position;
            select.setChecked(addressManageBean.isChecked());
            select.setText(addressManageBean.isChecked()?"默认地址":"设为默认");
            name.setText(addressManageBean.getName());
            address.setText("地址:"+addressManageBean.getAddress()+addressManageBean.getDetail());
            phoneNumber.setText(addressManageBean.getPhoneNumber());
        }

        @Override
        public void onClick(View view) {
            if (view.getId()==R.id.select){
                for (int i = 0; i < mData.size(); i++) {
                    mData.get(i).setChecked(position == i);
                }
            }else if (view.getId()==R.id.edit){
                Intent intent = new Intent(mContext, NewAddressActivity.class);
                intent.putExtra("type", AddressManagerActivity.EDIT);
                intent.putExtra("addressManageBean",addressManageBean);
                intent.putExtra("position",position);
                ((AddressManagerActivity)mContext).startActivityForResult(intent,AddressManagerActivity.RQT_EDIT);
            }else if (view.getId()==R.id.delete){
                AnimUtils.startAnim(position,mData,AddressManageAdapter.this,true,mView);
            }else {
                Intent data=new Intent();
                data.putExtra("SelectAddress", addressManageBean);
                ((AddressManagerActivity)mContext).setResult(RESULT_OK,data);
                ((AddressManagerActivity)mContext).finish();
            }
            notifyDataSetChanged();
        }
    }
}
