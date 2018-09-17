package com.twentyfourhours.tuchuang.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.common.util.DataClearUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/11.
 */
public class DrawActivity extends BaseActivity implements View.OnTouchListener, SeekBar.OnSeekBarChangeListener, RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.preview)
    TextView mPreview;
    @BindView(R.id.iv)
    ImageView mIv;
    @BindView(R.id.type1)
    TextView mType1;
    @BindView(R.id.type2)
    TextView mType2;
    @BindView(R.id.type3)
    TextView mType3;
    @BindView(R.id.clear)
    TextView mClear;
    @BindView(R.id.clear_all)
    TextView mClearAll;
    @BindView(R.id.down)
    ImageView mDown;
    @BindView(R.id.seekBar)
    SeekBar mSeekBar;
    @BindView(R.id.color_view)
    RelativeLayout mColorView;
    @BindView(R.id.up)
    ImageView mUp;
    @BindView(R.id.radio_grop)
    RadioGroup mRadioGrop;
    @BindView(R.id.color1)
    RadioButton mColor1;
    @BindView(R.id.color2)
    RadioButton mColor2;
    @BindView(R.id.color3)
    RadioButton mColor3;
    @BindView(R.id.color4)
    RadioButton mColor4;
    @BindView(R.id.color5)
    RadioButton mColor5;
    @BindView(R.id.color6)
    RadioButton mColor6;
    @BindView(R.id.color7)
    RadioButton mColor7;
    @BindView(R.id.color8)
    RadioButton mColor8;
    @BindView(R.id.color9)
    RadioButton mColor9;
    @BindView(R.id.color10)
    RadioButton mColor10;
    @BindView(R.id.right)
    LinearLayout mRight;
    private Bitmap bitmap;
    private Canvas canvas;
    private Matrix matrix;
    private Paint paint;
    private float mDownX;
    private float mDownY;
    private int ScrHeight;
    private int ScrWidth;
    private Handler mHandler = new Handler();
    private int mColor;
    private FileInputStream stream;

    @Override
    protected void initListener() {
        mIv.setOnTouchListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
        mRadioGrop.setOnCheckedChangeListener(this);
    }

    @Override
    protected void initData() {
        mType1.setSelected(false);
        mType1.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size_10));
        mType2.setSelected(true);
        mType2.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size_15));
        mType3.setSelected(false);
        mType3.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size_10));
        mClear.setSelected(false);
        mClear.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size_10));
        //屏幕信息
        DisplayMetrics dm = getResources().getDisplayMetrics();
        ScrHeight = dm.heightPixels;
        ScrWidth = dm.widthPixels;
        //1.创建空白纸张
        this.bitmap = Bitmap.createBitmap(ScrWidth, ScrHeight * 42 / 50, Bitmap.Config.ARGB_8888);
        //默认bitmap背景黑色
        //准备作画数据
        //创建画板
        canvas = new Canvas(this.bitmap);
        matrix = new Matrix();

        int progress = mSeekBar.getProgress();
        System.out.println(progress);

        paint = new Paint();
        //设置画笔重新
        paint.setStrokeWidth(progress + 8);
        //设置默认颜色
        mColor = Color.RED;
        paint.setColor(Color.RED);
        //设置画笔形状
        paint.setStrokeCap(Paint.Cap.ROUND);
        File file = null;
        if (getIntent().getBooleanExtra("draft",false)){
            file = new File(Environment.getExternalStorageDirectory(),"tuya.jpg");//System.currentTimeMillis()
        }
        //开始作画
        if (file!=null&&file.exists()){
            try {
                stream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            this.bitmap = BitmapFactory.decodeStream(stream);
        }
        canvas.drawBitmap(this.bitmap, matrix, paint);
        canvas.drawColor(Color.alpha(0));
//        canvas.drawColor(Color.WHITE);

        mIv.setImageBitmap(this.bitmap);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_draw;
    }

    @OnClick({R.id.back, R.id.preview, R.id.type1, R.id.type2, R.id.type3, R.id.clear, R.id.clear_all, R.id.down, R.id.up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.preview:
                //预览,跳转
                Intent intent = new Intent(this, PreviewActivity.class);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] bitmapByte = baos.toByteArray();
                intent.putExtra("bitmap", bitmapByte);
                startActivity(intent);
                break;
//            case R.id.push_pic:
////                mIv.setBackgroundResource(R.mipmap.img_customized);
//
//                break;
            case R.id.type1:
                paint.setColor(mColor);
                mType1.setSelected(true);
                mType1.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size_15));
                mType2.setSelected(false);
                mType2.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size_10));
                mType3.setSelected(false);
                mType3.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size_10));
                mClear.setSelected(false);
                mClear.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size_10));
                paint.setStrokeCap(Paint.Cap.BUTT);
                break;
            case R.id.type2:
                paint.setColor(mColor);
                mType1.setSelected(false);
                mType1.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size_10));
                mType2.setSelected(true);
                mType2.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size_15));
                mType3.setSelected(false);
                mType3.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size_10));
                mClear.setSelected(false);
                mClear.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size_10));
                paint.setStrokeCap(Paint.Cap.ROUND);
                break;
            case R.id.type3:
                paint.setColor(mColor);
                mType1.setSelected(false);
                mType1.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size_10));
                mType2.setSelected(false);
                mType2.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size_10));
                mType3.setSelected(true);
                mType3.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size_15));
                mClear.setSelected(false);
                mClear.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size_10));
                paint.setStrokeCap(Paint.Cap.SQUARE);
                break;
            case R.id.clear:
                mType1.setSelected(false);
                mType1.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size_10));
                mType2.setSelected(false);
                mType2.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size_10));
                mType3.setSelected(false);
                mType3.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size_10));
                mClear.setSelected(true);
                mClear.setTextSize(getResources().getDimensionPixelSize(R.dimen.text_size_15));
                paint.setColor(Color.WHITE);
                break;
            case R.id.clear_all:
                DataClearUtil.deleteFilesByDirectory(Environment.getExternalStorageDirectory());
                initData();
                break;
            case R.id.down:
                startAnim2();
                break;
            case R.id.up:
                startAnim();
                break;
        }
    }

    private boolean isFinish;

    private void startAnim2() {
        if (isFinish) return;
        isFinish=true;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);
                animation.setDuration(500);
                mColorView.startAnimation(animation);

                TranslateAnimation animation2 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
                animation2.setDuration(500);
                mRight.startAnimation(animation2);

                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                isFinish = false;
                                mRight.setVisibility(View.INVISIBLE);
                                mColorView.setVisibility(View.INVISIBLE);
                                mUp.setVisibility(View.VISIBLE);
                            }
                        });

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            }
        });

    }

    private void startAnim() {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRight.setVisibility(View.VISIBLE);
                        mColorView.setVisibility(View.VISIBLE);
                        mUp.setVisibility(View.INVISIBLE);
                    }
                });

                TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0);
                animation.setDuration(500);
                mColorView.startAnimation(animation);

                TranslateAnimation animation2 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
                animation2.setDuration(500);
                mRight.startAnimation(animation2);
            }
        });
    }

