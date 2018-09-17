package com.twentyfourhours.tuchuang.ui.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.leon.loopviewpagerlib.FunBanner;
import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.RecommendAdapter;
import com.twentyfourhours.tuchuang.adapter.RecommendLikeAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseFragment;
import com.twentyfourhours.tuchuang.model.bean.HotBeen;
import com.twentyfourhours.tuchuang.ui.activity.ShoppingDetailActivity;
import com.twentyfourhours.tuchuang.widget.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/1/9.
 */
public class RecommendFragment extends BaseFragment {
    @BindView(R.id.content_frame)
    FrameLayout mContentFrame;
    @BindView(R.id.grid_view1)
    MyGridView mGridView1;
    @BindView(R.id.time_h)
    TextView mTimeH;
    @BindView(R.id.time_m)
    TextView mTimeM;
    @BindView(R.id.time_s)
    TextView mTimeS;
    @BindView(R.id.delete_tv1)
    TextView mDeleteTv1;
    @BindView(R.id.delete_tv2)
    TextView mDeleteTv2;
    @BindView(R.id.delete_tv3)
    TextView mDeleteTv3;
    @BindView(R.id.grid_view2)
    MyGridView mGridView2;
    Unbinder unbinder;
    @BindView(R.id.scroll_view)
    ScrollView mScrollView;
    @BindView(R.id.shop1)
    RelativeLayout mShop1;
    @BindView(R.id.shop2)
    RelativeLayout mShop2;
    @BindView(R.id.shop3)
    RelativeLayout mShop3;
//    @BindView(R.id.digital_clock)
//    CustomDigitalClock mDigitalClock;
    private int[] imageResIds = {R.drawable.banner_img1, R.drawable.banner_img1, R.drawable.banner_img1, R.drawable.banner_img1, R.drawable.banner_img1, R.drawable.banner_img1, R.drawable.banner_img1};
    private RecommendAdapter mRecommendAdapter;
    private RecommendLikeAdapter mRecommendLikeAdapter;

    @Override
    protected View initView() {
        return View.inflate(mContext, R.layout.fragment_recommend, null);
    }

    @Override
    protected void initListener() {
//        mDigitalClock.setClockListener(new CustomDigitalClock.ClockListener() {
//
//            @Override
//            public void lastTime(String str) {
//
//            }
//
//            @Override
//            public void remainFiveMinutes() {
//                String digitalText =mDigitalClock.getText().toString().trim();
//                mTimeH.setText(digitalText.substring(0, 2));
//                mTimeM.setText(digitalText.substring(3, 5));
//                mTimeS.setText(digitalText.substring(6));
//            }
//        });
    }

    @Override
    protected void initData() {
        //mDigitalClock.setEndTime(System.currentTimeMillis() + 6 * 60 * 60 * 1000);

        mGridView1.setFocusable(false);

        mDeleteTv1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        mDeleteTv2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        mDeleteTv3.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        FunBanner.Builder builder = new FunBanner.Builder(mContext);
        FunBanner funBanner = builder.setEnableAutoLoop(true)
                .setImageResIds(imageResIds)
                .setDotSelectedColor(mContext.getResources().getColor(R.color.black_bg))
                .setHeightWidthRatio(0.528f)
                .setLoopInterval(8000)
                .setEnableAutoLoop(true)
                .build();
        mContentFrame.addView(funBanner);

        mRecommendAdapter = new RecommendAdapter(mContext);

        List<HotBeen> data2 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            HotBeen hotBeen = new HotBeen();
            hotBeen.setShow(i == 0 || i == 3);
            hotBeen.setShowLimitTime(i == 1 || i == 3);
            data2.add(hotBeen);
        }
        mRecommendAdapter.setData(data2);
        mGridView1.setAdapter(mRecommendAdapter);

        mRecommendLikeAdapter = new RecommendLikeAdapter(mContext);

        List<HotBeen> data = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            HotBeen hotBeen = new HotBeen();
            hotBeen.setShowLimitTime(i == 0 || i == 5);
            hotBeen.setShow1(i == 1 || i == 2 || i == 4);
            hotBeen.setShow2(i == 0 || i == 1 || i == 2);
            hotBeen.setShow3(i == 0 || i == 3);
            data.add(hotBeen);
        }
        mRecommendLikeAdapter.setData(data);
        mGridView2.setAdapter(mRecommendLikeAdapter);

//        mTimeH.setText(digitalText.substring(0,2));
//        mTimeM.setText(digitalText.substring(3,5));
//        mTimeS.setText(digitalText.substring(6));
    }

    @OnClick({R.id.shop1, R.id.shop2, R.id.shop3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shop1:
                goToLimit();
                break;
            case R.id.shop2:
                goToLimit();
                break;
            case R.id.shop3:
                goToLimit();
                break;
        }
    }

    private void goToLimit() {
        Intent intent = new Intent(mContext, ShoppingDetailActivity.class);
        intent.putExtra("limit_time", true);
        mContext.startActivity(intent);
    }

}
