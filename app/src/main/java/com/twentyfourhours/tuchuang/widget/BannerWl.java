package com.twentyfourhours.tuchuang.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.common.util.DensityUtil;


public class BannerWl extends RelativeLayout {

    private final int ScrHeight;
    private final int ScrWidth;
    private int mDotSize;
    private ViewPager mViewPager;
    private TextView mGo;
    private LinearLayout mContainer;
//    ShopView mShopView=new ShopView(getContext());
//    private ShopView[] mShopViews = {new ShopView(getContext()),new ShopView(getContext())};
    private int[] mImages = {R.drawable.welcome1,R.drawable.welcome2,R.drawable.welcome3,R.drawable.welcome4};
    private int mPrePosition=0;
    private float mX;
    private float mY;
    private Context mContext;

    public BannerWl(Context context) {
        this(context,null);
    }

    public BannerWl(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        //屏幕信息
        DisplayMetrics dm = getResources().getDisplayMetrics();
        ScrHeight = dm.heightPixels;
        ScrWidth = dm.widthPixels;
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Banner);
//        mDotSize = typedArray.getDimensionPixelSize(R.styleable.Banner_dot_size, 26);
//        typedArray.recycle();
        mDotSize=ScrWidth*8/100;

        LayoutInflater.from(getContext()).inflate(R.layout.banner_wl,this);

        initView();
//        initDots();//将点填入linearlayout中
        initData();
        setListener();
    }

    private OnGotoListener mListener;
    public interface OnGotoListener{
        void action();
    }
    public void setOnGotoListener(OnGotoListener listener){
        mListener=listener;
    }

    private void setListener() {
        mGo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null){
                    mListener.action();
                }
            }
        });
        mViewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mX = event.getX();
                        mY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float moveX = event.getX();
                        float moveY = event.getY();
                        if (mGo.getVisibility() == VISIBLE) {
                            if (mX - moveX > Math.abs(mY - moveY)) {
                                if (mListener != null) {
                                    mListener.action();
                                }
                                return true;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                View dot = mContainer.getChildAt(position);
                dot.setBackgroundColor(Color.BLACK);

                View preDot = mContainer.getChildAt(mPrePosition);
                preDot.setBackgroundColor(Color.WHITE);

                mPrePosition = position;
                if (position==mImages.length-1){
                    mGo.setVisibility(VISIBLE);
                }else {
                    mGo.setVisibility(GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });//监听页面轮播事件
    }

    private void initDots() {
        for (int i = 0; i < mImages.length; i++) {
            View view = new View(getContext());
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(DensityUtil.dip2px(mContext,25), DensityUtil.dip2px(mContext,1));
            if (i != mImages.length - 1) {
                params.rightMargin = DensityUtil.dip2px(mContext,10);
            }
            view.setLayoutParams(params);
            if (i==0){
                view.setBackgroundColor(Color.BLACK);
            }else {
                view.setBackgroundColor(Color.WHITE);
            }
            mContainer.addView(view);
        }
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int expendHight = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, expendHight);
//    }

    private void initData() {
        initDots();
        BannerAdapter adapter=new BannerAdapter();
        mViewPager.setAdapter(adapter);//设置适配器

        int initPosition = 0;
        mViewPager.setCurrentItem(initPosition);//设置viewpager初始化位置
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mGo = (TextView) findViewById(R.id.go);
        mContainer = (LinearLayout) findViewById(R.id.dot_container);
    }

    public class BannerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImages.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;//判断孩子是否有对应标记
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView=new ImageView(getContext());
            imageView.setImageResource(mImages[position]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);//设置填充类型
            container.addView(imageView);
            return imageView;//返回imageview作为自己的标记
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);//销毁对应位置的页面
        }
    }

}
