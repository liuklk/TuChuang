package com.twentyfourhours.tuchuang.ui.activity;

import android.app.FragmentTransaction;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.ui.fragment.DetailFragment;
import com.twentyfourhours.tuchuang.ui.fragment.ShopFragment;
import com.twentyfourhours.tuchuang.view.CustomDialog;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2018/1/8.
 */
public class ShoppingDetailActivity extends BaseActivity {


    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.gray_view1)
    View mGrayView1;
    @BindView(R.id.shop_btn)
    TextView mShopBtn;
    @BindView(R.id.detail_btn)
    TextView mDetailBtn;
    @BindView(R.id.gray_view2)
    View mGrayView2;
    @BindView(R.id.share)
    ImageView mShare;
    @BindView(R.id.home)
    ImageView mHome;
    @BindView(R.id.container)
    FrameLayout mContainer;
    @BindView(R.id.customer_service)
    ImageView mCustomerService;
    @BindView(R.id.cart_number)
    TextView mCartNumber;
    @BindView(R.id.cart)
    RelativeLayout mCart;
    @BindView(R.id.collection)
    CheckBox mCollection;
    @BindView(R.id.add_cart)
    Button mAddCart;
    @BindView(R.id.buy)
    Button mBuy;
    @BindView(R.id.bottom_view)
    LinearLayout mBottomView;
    @BindView(R.id.small_good)
    ImageView mSmallGood;

    public TextView getCartNumber() {
        return mCartNumber;
    }

    public ImageView getSmallGood() {
        return mSmallGood;
    }

    @BindView(R.id.rl_view)
    RelativeLayout mRlView;

    private CustomDialog mShaDialog;
    private ShopFragment mShopFragment;
    private DetailFragment mDetailFragment;

    @Override
    protected void initListener() {

        mCollection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toast("收藏成功");
                }
            }
        });
    }

    @Override
    protected void initData() {
        initFragment1();

//        String shopCode = getIntent().getStringExtra("shopCode");

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_detail;
    }

    @OnClick({R.id.back, R.id.home, R.id.shop_btn, R.id.detail_btn, R.id.share, R.id.customer_service, R.id.cart, R.id.add_cart, R.id.buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back://返回
                finish();
                break;
            case R.id.shop_btn://商品
                containShop();
                break;
            case R.id.detail_btn://详情
                containDetail();
                break;
            case R.id.share://分享

                break;
            case R.id.customer_service://客服

                break;
            case R.id.home:
                goTo(MainActivity.class, false);
                break;
            case R.id.cart://购物车
                goTo(CartActivity.class, false);
                break;
            case R.id.add_cart://添加购物车
                if ("规格数量选择".equals(mShopFragment.getTypeSelect().getText().toString().trim())) {
                    mShopFragment.showTypeDialog();
                } else {
                    mSmallGood.setVisibility(View.VISIBLE);
                    executeAllAnimations(mSmallGood, 1);
                }
                break;
            case R.id.buy://立即购买
                if ("规格数量选择".equals(mShopFragment.getTypeSelect().getText().toString().trim())) {
                    mShopFragment.showTypeDialog();
                } else {
                    goTo(FirmOrderActivity.class, false);
                }
                break;
        }
    }

    public void containDetail() {
        initFragment2();
        mShopBtn.setTextColor(Color.BLACK);
        mDetailBtn.setTextColor(Color.WHITE);
        mShopBtn.setBackgroundColor(Color.alpha(0));
        mDetailBtn.setBackgroundColor(getResources().getColor(R.color.tv_shop));
        mGrayView1.setBackground(null);
        mGrayView2.setBackground(getDrawable(R.drawable.gray_bg_shop));
    }

    public void containShop() {
        initFragment1();
        mShopBtn.setTextColor(Color.WHITE);
        mDetailBtn.setTextColor(Color.BLACK);
        mShopBtn.setBackgroundColor(getResources().getColor(R.color.tv_shop));
        mDetailBtn.setBackgroundColor(Color.alpha(0));
        mGrayView1.setBackground(getDrawable(R.drawable.gray_bg_shop));
        mGrayView2.setBackground(null);
    }

    //显示第一个fragment
    private void initFragment1() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
        if (mShopFragment == null) {
            mShopFragment = new ShopFragment();
            fragmentTransaction.add(R.id.container, mShopFragment);
        }
        //隐藏所有fragment
        hideFragment(fragmentTransaction);
        //显示需要显示的fragment
        fragmentTransaction.show(mShopFragment);
        //提交事务
        fragmentTransaction.commit();
    }

    //显示第二个fragment
    private void initFragment2() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
        if (mDetailFragment == null) {
            mDetailFragment = new DetailFragment();
            fragmentTransaction.add(R.id.container, mDetailFragment);
        }
        //隐藏所有fragment
        hideFragment(fragmentTransaction);
        //显示需要显示的fragment
        fragmentTransaction.show(mDetailFragment);
        //提交事务
        fragmentTransaction.commit();
    }

    //隐藏所有的fragment
    private void hideFragment(FragmentTransaction transaction) {
        if (mShopFragment != null) {
            transaction.hide(mShopFragment);
        }
        if (mDetailFragment != null) {
            transaction.hide(mDetailFragment);
        }
    }

    private void showShareDialog() {
        View shareDialog = View.inflate(this, R.layout.dialog_share, null);
        TextView wechat = (TextView) shareDialog.findViewById(R.id.wechat);
        TextView friends = (TextView) shareDialog.findViewById(R.id.friends);
//        TextView link = (TextView) shareDialog.findViewById(R.id.link);
        Button cancel = (Button) shareDialog.findViewById(R.id.cancel);
        mShaDialog = new CustomDialog(this);
        mShaDialog.setContentView(shareDialog);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShaDialog.dismiss();
            }
        });
        mShaDialog.show();
    }

    /**
     * 一次执行所有的动画效果
     *
     * @param view
     */
    public void executeAllAnimations(View view, final int type) {
        /*
         *  创建一个AnimationSet，它能够同时执行多个动画效果
         *  构造方法的入参如果是“true”，则代表使用默认的interpolator，如果是“false”则代表使用自定义interpolator
         */
        AnimationSet animationSet = new AnimationSet(true);

        /*
         *  创建一个半透明效果的动画对象，效果从完全透明到完全不透明
         */
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0.1f);

        /*
         *  设置动画的持续时间
         */
        alphaAnimation.setDuration(1000);

        /*
         *  创建一个旋转动画对象
         *  入参列表含义如下：
         *  1.fromDegrees：从哪个角度开始旋转
         *  2.toDegrees：旋转到哪个角度结束
         *  3.pivotXType：旋转所围绕的圆心的x轴坐标的类型，有ABSOLUT绝对坐标、RELATIVE_TO_SELF相对于自身坐标、RELATIVE_TO_PARENT相对于父控件的坐标
         *  4.pivotXValue：旋转所围绕的圆心的x轴坐标,0.5f表明是以自身这个控件的一半长度为x轴
         *  5.pivotYType：y轴坐标的类型
         *  6.pivotYValue：y轴坐标
         */
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        /*
         *  设置动画的持续时间
         */
        rotateAnimation.setDuration(1000);

        /*
         *  创建一个缩放效果的动画
         *  入参列表含义如下：
         *  fromX：x轴的初始值
         *  toX：x轴缩放后的值
         *  fromY：y轴的初始值
         *  toY：y轴缩放后的值
         *  pivotXType：x轴坐标的类型，有ABSOLUT绝对坐标、RELATIVE_TO_SELF相对于自身坐标、RELATIVE_TO_PARENT相对于父控件的坐标
         *  pivotXValue：x轴的值，0.5f表明是以自身这个控件的一半长度为x轴
         *  pivotYType：y轴坐标的类型
         *  pivotYValue：轴的值，0.5f表明是以自身这个控件的一半长度为y轴
         */
