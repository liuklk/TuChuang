package com.twentyfourhours.tuchuang.ui.fragment;

import android.os.Handler;
import android.view.View;
import android.webkit.WebView;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.common.base.BaseFragment;
import com.twentyfourhours.tuchuang.ui.activity.ShoppingDetailActivity;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/2/5.
 */

public class DetailFragment extends BaseFragment {
    @BindView(R.id.detail_view)
    WebView mDetailView;
    @BindView(R.id.refreshLayout2)
    PullToRefreshLayout mRefreshLayout2;
    Unbinder unbinder;

    @Override
    protected View initView() {
        return View.inflate(mContext, R.layout.fragment_detail, null);
    }

    @Override
    protected void initListener() {

        mRefreshLayout2.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadData();
                        //  mProductList.get(0).getId();
                        // 结束刷新
                        mRefreshLayout2.finishRefresh();
                    }
                }, 10);
            }

            /*------------------------ 加载更多 ----------------------------------*/
            @Override
            public void loadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        loadMoredata();
                        // 结束加载更多
                        mRefreshLayout2.finishLoadMore();
                    }
                }, 0);
            }
        });
    }

    private void loadMoredata() {

    }

    private void loadData() {
        ((ShoppingDetailActivity)mContext).containShop();
    }

    @Override
    protected void initData() {

    }

}
