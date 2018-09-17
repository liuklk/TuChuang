package com.twentyfourhours.tuchuang.ui.activity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.CartStoreAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.model.bean.StoreBeen;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/9.
 */
public class CartActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.cart_number)
    public TextView mCartNumber;
    @BindView(R.id.list_view)
    ListView mListView;
    @BindView(R.id.choice_all)
    CheckBox mChoiceAll;
    @BindView(R.id.total_tv)
    public TextView mTotalTv;
    @BindView(R.id.oder_btn)
    public TextView mOderBtn;
    private CartStoreAdapter mAdapter;
    private ArrayList<StoreBeen> mStoreBeens=new ArrayList<>();

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mTotalTv.setText("000");
        mChoiceAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                mStoreBeens.clear();
                for (int i = 0; i < mStoreBeens.size(); i++) {
                    StoreBeen been = mStoreBeens.get(i);
                    been.setCheck(isChecked);
                    mStoreBeens.set(i,been);
                }
//                toast(mStoreBeens.size()+"");
                mAdapter.setData(mStoreBeens);
            }
        });

        mAdapter = new CartStoreAdapter(this);
        mStoreBeens = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            StoreBeen been = new StoreBeen();
            been.setCheck(true);
            been.setItemCount(1);
            mStoreBeens.add(been);
        }
        mAdapter.setData(mStoreBeens);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cart;
    }


    @OnClick({R.id.back, R.id.oder_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.oder_btn:
                goTo(FirmOrderActivity.class,false);
                break;
        }
    }
}
