package com.twentyfourhours.tuchuang.ui.fragment;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.StoreFragmentPageAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseFragment;
import com.twentyfourhours.tuchuang.ui.activity.SearchActivity;
import com.twentyfourhours.tuchuang.widget.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/4.
 */

public class StoreFragment extends BaseFragment {

    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.iv_arrow)
    ImageView mIvArrow;
    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.search)
    RelativeLayout mSearch;
    @BindView(R.id.pindao)
    TextView mPindao;
    @BindView(R.id.view)
    View mView;
    private StoreFragmentPageAdapter mFragmentPageAdapter;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private boolean isPop;
    private MyGridView mListView1;
    private int mLastSelectedPosition;
    private String[] types = {"推荐", "新品", "众筹", "服装", "居家", "餐具","玩具","配件"};
    private PopupWindow mPopupWindow1;
    private FragmentManager mChildFragmentManager;

    @Override
    protected View initView() {
        return View.inflate(mContext, R.layout.fragment_store, null);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        initFragment();
        mChildFragmentManager = getChildFragmentManager();
        if (mFragmentPageAdapter == null) {
            mFragmentPageAdapter = new StoreFragmentPageAdapter(mChildFragmentManager, mFragmentList);
        }
//        mFragmentPageAdapter.setTitles(new String[]{"推荐","新品","众筹","限时购","居家","餐厨","配件","服装","电器","洗护","杂货","饮食","婴童","志趣"});
//        mFragmentPageAdapter.setTitles(new String[]{"推荐","新品","众筹"});
        mVp.setAdapter(mFragmentPageAdapter);
        mTabs.setupWithViewPager(mVp);

    }


    private void initFragment() {
        mFragmentList.clear();
        mFragmentList.add(new RecommendFragment());
        mFragmentList.add(new NewFragment());
        mFragmentList.add(new NewFragment());
        mFragmentList.add(new NewFragment());
        mFragmentList.add(new NewFragment());
        mFragmentList.add(new NewFragment());
        mFragmentList.add(new NewFragment());
        mFragmentList.add(new NewFragment());
//        mFragmentList.add(new NewFragment());
//        mFragmentList.add(new NewFragment());
//        mFragmentList.add(new NewFragment());
//        mFragmentList.add(new NewFragment());
//        mFragmentList.add(new NewFragment());
//        mFragmentList.add(new NewFragment());
//        mFragmentList.add(new NewFragment());
//        mFragmentList.add(new NewFragment());
    }


    @OnClick({R.id.search, R.id.iv_arrow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search:
                goTo(SearchActivity.class, false);
                break;
            case R.id.iv_arrow:
                showPopWindow();
                break;
        }
    }

    private void showPopWindow() {

        mListView1 = new MyGridView(mContext);
        mListView1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mListView1.setPadding(getResources().getDisplayMetrics().widthPixels * 3 / 50, getResources().getDisplayMetrics().widthPixels  / 50, getResources().getDisplayMetrics().widthPixels * 3 / 50, getResources().getDisplayMetrics().widthPixels * 3 / 50);
        mListView1.setBackgroundColor(Color.WHITE);
        mListView1.setNumColumns(4);
        mListView1.setVerticalSpacing(getResources().getDisplayMetrics().widthPixels * 2 / 50);
        mListView1.setHorizontalSpacing(getResources().getDisplayMetrics().widthPixels * 2 / 50);
        MyAdapter adapter = new MyAdapter();
        adapter.setPosition(mLastSelectedPosition);
        adapter.setData(types);
        mListView1.setAdapter(adapter);
        mPopupWindow1 = new PopupWindow(mListView1, getResources().getDisplayMetrics().widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        ////让pop可以点击外面消失掉
        mPopupWindow1.setBackgroundDrawable(new ColorDrawable(0));
        mPopupWindow1.showAsDropDown(mTabs);
        mPopupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                ObjectAnimator anim = ObjectAnimator.ofFloat(mIvArrow, "rotation", -180f, -0);
                anim.setDuration(150);
                anim.start();
                mView.setVisibility(View.GONE);
                mTabs.setVisibility(View.VISIBLE);
                mPindao.setVisibility(View.INVISIBLE);
                isPop = !isPop;
                mPopupWindow1.dismiss();
            }
        });
        mPopupWindow1.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    mPopupWindow1.dismiss();
                    return true;
                }
                return false;
            }
        });
        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //如果没有发生切换，就不去处理
                if (i != mLastSelectedPosition) {
                    //选项的切换
                    //将点击位置的条目变成enable
                    mVp.setCurrentItem(i);
                    View checkTextView = adapterView.getChildAt(i);
                    TextView viewById = (TextView) checkTextView.findViewById(R.id.text_view);
                    viewById.setTextColor(getResources().getColor(R.color.black_bg));
                    viewById.setBackgroundResource(R.drawable.text_rc_bg_select);

                    //将上次选中的条目变成disable
                    View oldView = adapterView.getChildAt(mLastSelectedPosition);
                    TextView textView = (TextView) oldView.findViewById(R.id.text_view);
                    textView.setTextColor(getResources().getColor(R.color.textcolor3));
                    textView.setBackgroundResource(R.drawable.text_rc_bg2);

                    //更新上次选中的位置
                    mLastSelectedPosition = i;
                }
                mPopupWindow1.dismiss();
            }
        });
        if (isPop) {
            mPopupWindow1.dismiss();
        } else {
            ObjectAnimator anim = ObjectAnimator.ofFloat(mIvArrow, "rotation", 0f, -180f);
            anim.setDuration(150);
            anim.start();
            mView.setVisibility(View.VISIBLE);
            mTabs.setVisibility(View.INVISIBLE);
            mPindao.setVisibility(View.VISIBLE);
            isPop = !isPop;
        }
    }

    private class MyAdapter extends BaseAdapter {
        private String[] data;
        private int position;

        public void setPosition(int position) {
            this.position = position;
        }

        public void setData(String[] data) {
            this.data = data;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int i) {
            return data[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_store_list, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.type.setText(data[i]);
            holder.type.setTextColor(i == position ? getResources().getColor(R.color.black_bg) : getResources().getColor(R.color.textcolor3));
            holder.type.setBackgroundResource(i == position ? R.drawable.text_rc_bg_select : R.drawable.text_rc_bg2);
            return view;
        }
    }


    static class ViewHolder {
        TextView type;

        ViewHolder(View view) {
            type = (TextView) view.findViewById(R.id.text_view);
        }
    }

}
