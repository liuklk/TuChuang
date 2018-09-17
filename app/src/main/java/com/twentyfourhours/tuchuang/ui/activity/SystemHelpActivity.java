package com.twentyfourhours.tuchuang.ui.activity;

import android.animation.ObjectAnimator;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.CustomExpandAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.model.bean.DataBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/19.
 */
public class SystemHelpActivity extends BaseActivity implements ExpandableListView.OnGroupExpandListener, ExpandableListView.OnGroupCollapseListener {

    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.expend_lv)
    ExpandableListView mExpendLv;
    private List<DataBean> dataBeanList;
    private DataBean dataBean;
    private String[] mTitles = {"优惠券", "关于支付", "关于发票", "关于配送", "关于订单的变更,取消", "关于退货", "意见反馈", "热线客服"};
    private String[] mContents = {"优惠券的使用方法,使用优惠券购物后的退货", "发票要求", "帮助中心", "配送期限,配送费用", "变更订单,取消订单", "退换货,寄回商品", "界面问题,功能问题", "服务时间9:00-22:30"};
    private int[] mPics = {R.drawable.icon_coupon_help, R.drawable.icon_payment_help, R.drawable.icon_invoice_help, R.drawable.icon_diistribution_help, R.drawable.icon_order_help, R.drawable.icon_returngoods_help, R.drawable.icon_opinion_help, R.drawable.icon_customerservice_help};

    /**
     * 模拟数据
     */
    protected void initData() {
        dataBeanList = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            dataBean = new DataBean();
            dataBean.setTitle(mTitles[i]);
            dataBean.setContent(mContents[i]);
            dataBean.setPic(mPics[i]);
            dataBean.setID((i + 1) + "");
            dataBean.setType(0);
            dataBean.setChildBean(dataBean);
            dataBeanList.add(dataBean);
        }
        setData();
    }

    private void setData() {
        CustomExpandAdapter adapter = new CustomExpandAdapter(this, dataBeanList);
        mExpendLv.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        mExpendLv.setOnGroupExpandListener(this);
        mExpendLv.setOnGroupCollapseListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_system_help_;
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    private int prePosition=-1;

    @Override
    public void onGroupExpand(int groupPosition) {
        if (prePosition!=groupPosition){
            if (prePosition!=-1){
                mExpendLv.collapseGroup(prePosition);
            }
            prePosition=groupPosition;
        }
        ImageView expend = (ImageView) mExpendLv.getChildAt(groupPosition).findViewById(R.id.expend);
        rotationExpandIcon1(expend);
    }

    @Override
    public void onGroupCollapse(int groupPosition) {
        ImageView expend = (ImageView) mExpendLv.getChildAt(groupPosition).findViewById(R.id.expend);
        rotationExpandIcon2(expend);
    }

    private void rotationExpandIcon1(ImageView expand) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(expand, "rotation", 0, 90);
        anim.setDuration(300);
        anim.start();
    }
    private void rotationExpandIcon2(ImageView expand) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(expand, "rotation", 90, 0);
        anim.setDuration(300);
        anim.start();
    }
}
