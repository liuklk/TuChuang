package com.twentyfourhours.tuchuang.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/5.
 */

public class ShopView extends RelativeLayout {
    @BindView(R.id.name1)
    TextView mName1;
    @BindView(R.id.pic1)
    ImageView mPic1;
    @BindView(R.id.name2)
    TextView mName2;
    @BindView(R.id.pic2)
    ImageView mPic2;
    @BindView(R.id.name3)
    TextView mName3;
    @BindView(R.id.pic3)
    ImageView mPic3;
    @BindView(R.id.name4)
    TextView mName4;
    @BindView(R.id.pic4)
    ImageView mPic4;

    public ShopView(Context context) {
        this(context, null);
    }

    public ShopView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.fragment_shop1, this);
        ButterKnife.bind(this);
    }
}
