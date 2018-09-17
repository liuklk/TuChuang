package com.twentyfourhours.tuchuang.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.CustomizedAdapter;
import com.twentyfourhours.tuchuang.adapter.TextStyleAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.model.bean.HotBeen;
import com.twentyfourhours.tuchuang.model.bean.StoreBeen;
import com.twentyfourhours.tuchuang.common.util.DensityUtil;
import com.twentyfourhours.tuchuang.common.util.ImgUtil;
import com.twentyfourhours.tuchuang.common.util.Utils;
import com.twentyfourhours.tuchuang.view.ColorBar;
import com.twentyfourhours.tuchuang.view.StickerView;
import com.twentyfourhours.tuchuang.widget.CircleMenuLayout2;
import com.twentyfourhours.tuchuang.widget.GallaryView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.twentyfourhours.tuchuang.common.util.Utils.comp;

/**
 * Created by Administrator on 2018/1/11.
 */
public class CustomizedActivity extends BaseActivity {
    public static final int SELECT_PHOTO = 1000;//启动相册标识
    public static final int SET_WZ_BITMAP = 3000;//设置文字
    @BindView(R.id.back)
    ImageView mBack;
//    @BindView(R.id.face)
//    CheckBox mFace;
    @BindView(R.id.shure)
    ImageView mShure;
    @BindView(R.id.image1)
    ImageView mImage1;
    @BindView(R.id.image2)
    ImageView mImage2;

    @BindView(R.id.id_circle_menu_item_center)
    RelativeLayout mIdCircleMenuItemCenter;
    @BindView(R.id.id_menulayout)
    CircleMenuLayout2 mCircleMenuLayout;
    Unbinder unbinder;
    @BindView(R.id.adult)
    TextView mAdult;
    @BindView(R.id.child)
    TextView mChild;
    @BindView(R.id.right_view)
    RelativeLayout mRightView;
    @BindView(R.id.photograph)
    TextView mPhotograph;
    @BindView(R.id.album)
    TextView mAlbum;
    @BindView(R.id.gallery)
    TextView mGallery;
    @BindView(R.id.right_view_pic)
    RelativeLayout mRightViewPic;
    @BindView(R.id.container)
    LinearLayout mContainer;
    @BindView(R.id.scroll_view)
    HorizontalScrollView mScrollView;
    @BindView(R.id.push_pic)
    RelativeLayout contentRootRlyt;
    //    @BindView(R.id.dottted_view)
//    ImageView mDotttedView;
    @BindView(R.id.text_style_top_view)
    ListView mTextStyleTopView;
    @BindView(R.id.color_bar)
    ColorBar mColorBar;
    @BindView(R.id.wenbenyangshi)
    TextView mWenbenyangshi;
    @BindView(R.id.text_b)
    ImageView mTextB;
    @BindView(R.id.text_i)
    ImageView mTextI;
    @BindView(R.id.text_u)
    ImageView mTextU;
    @BindView(R.id.text_style_top_view2)
    LinearLayout mTextStyleTopView2;
    @BindView(R.id.grid_view)
    GridView mGridView;
    @BindView(R.id.expend)
    ImageView mExpend;
    @BindView(R.id.push_pic_container)
    RelativeLayout mPushPicContainer;
    @BindView(R.id.style_text)
    TextView mStyleText;
    @BindView(R.id.image3)
    ImageView mImage3;
    @BindView(R.id.push_pic2)
    RelativeLayout mPushPic2;
    @BindView(R.id.push_pic_container2)
    RelativeLayout mPushPicContainer2;
    @BindView(R.id.push_pic3)
    RelativeLayout mPushPic3;
    @BindView(R.id.push_pic_container3)
    RelativeLayout mPushPicContainer3;
    @BindView(R.id.face_iv)
    ImageView mFaceIv;
    @BindView(R.id.shop_face)
    LinearLayout mShopFace;
    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.shop_back)
    LinearLayout mShopBack;
    @BindView(R.id.side_iv)
    ImageView mSideIv;
    @BindView(R.id.shop_side)
    LinearLayout mShopSide;
    @BindView(R.id.font)
    TextView mFont;
    @BindView(R.id.complete_iv)
    ImageView mCompleteIv;
    @BindView(R.id.complete_iv2)
    ImageView mCompleteIv2;
    @BindView(R.id.complete_iv3)
    ImageView mCompleteIv3;
    @BindView(R.id.right_view_pic_text)
    RelativeLayout mRightViewPicText;
    @BindView(R.id.face_tv)
    TextView mFaceTv;
    @BindView(R.id.back_tv)
    TextView mBackTv;
    @BindView(R.id.side_tv)
    TextView mSideTv;
    //    @BindView(R.id.list_view)
