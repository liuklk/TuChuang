package com.twentyfourhours.tuchuang.ui.fragment;

import android.os.Handler;
import android.view.View;
import android.widget.GridView;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.HotAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseFragment;
import com.twentyfourhours.tuchuang.model.bean.HotBeen;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/1/17.
 */
public class LookMoreFragment extends BaseFragment {
    @BindView(R.id.grid_view)
    GridView mGridView;
    @BindView(R.id.refreshLayout)
    PullToRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    private HotAdapter mHotAdapter;

    @Override
    protected View initView() {
        return View.inflate(mContext, R.layout.fragment_look_more, null);
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
                }, 1500);
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
                }, 1500);
            }
        });
    }

    private void loadMoredata() {

    }

    private void loadData() {

    }

    @Override
    protected void initData() {
        mHotAdapter = new HotAdapter(mContext);
        ;
        List<HotBeen> data2 = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            HotBeen hotBeen = new HotBeen();
            hotBeen.setShow(true);
            hotBeen.setPic1(R.drawable.face1);
            hotBeen.setPic2(R.drawable.back1);
            data2.add(hotBeen);
        }
        mHotAdapter.setData(data2);
        mGridView.setAdapter(mHotAdapter);
    }
}
