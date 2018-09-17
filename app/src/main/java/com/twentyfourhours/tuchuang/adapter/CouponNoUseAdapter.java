package com.twentyfourhours.tuchuang.adapter;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.model.bean.StoreBeen;
import com.twentyfourhours.tuchuang.common.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/8.
 */
public class CouponNoUseAdapter extends BaseAdapter{

    private Context mContext;

    private List<StoreBeen> data=new ArrayList<>();
    private int mPreHeight;

    public List<StoreBeen> getData() {
        return data;
    }

    public void setData(List<StoreBeen> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public CouponNoUseAdapter(Context context) {
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_coupon_no_use_view,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.setData(data.get(position),position);
        return convertView;
    }

    class ViewHolder implements View.OnClickListener {
        StoreBeen mStoreBeen;
        TextView detail;
        ImageView expend_iv;
        LinearLayout expend;
        int count;
        boolean isExpend;

        public ViewHolder(View view) {
            expend_iv= (ImageView) view.findViewById(R.id.expend_iv);
            expend= (LinearLayout) view.findViewById(R.id.expend);
            detail= (TextView) view.findViewById(R.id.detail);
            expend.setOnClickListener(this);
            //获取布局树
            ViewTreeObserver vto = detail.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    detail.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    mPreHeight = detail.getHeight();
                }
            });
        }

        public void setData(StoreBeen storeBeen,int position) {

        }

        private void startAnimFold() {
            ObjectAnimator anim = ObjectAnimator.ofFloat(expend_iv, "rotation", -180f, -0);
            anim.setDuration(300);
            anim.start();

            // 这里指定了值的变化范围
            ValueAnimator animator = ValueAnimator.ofFloat(42, DensityUtil.px2dip(mContext,mPreHeight));
            // 这里指定变化持续时间
            animator.setDuration(300);
            //开始动画
            animator.start();
            //开始动画后，我们可以动态的获取变化值
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //根据变化值来设置
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DensityUtil.dip2px(mContext,260), DensityUtil.dip2px(mContext, (Float) animation.getAnimatedValue()));
                    detail.setLayoutParams(lp);
                }
            });
        }

        private void startAnimExpend() {
            ObjectAnimator anim = ObjectAnimator.ofFloat(expend_iv, "rotation", 0f, -180f);
            anim.setDuration(300);
            anim.start();

            // 这里指定了值的变化范围
            ValueAnimator animator = ValueAnimator.ofFloat(DensityUtil.px2dip(mContext,mPreHeight), 42);
            // 这里指定变化持续时间
            animator.setDuration(300);
            //开始动画
            animator.start();
            //开始动画后，我们可以动态的获取变化值
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //根据变化值来设置
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DensityUtil.dip2px(mContext,260), DensityUtil.dip2px(mContext, (Float) animation.getAnimatedValue()));
                    detail.setLayoutParams(lp);

                }
            });
        }

        @Override
        public void onClick(View v) {
            if (isExpend){
                startAnimFold();
            }else {
                startAnimExpend();
            }
            isExpend=!isExpend;
        }
    }
}
