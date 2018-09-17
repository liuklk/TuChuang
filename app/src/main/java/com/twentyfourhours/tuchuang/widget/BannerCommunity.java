package com.twentyfourhours.tuchuang.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;

/**
 * Created by 徐良木 on 2017/7/2.
 */

public class BannerCommunity extends RelativeLayout {

    private ViewPager mViewPager;
    private TextView mCurrentPosition;
    private TextView mTotalNumber;
    private int[] mImages = {R.drawable.img1_community_detail,R.drawable.img1_community_detail,R.drawable.img1_community_detail,R.drawable.img1_community_detail,R.drawable.img1_community_detail};

    public BannerCommunity(Context context) {
        this(context,null);
    }

    public BannerCommunity(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(getContext()).inflate(R.layout.banner_community,this);

        initView();
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
                mCurrentPosition.setText((position+1)+"");//切换标题
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });//监听页面轮播事件
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expendHight = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expendHight);
    }

    private void initData() {
        mCurrentPosition.setText("1");//初始化标题
        mTotalNumber.setText(mImages.length+"");
        BannerAdapter adapter=new BannerAdapter();
        mViewPager.setAdapter(adapter);//设置适配器

        int initPosition = 0;
        mViewPager.setCurrentItem(initPosition);//设置viewpager初始化位置
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mCurrentPosition = (TextView) findViewById(R.id.current_position);
        mTotalNumber = (TextView) findViewById(R.id.total_number);
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
