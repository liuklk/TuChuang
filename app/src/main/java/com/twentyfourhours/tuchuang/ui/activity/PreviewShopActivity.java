package com.twentyfourhours.tuchuang.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.twentyfourhours.tuchuang.R.id.iv1;

/**
 * Created by Administrator on 2018/1/13.
 */
public class PreviewShopActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.next_step)
    TextView mNextStep;
    @BindView(iv1)
    ImageView mIv1;
    @BindView(R.id.iv2)
    ImageView mIv2;
    @BindView(R.id.face)
    ImageView mFace;
    @BindView(R.id.shop_number)
    TextView mShopNumber;
    @BindView(R.id.shop_total_price)
    TextView mShopTotalPrice;
    @BindView(R.id.shop_price)
    TextView mShopPrice;
    @BindView(R.id.face_customized_price)
    TextView mFaceCustomizedPrice;
    private int count=1;
    private Bitmap bitmap;
    private Bitmap bitmap2;
    private Bitmap bitmap3;
    private Bitmap[] mBitmaps={bitmap,bitmap2,bitmap3};

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            byte[] bis = intent.getByteArrayExtra("bitmap");
            byte[] bis2 = intent.getByteArrayExtra("bitmap2");
            byte[] bis3 = intent.getByteArrayExtra("bitmap3");
            bitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);
            bitmap2 = BitmapFactory.decodeByteArray(bis2, 0, bis2.length);
            bitmap3 = BitmapFactory.decodeByteArray(bis3, 0, bis3.length);
            mBitmaps=new Bitmap[]{bitmap,bitmap2,bitmap3};
            if (bitmap == null) {
                mIv2.setImageResource(R.drawable.img_customized);
            } else {
                mIv2.setImageBitmap(bitmap);
            }
            if (bitmap2 == null) {
                mIv1.setImageResource(R.drawable.img_customized);
            } else {
                mIv1.setImageBitmap(bitmap2);
            }
            mIv1.setScaleX(0.8f);
            mIv1.setScaleY(0.8f);
            mIv2.setScaleX(0.8f);
            mIv2.setScaleY(0.8f);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_preview_shop;
    }


    @OnClick({R.id.face,R.id.back, R.id.next_step})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.face:
                change();
                break;
            case R.id.next_step:
                showAlertDialog();
                break;
        }
    }

    private void test(final ImageView Iv1, ImageView Iv2){
        TranslateAnimation animation1;
        if (bitmap!=null) {
            Iv1.setImageBitmap(count % 3 == 0 ? mBitmaps[count % 3 + 2] : mBitmaps[count % 3 - 1]);
            Iv2.setImageBitmap(mBitmaps[count % 3]);
        }else {
            Iv1.setImageResource(R.drawable.img_customized);
            Iv2.setImageResource(R.drawable.img_customized);
        }
        Iv2.setVisibility(View.VISIBLE);
        animation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animation1.setDuration(500);
        Iv1.startAnimation(animation1);
        TranslateAnimation animation2 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animation2.setDuration(500);
        Iv2.startAnimation(animation2);
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Iv1.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        count++;
    }

    private void change() {
        TranslateAnimation animation1;
        switch (count%3){
            case 0:
                mFace.setImageResource(R.drawable.icon_right);
                break;
            case 1:
                mFace.setImageResource(R.drawable.icon_reverse);
                break;
            case 2:
                mFace.setImageResource(R.drawable.icon_side);
                break;
        }

        test(count%2==0?mIv1:mIv2,count%2==0?mIv2:mIv1);
    }

    private void showAlertDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.item_shop_pre_dialog, null);
        Button modify = (Button) dialogView.findViewById(R.id.modify_btn);
        Button buy = (Button) dialogView.findViewById(R.id.buy_btn);
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .show();
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                dialog.dismiss();
            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(FirmOrderActivity.class, false);
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
    }
}
