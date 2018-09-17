package com.twentyfourhours.tuchuang.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.model.bean.HotBeen;
import com.twentyfourhours.tuchuang.ui.activity.ShoppingDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/4.
 */

public class HotAdapter extends BaseAdapter {

    private Context mContext;

    public HotAdapter(Context context) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_hot_view, parent, false);
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
        TextView mTextView2;
        ImageView mImageView1;
        ImageView mImageView2;
        ImageView mImageView3;
        ImageView mImageView;
        HotBeen mHotBeen;
        LinearLayout mLinearLayout;
        LinearLayout mLinearLayout2;
        boolean loop=true;
        Handler mHandler=new Handler(Looper.getMainLooper());

        public ViewHolder(View view) {
            mTextView1 = (TextView) view.findViewById(R.id.text1);
            mTextView2 = (TextView) view.findViewById(R.id.text2);
            mImageView1 = (ImageView) view.findViewById(R.id.image1);
            mImageView2 = (ImageView) view.findViewById(R.id.image2);
            mImageView3 = (ImageView) view.findViewById(R.id.image3);
            mImageView = (ImageView) view.findViewById(R.id.image_button);
            mLinearLayout = (LinearLayout) view.findViewById(R.id.ll_text);
            mLinearLayout2 = (LinearLayout) view.findViewById(R.id.ll_text2);
            initListener();
            startAnim(mLinearLayout,mLinearLayout2);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext, ShoppingDetailActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }

        private void startAnim(final LinearLayout linearLayout, LinearLayout linearLayout2) {
            TranslateAnimation animation1=new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1);
            animation1.setDuration(900);
            linearLayout.startAnimation(animation1);
            linearLayout2.setVisibility(View.VISIBLE);
            TranslateAnimation animation2=new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0);
            animation2.setDuration(900);
            linearLayout2.startAnimation(animation2);

            animation1.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    linearLayout.setVisibility(View.GONE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SystemClock.sleep(900);
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (loop) {
                                        loop=!loop;
                                        startAnim(mLinearLayout2, mLinearLayout);
                                    }else {
                                        loop=!loop;
                                        startAnim(mLinearLayout, mLinearLayout2);
                                    }
                                }
                            });

                        }
                    }).start();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

        }

        private void initListener() {
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                    notifyDataSetChanged();
                }
            });
        }

        public void setData(HotBeen hotBeen) {
            this.mHotBeen = hotBeen;
            mImageView.setImageResource(hotBeen.isShow()?R.drawable.face:R.drawable.back);
            mImageView2.setVisibility(hotBeen.isShow()?View.VISIBLE:View.GONE);
            mImageView3.setVisibility(hotBeen.isShow()?View.GONE:View.VISIBLE);
            mImageView2.setImageResource(hotBeen.getPic1());
            mImageView3.setImageResource(hotBeen.getPic2());
        }
    }
}