//    ListView mListView;
    private int prePostion = -1;

    private String[] mItemTexts = new String[]{"款式 ", "图库", "画画",
            "文字", "款式 ", "图库", "画画",
            "文字"};
    private int[] mItemImgs = new int[]{R.drawable.icon_style_selector,
            R.drawable.icon_gallery_selector, R.drawable.icon_draw_selector,
            R.drawable.icon_word_selector, R.drawable.icon_style_selector,
            R.drawable.icon_gallery_selector, R.drawable.icon_draw_selector,
            R.drawable.icon_word_selector};
    private PopupWindow mPopupWindow1;
    private Bitmap mBitmap;
    private Typeface mFromAsset;
    private CustomizedAdapter mCustomizedAdapter;
    private String mEditContent;
    private String[] textTitles = {"默认字体", "涂创定制", "涂创定制", "涂创定制"};
    private List<StoreBeen> mBeens = new ArrayList<>();
    private TextStyleAdapter mTextStyleAdapter;
    private int position = -1;

    @Override
    protected void initListener() {
        mTextStyleTopView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomizedActivity.this.position = position;
                if (onClicEditPosition != -1) {
                    if (position == 1) {
                        ((StickerView) mStickers.get(onClicEditPosition))
                                .resetText(mFromAsset);
                    } else {
                        ((StickerView) mStickers.get(onClicEditPosition))
                                .resetText(Typeface.DEFAULT);
                    }
                }
                for (int i = 0; i < mBeens.size(); i++) {
                    mBeens.get(i).setShow(position == i);
                }
                mTextStyleAdapter.notifyDataSetChanged();
                hideTextSetting();
            }
        });

//        mFace.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
//                TranslateAnimation animation1;
//                if (isChecked) {
//                    mImage2.setVisibility(View.VISIBLE);
//                    animation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
//                    animation1.setDuration(500);
//                    mImage1.startAnimation(animation1);
//                    TranslateAnimation animation2 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
//                    animation2.setDuration(500);
//                    mImage2.startAnimation(animation2);
//                } else {
//                    mImage1.setVisibility(View.VISIBLE);
//                    animation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
//                    animation1.setDuration(500);
//                    mImage2.startAnimation(animation1);
//                    TranslateAnimation animation2 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
//                    animation2.setDuration(500);
//                    mImage1.startAnimation(animation2);
//                }
//
//                animation1.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                        if (isChecked) {
//                            mImage1.setVisibility(View.GONE);
//                        } else {
//                            mImage2.setVisibility(View.GONE);
//                        }
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//
//                    }
//                });
//            }
//        });
//        mPushPic.setOnTouchListener(this);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < data2.size(); i++) {
                    if (i == position) {
                        data2.get(i).setShow(true);
                    } else {
                        data2.get(i).setShow(false);
                    }
                }
                mCustomizedAdapter.notifyDataSetChanged();
                mImage1.setImageResource(R.drawable.back1);
                mImage2.setImageResource(R.drawable.face1);
