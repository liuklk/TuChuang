package com.twentyfourhours.tuchuang.ui.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scrat.app.selectorlibrary.ImageSelector;
import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.common.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/2/8.
 */
public class WriteHeartActivity extends BaseActivity {
    @BindView(R.id.gold_back)
    ImageView mGoldBack;
    @BindView(R.id.publish)
    TextView mPublish;
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
    @BindView(R.id.text_number)
    TextView mTextNumber;
    @BindView(R.id.pull)
    ImageView mPull;
    @BindView(R.id.select_classify)
    RelativeLayout mSelectClassify;
    private static final int REQUEST_CODE_SELECT_IMG = 1;
    private static final int MAX_SELECT_COUNT = 4;
    @BindView(R.id.classify)
    TextView mClassify;
    private ImageView[] mImageViews;
    private ListView mListView1;
    private int mLastSelectedPosition = -1;
    private String[] types = {"手机壳", "衣服啊", "背包啊", "鞋子啊"};
    private PopupWindow mPopupWindow1;
    private boolean isPop;
    private int position;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mImageViews = new ImageView[]{mLoadImage1, mLoadImage2, mLoadImage3, mLoadImage4};
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_heart;
    }

    @OnClick({R.id.gold_back, R.id.publish, R.id.upload, R.id.select_classify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.gold_back:
                finish();
                break;
            case R.id.publish:
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
            case R.id.select_classify:
                showPopWindow();
                break;
        }
    }

    public void selectImg() {
        ImageSelector.show(this, REQUEST_CODE_SELECT_IMG, MAX_SELECT_COUNT);
    }

    private void showContent(Intent data) {
        List<String> paths = ImageSelector.getImagePaths(data);
        if (!paths.isEmpty()) {
            for (int i = 0; i < paths.size(); i++) {
                mImageViews[i].setImageBitmap(BitmapFactory.decodeFile(paths.get(i)));
            }
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

    private void showPopWindow() {

        mListView1 = new ListView(this);
        mListView1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            mListView1.setPadding(getResources().getDisplayMetrics().widthPixels * 3 / 50, getResources().getDisplayMetrics().widthPixels  / 50, getResources().getDisplayMetrics().widthPixels * 3 / 50, getResources().getDisplayMetrics().widthPixels * 3 / 50);
        mListView1.setBackground(getResources().getDrawable(R.drawable.customized_style_bg));
        mListView1.setDivider(null);
        MyAdapter adapter = new MyAdapter();

        if (mLastSelectedPosition != -1) {
            adapter.setPosition(mLastSelectedPosition);
        }

        adapter.setData(types);
        mListView1.setAdapter(adapter);
        mPopupWindow1 = new PopupWindow(mListView1, mClassify.getMeasuredWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        ////让pop可以点击外面消失掉
        mPopupWindow1.setBackgroundDrawable(new ColorDrawable(0));
        mPopupWindow1.showAsDropDown(mClassify);
        mPopupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                ObjectAnimator anim = ObjectAnimator.ofFloat(mPull, "rotation", -180f, -0);
                anim.setDuration(150);
                anim.start();
//                    mTabs.setVisibility(View.VISIBLE);
//                    mPindao.setVisibility(View.INVISIBLE);
                isPop = !isPop;
                mPopupWindow1.dismiss();
            }
        });
        mPopupWindow1.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    mPopupWindow1.dismiss();
                    return true;
                }
                return false;
            }
        });
        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //如果没有发生切换，就不去处理

                if (i != mLastSelectedPosition) {
                    //选项的切换
                    //将点击位置的条目变成enable
                    View checkTextView = adapterView.getChildAt(i);
                    TextView viewById = (TextView) checkTextView.findViewById(R.id.text_view);
                    viewById.setTextColor(getResources().getColor(R.color.yellow_dot));
                    if (mLastSelectedPosition != -1) {
                        //将上次选中的条目变成disable
                        View oldView = adapterView.getChildAt(mLastSelectedPosition);
                        TextView textView = (TextView) oldView.findViewById(R.id.text_view);
                        textView.setTextColor(getResources().getColor(R.color.black_bg));

                    }
                    mClassify.setText(types[i]);
                    mClassify.setTextColor(getResources().getColor(R.color.yellow_dot));
                    //更新上次选中的位置
                    mLastSelectedPosition = i;

                }
                mPopupWindow1.dismiss();
            }
        });
        if (isPop) {
            mPopupWindow1.dismiss();
        } else {
            ObjectAnimator anim = ObjectAnimator.ofFloat(mPull, "rotation", 0f, -180f);
            anim.setDuration(150);
            anim.start();

            isPop = !isPop;
        }
    }

    public class MyAdapter extends BaseAdapter {
        private String[] data;
        private int position = -1;

        public void setPosition(int position) {
            this.position = position;
        }

        public void setData(String[] data) {
            this.data = data;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int i) {
            return data[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewStyleHolder holder;
            if (view == null) {
                view = LayoutInflater.from(WriteHeartActivity.this).inflate(R.layout.item_community_list, null);
                holder = new ViewStyleHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewStyleHolder) view.getTag();
            }
            holder.type.setText(data[i]);
            holder.type.setTextColor(i == position ? getResources().getColor(R.color.yellow_dot) : getResources().getColor(R.color.black_bg));
//            holder.type.setTextColor(mContext.getResources().getColor(R.color.black_bg));
            return view;
        }
    }

    static class ViewStyleHolder {
        TextView type;

        ViewStyleHolder(View view) {
            type = (TextView) view.findViewById(R.id.text_view);
        }
    }
}
