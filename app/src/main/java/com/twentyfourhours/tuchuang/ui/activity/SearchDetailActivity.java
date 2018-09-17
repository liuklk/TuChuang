package com.twentyfourhours.tuchuang.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.RecommendLikeAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.model.bean.HotBeen;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/16.
 */
public class SearchDetailActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.search_view)
    TextView mSearchView;
    @BindView(R.id.all)
    TextView mAll;
    @BindView(R.id.zonghe)
    RelativeLayout mZonghe;
    @BindView(R.id.sale)
    TextView mSale;
    @BindView(R.id.sale_up)
    ImageView mSaleUp;
    @BindView(R.id.sale_down)
    ImageView mSaleDown;
    @BindView(R.id.xiaoliang)
    RelativeLayout mXiaoliang;
    @BindView(R.id.price)
    TextView mPrice;
    @BindView(R.id.sort_up)
    ImageView mSortUp;
    @BindView(R.id.sort_down)
    ImageView mSortDown;
    @BindView(R.id.jiage)
    RelativeLayout mJiage;
    @BindView(R.id.list_ll)
    LinearLayout mListLl;
    @BindView(R.id.grid_view)
    GridView mGridView;
    private RecommendLikeAdapter mRecommendLikeAdapter;
    private boolean isAll = true;
    private boolean isSale = true;
    private boolean isPrice = true;
    private String mGoods;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mGoods = getIntent().getStringExtra("goods");
        mSearchView.setText(mGoods);

        mAll.setEnabled(true);
        mSale.setEnabled(false);
        mSaleDown.setEnabled(false);
        mSaleUp.setEnabled(false);
        mPrice.setEnabled(false);
        mSortDown.setEnabled(false);
        mSortUp.setEnabled(false);

        mRecommendLikeAdapter = new RecommendLikeAdapter(this);

        List<HotBeen> data = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            HotBeen hotBeen = new HotBeen();
            hotBeen.setShow1(i == 1 || i == 2 || i == 4);
            hotBeen.setShow2(i == 0 || i == 1 || i == 2);
            hotBeen.setShow3(i == 0 || i == 3);
            data.add(hotBeen);
        }
        mRecommendLikeAdapter.setData(data);
        mGridView.setAdapter(mRecommendLikeAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_detail;
    }

    @OnClick({R.id.back, R.id.search_view, R.id.zonghe, R.id.xiaoliang, R.id.jiage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.search_view:
                Intent intent=new Intent();
                intent.putExtra("goods",mGoods);
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.zonghe:
                showAll();
                break;
            case R.id.xiaoliang:
                showSale();
                break;
            case R.id.jiage:
                showPrice();
                break;
        }
    }

    private void showPrice() {
        mSale.setEnabled(false);
        mAll.setEnabled(false);
        mPrice.setEnabled(true);

        mSortDown.setEnabled(!isPrice);
        mSortUp.setEnabled(isPrice);
        isPrice = !isPrice;
    }

    private void showSale() {
        mSale.setEnabled(true);
        mAll.setEnabled(false);
        mPrice.setEnabled(false);

        mSaleDown.setEnabled(!isSale);
        mSaleUp.setEnabled(isSale);
        isSale = !isSale;
    }

    private void showAll() {
        mAll.setEnabled(true);
        mSale.setEnabled(false);
        mSaleDown.setEnabled(false);
        mSaleUp.setEnabled(false);
        mPrice.setEnabled(false);
        mSortDown.setEnabled(false);
        mSortUp.setEnabled(false);
    }
}
