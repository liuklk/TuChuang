package com.twentyfourhours.tuchuang.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.twentyfourhours.tuchuang.R;

/**
 * Created by 徐良木 on 2017/7/2.
 */

public class Banner extends RelativeLayout {

    private final int ScrHeight;
    private final int ScrWidth;
    private int mDotSize;
    private ViewPager mViewPager;
//    private TextView mTitle;
    private LinearLayout mContainer;
//    ShopView mShopView=new ShopView(getContext());
    private ShopView[] mShopViews = {new ShopView(getContext()),new ShopView(getContext())};
//    private int[] mImages = {R.mipmap.banner,R.mipmap.banner};
    private int mPrePosition=0;



    public Banner(Context context) {
        this(context,null);
    }

    public Banner(Context context, AttributeSet attrs) {
        super(context, attrs);
        //屏幕信息
        DisplayMetrics dm = getResources().getDisplayMetrics();
        ScrHeight = dm.heightPixels;
        ScrWidth = dm.widthPixels;
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Banner);
//        mDotSize = typedArray.getDimensionPixelSize(R.styleable.Banner_dot_size, 26);
//        typedArray.recycle();
        mDotSize=ScrWidth*18/1000;

        LayoutInflater.from(getContext()).inflate(R.layout.banner,this);

        initView();
//        initDots();//将点填入linearlayout中
        initData();
        setListener();
    }

    private void setListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                position = position % mShopViews.length;
//                mTitle.setText(mTitles[position]);//切换标题
                View dot = mContainer.getChildAt(position);
                dot.setBackgroundResource(R.drawable.dot_select);

                View preDot = mContainer.getChildAt(mPrePosition);
                preDot.setBackgroundResource(R.drawable.dot_normal);

                mPrePosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });//监听页面轮播事件
    }

    private void initDots() {
        for (int i = 0; i < mShopViews.length; i++) {
            View view = new View(getContext());
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(mDotSize,mDotSize);
            if (i != mShopViews.length - 1) {
                params.rightMargin = 10;
            }
            view.setLayoutParams(params);
            if (i==0){
                view.setBackgroundResource(R.drawable.dot_select);
            }else {
                view.setBackgroundResource(R.drawable.dot_normal);
            }
            mContainer.addView(view);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expendHight = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expendHight);
    }

    private void initData() {
//        mTitle.setText(mTitles[0]);//初始化标题
        initDots();
        BannerAdapter adapter=new BannerAdapter();
        mViewPager.setAdapter(adapter);//设置适配器

        int initPosition = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % mShopViews.length;
        mViewPager.setCurrentItem(initPosition);//设置viewpager初始化位置
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp);
//        mTitle = (TextView) findViewById(R.id.tv_title);
        mContainer = (LinearLayout) findViewById(R.id.dot_container);
    }

    public class BannerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;//判断孩子是否有对应标记
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position=position%mShopViews.length;
            ShopView shopView=new ShopView(getContext());
//            ImageView imageView=new ImageView(getContext());
//            imageView.setImageResource(mImages[position]);
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);//设置填充类型
            container.addView(shopView);
//            container.addView(imageView);
            return shopView;//返回imageview作为自己的标记
//            return imageView;//返回imageview作为自己的标记
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);//销毁对应位置的页面
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        startLoop();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopLoop();
    }

    private void stopLoop() {
        mViewPager.removeCallbacks(mRunnable);
    }

    private void startLoop() {
        postDelayed(mRunnable,3000);
    }

    private Runnable mRunnable=new Runnable() {
        @Override
        public void run() {
            int currentItem = mViewPager.getCurrentItem();
            int nextItem = currentItem + 1;
            mViewPager.setCurrentItem(nextItem);
//            startLoop();
        }
    };
}
