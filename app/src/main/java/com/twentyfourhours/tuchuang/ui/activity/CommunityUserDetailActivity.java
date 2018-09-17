package com.twentyfourhours.tuchuang.ui.activity;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.Comment_Adapter;
import com.twentyfourhours.tuchuang.adapter.MasonryAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.model.bean.Product;
import com.twentyfourhours.tuchuang.model.bean.StoreBeen;
import com.twentyfourhours.tuchuang.common.util.DensityUtil;
import com.twentyfourhours.tuchuang.view.CustomDialog;
import com.twentyfourhours.tuchuang.view.MatchGoodDialog;
import com.twentyfourhours.tuchuang.view.SpacesItemDecoration;
import com.twentyfourhours.tuchuang.widget.MyRecyclerView;
import com.twentyfourhours.tuchuang.widget.ObservableScrollview;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/2/27.
 */
public class CommunityUserDetailActivity extends BaseActivity {
    @BindView(R.id.image_head)
    ImageView mImageHead;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.follow)
    TextView mFollow;
    @BindView(R.id.my_recycler_view)
    MyRecyclerView mMyRecyclerView;
    @BindView(R.id.scroll_view)
    ObservableScrollview mScrollView;
    @BindView(R.id.top_blank_view)
    View mTopBlankView;
    @BindView(R.id.follow_iv)
    TextView mFollowIv;
    @BindView(R.id.title_view)
    RelativeLayout mTitleView;
    @BindView(R.id.top_view)
    RelativeLayout mTopView;
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.share)
    ImageView mShare;
    @BindView(R.id.community_ll_view)
    LinearLayout mCommunityLlView;
    @BindView(R.id.head_view)
    RelativeLayout mHeadView;
    private PopupWindow mPopupWindow1;


    private Comment_Adapter mAdapter;
    private ArrayList<StoreBeen> mStoreBeens = new ArrayList<>();
    private List<Product> productList = new ArrayList<>();
    private MatchGoodDialog mMachDialog;
    private CustomDialog mShaDialog;
    private int mPreHeight;
    private int mPreTopHeight;

    @Override
    protected void initListener() {
        ViewTreeObserver vto = mHeadView.getViewTreeObserver();
        ViewTreeObserver vto2 = mTopView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mHeadView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mPreHeight = mHeadView.getHeight();
            }
        });
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTopView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mPreTopHeight = mTopView.getHeight();
            }
        });

        mScrollView.setOnScollChangedListener(new ObservableScrollview.OnScollChangedListener() {
            @Override
            public void onScrollChanged(ObservableScrollview scrollView, int x, int y, int oldx, int oldy) {
                if (y <= mPreHeight - mPreTopHeight) {
                    mTopView.setAlpha((float) y / (float) (mPreHeight - mPreTopHeight));
                    mFollowIv.setAlpha(0);
                } else {
                    mTopView.setAlpha(1);
                    if (y >= mPreHeight - mPreTopHeight+DensityUtil.dip2px(CommunityUserDetailActivity.this,20)&&y<mPreHeight - mPreTopHeight+DensityUtil.dip2px(CommunityUserDetailActivity.this,50)) {
                        mFollowIv.setAlpha((float)(y-(mPreHeight - mPreTopHeight+DensityUtil.dip2px(CommunityUserDetailActivity.this,20))) / (float) DensityUtil.dip2px(CommunityUserDetailActivity.this,30));
                    } else if (y>=mPreHeight - mPreTopHeight+DensityUtil.dip2px(CommunityUserDetailActivity.this,50)){
                        mFollowIv.setAlpha(1);
                    }else {
                        mFollowIv.setAlpha(0);
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        mTopView.setAlpha(0);
        mFollowIv.setAlpha(0);
        mMyRecyclerView.setFocusable(false);

        //更多内容
        //设置layoutManager解决滑动冲突
        mMyRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        //设置adapter
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setGood(false);
            product.setTitle("赞");
            if (i % 2 == 0) {
                product.setImg(R.drawable.img1_community);
            } else {
                product.setImg(R.drawable.img2_community);
            }
            productList.add(product);
        }
        MasonryAdapter moreAdapter = new MasonryAdapter(this);
        moreAdapter.setProducts(productList);
        mMyRecyclerView.setAdapter(moreAdapter);
        //设置item之间的间隔
        SpacesItemDecoration decoration = new SpacesItemDecoration(DensityUtil.dip2px(this, 5));
        mMyRecyclerView.addItemDecoration(decoration);

    }

    private void showShareDialog() {
        View shareDialog = View.inflate(this, R.layout.dialog_share, null);
        TextView wechat = (TextView) shareDialog.findViewById(R.id.wechat);
        TextView friends = (TextView) shareDialog.findViewById(R.id.friends);
//        TextView link = (TextView) shareDialog.findViewById(R.id.link);
        Button cancel = (Button) shareDialog.findViewById(R.id.cancel);
        mShaDialog = new CustomDialog(this);
        mShaDialog.setContentView(shareDialog);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShaDialog.dismiss();
            }
        });
        mShaDialog.show();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_community_user_detail;
    }


    @OnClick({R.id.follow, R.id.back, R.id.share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.follow:

                break;
            case R.id.back:
                finish();
                break;
            case R.id.share:
               //分享
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
