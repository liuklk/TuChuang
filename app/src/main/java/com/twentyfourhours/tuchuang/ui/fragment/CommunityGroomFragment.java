package com.twentyfourhours.tuchuang.ui.fragment;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.MasonryAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseFragment;
import com.twentyfourhours.tuchuang.model.bean.Product;
import com.twentyfourhours.tuchuang.common.util.DensityUtil;
import com.twentyfourhours.tuchuang.view.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/2/7.
 */
public class CommunityGroomFragment extends BaseFragment {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.refreshLayout)
    PullToRefreshLayout mRefreshLayout;
    Unbinder unbinder1;
    private List<Product> productList = new ArrayList<>();

    @Override
    protected View initView() {
        return View.inflate(mContext, R.layout.fragment_community_groom, null);
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
                }, 200);
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
                }, 200);
            }
        });
    }

    private void loadMoredata() {

    }

    private void loadData() {

    }

    @Override
    protected void initData() {
        //设置layoutManager
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
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
        MasonryAdapter adapter = new MasonryAdapter(mContext);
        adapter.setProducts(productList);
        mRecyclerView.setAdapter(adapter);
        //设置item之间的间隔
        SpacesItemDecoration decoration = new SpacesItemDecoration(DensityUtil.dip2px(mContext, 5));
        mRecyclerView.addItemDecoration(decoration);
    }

}