//                mImage2.setBgBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.face1));
//                mImage1.setBgBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.back1));
//                mImage2.setImageResource(R.drawable.face1);
                hideAdultStyle();//隐藏成人款式
            }
        });

        mColorBar.setOnColorChangerListener(new ColorBar.ColorChangeListener() {
            @Override
            public void colorChange(int color) {
                if (onClicEditPosition != -1) {
                    ((StickerView) mStickers.get(onClicEditPosition))
                            .resetText(mColorBar.getCurrentColor());//
                }
            }
        });

        mCircleMenuLayout.setOnMenuItemClickListener(new CircleMenuLayout2.OnMenuItemClickListener() {

            @Override
            public void itemClick(View view, int pos) {
//                toast(mItemTexts[pos]);
                switch (pos) {
                    case 0:
                    case 4://款式
                        mTextStyleTopView.setVisibility(View.GONE);
                        mScrollView.setVisibility(View.INVISIBLE);
                        if (free != null && pay != null) {
                            free.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                            pay.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                            count = 0;
                        }
                        startTypeAnim1();
                        break;
                    case 1:
                    case 5://图库
                        mTextStyleTopView.setVisibility(View.GONE);
//                        mScrollView.setVisibility(View.VISIBLE);
                        startPicAnim1();
                        break;
                    case 2:
                    case 6://画画
                        mTextStyleTopView.setVisibility(View.GONE);
                        mScrollView.setVisibility(View.INVISIBLE);
                        mRightView.setVisibility(View.INVISIBLE);
                        mRightViewPic.setVisibility(View.INVISIBLE);
                        if (free != null && pay != null) {
                            free.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                            pay.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                            count = 0;
                        }
                        goTo(DrawActivity.class, false);
                        break;
                    case 3:
                    case 7://文字
                        mScrollView.setVisibility(View.INVISIBLE);
//                        Bitmap bitmap = Bitmap.createBitmap(400, 300, Bitmap.Config.ALPHA_8);
                        Bitmap bitmap = Bitmap.createBitmap(200, 150, Bitmap.Config.ALPHA_8);
                        if (free != null && pay != null) {
                            free.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                            pay.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                            count = 0;
                        }
                        addStickerView(bitmap, true);
                        startTextAnim1();

//                        mImage1.setDrawingCacheEnabled(true);
//                        Bitmap drawingCache = mImage1.getDrawingCache();
//                        addStickerView(mBitmap,true);
//                        mImage1.setDrawingCacheEnabled(false);
                        break;
                }
                if (pos != prePostion) {
                    ((RelativeLayout) view.findViewById(R.id.item_bg)).setSelected(true);
                    ((ImageView) view.findViewById(R.id.id_circle_menu_item_image)).setSelected(true);
                    if (prePostion != -1) {
                        ((RelativeLayout) mCircleMenuLayout.getChildAt(prePostion + 1)).findViewById(R.id.item_bg).setSelected(false);
                        ((ImageView) mCircleMenuLayout.getChildAt(prePostion + 1).findViewById(R.id.id_circle_menu_item_image)).setSelected(false);
                    }
                    prePostion = pos;
                }

                mCircleMenuLayout.startAnim();
                mCircleMenuLayout.setClick(!mCircleMenuLayout.isClick());
            }

            @Override
            public void itemCenterClick(View view) {

            }
        });

        mCircleMenuLayout.setOnAnimFinishListener(new CircleMenuLayout2.OnAnimFinishListener() {
            @Override
            public void finish() {
                mCircleMenuLayout.setVisibility(View.INVISIBLE);
                mExpend.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 隐藏成人款式
     */
    private void hideAdultStyle() {
        TranslateAnimation animation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1);
        animation1.setDuration(500);
        mGridView.startAnimation(animation1);
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mGridView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 文字弹出动画
     */
    private void startTextAnim1() {
        if (mRightViewPicText.getVisibility() == View.VISIBLE) return;
        mRightView.setVisibility(View.INVISIBLE);
        mRightViewPic.setVisibility(View.INVISIBLE);
        mRightViewPicText.setVisibility(View.VISIBLE);
        TranslateAnimation animation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animation1.setDuration(500);
        mRightViewPicText.startAnimation(animation1);
    }

    private void startPicAnim2() {
        TranslateAnimation animation2 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animation2.setDuration(500);
        mRightViewPic.startAnimation(animation2);
        animation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mRightViewPic.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 图库弹出动画
     */
    private void startPicAnim1() {
        if (mRightViewPic.getVisibility() == View.VISIBLE) return;
        mRightViewPicText.setVisibility(View.INVISIBLE);
        mRightView.setVisibility(View.INVISIBLE);
        mRightViewPic.setVisibility(View.VISIBLE);
        TranslateAnimation animation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animation1.setDuration(500);
        mRightViewPic.startAnimation(animation1);
    }

    private void startTypeAnim2() {
        TranslateAnimation animation2 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animation2.setDuration(500);
        mRightView.startAnimation(animation2);
        animation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mRightView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    // 存储贴纸列表
    private ArrayList<View> mStickers;
    // 添加贴纸要用到的View
//    private RelativeLayout contentRootRlyt;
    private boolean isdel = false;// 判断只进行删除操作
    // 存储删除贴纸列表
    private ArrayList<View> mDelStickers;
    // 当前处于编辑状态的贴纸
    private StickerView mCurrentView;
    private int onClicEditPosition = -1;// 点击文字贴纸的下标
    private int delPosition = -1;// 要删除的贴纸下标
    // 存储过程贴纸列表
    private ArrayList<View> mResultStickers;
    private boolean isEditText;

    /**
     * @param bitmap
     * @param isEditText
     * @modificationHistory=====添加贴纸
     * @modify by reason: 原因
     */
    private void addStickerView(Bitmap bitmap, boolean isEditText) {
        this.isEditText = isEditText;

        final StickerView stickerView = new StickerView(this, isEditText);
        stickerView.setImageBitmap(bitmap);
        stickerView.setOperationListener(new StickerView.OperationListener() {
            @Override
            public void onDeleteClick() {
                mStickers.remove(stickerView);
                switch (select) {
                    case 0:
                        contentRootRlyt.removeView(stickerView);
                        break;
                    case 1:
                        mPushPic2.removeView(stickerView);
                        break;
                    case 2:
                        mPushPic3.removeView(stickerView);
                        break;
                }
                isdel = true;
                mDelStickers.add(stickerView);
            }

            @Override
            public void onEdit(StickerView stickerView) {
                mCurrentView.setInEdit(false);
                mCurrentView = stickerView;
                mCurrentView.setInEdit(true);
            }

            @Override
            public void onTop(StickerView stickerView) {
                int position = mStickers.indexOf(stickerView);
                if (position == mStickers.size() - 1) {
                    delPosition = mStickers.size() - 1;
                    return;
                }
                StickerView stickerTemp = (StickerView) mStickers
                        .remove(position);
                mStickers.add(mStickers.size(), stickerTemp);
                delPosition = mStickers.size() - 1;
            }

            @Override
            public void setTextContext(StickerView stickerView) {
                onClicEditPosition = mStickers.indexOf(stickerView);
//                if (onClicEditPosition != -1) {
//                    ((StickerView) mStickers.get(onClicEditPosition))
//                            .resetText("输入文本",
//                                    TextView.BufferType.NORMAL);
//                }

                Intent intent = new Intent(CustomizedActivity.this, EditTextActivity.class);
                intent.putExtra("editContent", mEditContent);
                startActivityForResult(intent, SET_WZ_BITMAP);
            }
        });
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        switch (select) {
            case 0:
                contentRootRlyt.addView(stickerView, lp);
                break;
            case 1:
                mPushPic2.addView(stickerView, lp);
                break;
            case 2:
                mPushPic3.addView(stickerView, lp);
                break;
        }

        if (mResultStickers.size() > mStickers.size()) {
            for (int i = mStickers.size(); i < mResultStickers.size(); i++) {
                mResultStickers.remove(i);
            }
        }
        mStickers.add(stickerView);
        delPosition = mStickers.size() - 1;
        mResultStickers.add(stickerView);
        setCurrentEdit(stickerView);
        isdel = false;
        mDelStickers.clear();
    }

    /**
     * @param stickerView
     * @modificationHistory====设置当前处于编辑模式的贴纸
     * @modify by reason: 原因
     */

    private void setCurrentEdit(StickerView stickerView) {
        if (mCurrentView != null) {
            mCurrentView.setInEdit(false);
        }
        mCurrentView = stickerView;
        stickerView.setInEdit(true);
    }

    /**
     * @author 常瑞 2016-5-28 下午6:05:11
     * @modificationHistory=====取消标签的编辑事件
     * @modify by user: 常瑞 2016-5-28
     * @modify by reason: 原因
     */
    public void cancelTzFocus() {
        if (mCurrentView != null) {
            mCurrentView.setInEdit(false);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                cancelTzFocus();
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 款式弹出
     */
    private void startTypeAnim1() {
        if (mRightView.getVisibility() == View.VISIBLE) return;
        mRightViewPic.setVisibility(View.INVISIBLE);
        mRightView.setVisibility(View.VISIBLE);
        mRightViewPicText.setVisibility(View.INVISIBLE);
        TranslateAnimation animation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animation1.setDuration(500);
        mRightView.startAnimation(animation1);
    }

    private List<HotBeen> data2 = new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();
        contentRootRlyt.setBackgroundResource(R.drawable.dotted_bg);
        mPushPic2.setBackgroundResource(R.drawable.dotted_bg);
        mPushPic3.setBackgroundResource(R.drawable.dotted_bg);
    }

    @Override
    protected void initData() {
//        mCircleMenuLayout.startAnim();
//        mCircleMenuLayout.setClick(!mCircleMenuLayout.isClick());

        //默认选中第一个
        mFaceIv.setSelected(true);
        mBackIv.setSelected(false);
        mSideIv.setSelected(false);
        mFaceTv.setSelected(true);
        mBackTv.setSelected(false);
        mSideTv.setSelected(false);

        //将字体文件保存在assets/fonts/目录下，创建Typeface对象
        mFromAsset = Typeface.createFromAsset(getAssets(), "fonnts/fangsong.ttf");
        Typeface typeface = mFromAsset;
        for (int i = 0; i < textTitles.length; i++) {
            StoreBeen been = new StoreBeen();
            been.setText(textTitles[i]);
            been.setTypeface(i == 1 ? typeface : Typeface.DEFAULT);
            been.setShow(i == 0);
            mBeens.add(been);
        }
        mTextStyleAdapter = new TextStyleAdapter(this);
        mTextStyleAdapter.setData(mBeens);
        mTextStyleTopView.setAdapter(mTextStyleAdapter);

        /**
         * 款式
         */
        mCustomizedAdapter = new CustomizedAdapter(this);
        data2 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            HotBeen hotBeen = new HotBeen();
            hotBeen.setShow1(false);
            hotBeen.setShow2(false);
            hotBeen.setStyle("款式");
            hotBeen.setSize("尺码");
            hotBeen.setShow(false);
            data2.add(hotBeen);
        }
        mCustomizedAdapter.setData(data2);
        mGridView.setAdapter(mCustomizedAdapter);

        if (onClicEditPosition != -1) {
            ((StickerView) mStickers.get(onClicEditPosition))
                    .resetText(mColorBar.getCurrentColor());//
        }


        mStickers = new ArrayList<View>();
        mResultStickers = new ArrayList<>();
        mDelStickers = new ArrayList<View>();


        //默认选择成人
        mAdult.setSelected(false);
        mChild.setSelected(false);

        mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);

        Intent intent = getIntent();
        if (intent != null) {
            byte[] bis = intent.getByteArrayExtra("bitmap");
            if (bis != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);
//                bitmap = com.twentyfourhours.tuchuang.common.util.Utils.zoomImage(bitmap, 200, 200);
//                mCircleMenuLayout.startAnim();
//                mCircleMenuLayout.setClick(!mCircleMenuLayout.isClick());
                addStickerView(bitmap, false);
            }
        }

//        mImage1.setBgBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.sweater));
////        mImage1.setBitmap(0,BitmapFactory.decodeResource(getResources(),R.mipmap.img_customized),false);
////        mImage1.setBackgroundResource(R.mipmap.img_customized);
//        mImage2.setBgBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.sweater));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customized;
    }

    @OnClick({R.id.shop_face, R.id.shop_back, R.id.shop_side, R.id.expend, R.id.back, R.id.shure, R.id.adult, R.id.child, R.id.photograph, R.id.album, R.id.gallery, R.id.style_text, R.id.font, R.id.text_b, R.id.text_i, R.id.text_u})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.shure:
                showShureDialog();
                break;
            case R.id.adult:
                mAdult.setSelected(true);
                mChild.setSelected(false);
                showAdultStyle();
                break;
            case R.id.child:
                mAdult.setSelected(false);
                mChild.setSelected(true);
                showAdultStyle();
                break;
            case R.id.photograph://拍照
                mPhotograph.setSelected(true);
                mAlbum.setSelected(false);
                mGallery.setSelected(false);
                xiangjiClick();
                break;
            case R.id.album://相册
                mPhotograph.setSelected(false);
                mAlbum.setSelected(true);
                mGallery.setSelected(false);
                openAlbum();
                break;
            case R.id.gallery://图库
                mPhotograph.setSelected(false);
                mAlbum.setSelected(false);
                mGallery.setSelected(true);
                showPopWindow();
                break;
            case R.id.style_text:
                mFont.setSelected(false);
                mStyleText.setSelected(true);
                mTextStyleTopView.setVisibility(View.INVISIBLE);
                showTextStyleAnim();
                break;
            case R.id.font://字体
                mFont.setSelected(true);
                mStyleText.setSelected(false);
                mTextStyleTopView2.setVisibility(View.INVISIBLE);
                showTextSetting();
                break;
            case R.id.text_b://字体加粗
                mTextB.setSelected(true);
                mTextI.setSelected(false);
                mTextU.setSelected(false);
                if (onClicEditPosition != -1) {
                    ((StickerView) mStickers.get(onClicEditPosition))
                            .resetTextStyle(0);
                }
                break;
            case R.id.text_i://字体斜体
                mTextB.setSelected(false);
                mTextI.setSelected(true);
                mTextU.setSelected(false);
                if (onClicEditPosition != -1) {
                    ((StickerView) mStickers.get(onClicEditPosition))
                            .resetTextStyle(1);
                }
                break;
            case R.id.text_u://字体下划线
                mTextB.setSelected(false);
                mTextI.setSelected(false);
                mTextU.setSelected(true);
                if (onClicEditPosition != -1) {
                    ((StickerView) mStickers.get(onClicEditPosition))
                            .resetTextStyle(2);
                }
                break;
            case R.id.expend:
                mCircleMenuLayout.setVisibility(View.VISIBLE);
                mCircleMenuLayout.startAnim2();
                mCircleMenuLayout.setClick(!mCircleMenuLayout.isClick());
                mExpend.setVisibility(View.INVISIBLE);
                break;
            case R.id.shop_face:
                shopFace();
                break;
            case R.id.shop_back:
                shopBack();
                break;
            case R.id.shop_side:
                shopSide();
                break;
        }
    }

    private void shopSide() {
        select = 2;
        mCompleteIv2.setVisibility(mPushPic2.getChildCount() != 0 ? View.VISIBLE : View.GONE);
        mCompleteIv.setVisibility(contentRootRlyt.getChildCount() != 0 ? View.VISIBLE : View.GONE);
        mFaceIv.setSelected(false);
        mBackIv.setSelected(false);
        mSideIv.setSelected(true);
        mFaceTv.setSelected(false);
        mBackTv.setSelected(false);
        mSideTv.setSelected(true);
        mPushPicContainer.setVisibility(View.GONE);
        mPushPicContainer2.setVisibility(View.GONE);
        mPushPicContainer3.setVisibility(View.VISIBLE);
        mImage1.setVisibility(View.GONE);
        mImage2.setVisibility(View.GONE);
        mImage3.setVisibility(View.VISIBLE);
    }

    private void shopBack() {
        select = 1;
        mCompleteIv.setVisibility(contentRootRlyt.getChildCount() != 0 ? View.VISIBLE : View.GONE);
        mCompleteIv3.setVisibility(mPushPic3.getChildCount() != 0 ? View.VISIBLE : View.GONE);
        mFaceIv.setSelected(false);
        mBackIv.setSelected(true);
        mSideIv.setSelected(false);
        mFaceTv.setSelected(false);
        mBackTv.setSelected(true);
        mSideTv.setSelected(false);
        mPushPicContainer.setVisibility(View.GONE);
        mPushPicContainer2.setVisibility(View.VISIBLE);
        mPushPicContainer3.setVisibility(View.GONE);
        mImage1.setVisibility(View.GONE);
        mImage2.setVisibility(View.VISIBLE);
        mImage3.setVisibility(View.GONE);
    }

    private void shopFace() {
        select = 0;
        mCompleteIv2.setVisibility(mPushPic2.getChildCount() != 0 ? View.VISIBLE : View.GONE);
        mCompleteIv3.setVisibility(mPushPic3.getChildCount() != 0 ? View.VISIBLE : View.GONE);
        mFaceIv.setSelected(true);
        mBackIv.setSelected(false);
        mSideIv.setSelected(false);
        mFaceTv.setSelected(true);
        mBackTv.setSelected(false);
        mSideTv.setSelected(false);

        mPushPicContainer.setVisibility(View.VISIBLE);
        mPushPicContainer2.setVisibility(View.GONE);
        mPushPicContainer3.setVisibility(View.GONE);
        mImage1.setVisibility(View.VISIBLE);
        mImage2.setVisibility(View.GONE);
        mImage3.setVisibility(View.GONE);
    }

    private void showShureDialog() {
        String message="";
        final int faceCount = contentRootRlyt.getChildCount();
        final int backCount = mPushPic2.getChildCount();
        final int sideCount = mPushPic3.getChildCount();
        if (faceCount==0){
            message="您的正面还未编辑，是否继续？";
        }else if (backCount==0){
            message="您的背面还未编辑，是否继续？";
        }else if (sideCount==0){
            message="您的侧面还未编辑，是否继续？";
        }else {
            shure();
            return;
        }

        View dialogView = LayoutInflater.from(this).inflate(R.layout.item_customized_pre_dialog, null);
        Button cancel = (Button) dialogView.findViewById(R.id.cancel);
        Button next = (Button) dialogView.findViewById(R.id.next);
        TextView title = (TextView) dialogView.findViewById(R.id.title);
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (faceCount==0){
                    shopFace();
                }else if (backCount==0){
                    shopBack();
                }else{
                    shopSide();
                }
                dialog.dismiss();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shure();
                dialog.dismiss();
            }
        });
        title.setText(message);
        dialog.setCanceledOnTouchOutside(false);

    }

    private int select = 0;

    private void shure() {
        cancelTzFocus();
        contentRootRlyt.setBackgroundColor(Color.alpha(0));
        mPushPic2.setBackgroundColor(Color.alpha(0));
        mPushPic3.setBackgroundColor(Color.alpha(0));
        Bitmap mBackgroundBitmap = Bitmap
                .createBitmap(mImage1.getWidth(),
                        mImage1.getHeight(),
                        Bitmap.Config.RGB_565);//ARGB_8888RGB_565
        Bitmap bitmap = Utils.getBitmap(CustomizedActivity.this,
                mBackgroundBitmap, mImage1,
                mPushPicContainer);
        Bitmap bitmap2 = Utils.getBitmap(CustomizedActivity.this,
                mBackgroundBitmap, mImage2,
                mPushPicContainer2);
        Bitmap bitmap3 = Utils.getBitmap(CustomizedActivity.this,
                mBackgroundBitmap, mImage3,
                mPushPicContainer3);
//                File file = Utils.saveBitmap(Utils.getBitmap(CustomizedActivity.this,
//                        mBackgroundBitmap, mImage1,
//                        contentRootRlyt), "DCIM");
        bitmap = Utils.zoomImage(bitmap, DensityUtil.dip2px(this, 80), DensityUtil.dip2px(this, 128));
        bitmap2 = Utils.zoomImage(bitmap2, DensityUtil.dip2px(this, 80), DensityUtil.dip2px(this, 128));
        bitmap3 = Utils.zoomImage(bitmap3, DensityUtil.dip2px(this, 80), DensityUtil.dip2px(this, 128));
        if (bitmap != null) {
//                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            Intent intent = new Intent(CustomizedActivity.this, PreviewShopActivity.class);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
            bitmap2.compress(Bitmap.CompressFormat.PNG, 100, baos2);
            ByteArrayOutputStream baos3 = new ByteArrayOutputStream();
            bitmap3.compress(Bitmap.CompressFormat.PNG, 100, baos3);
            byte[] bitmapByte = baos.toByteArray();
            byte[] bitmapByte2 = baos2.toByteArray();
            byte[] bitmapByte3 = baos3.toByteArray();
            intent.putExtra("bitmap", bitmapByte);
            intent.putExtra("bitmap2", bitmapByte2);
            intent.putExtra("bitmap3", bitmapByte3);
            try {
                baos.close();
                baos2.close();
                baos3.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            startActivity(intent);
        }
    }

    /**
     * 弹出成人款式定制
     */
    private void showAdultStyle() {
//        if (mGridView.getVisibility() == View.VISIBLE) return;
        mGridView.setVisibility(View.VISIBLE);
        TranslateAnimation animation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 0);
        animation1.setDuration(500);
        mGridView.startAnimation(animation1);
    }

    /**
     * 弹出字体样式
     */
    private void showTextStyleAnim() {
        if (mTextStyleTopView2.getVisibility() == View.VISIBLE) return;
        mTextStyleTopView2.setVisibility(View.VISIBLE);
        TranslateAnimation animation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 0);
        animation1.setDuration(500);
        mTextStyleTopView2.startAnimation(animation1);
    }

    /**
     * 弹出字体设置
     */
    private void showTextSetting() {
        if (mTextStyleTopView.getVisibility() == View.VISIBLE) return;
        mTextStyleTopView.setVisibility(View.VISIBLE);
        TranslateAnimation animation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 0);
        animation1.setDuration(500);
        mTextStyleTopView.startAnimation(animation1);
    }

    /**
     * 退出字体设置
     */
    private void hideTextSetting() {
        if (mTextStyleTopView.getVisibility() == View.INVISIBLE) return;
        TranslateAnimation animation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1);
        animation1.setDuration(500);
        mTextStyleTopView.startAnimation(animation1);
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mTextStyleTopView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private int count;
    private TextView free;
    private TextView pay;

    /**
     * 弹出选择窗
     */
    private void showPopWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.item_gallery_select, null);
        free = (TextView) view.findViewById(R.id.tv_free);
        pay = (TextView) view.findViewById(R.id.tv_pay);
        if (count != 0) {
            if (isFree) {
                free.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                pay.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            } else {
                free.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                pay.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }
        }
        mPopupWindow1 = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow1.showAsDropDown(mAlbum, DensityUtil.dip2px(this, -150), 0);
        mPopupWindow1.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mPopupWindow1.setOutsideTouchable(true);

        free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (free.getTypeface() == Typeface.defaultFromStyle(Typeface.BOLD)) {
                    mPopupWindow1.dismiss();
                    return;
                }
                free.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                pay.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                isFree = !isFree;
                showFreeScrollview();//顶部弹出
                count++;
                mPopupWindow1.dismiss();
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pay.getTypeface() == Typeface.defaultFromStyle(Typeface.BOLD)) {
                    mPopupWindow1.dismiss();
                    return;
                }
                free.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                pay.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                isFree = !isFree;
                showPayScrollview();//顶部弹出
                count++;
                mPopupWindow1.dismiss();
            }
        });
    }

