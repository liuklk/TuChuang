package com.twentyfourhours.tuchuang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.model.bean.HotBeen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/4.
 */

public class MyDesignAdapter extends BaseAdapter {

    private Context mContext;

    public MyDesignAdapter(Context context) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_design_view, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setData(data.get(position));
        return convertView;
    }

    class ViewHolder implements View.OnClickListener {
        TextView name;
        TextView style;
        TextView price;
        ImageView mImageView2;
        ImageView mImageView3;
        ImageView mImageView;
        CheckBox mCheckBox;
        HotBeen mHotBeen;
        boolean ischeck;

        public ViewHolder(View view) {
            name = (TextView) view.findViewById(R.id.name);
            style = (TextView) view.findViewById(R.id.style);
            price = (TextView) view.findViewById(R.id.price);
            mImageView2 = (ImageView) view.findViewById(R.id.image2);
            mImageView3 = (ImageView) view.findViewById(R.id.image3);
            mImageView = (ImageView) view.findViewById(R.id.image_button);
            mCheckBox = (CheckBox) view.findViewById(R.id.check_box);

            view.setOnClickListener(this);
            mImageView.setOnClickListener(this);
        }

        public void setData(HotBeen hotBeen) {
            this.mHotBeen = hotBeen;
            mCheckBox.setVisibility(hotBeen.isEdit()?View.VISIBLE:View.GONE);
            mCheckBox.setChecked(hotBeen.isCheck());
            mImageView.setImageResource(hotBeen.isShow()?R.drawable.face:R.drawable.back);
            mImageView2.setVisibility(hotBeen.isShow()?View.VISIBLE:View.GONE);
            mImageView3.setVisibility(hotBeen.isShow()?View.GONE:View.VISIBLE);
            mImageView2.setImageResource(hotBeen.getPic1());
            mImageView3.setImageResource(hotBeen.getPic2());
        }

        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.image_button){
                TranslateAnimation animation1;
                if (mHotBeen.isShow()) {
                    mImageView3.setVisibility(View.VISIBLE);
                    animation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1.5f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
                    animation1.setDuration(500);
                    mImageView2.startAnimation(animation1);
                    TranslateAnimation animation2 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.5f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
                    animation2.setDuration(500);
                    mImageView3.startAnimation(animation2);
                } else {
                    mImageView2.setVisibility(View.VISIBLE);
                    animation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1.5f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
                    animation1.setDuration(500);
                    mImageView3.startAnimation(animation1);
                    TranslateAnimation animation2 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.5f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
                    animation2.setDuration(500);
                    mImageView2.startAnimation(animation2);
                }

                //设置动画结束监听器
                animation1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (mHotBeen.isShow()) {
                            mImageView2.setVisibility(View.VISIBLE);
                            mImageView3.setVisibility(View.GONE);
                        }else {
                            mImageView3.setVisibility(View.VISIBLE);
                            mImageView2.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                mHotBeen.setShow(!mHotBeen.isShow());
            }else {
                if (mHotBeen.isEdit()){
                        if (ischeck){
                            mHotBeen.setCheck(false);
                        }else {
                            mHotBeen.setCheck(true);
                        }
                        ischeck=!ischeck;
                    }
            }
            notifyDataSetChanged();
        }
    }
}
