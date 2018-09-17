package com.twentyfourhours.tuchuang.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/22.
 */
public class PreviewActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.next_step)
    TextView mNextStep;
    @BindView(R.id.img)
    ImageView mImg;
    private Bitmap mBitmap;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            byte[] bis = intent.getByteArrayExtra("bitmap");
            mBitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);
            Bitmap bitmap = mBitmap;
            mImg.setImageBitmap(bitmap);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_preview;
    }

    @OnClick({R.id.back, R.id.next_step})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.next_step:
                showAlertDialog();
                break;
        }
    }

    private void showAlertDialog() {
        saveBitmap();
        View dialogView = LayoutInflater.from(this).inflate(R.layout.item_shop_pre_dialog, null);
        Button modify= (Button) dialogView.findViewById(R.id.modify_btn);
        Button buy= (Button) dialogView.findViewById(R.id.buy_btn);
        buy.setText("下一步");
        final AlertDialog dialog=new AlertDialog.Builder(this)
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
                Intent intent=new Intent(PreviewActivity.this,CustomizedActivity.class);
                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte [] bitmapByte =baos.toByteArray();
                intent.putExtra("bitmap", bitmapByte);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
    }

    private void saveBitmap() {
        OutputStream stream = null;
        try {
//            File file = new File("/mnt/sdcard/" + "tuya" + ".jpg");//System.currentTimeMillis()
            File file = new File(Environment.getExternalStorageDirectory(),System.currentTimeMillis()+".jpg");//System.currentTimeMillis()
            stream = new FileOutputStream(file);
            //bitmmap存储到一张图片里面:参数1-图片格式,参数2-图片质量
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

            //通知系统扫描文件（全局扫描、扫描单个文件）
            Intent intent = new Intent();
            //intent.setAction(Intent.ACTION_MEDIA_MOUNTED);//全局扫描：耗性能（Android4.4以上禁用）
            intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);//扫描单个文件
            //设置数据
            intent.setData(Uri.fromFile(file));
            sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
