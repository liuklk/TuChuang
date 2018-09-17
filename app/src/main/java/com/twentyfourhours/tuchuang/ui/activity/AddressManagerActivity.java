package com.twentyfourhours.tuchuang.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.AddressManageAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.model.bean.AddressManageBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/15.
 */
public class AddressManagerActivity extends BaseActivity {
    public static final int ADD = 1;
    public static final int EDIT = 0;
    public static final int RQT_ADD = 100;
    public static final int RQT_EDIT = 101;

    @BindView(R.id.list_view)
    ListView mListView;
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.add)
    TextView mAdd;
    private List<AddressManageBean> mData = new ArrayList<>();
    private AddressManageAdapter mAdapter;

    @Override
    protected void initData() {
        mAdapter = new AddressManageAdapter(this);
        mData.clear();
        for (int i = 0; i < 3; i++) {
            AddressManageBean bean = new AddressManageBean();
            bean.setName("张三");
            bean.setPhoneNumber("13266739747");
            bean.setAddress("广东省深圳市南山区");
            bean.setDetail("华丰国际商务大厦");
            bean.setChecked(i == 0);
            mData.add(bean);
        }
        mAdapter.setData(mData);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.back, R.id.add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.add:
                Intent intent = new Intent(this, NewAddressActivity.class);
                intent.putExtra("type", ADD);
                startActivityForResult(intent, RQT_ADD);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            AddressManageBean bean = (AddressManageBean) data.getSerializableExtra("AddressManageBean");
            int position = data.getIntExtra("position", -1);
            if (requestCode == RQT_ADD) {
                if (bean.isChecked()){
                    for (int i = 0; i < mData.size(); i++) {
                        mData.get(i).setChecked(false);
                    }
                }
                mData.add(bean);
            } else if (requestCode == RQT_EDIT) {
                mData.set(position, bean);
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_manager;
    }

}
