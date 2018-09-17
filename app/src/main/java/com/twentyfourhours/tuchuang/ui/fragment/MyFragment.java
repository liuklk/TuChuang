package com.twentyfourhours.tuchuang.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.DeliveryAdapter;
import com.twentyfourhours.tuchuang.adapter.HotAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseFragment;
import com.twentyfourhours.tuchuang.model.bean.HotBeen;
import com.twentyfourhours.tuchuang.model.bean.StoreBeen;
import com.twentyfourhours.tuchuang.ui.activity.DrawActivity;
import com.twentyfourhours.tuchuang.ui.activity.LoginActivity;
import com.twentyfourhours.tuchuang.ui.activity.MessageCenterActivity;
import com.twentyfourhours.tuchuang.ui.activity.MyCollectionActivity;
import com.twentyfourhours.tuchuang.ui.activity.MyCouponActivity;
import com.twentyfourhours.tuchuang.ui.activity.MyDesignActivity;
import com.twentyfourhours.tuchuang.ui.activity.MyOderActivity;
import com.twentyfourhours.tuchuang.ui.activity.SettingActivity;
import com.twentyfourhours.tuchuang.ui.activity.SystemHelpActivity;
import com.twentyfourhours.tuchuang.widget.MyGridView;
import com.twentyfourhours.tuchuang.widget.MyListView;
import com.twentyfourhours.tuchuang.widget.WaveViewByBezier2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/1/4.
 */

public class MyFragment extends BaseFragment {
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.grid_view)
    MyGridView mGridView;
    @BindView(R.id.list_view)
    MyListView mListView;
    @BindView(R.id.scroll_view)
    ScrollView mScrollView;
    Unbinder unbinder;
    @BindView(R.id.my_design)
    TextView mMyDesign;
    @BindView(R.id.my_draft)
    TextView mMyDraft;
    @BindView(R.id.linearLayout)
    LinearLayout mLinearLayout;
    @BindView(R.id.msg_iv)
    ImageView mMsgIv;
    @BindView(R.id.msg_number)
    TextView mMsgNumber;
    @BindView(R.id.setting_iv)
    ImageView mSettingIv;
    @BindView(R.id.pending_payment)
    TextView mPendingPayment;
    @BindView(R.id.pending_delivery)
    TextView mPendingDelivery;
    @BindView(R.id.pending_received)
    TextView mPendingReceived;
    @BindView(R.id.pending_evaluated)
    TextView mPendingEvaluated;
    @BindView(R.id.all_oder)
    TextView mAllOder;
    @BindView(R.id.collection)
    TextView mCollection;
    @BindView(R.id.discount)
    TextView mDiscount;
    @BindView(R.id.help)
    TextView mHelp;
    @BindView(R.id.customer)
    TextView mCustomer;
    @BindView(R.id.image_head)
    ImageView mImageHead;
//    @BindView(R.id.wave_bezier)
//    WaveViewByBezier mWaveBezier;
    @BindView(R.id.wave_bezier2)
    WaveViewByBezier2 mWaveBezier2;
    Unbinder unbinder1;
    private HotAdapter mHotAdapter;
    private Bitmap queryBitmap;

    @Override
    protected View initView() {
        return View.inflate(mContext, R.layout.fragment_my, null);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
//        mWaveBezier.startAnimation();
        mWaveBezier2.startAnimation();
        mGridView.setFocusable(false);
        mListView.setFocusable(false);


        DeliveryAdapter adapter = new DeliveryAdapter(mContext);
        List<StoreBeen> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            StoreBeen been = new StoreBeen();
            data.add(been);
        }
        adapter.setData(data);
        mListView.setAdapter(adapter);

        mHotAdapter = new HotAdapter(mContext);
        List<HotBeen> data2 = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            HotBeen hotBeen = new HotBeen();
            hotBeen.setShow(true);
            hotBeen.setPic1(R.drawable.face1);
            hotBeen.setPic2(R.drawable.back1);
            data2.add(hotBeen);
        }
        mHotAdapter.setData(data2);
        mGridView.setAdapter(mHotAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
//        mWaveBezier.resumeAnimation();
        mWaveBezier2.resumeAnimation();
    }



    @OnClick(R.id.user_name)
    public void onViewClicked() {
        goTo(LoginActivity.class, false);
    }

    @OnClick({R.id.collection, R.id.discount, R.id.help, R.id.customer, R.id.my_design, R.id.my_draft, R.id.msg_iv, R.id.setting_iv, R.id.pending_payment, R.id.pending_delivery, R.id.pending_received, R.id.pending_evaluated, R.id.all_oder})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_design:
                goTo(MyDesignActivity.class, false);
                break;
            case R.id.my_draft:
                Intent intent = new Intent(mContext, DrawActivity.class);
                intent.putExtra("draft", true);
                startActivity(intent);
//                goTo(DrawActivity.class,false);
                break;
            case R.id.msg_iv:
                goTo(MessageCenterActivity.class, false);
                break;
            case R.id.setting_iv:
                goTo(SettingActivity.class, false);
                break;
            case R.id.pending_payment:
                gotoMyOder(1);
                break;
            case R.id.pending_delivery:
                gotoMyOder(2);
                break;
            case R.id.pending_received:
                gotoMyOder(3);
                break;
            case R.id.pending_evaluated:
                gotoMyOder(4);
                break;
            case R.id.all_oder:
                gotoMyOder(0);
                break;
            case R.id.collection:
                goTo(MyCollectionActivity.class, false);
                break;
            case R.id.discount:
                goTo(MyCouponActivity.class, false);
                break;
            case R.id.help:
                goTo(SystemHelpActivity.class, false);
                break;
            case R.id.customer:

                break;
        }
    }

    private void gotoMyOder(int i) {
        Intent intent = new Intent(getActivity(), MyOderActivity.class);
        intent.putExtra("oderPosition", i);
        startActivity(intent);
    }


    @Override
    public void onPause() {
        super.onPause();
//        mWaveBezier.pauseAnimation();
        mWaveBezier2.pauseAnimation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mWaveBezier.stopAnimation();
        mWaveBezier2.stopAnimation();
    }
}
