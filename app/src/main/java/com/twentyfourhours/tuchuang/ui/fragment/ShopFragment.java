package com.twentyfourhours.tuchuang.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PathMeasure;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.LookMoreAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseFragment;
import com.twentyfourhours.tuchuang.image.ImagePagerActivity;
import com.twentyfourhours.tuchuang.image.MyGridAdapter;
import com.twentyfourhours.tuchuang.image.NoScrollGridView;
import com.twentyfourhours.tuchuang.model.bean.HotBeen;
import com.twentyfourhours.tuchuang.ui.activity.EvaluateShowActivity;
import com.twentyfourhours.tuchuang.ui.activity.FirmOrderActivity;
import com.twentyfourhours.tuchuang.ui.activity.ShoppingDetailActivity;
import com.twentyfourhours.tuchuang.view.CustomDialog;
import com.twentyfourhours.tuchuang.widget.CustomDigitalClock;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/2/5.
 */

public class ShopFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.count_down)
    CustomDigitalClock mCountDown;
    @BindView(R.id.time_last)
    TextView mTimeLast;
    @BindView(R.id.limit_time)
    LinearLayout mLimitTime;
    @BindView(R.id.shop_name)
    TextView mShopName;
    @BindView(R.id.shop_price)
    TextView mShopPrice;
    @BindView(R.id.delete_price)
    TextView mDeletePrice;
    @BindView(R.id.type_select)
    TextView mTypeSelect;

    public TextView getTypeSelect() {
        return mTypeSelect;
    }

    public void setTypeSelect(TextView typeSelect) {
        mTypeSelect = typeSelect;
    }

    @BindView(R.id.promotion)
    TextView mPromotion;
    @BindView(R.id.service)
    TextView mService;
    @BindView(R.id.evaluated_number)
    TextView mEvaluatedNumber;
    @BindView(R.id.evaluated_precent)
    TextView mEvaluatedPrecent;
    @BindView(R.id.evaluated)
    RelativeLayout mEvaluated;
    @BindView(R.id.user_name)
    TextView mUserName;
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
    @BindView(R.id.time)
    TextView mTime;
    @BindView(R.id.style)
    TextView mStyle;
    @BindView(R.id.comment)
    TextView mComment;
    @BindView(R.id.gridView)
    NoScrollGridView mGridView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    PullToRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    private String[] mVals = {"热烈黄", "热烈红", "热烈黑", "热烈蓝", "热烈紫"};
    private String[] mVals2 = {"薄款", "厚款"};
    private String[] mVals3 = {"S", "M", "L", "XL", "XXL", "XXXL"};
    private List<String> mData = new ArrayList<>();
    private List<String> mData2 = new ArrayList<>();
    private List<String> mData3 = new ArrayList<>();
    private CustomDialog mDialog;
    private CustomDialog mShaDialog;
    private boolean isLimitTime;
    private String[] urls = {"http://g.hiphotos.bdimg.com/album/s%3D680%3Bq%3D90/sign=ccd33b46d53f8794d7ff4b26e2207fc9/0d338744ebf81a4c0f993437d62a6059242da6a1.jpg", "http://c.hiphotos.bdimg.com/album/s%3D900%3Bq%3D90/sign=b8658f17f3d3572c62e290dcba28121a/5fdf8db1cb134954bb97309a574e9258d0094a47.jpg", "http://g.hiphotos.bdimg.com/album/s%3D680%3Bq%3D90/sign=ccd33b46d53f8794d7ff4b26e2207fc9/0d338744ebf81a4c0f993437d62a6059242da6a1.jpg", "http://c.hiphotos.bdimg.com/album/s%3D900%3Bq%3D90/sign=b8658f17f3d3572c62e290dcba28121a/5fdf8db1cb134954bb97309a574e9258d0094a47.jpg"};
    private int[] images = {R.drawable.img_similar, R.drawable.img_type, R.drawable.img_similar, R.drawable.img_type, R.drawable.img_similar, R.drawable.img_type};
    private PathMeasure mPathMeasure;
    private float[] mCurrentPosition = new float[2];
    private TextView mShopNumber;
    private TagFlowLayout mFlowlayout;

    public TextView getShopNumber() {
        return mShopNumber;
    }

    public String[] getVals() {
        return mVals;
    }

    public TagFlowLayout getFlowlayout() {
        return mFlowlayout;
    }

    private TagFlowLayout mFlowlayout2;
    private TagFlowLayout mFlowlayout3;

    private void imageBrower(int position, String[] urls) {
        Intent intent = new Intent(mContext, ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        startActivity(intent);
    }

    @Override
    protected View initView() {
        return View.inflate(mContext, R.layout.fragment_shop, null);
    }

    @Override
    protected void initListener() {
        mCountDown.setClockListener(new CustomDigitalClock.ClockListener() {
            @Override
            public void lastTime(String str) {
                mTimeLast.setText(str);
            }

            @Override
            public void remainFiveMinutes() {

            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                imageBrower(position, urls);
            }
        });

        mRefreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadData();
                        //  mProductList.get(0).getId();
                        // 结束刷新
                        mRefreshLayout.finishRefresh();
                    }
                }, 0);
            }

            /*------------------------ 加载更多 ----------------------------------*/
            @Override
            public void loadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        loadMoredata();
                        // 结束加载更多
                        mRefreshLayout.finishLoadMore();
                    }
                }, 10);
            }
        });
    }

    private void loadMoredata() {
        ((ShoppingDetailActivity)mContext).containDetail();
    }

    private void loadData() {

    }

    @Override
    protected void initData() {
        isLimitTime = ((ShoppingDetailActivity)mContext).getIntent().getBooleanExtra("limit_time", false);//限时购
        mLimitTime.setVisibility(isLimitTime ? View.VISIBLE : View.GONE);
        mGridView.setAdapter(new MyGridAdapter(urls, mContext));

        String shopCode = ((ShoppingDetailActivity)mContext).getIntent().getStringExtra("shopCode");

        for (int i = 0; i < mStar.getChildCount(); i++) {
            if (i != mStar.getChildCount() - 1) {
                ImageView select = (ImageView) mStar.getChildAt(i);
                select.setSelected(true);
            }
        }
        mDeletePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        LinearLayoutManager lm = new LinearLayoutManager(mContext);
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(lm);
        LookMoreAdapter adapter = new LookMoreAdapter(mContext);
        List<HotBeen> data = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            HotBeen hotBeen = new HotBeen();
            hotBeen.setShowLimitTime(i == 0 || i == 5);
            hotBeen.setShow1(i == 1 || i == 2 || i == 4);
            hotBeen.setShow2(i == 0 || i == 1 || i == 2);
            hotBeen.setShow3(i == 0 || i == 3);
            data.add(hotBeen);
        }
        adapter.setData(data);
        mRecyclerView.setAdapter(adapter);

        mCountDown.setEndTime(System.currentTimeMillis() + 6 * 60 * 60 * 1000);
    }

    @OnClick({ R.id.type_select, R.id.promotion, R.id.service, R.id.evaluated})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.type_select://规格
                showTypeDialog();
                break;
            case R.id.promotion://促销
                break;
            case R.id.service://服务
                showServiceDialog();
                break;
            case R.id.evaluated://好评
                goTo(EvaluateShowActivity.class, false);
                break;
        }
    }

    public void showTypeDialog() {
        View typeDialog = View.inflate(mContext, R.layout.dialog_type, null);
        final ImageView pic_iv = (ImageView) typeDialog.findViewById(R.id.pic_iv);
        ImageView close = (ImageView) typeDialog.findViewById(R.id.close);
        TextView price = (TextView) typeDialog.findViewById(R.id.price);
        TextView add = (TextView) typeDialog.findViewById(R.id.add_tv);
        TextView limit_time = (TextView) typeDialog.findViewById(R.id.limit_time);
        mShopNumber = (TextView) typeDialog.findViewById(R.id.shop_number_tv);
        TextView minus = (TextView) typeDialog.findViewById(R.id.minus_tv);
        RelativeLayout cart = (RelativeLayout) typeDialog.findViewById(R.id.cart);
        TextView cartNumber = (TextView) typeDialog.findViewById(R.id.cart_number);
        ImageView customerService = (ImageView) typeDialog.findViewById(R.id.customer_service);
        CheckBox collection = (CheckBox) typeDialog.findViewById(R.id.collection);
        TextView addCart = (TextView) typeDialog.findViewById(R.id.add_cart);
        TextView buy = (TextView) typeDialog.findViewById(R.id.buy);
        mFlowlayout = (TagFlowLayout) typeDialog.findViewById(R.id.id_flowlayout);
        mFlowlayout2 = (TagFlowLayout) typeDialog.findViewById(R.id.id_flowlayout2);
        mFlowlayout3 = (TagFlowLayout) typeDialog.findViewById(R.id.id_flowlayout3);

        limit_time.setVisibility(isLimitTime ? View.VISIBLE : View.GONE);

        mDialog = new CustomDialog(mContext);
        mDialog.setContentView(typeDialog);

        close.setOnClickListener(this);
        add.setOnClickListener(this);
        minus.setOnClickListener(this);
        cart.setOnClickListener(this);
        customerService.setOnClickListener(this);
        collection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        addCart.setOnClickListener(this);
        buy.setOnClickListener(this);

        mData.clear();
        for (int i = 0; i < mVals.length; i++) {
            mData.add(mVals[i]);
        }
        mFlowlayout.setAdapter(mTagAdapter);
        mFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
//                toast(mVals[position]);
                pic_iv.setImageResource(images[position]);
                return true;
            }
        });

        mData2.clear();
        for (int i = 0; i < mVals2.length; i++) {
            mData2.add(mVals2[i]);
        }
        mFlowlayout2.setAdapter(mTagAdapter2);
        mFlowlayout2.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
