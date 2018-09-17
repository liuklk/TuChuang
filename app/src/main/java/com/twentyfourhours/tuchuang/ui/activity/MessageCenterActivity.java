package com.twentyfourhours.tuchuang.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.MessageCenterAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.model.bean.StoreBeen;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/16.
 */
public class MessageCenterActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.list_tv)
    ListView mListTv;
    @BindView(R.id.action)
    TextView mAction;
    @BindView(R.id.choice_all)
    CheckBox mChoiceAll;
    @BindView(R.id.delete)
    Button mDelete;
    @BindView(R.id.bottom_view)
    RelativeLayout mBottomView;
    private MessageCenterAdapter mAdapter;
    private ArrayList<StoreBeen> mStoreBeens;

    @Override
    protected void initListener() {
        mChoiceAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    for (int i = 0; i < mStoreBeens.size(); i++) {
                        mStoreBeens.get(i).setCheck(isChecked);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initData() {
        mAdapter = new MessageCenterAdapter(this);
        mStoreBeens = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            StoreBeen been = new StoreBeen();
            been.setNotify(i<5);
            been.setShow(false);
            mStoreBeens.add(been);
        }
        mAdapter.setData(mStoreBeens);
        mListTv.setAdapter(mAdapter);
    }

    private boolean isCancle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_center;
    }

    @OnClick({R.id.action, R.id.delete,R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.action:
                if (isCancle){
                    mAction.setText("批量删除");
                    mBottomView.setVisibility(View.GONE);
                    for (int i = 0; i < mStoreBeens.size(); i++) {
                        mStoreBeens.get(i).setShow(false);
                    }
                }else {
                    mAction.setText("取消");
                    mBottomView.setVisibility(View.VISIBLE);
                    for (int i = 0; i < mStoreBeens.size(); i++) {
                        mStoreBeens.get(i).setShow(true);
                    }
                }
                isCancle=!isCancle;
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.delete:
                for (int i = 0; i < mStoreBeens.size(); i++) {
                    if (mStoreBeens.get(i).isCheck()){
                        mStoreBeens.remove(i--);
                    }
                }
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