//    private Resources res = CustomizedActivity.this.getResources();

    private void showPayScrollview() {
        mContainer.removeAllViews();
//        if (mScrollView.getVisibility()==View.VISIBLE)return;
        mScrollView.setVisibility(View.VISIBLE);
        for (int i = 0; i < 20; i++) {
            GallaryView view = new GallaryView(this);
            ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_text_view);
            imageView.setSelected(false);
            linearLayout.setVisibility(View.INVISIBLE);
            mContainer.addView(view);
        }

        for (int i = 0; i < mContainer.getChildCount(); i++) {
            final int finalI = i;
            mContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < mContainer.getChildCount(); j++) {
                        if (finalI == j) {
                            mContainer.getChildAt(j).findViewById(R.id.image_view).setSelected(true);
                            mContainer.getChildAt(j).findViewById(R.id.ll_text_view).setVisibility(View.VISIBLE);
                            ImageView selectView = (ImageView) mContainer.getChildAt(j).findViewById(R.id.image_view);
//                            Bitmap bitmap = BitmapFactory.decodeResource(CustomizedActivity.this.getResources(), R.drawable.ic_icon_item_gallaly);
//                            BitmapDrawable bd = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_icon_item_gallaly);
//                            Bitmap bm= bd.getBitmap();
                            selectView.setDrawingCacheEnabled(true);
//                            Bitmap bitmap = com.twentyfourhours.tuchuang.common.util.Utils.zoomImage(drawableToBitmap(selectView.getDrawable()), 200, 200);
                            addStickerView(drawableToBitmap(selectView.getDrawable()), false);
                            selectView.setDrawingCacheEnabled(false);
//                            Bitmap bitmap = mBitmap;
//                            addStickerView(bitmap, false);//com.twentyfourhours.tuchuang.common.util.Utils.zoomImage(drawableToBitmap(selectView.getDrawable()), 200, 200)
                        } else {
                            mContainer.getChildAt(j).findViewById(R.id.image_view).setSelected(false);
                            mContainer.getChildAt(j).findViewById(R.id.ll_text_view).setVisibility(View.INVISIBLE);
                        }
                    }
                }
            });
        }
        TranslateAnimation animation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 0);
        animation1.setDuration(500);
        mScrollView.startAnimation(animation1);
    }

    /**
     * 显示顶部
     */
    private void showFreeScrollview() {
//        if (mScrollView.getVisibility()==View.VISIBLE)return;
        mContainer.removeAllViews();
        mScrollView.setVisibility(View.VISIBLE);
        for (int i = 0; i < 20; i++) {
            GallaryView view = new GallaryView(this);
            ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_text_view);
            imageView.setSelected(false);
            linearLayout.setVisibility(View.INVISIBLE);
            mContainer.addView(view);
        }
        for (int i = 0; i < mContainer.getChildCount(); i++) {
            final int finalI = i;
            mContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < mContainer.getChildCount(); j++) {
                        if (finalI == j) {
                            ImageView selectView = (ImageView) mContainer.getChildAt(j).findViewById(R.id.image_view);
                            selectView.setSelected(true);
                            selectView.setDrawingCacheEnabled(true);
//                            Bitmap bitmap = com.twentyfourhours.tuchuang.common.util.Utils.zoomImage(drawableToBitmap(selectView.getDrawable()), 200, 200);
                            addStickerView(drawableToBitmap(selectView.getDrawable()), false);
                            selectView.setDrawingCacheEnabled(false);
                        } else {
                            mContainer.getChildAt(j).findViewById(R.id.image_view).setSelected(false);
                        }
                    }
                }
            });
        }
        TranslateAnimation animation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 0);
        animation1.setDuration(500);
        mScrollView.startAnimation(animation1);
    }

    public Bitmap drawableToBitmap(Drawable drawable) {

        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        System.out.println("Drawable转Bitmap");
        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        //注意，下面三行代码要用到，否则在View或者SurfaceView里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);

        return bitmap;
    }

    private boolean isFree;


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == SET_WZ_BITMAP) {
            if (data != null) {
                mEditContent = data.getStringExtra("editContent");
//                    String editContent = mEditContent;
                if (onClicEditPosition != -1) {
                    if (position != -1 && position == 1) {
                        ((StickerView) mStickers.get(onClicEditPosition))
                                .resetText(mFromAsset);
                    } else {
                        ((StickerView) mStickers.get(onClicEditPosition))
                                .resetText(Typeface.DEFAULT);
                    }
                    ((StickerView) mStickers.get(onClicEditPosition))
                            .resetText(data.getStringExtra("editContent"),
                                    TextView.BufferType.NORMAL);
                }
            }
        }

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                //打开相册后返回
                case SELECT_PHOTO:
                    Bitmap bitmap = null;
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                        bitmap = ImgUtil.handleImageOnKitKat(this, data);
                    } else {
                        bitmap = ImgUtil.handleImageBeforeKitKat(this, data);
                    }
