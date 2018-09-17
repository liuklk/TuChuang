package com.twentyfourhours.tuchuang.ui.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.EvaluateAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.model.bean.StoreBeen;
import com.twentyfourhours.tuchuang.common.util.DensityUtil;
import com.twentyfourhours.tuchuang.widget.MyListView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/18.
 */
public class EvaluateShowActivity extends BaseActivity {
    @BindView(R.id.gold_back)
    ImageView mGoldBack;
    @BindView(R.id.star1)
    ImageView mStar1;
    @BindView(R.id.star2)
    ImageView mStar2;
    @BindView(R.id.star3)
    ImageView mStar3;
    @BindView(R.id.star4)
    ImageView mStar4;
    @BindView(R.id.star5)
    ImageView mStar5;
    @BindView(R.id.star)
    LinearLayout mStar;
    @BindView(R.id.evaluated_precent)
    TextView mEvaluatedPrecent;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout mFlowLayout;
    @BindView(R.id.list_view)
    MyListView mListView;
    @BindView(R.id.more_iv)
    ImageView mMoreIv;
    @BindView(R.id.more)
    LinearLayout mMore;
    private String[] mVals = {"全部(999+)", "有图(999+)", "追评(236)", "漂亮精致(999+)", "质量上乘(999+)","全部(999+)", "有图(999+)", "追评(236)", "漂亮精致(999+)", "质量上乘(999+)"};
    private List<String> mData = new ArrayList<>();
    private EvaluateAdapter mAdapter;
    private ArrayList<StoreBeen> mStoreBeens;
    private int mPreHeight;
    private String[] urls={"http://g.hiphotos.bdimg.com/album/s%3D680%3Bq%3D90/sign=ccd33b46d53f8794d7ff4b26e2207fc9/0d338744ebf81a4c0f993437d62a6059242da6a1.jpg","http://c.hiphotos.bdimg.com/album/s%3D900%3Bq%3D90/sign=b8658f17f3d3572c62e290dcba28121a/5fdf8db1cb134954bb97309a574e9258d0094a47.jpg","http://g.hiphotos.bdimg.com/album/s%3D680%3Bq%3D90/sign=ccd33b46d53f8794d7ff4b26e2207fc9/0d338744ebf81a4c0f993437d62a6059242da6a1.jpg","http://c.hiphotos.bdimg.com/album/s%3D900%3Bq%3D90/sign=b8658f17f3d3572c62e290dcba28121a/5fdf8db1cb134954bb97309a574e9258d0094a47.jpg"};

    @Override
    protected void initListener() {
        ViewTreeObserver vto = mFlowLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mFlowLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mPreHeight = mFlowLayout.getHeight();
            }
        });

        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
//                toast(mVals[position]);
                if (position!=0){
                    TextView all = (TextView) parent.getChildAt(0).findViewById(R.id.text_view);
                    all.setTextColor(getResources().getColor(R.color.text_bg_3));
                    all.setBackgroundResource(R.drawable.text_rc_bg2);
                }
                return true;
            }
        });
    }

    @Override
    protected void initData() {
        mListView.setFocusable(false);
        for (int i = 0; i < mStar.getChildCount(); i++) {
            if (i != mStar.getChildCount() - 1) {
                ImageView select = (ImageView) mStar.getChildAt(i);
                select.setSelected(true);
            }
        }

        //评价数据填充
        mAdapter = new EvaluateAdapter(this,urls);
        mStoreBeens = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            StoreBeen been = new StoreBeen();
            if (i % 2 == 0) {
                been.setCheck(true);
            } else {
                been.setCheck(false);
            }
//            been.setItemCount(1);
            mStoreBeens.add(been);
        }
        mAdapter.setData(mStoreBeens);
        mListView.setAdapter(mAdapter);

        //评价标签
        mData.clear();
        for (int i = 0; i < mVals.length; i++) {
            mData.add(mVals[i]);
        }
        mFlowLayout.setAdapter(mTagAdapter);
    }

    private TagAdapter mTagAdapter = new TagAdapter<String>(mData) {
        @Override
        public View getView(FlowLayout parent, int position, String s) {
            TextView tv = (TextView) LayoutInflater.from(EvaluateShowActivity.this).inflate(R.layout.tv,
                    mFlowLayout, false);
            tv.setTextColor(position==0?Color.BLACK:getResources().getColor(R.color.text_bg_3));
            tv.setBackgroundResource(position==0?R.drawable.text_rc_bg_select:R.drawable.text_rc_bg2);
            tv.setText(s);
            return tv;
        }

        @Override
        public void onSelected(int position, View view) {
            super.onSelected(position, view);
            ((TextView) view).setTextColor(Color.BLACK);
            ((TextView) view).setBackgroundResource(R.drawable.text_rc_bg_select);
        }

        @Override
        public void unSelected(int position, View view) {
            super.unSelected(position, view);
            ((TextView) view).setTextColor(getResources().getColor(R.color.text_bg_3));
            ((TextView) view).setBackgroundResource(R.drawable.text_rc_bg2);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_evaluate_show;
    }

    private boolean isExpend;


    @OnClick({R.id.gold_back, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.gold_back:
                finish();
                break;
            case R.id.more:
                if (isExpend) {
                    startAnimFold();
                }else {
                    startAnimExpend();
                }
                isExpend=!isExpend;
                break;
        }
    }

    private void startAnimFold() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(mMoreIv, "rotation", -180f, -0);
        anim.setDuration(300);
        anim.start();

        // 这里指定了值的变化范围
        ValueAnimator animator = ValueAnimator.ofFloat(126, DensityUtil.px2dip(this,mPreHeight));
        // 这里指定变化持续时间
        animator.setDuration(300);
        //开始动画
        animator.start();
        //开始动画后，我们可以动态的获取变化值
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //根据变化值来设置
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(EvaluateShowActivity.this, (Float) animation.getAnimatedValue()));
                mFlowLayout.setLayoutParams(lp);
            }
        });
    }

    private void startAnimExpend() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(mMoreIv, "rotation", 0f, -180f);
        anim.setDuration(300);
        anim.start();

        // 这里指定了值的变化范围
        ValueAnimator animator = ValueAnimator.ofFloat(DensityUtil.px2dip(this,mPreHeight), 126);
        // 这里指定变化持续时间
        animator.setDuration(300);
        //开始动画
        animator.start();
        //开始动画后，我们可以动态的获取变化值
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //根据变化值来设置
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(EvaluateShowActivity.this, (Float) animation.getAnimatedValue()));
                mFlowLayout.setLayoutParams(lp);
            }
        });
    }
}
