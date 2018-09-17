package com.twentyfourhours.tuchuang.ui.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.RecommendLikeAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseFragment;
import com.twentyfourhours.tuchuang.model.bean.HotBeen;
import com.twentyfourhours.tuchuang.widget.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/1/9.
 */
public class NewFragment extends BaseFragment {
    @BindView(R.id.grid_view)
    MyGridView mGridView;
    @BindView(R.id.all)
    TextView mAll;
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
    Unbinder unbinder;
    Unbinder unbinder1;
    @BindView(R.id.zonghe)
    RelativeLayout mZonghe;
    Unbinder unbinder2;
    @BindView(R.id.scroll_view)
    ScrollView mScrollView;
    Unbinder unbinder3;
    private RecommendLikeAdapter mRecommendLikeAdapter;
    private boolean isAll = true;
    private boolean isSale = true;
    private boolean isPrice = true;

    @Override
    protected View initView() {
        return View.inflate(mContext, R.layout.new_fragment, null);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mGridView.setFocusable(false);

        mAll.setEnabled(true);
        mSale.setEnabled(false);
        mSaleDown.setEnabled(false);
        mSaleUp.setEnabled(false);
        mPrice.setEnabled(false);
        mSortDown.setEnabled(false);
        mSortUp.setEnabled(false);

        mRecommendLikeAdapter = new RecommendLikeAdapter(mContext);

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

    @OnClick({R.id.zonghe, R.id.xiaoliang, R.id.jiage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
