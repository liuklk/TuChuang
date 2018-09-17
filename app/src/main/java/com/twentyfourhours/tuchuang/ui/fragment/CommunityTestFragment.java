package com.twentyfourhours.tuchuang.ui.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.StoreFragmentPageAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseFragment;
import com.twentyfourhours.tuchuang.ui.activity.CommentActivity;
import com.twentyfourhours.tuchuang.ui.activity.FindFriendActivity;
import com.twentyfourhours.tuchuang.ui.activity.MainActivity;
import com.twentyfourhours.tuchuang.ui.activity.MessageCenterActivity;
import com.twentyfourhours.tuchuang.ui.activity.SearchActivity;
import com.twentyfourhours.tuchuang.ui.activity.WriteHeartTextActivity;
import com.twentyfourhours.tuchuang.common.util.DensityUtil;
import com.twentyfourhours.tuchuang.widget.ObservableCoordinatorLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/2/7.
 */
public class CommunityTestFragment extends BaseFragment {

    @BindView(R.id.search)
    RelativeLayout mSearch;
    @BindView(R.id.write_heart)
    TextView mWriteHeart;
    @BindView(R.id.qa)
    TextView mQa;
    @BindView(R.id.find_friend)
    TextView mFindFriend;
    @BindView(R.id.my_home)
    TextView mMyHome;
    @BindView(R.id.write_heart_view)
    ImageView mWriteHeartView;
    @BindView(R.id.search_view)
    TextView mSearchView;
    @BindView(R.id.msg_view)
    ImageView mMsgView;
    @BindView(R.id.top_view)
    LinearLayout mTopView;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.vp)
    ViewPager mVp;
    Unbinder unbinder;
    @BindView(R.id.community_layout)
    RelativeLayout mCommunityLayout;
    @BindView(R.id.community_bg_view)
    ImageView mCommunityBgView;
    @BindView(R.id.container_coor)
    ObservableCoordinatorLayout mContainerCoor;
    @BindView(R.id.user_head)
    ImageView mUserHead;
    //    @BindView(R.id.refreshLayout)
//    SwipeRefreshLayout mRefreshLayout;
    //    @BindView(R.id.toolbar)
//    Toolbar mToolbar;
    private StoreFragmentPageAdapter mFragmentPageAdapter;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private String[] types = {"推荐", "关注", "猜你喜欢", "手机壳", "T恤", "水杯", "玩具", "配件"};
    private FragmentManager mChildFragmentManager;
    private Bitmap bitmap;
    private ActionBar actionBar;
    private int mPreHeight;
    private int mPreTopHeight;
    private Drawable mDrawingCache;

    @Override
    protected View initView() {
        return View.inflate(mContext, R.layout.fragment_community_, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        mTopView.setBackground(new BitmapDrawable(rsBlur(25)));
    }

    @Override
    protected void initListener() {
        ViewTreeObserver vto2 = mTopView.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTopView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mPreTopHeight = mTopView.getHeight();
            }
        });
    }

    @Override
    protected void initData() {

        mTopView.setAlpha(0);

        Bitmap whiteBgBitmap = Bitmap.createBitmap(
                getResources().getDisplayMetrics().widthPixels, DensityUtil.dip2px(mContext, 288),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(whiteBgBitmap);
        canvas.drawColor(getResources().getColor(R.color.white));
        mCommunityLayout.draw(canvas);

        mCollapsingToolbarLayout.setContentScrim(new BitmapDrawable(whiteBgBitmap));
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (Math.abs(verticalOffset) < mCommunityLayout.getHeight() - mPreTopHeight) {
                    mTopView.setAlpha((float) Math.abs(verticalOffset) / (float) (mCommunityLayout.getHeight() - mPreTopHeight));
//                    rsBlur((int) ((float) Math.abs(verticalOffset) / (float) (mCommunityLayout.getHeight() - mPreTopHeight)*15)+1);
                } else {
                    mTopView.setAlpha(1);
                }

            }
        });

        initFragment();
        mChildFragmentManager = getChildFragmentManager();
        if (mFragmentPageAdapter == null) {
            mFragmentPageAdapter = new StoreFragmentPageAdapter(mChildFragmentManager, mFragmentList);
        }
        mFragmentPageAdapter.setTitles(types);
        mVp.setAdapter(mFragmentPageAdapter);
        mTabs.setupWithViewPager(mVp);

//        mCommunityBgView.setDrawingCacheEnabled(true);
//        mDrawingCache = mCommunityBgView.getDrawable();
        mTopView.setBackground(new BitmapDrawable(rsBlur(25)));
//        mCommunityBgView.setDrawingCacheEnabled(false);
    }

    private Bitmap rsBlur(int radius) {
        RenderScript renderScript = RenderScript.create(mContext);
        final Allocation input = Allocation.createFromBitmap(renderScript, ((BitmapDrawable) getResources().getDrawable(R.drawable.bj_community2)).getBitmap());
        final Allocation output = Allocation.createTyped(renderScript, input.getType());
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setInput(input);
        scriptIntrinsicBlur.setRadius(radius);
        scriptIntrinsicBlur.forEach(output);
        output.copyTo(((BitmapDrawable) getResources().getDrawable(R.drawable.bj_community2)).getBitmap());
        renderScript.destroy();
        return ((BitmapDrawable) getResources().getDrawable(R.drawable.bj_community2)).getBitmap();

    }

    @OnClick({R.id.search, R.id.write_heart, R.id.qa, R.id.find_friend, R.id.my_home, R.id.msg_view, R.id.write_heart_view, R.id.search_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search:
                goTo(SearchActivity.class, false);
                break;
            case R.id.write_heart:
//                goTo(WriteHeartActivity.class, false);
                goTo(WriteHeartTextActivity.class, false);
                break;
            case R.id.qa:
                goTo(CommentActivity.class, false);
                break;
            case R.id.find_friend:
                goTo(FindFriendActivity.class,false);
                break;
            case R.id.my_home:
                ((MainActivity) getActivity()).initFragment1();
                break;
            case R.id.msg_view:
                goTo(MessageCenterActivity.class, false);
                break;
            case R.id.write_heart_view:
//                goTo(WriteHeartActivity.class, false);
                goTo(WriteHeartTextActivity.class, false);
                break;
            case R.id.search_view:
                goTo(SearchActivity.class, false);
                break;
        }
    }

    private void initFragment() {
        mFragmentList.clear();
        mFragmentList.add(new CommunityGroomFragment());
        mFragmentList.add(new CommunityGroomFragment());
        mFragmentList.add(new CommunityGroomFragment());
        mFragmentList.add(new CommunityGroomFragment());
        mFragmentList.add(new CommunityGroomFragment());
        mFragmentList.add(new CommunityGroomFragment());
        mFragmentList.add(new CommunityGroomFragment());
        mFragmentList.add(new CommunityGroomFragment());
    }

}
