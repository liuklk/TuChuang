package com.twentyfourhours.tuchuang.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scrat.app.selectorlibrary.ImageSelector;
import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.common.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/17.
 */
public class FeedBackActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.commit)
    TextView mCommit;
    @BindView(R.id.number)
    EditText mNumber;
    @BindView(R.id.detail)
    EditText mDetail;
    @BindView(R.id.text_count)
    TextView mTextCount;
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
    private static final int REQUEST_CODE_SELECT_IMG = 1;
    private static final int MAX_SELECT_COUNT = 4;
    private ImageView[] mImageViews;

    @Override
    protected void initListener() {
        mDetail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTextCount.setText(String.valueOf(s.length()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initData() {
        mImageViews=new ImageView[]{mLoadImage1,mLoadImage2,mLoadImage3,mLoadImage4};
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    @OnClick({R.id.back, R.id.commit, R.id.upload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.commit:
                ToastUtil.showStatuDialog(this, "提交", new ToastUtil.OnActionListener() {
                    @Override
                    public void action() {
                        finish();
                    }
                });
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
