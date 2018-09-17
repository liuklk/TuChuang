package com.twentyfourhours.tuchuang.ui.fragment;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.common.base.BaseFragment;
import com.twentyfourhours.tuchuang.ui.activity.CustomizedActivity;
import com.twentyfourhours.tuchuang.widget.CircleMenuLayout;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/1/4.
 */

public class CustomizedFragment extends BaseFragment {
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.id_circle_menu_item_center)
    RelativeLayout mIdCircleMenuItemCenter;
    @BindView(R.id.id_menulayout)
    CircleMenuLayout mCircleMenuLayout;
    Unbinder unbinder;

    private String[] mItemTexts = new String[]{"卫衣 ", "裤子", "帆布袋",
            "手机壳", "鞋子", "枕头","T恤","枕头","T恤"};
    private int[] mItemImgs = new int[]{R.drawable.icon_sweater,
            R.drawable.icon_trousers, R.drawable.icon_kitbag,
            R.drawable.icon_mobile_phone, R.drawable.icon_shoes,
            R.drawable.icon_pillow,R.drawable.icon_t,R.drawable.icon_pillow,R.drawable.icon_t};

    @Override
    protected View initView() {
        return View.inflate(mContext, R.layout.fragment_customized, null);
    }

    @Override
    protected void initListener() {
        mCircleMenuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener()
        {

            @Override
            public void itemClick(View view, int pos)
            {
                goTo(CustomizedActivity.class,false);
            }

            @Override
            public void itemCenterClick(View view)
            {


            }
        });
    }

    @Override
    protected void initData() {
        mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
    }

}
