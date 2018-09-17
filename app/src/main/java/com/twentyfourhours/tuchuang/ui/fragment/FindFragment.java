package com.twentyfourhours.tuchuang.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.leon.loopviewpagerlib.FunBanner;
import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.HotAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseFragment;
import com.twentyfourhours.tuchuang.model.bean.HotBeen;
import com.twentyfourhours.tuchuang.scan.MipcaActivityCapture;
import com.twentyfourhours.tuchuang.ui.activity.CartActivity;
import com.twentyfourhours.tuchuang.ui.activity.LookMoreActivity;
import com.twentyfourhours.tuchuang.ui.activity.SearchActivity;
import com.twentyfourhours.tuchuang.ui.activity.ShoppingDetailActivity;
import com.twentyfourhours.tuchuang.widget.Banner;
import com.twentyfourhours.tuchuang.widget.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/1/4.
 */

public class FindFragment extends BaseFragment {
    @BindView(R.id.content_frame)
    FrameLayout mContentFrame;
    @BindView(R.id.search_view)
    View mSearchView;
    @BindView(R.id.cart)
    ImageView mCart;
    @BindView(R.id.grid_view)
    MyGridView mGridView;
    Unbinder unbinder;
    @BindView(R.id.content_shop)
    FrameLayout mContentShop;
    @BindView(R.id.hot)
    TextView mHot;
    @BindView(R.id.progress_bar)
    ImageView mProgressBar;
    @BindView(R.id.search_btn)
    ImageView mSearchBtn;
    @BindView(R.id.newest)
    TextView mNewest;
    @BindView(R.id.zxing)
    ImageView mZxing;
    @BindView(R.id.search)
    RelativeLayout mSearch;
    @BindView(R.id.cart_number)
    TextView mCartNumber;
    @BindView(R.id.look_more)
    TextView mLookMore;
    @BindView(R.id.scroll_view)
    ScrollView mScrollView;
    @BindView(R.id.refreshLayout)
    PullToRefreshLayout mRefreshLayout;
    private int[] imageResIds = {R.drawable.banner, R.drawable.banner, R.drawable.banner};
    private HotAdapter mHotAdapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private final static int SCANNIN_GREQUEST_CODE = 1;
    private AnimationDrawable mDrawable;

    @Override
    protected View initView() {
        return View.inflate(mContext, R.layout.fragment_find, null);
    }

    @Override
    protected void initListener() {
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
                }, 500);
            }
        });
    }

    private void loadMoredata() {
        goTo(LookMoreActivity.class, false);
    }

    private void loadData() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    String str = data.getStringExtra("result");
                    Intent intent = new Intent(getActivity(), ShoppingDetailActivity.class);
                    intent.putExtra("shopCode", str);
                    startActivity(intent);
                }
                break;
        }
    }

    private void zxing() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), MipcaActivityCapture.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
    }

    @Override
    protected void initData() {
        mGridView.setFocusable(false);

        mDrawable = (AnimationDrawable) mProgressBar.getBackground();

        FunBanner.Builder builder = new FunBanner.Builder(mContext);
        FunBanner funBanner = builder.setEnableAutoLoop(true)
                .setImageResIds(imageResIds)
                .setDotSelectedColor(Color.parseColor("#ffd011"))
                .setHeightWidthRatio(0.528f)
                .setLoopInterval(8000)
                .setEnableAutoLoop(true)
                .build();
        mContentFrame.addView(funBanner);

//        ShoppingFragmentPageAdapter adapter = new ShoppingFragmentPageAdapter(getActivity().getSupportFragmentManager());
//        List<Fragment> data = new ArrayList<>();
//        data.add(new ShoppingFragment1());
//        data.add(new ShoppingFragment2());
//        adapter.setData(data);
//        mVp.setAdapter(adapter);


//        mHotAdapter = new HotAdapter(mContext);
//
//        List<HotBeen> data2 = new ArrayList<>();
//        for (int i = 0; i < 8; i++) {
//            HotBeen hotBeen = new HotBeen();
//            hotBeen.setShow(true);
//            hotBeen.setPic1(R.drawable.face1);
//            hotBeen.setPic2(R.drawable.back1);
//            data2.add(hotBeen);
//        }
//        mHotAdapter.setData(data2);
//        mGridView.setAdapter(mHotAdapter);
//
        Banner banner = new Banner(mContext);
        mContentShop.addView(banner);

    }

    private boolean isChange;


    @OnClick({R.id.cart, R.id.hot, R.id.newest, R.id.look_more, R.id.zxing, R.id.search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cart:
                goTo(CartActivity.class, false);
                break;
            case R.id.hot:
                if (isChange) {
                    List<HotBeen> data1 = new ArrayList<>();
                    for (int i = 0; i < 8; i++) {
                        HotBeen hotBeen = new HotBeen();
                        hotBeen.setShow(true);
                        hotBeen.setPic1(R.drawable.face1);
                        hotBeen.setPic2(R.drawable.back1);
                        data1.add(hotBeen);
                    }
                    changeData(mHot, mNewest, data1);
                    isChange = !isChange;
                }
                break;
            case R.id.newest:
                if (!isChange) {
                    List<HotBeen> data2 = new ArrayList<>();
                    for (int i = 0; i < 8; i++) {
                        HotBeen hotBeen = new HotBeen();
                        hotBeen.setShow(true);
                        hotBeen.setPic1(R.drawable.canvans2);
                        hotBeen.setPic2(R.drawable.canvans_bag);
                        data2.add(hotBeen);
                    }
                    changeData(mNewest, mHot, data2);
                    isChange = !isChange;
                }
                break;
            case R.id.look_more:
                goTo(LookMoreActivity.class, false);
                break;
            case R.id.zxing:
                zxing();
                break;
            case R.id.search:
                goTo(SearchActivity.class, false);
                break;
        }
    }

    private void changeData(TextView newest, TextView hot, final List<HotBeen> data2) {
        newest.setTextColor(getResources().getColor(R.color.textcolor1));
        hot.setTextColor(getResources().getColor(R.color.textcolor3));
        mProgressBar.setVisibility(View.VISIBLE);
        mDrawable.start();
        mGridView.setVisibility(View.GONE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1500);
                mHandler.post(new Runnable() {//在主线程
                    @Override
                    public void run() {
                        mGridView.setVisibility(View.VISIBLE);
                        mDrawable.stop();
                        mProgressBar.setVisibility(View.GONE);
                        mHotAdapter.setData(data2);
                        mHotAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}