//        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0.1f, 1, 0.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//
//        /*
//         *  设置动画的持续时间
//         */
//        scaleAnimation.setDuration(1000);

        /*
         *  创建一个移动动画效果
         *  入参的含义如下：
         *  fromXType：移动前的x轴坐标的类型
         *  fromXValue：移动前的x轴的坐标
         *  toXType：移动后的x轴的坐标的类型
         *  toXValue：移动后的x轴的坐标
         *  fromYType：移动前的y轴的坐标的类型
         *  fromYValue：移动前的y轴的坐标
         *  toYType：移动后的y轴的坐标的类型
         *  toYValue：移动后的y轴的坐标
         */
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -7f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);

        /*
         *  设置动画的持续时间
         */
        translateAnimation.setDuration(1000);

        /*
         *  将四种动画效果放入同一个AnimationSet中
         */
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(rotateAnimation);
//        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(translateAnimation);

        /*
         *  同时执行多个动画效果
         */
        view.startAnimation(animationSet);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mSmallGood.setVisibility(View.GONE);
                if (type == 0) {
                    int selects = mShopFragment.getFlowlayout().getSelectedList().iterator().next();
                    mShopFragment.getTypeSelect().setText(mShopFragment.getVals()[selects] + "  " + mShopFragment.getShopNumber().getText().toString().trim() + "件");
                    int totalNumber = Integer.parseInt(mCartNumber.getText().toString().trim()) + Integer.parseInt(mShopFragment.getShopNumber().getText().toString().trim());
                    mCartNumber.setText(String.valueOf(totalNumber));
                } else {
                    String typeSelect = mShopFragment.getTypeSelect().getText().toString().trim();
                    int totalNumber = Integer.parseInt(mCartNumber.getText().toString().trim()) + Integer.parseInt(typeSelect.substring(typeSelect.length() - 2, typeSelect.length() - 1));
                    mCartNumber.setText(String.valueOf(totalNumber));
                }

                ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1.2f, 1, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(500);
                mCartNumber.startAnimation(scaleAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
