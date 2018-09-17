package com.twentyfourhours.tuchuang.ui.fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.MyOdeAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseFragment;
import com.twentyfourhours.tuchuang.model.bean.MyOderBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/10/17 0017.
 */

public class AllMyOderFragment extends BaseFragment {
    @BindView(R.id.list_view)
    ListView mListView;
    @BindView(R.id.refreshLayout)
    PullToRefreshLayout mRefreshLayout;
    Unbinder unbinder;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_list, null);
        return view;
    }

    @Override
    protected void initData() {
        List<MyOderBean> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            MyOderBean myOderBean = new MyOderBean();
            if (i % 4 == 0) {
                myOderBean.setStatu("待付款");
                myOderBean.setLogistics("取消订单");
                myOderBean.setShowAction(true);
                myOderBean.setAction("付款");
            }else if (i%3==0){
                myOderBean.setStatu("待发货");
                myOderBean.setLogistics("取消订单");
                myOderBean.setShowAction(false);
            }else if (i%2==0){
                myOderBean.setLogistics("查看物流");
                myOderBean.setStatu("待收货");
                myOderBean.setShowAction(true);
                myOderBean.setAction("确认收货");
            }else {
                myOderBean.setLogistics("评价");
                myOderBean.setStatu("待评价");
                myOderBean.setShowAction(false);
            }
            data.add(myOderBean);
        }
        MyOdeAdapter adapter = new MyOdeAdapter(getActivity());
        adapter.setData(data);
        mListView.setAdapter(adapter);
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

}