//    /**
//     * 保存图片
//     */
//    private void saveBitmap() {
//        OutputStream stream = null;
//        try {
////            File file = new File("/mnt/sdcard/" + "tuya" + ".jpg");//System.currentTimeMillis()
//            File file = new File(Environment.getExternalStorageDirectory(),System.currentTimeMillis()+".jpg");//System.currentTimeMillis()
//            stream = new FileOutputStream(file);
//            //bitmmap存储到一张图片里面:参数1-图片格式,参数2-图片质量
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//
//            //通知系统扫描文件（全局扫描、扫描单个文件）
//            Intent intent = new Intent();
//            //intent.setAction(Intent.ACTION_MEDIA_MOUNTED);//全局扫描：耗性能（Android4.4以上禁用）
//            intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);//扫描单个文件
//            //设置数据
//            intent.setData(Uri.fromFile(file));
//            sendBroadcast(intent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                stream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        saveBitmap();
//    }

    /**
     * 触摸调用该方法 参数1：触摸的控件 参数2：事件类型（按下，移动，抬起）
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // 判断动作
        int action = event.getAction();// 获取事件类型（按下，移动，抬起）
        switch (action) {
            case MotionEvent.ACTION_DOWN:// 按下
                if (mUp.getVisibility() == View.INVISIBLE) {
                    startAnim2();
                }
                //获取起点坐标
                mDownX = event.getX();
                mDownY = event.getY();


                break;
            case MotionEvent.ACTION_MOVE:// 移动

                //获取移动坐标
                float moveX = event.getX();
                float moveY = event.getY();

                //开始划线
                //画画：参数1、2：起点坐标,参数3、4：终点坐标
                canvas.drawLine(mDownX, mDownY, moveX, moveY, paint);

                //显示结果
                mIv.setImageBitmap(bitmap);

                //重新计算起点坐标
                mDownX = moveX;
                mDownY = moveY;

                break;
            case MotionEvent.ACTION_UP:// 抬起
                break;

        }

        //true-是否消费该事件，事件不会传递给别的控件（后面有自定义控件）
        //false:不消耗该事件，传递给别人
        return true;
    }

    //拖拽就调用
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
    }

    //开始拖拽调用
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    //停止拖拽调用
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

        int progress = seekBar.getProgress();
        paint.setStrokeWidth(progress);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.color1:
                mColor = getResources().getColor(R.color.color1);
                break;
            case R.id.color2:
                mColor = getResources().getColor(R.color.color2);
                break;
            case R.id.color3:
                mColor = getResources().getColor(R.color.color3);
                break;
            case R.id.color4:
                mColor = getResources().getColor(R.color.color4);
                break;
            case R.id.color5:
                mColor = getResources().getColor(R.color.color5);
                break;
            case R.id.color6:
                mColor = getResources().getColor(R.color.color6);
                break;
            case R.id.color7:
                mColor = getResources().getColor(R.color.color7);
                break;
            case R.id.color8:
                mColor = getResources().getColor(R.color.color8);
                break;
            case R.id.color9:
                mColor = getResources().getColor(R.color.color9);
                break;
            case R.id.color10:
                mColor = getResources().getColor(R.color.color10);
                break;
        }
        if (!mClear.isSelected()) {
            paint.setColor(mColor);
        }
    }

}