//                    addStickerView(com.twentyfourhours.tuchuang.common.util.Utils.zoomImage(bitmap, 200, 200), false);
                    addStickerView(bitmap, false);
                    break;
                //打开相机后返回
                case CHOOSE_PHOTO:
                    /**
                     * 这种方法是通过内存卡的路径进行读取图片，所以的到的图片是拍摄的原图
                     */
                    displayImage(outputImagepath.getAbsolutePath());
                    break;

            }

        }
    }

    /**
     * 打开相机
     *
     * @param
     */
    public void xiangjiClick() {
        //checkSelfPermission 检测有没有 权限
//        PackageManager.PERMISSION_GRANTED 有权限
//        PackageManager.PERMISSION_DENIED  拒绝权限
        //一定要先判断权限,再打开相机,否则会报错
        if (Build.VERSION.SDK_INT >= 23 && (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
            //权限发生了改变 true  //  false,没有权限时
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                new AlertDialog.Builder(this).setTitle("title")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 请求授权
                                if (Build.VERSION.SDK_INT >= 23) {
                                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
                                }
                            }
                        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //不请求权限的操作
                    }
                }).create().show();
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
            }
        } else {
            take_photo();//已经授权了就调用打开相机的方法
        }
    }

    /**
     * 拍照获取图片
     **/
    public void take_photo() {
        //获取系統版本
        int currentapiVersion = Build.VERSION.SDK_INT;
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                    "yyyy_MM_dd_HH_mm_ss");
            String filename = timeStampFormat.format(new Date());
            outputImagepath = new File(Environment.getExternalStorageDirectory(),
                    filename + ".jpg");
            if (currentapiVersion < 24) {
                // 从文件中创建uri
                Uri uri = Uri.fromFile(outputImagepath);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            } else {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, outputImagepath.getAbsolutePath());
                Uri uri = getApplication().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            }
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    /**
     * 拍完照和从相册获取玩图片都要执行的方法(根据图片路径显示图片)
     */
    private void displayImage(String imagePath) {
        if (!TextUtils.isEmpty(imagePath)) {
            //orc_bitmap = BitmapFactory.decodeFile(imagePath);//获取图片
            orc_bitmap = comp(BitmapFactory.decodeFile(imagePath)); //压缩图片
            ImgUpdateDirection(imagePath);//显示图片,并且判断图片显示的方向,如果不正就放正
        } else {
            Toast.makeText(this, "图片获取失败", Toast.LENGTH_LONG).show();
        }
    }

    //改变拍完照后图片方向不正的问题
    private void ImgUpdateDirection(String filepath) {
        int digree = 0;//图片旋转的角度
        //根据图片的URI获取图片的绝对路径
        //String filepath = ImgUriDoString.getRealFilePath(getApplicationContext(), uri);
        //根据图片的filepath获取到一个ExifInterface的对象
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
            if (exif != null) {

                // 读取图片中相机方向信息
                int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

                // 计算旋转角度
                switch (ori) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        digree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        digree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        digree = 270;
                        break;
                    default:
                        digree = 0;
                        break;

                }

            }
            //如果图片不为0
            if (digree != 0) {
                // 旋转图片
                Matrix m = new Matrix();
                m.postRotate(digree);
                orc_bitmap = Bitmap.createBitmap(orc_bitmap, 0, 0, orc_bitmap.getWidth(),
                        orc_bitmap.getHeight(), m, true);
            }
            if (orc_bitmap != null) {
//                addStickerView(com.twentyfourhours.tuchuang.common.util.Utils.zoomImage(orc_bitmap, 200, 200), false);
                addStickerView(orc_bitmap, false);
            }
        } catch (IOException e) {
            e.printStackTrace();
            exif = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (orc_bitmap != null) {
            orc_bitmap.recycle();
        } else {
            orc_bitmap = null;
        }
    }

    /*
    * 判断sdcard是否被挂载
    */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 打开相册的方法
     */
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                //判断是否有权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    take_photo();//打开相机
                } else {
                    Toast.makeText(this, "你需要许可", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    private static final int CHOOSE_PHOTO = 0;//启动相机标识
    private File outputImagepath;//存储拍完照后的图片
    private Bitmap orc_bitmap;//拍照和相册获取图片的Bitmap

}
