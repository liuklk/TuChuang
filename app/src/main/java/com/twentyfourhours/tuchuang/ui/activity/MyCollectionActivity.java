package com.twentyfourhours.tuchuang.ui.activity;

import android.os.Handler;
import android.widget.GridView;
import android.widget.ImageView;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.MyDesignAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.model.bean.HotBeen;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/16.
 */
public class MyCollectionActivity extends BaseActivity {
    @BindView(R.id.gold_back)
    ImageView mGoldBack;
    @BindView(R.id.grid_view)
    GridView mGridView;
    @BindView(R.id.refreshLayout)
    PullToRefreshLayout mRefreshLayout;
    private MyDesignAdapter mHotAdapter;

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
        mHotAdapter = new MyDesignAdapter(this);

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_collection;
    }

    @OnClick(R.id.gold_back)
    public void onViewClicked() {
        finish();
    }
}