//                toast(mVals[position]);
                pic_iv.setImageResource(images[position]);
                return true;
            }
        });

        mData3.clear();
        for (int i = 0; i < mVals3.length; i++) {
            mData3.add(mVals3[i]);
        }
        mFlowlayout3.setAdapter(mTagAdapter3);
        mFlowlayout3.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
//                toast(mVals[position]);
                pic_iv.setImageResource(images[position]);
                return true;
            }
        });

        mDialog.show();
    }

    private TagAdapter mTagAdapter = new TagAdapter<String>(mData) {
        @Override
        public View getView(FlowLayout parent, int position, String s) {
            TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.tv,
                    mFlowlayout, false);
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

    private TagAdapter mTagAdapter2 = new TagAdapter<String>(mData2) {
        @Override
        public View getView(FlowLayout parent, int position, String s) {
            TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.tv,
                    mFlowlayout2, false);
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

    private TagAdapter mTagAdapter3 = new TagAdapter<String>(mData3) {
        @Override
        public View getView(FlowLayout parent, int position, String s) {
            TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.tv,
                    mFlowlayout3, false);
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

    private void showServiceDialog() {
        View serviceDialog = View.inflate(mContext, R.layout.dialog_service, null);
        CustomDialog dialog = new CustomDialog(mContext);
        dialog.setContentView(serviceDialog);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                mDialog.dismiss();
                break;
            case R.id.buy:
                if ((mFlowlayout.getSelectedList().size() != 0 && mFlowlayout2.getSelectedList().size() != 0) && mFlowlayout3.getSelectedList().size() != 0) {
                    goTo(FirmOrderActivity.class, false);
                    mDialog.dismiss();
                } else {
                    toast("请选择规格属性");
                }
                break;
            case R.id.minus_tv:
                String shopNumber1 = mShopNumber.getText().toString().trim();
                mShopNumber.setText(String.valueOf(Integer.parseInt(shopNumber1) + 1));
                break;
            case R.id.add_cart:
                if (mFlowlayout.getSelectedList().size() != 0) {
                    ((ShoppingDetailActivity)mContext).getSmallGood().setVisibility(View.VISIBLE);
                    ((ShoppingDetailActivity)mContext).executeAllAnimations(((ShoppingDetailActivity)mContext).getSmallGood(), 0);

                    int selects = mFlowlayout.getSelectedList().iterator().next();
                    mTypeSelect.setText(mVals[selects] + "  " + mShopNumber.getText().toString().trim() + "件");
                    int totalNumber = Integer.parseInt(((ShoppingDetailActivity)mContext).getCartNumber().getText().toString().trim()) + Integer.parseInt(mShopNumber.getText().toString().trim());
                    ((ShoppingDetailActivity)mContext).getCartNumber().setText(String.valueOf(totalNumber));
                }
                mDialog.dismiss();
                break;
            case R.id.customer_service:

                break;
            case R.id.add_tv:
                String shopNumber2 = mShopNumber.getText().toString().trim();
                if ("1".equals(shopNumber2)) return;
                mShopNumber.setText(String.valueOf(Integer.parseInt(shopNumber2) - 1));
                break;
            case R.id.cancel:
                mShaDialog.dismiss();
                break;
        }
    }

}
