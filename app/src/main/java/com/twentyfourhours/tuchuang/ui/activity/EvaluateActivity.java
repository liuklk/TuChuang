package com.twentyfourhours.tuchuang.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scrat.app.selectorlibrary.ImageSelector;
import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.common.util.ToastUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/7 0007.
 */

public class EvaluateActivity extends BaseActivity {

    @BindView(R.id.gold_back)
    ImageView mGoldBack;
    @BindView(R.id.publish)
    TextView mPublish;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout mFlowLayout;
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
    @BindView(R.id.upload)
    ImageView mUpload;
    @BindView(R.id.load_image1)
    ImageView mLoadImage1;
    @BindView(R.id.load_image2)
    ImageView mLoadImage2;
    @BindView(R.id.load_image3)
    ImageView mLoadImage3;
    @BindView(R.id.load_image4)
    ImageView mLoadImage4;
    @BindView(R.id.load_image)
    LinearLayout mLoadImage;
    @BindView(R.id.content)
    EditText mContent;
    private String[] mVals = {"漂亮精致", "质量上乘", "物流快"};
    private List<String> mData = new ArrayList<>();
    private static final int REQUEST_CODE_SELECT_IMG = 1;
    private static final int MAX_SELECT_COUNT = 4;
    private ImageView[] mImageViews;

    @Override
    protected void initData() {
        mImageViews = new ImageView[]{mLoadImage1, mLoadImage2, mLoadImage3, mLoadImage4};
        //评价标签
        mData.clear();
        for (int i = 0; i < mVals.length; i++) {
            mData.add(mVals[i]);
        }
        mFlowLayout.setAdapter(mTagAdapter);

        for (int i = 0; i < mStar.getChildCount(); i++) {
            ImageView childAt = (ImageView) mStar.getChildAt(i);
            childAt.setSelected(true);
        }
        evalute(mStar);
    }

    private TagAdapter mTagAdapter = new TagAdapter<String>(mData) {
        @Override
        public View getView(FlowLayout parent, int position, String s) {
            TextView tv = (TextView) LayoutInflater.from(EvaluateActivity.this).inflate(R.layout.tv,
                    mFlowLayout, false);
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
        return R.layout.activity_evaluate;
    }

    private void evalute(final LinearLayout parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            final int position = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int j = 1; j < parent.getChildCount(); j++) {
                        if (parent.getChildAt(j) instanceof ImageView) {
                            if (j <= position) {
                                ((ImageView) parent.getChildAt(j)).setSelected(true);
                            } else {
                                ((ImageView) parent.getChildAt(j)).setSelected(false);
                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void initListener() {
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
//                toast(mVals[position]);
                return true;
            }
        });
    }


    @OnClick({R.id.gold_back, R.id.publish, R.id.upload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.gold_back:
                finish();
                break;
            case R.id.publish:
                if (mContent.getText().toString().trim().length() < 10) {
                    toast("请输入不少于10个字数");
                } else {
                    ToastUtil.showStatuDialog(this, "提交", new ToastUtil.OnActionListener() {
                        @Override
                        public void action() {
                            finish();
                        }
                    });
                }
                break;
            case R.id.upload:
                for (int i = 0; i < mImageViews.length; i++) {
                    mImageViews[i].setImageBitmap(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888));
                }
                selectImg();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SELECT_IMG) {
            showContent(data);
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showContent(Intent data) {
        List<String> paths = ImageSelector.getImagePaths(data);
        if (!paths.isEmpty()) {
            for (int i = 0; i < paths.size(); i++) {
                mImageViews[i].setImageBitmap(BitmapFactory.decodeFile(paths.get(i)));
            }
        }
    }

    public void selectImg() {
        ImageSelector.show(this, REQUEST_CODE_SELECT_IMG, MAX_SELECT_COUNT);
    }

}
