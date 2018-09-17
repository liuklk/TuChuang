package com.twentyfourhours.tuchuang.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/5.
 */

public class GallaryView extends RelativeLayout {


    @BindView(R.id.image_view)
    ImageView mImageView;
    @BindView(R.id.price)
    TextView mPrice;
    @BindView(R.id.introduce)
    TextView mIntroduce;
    @BindView(R.id.ll_text_view)
    LinearLayout mLlTextView;

    public GallaryView(Context context) {
        this(context, null);
    }

    public GallaryView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GallaryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.item_gallery, this);
        ButterKnife.bind(this);
    }
}
