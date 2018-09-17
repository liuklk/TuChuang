package com.twentyfourhours.tuchuang.ui.activity;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
public class MyDesignActivity extends BaseActivity {
    @BindView(R.id.gold_back)
    ImageView mGoldBack;
    @BindView(R.id.grid_view)
    GridView mGridView;
    @BindView(R.id.refreshLayout)
    PullToRefreshLayout mRefreshLayout;
    @BindView(R.id.manager)
    TextView mManager;
    @BindView(R.id.choice_all)
    CheckBox mChoiceAll;
    @BindView(R.id.delete)
    Button mDelete;
    @BindView(R.id.bottom_view)
    RelativeLayout mBottomView;
    private MyDesignAdapter mHotAdapter;
    private List<HotBeen> mData=new ArrayList<>();

    @Override
    protected void initListener() {
        mChoiceAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (int i = 0; i < mData.size(); i++) {
                    mData.get(i).setCheck(isChecked);
                }
                mHotAdapter.notifyDataSetChanged();
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
        for (int i = 0; i < 8; i++) {
            HotBeen hotBeen = new HotBeen();
            hotBeen.setShow(true);
            hotBeen.setEdit(false);
            hotBeen.setCheck(false);
            hotBeen.setPic1(R.drawable.face1);
            hotBeen.setPic2(R.drawable.back1);
            mData.add(hotBeen);
        }
        mHotAdapter.setData(mData);
        mGridView.setAdapter(mHotAdapter);
    }

    private boolean isManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_design;
    }

    @OnClick({R.id.gold_back,R.id.manager,R.id.delete})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.manager:
                if (isManager){
                    mManager.setText("管理");
                    mBottomView.setVisibility(View.GONE);
                    for (int i = 0; i < mData.size(); i++) {
                        mData.get(i).setEdit(false);
                    }
                }else {
                    mManager.setText("取消");
                    mBottomView.setVisibility(View.VISIBLE);
                    for (int i = 0; i < mData.size(); i++) {
                        mData.get(i).setEdit(true);
                    }
                }
                isManager=!isManager;
                mHotAdapter.notifyDataSetChanged();
                break;
            case R.id.gold_back:
                finish();
                break;
            case R.id.delete:
                for (int i = 0; i < mData.size(); i++) {
                    if (mData.get(i).isCheck()){
                        mData.remove(i--);
                    }
                }
                mHotAdapter.notifyDataSetChanged();
                break;
        }
    }

}
