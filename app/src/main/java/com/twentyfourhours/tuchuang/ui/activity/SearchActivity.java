package com.twentyfourhours.tuchuang.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.SearchView;
import android.text.Selection;
import android.text.Spannable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/6.
 */
public class SearchActivity extends BaseActivity {
    private static final int REQUESR_CODE = 2000;
    @BindView(R.id.search_view)
    SearchView mSearchView;
    @BindView(R.id.search_btn)
    ImageView mSearchBtn;
    @BindView(R.id.cancel)
    TextView mCancel;
    @BindView(R.id.clear_history)
    TextView mClearHistory;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout mFlowLayout;
    @BindView(R.id.hot_flowlayout)
    TagFlowLayout mHotFlowlayout;
    private String[] mVals = {"羽毛球拍", "笔记本", "防滑鞋套防滑钉", "雨鞋女 中筒", "羽毛球拍", "笔记本", "防滑钉", "雨鞋女", "杯子"};
    private String[] mValsHot = {"年货必备买一送一", "超值买3免1", "潮品T恤特惠", "骨瓷杯子", "鞋子", "明星片", "裤子", "飞船", "餐具"};
    private List<String> mData = new ArrayList<>();
    private List<String> mHot = new ArrayList<>();
    SearchView.SearchAutoComplete mSearchTextView;

    @Override
    protected void initListener() {
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Intent intent = new Intent(SearchActivity.this, SearchDetailActivity.class);
                intent.putExtra("goods", mVals[position]);
                startActivityForResult(intent,REQUESR_CODE);
                return true;
            }
        });
        mHotFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Intent intent = new Intent(SearchActivity.this, SearchDetailActivity.class);
                intent.putExtra("goods", mValsHot[position]);
                startActivityForResult(intent,REQUESR_CODE);
                return true;
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent=new Intent(SearchActivity.this,SearchDetailActivity.class);
                intent.putExtra("goods", query);
                startActivityForResult(intent,REQUESR_CODE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            String goods = data.getStringExtra("goods");
            if (requestCode==REQUESR_CODE){
                mSearchTextView.setText(goods);
                CharSequence text = mSearchTextView.getText();
                //Debug.asserts(text instanceof Spannable);
                if (text instanceof Spannable) {
                    Spannable spanText = (Spannable)text;
                    Selection.setSelection(spanText, text.length());
                }
            }
        }
    }

    @Override
    protected void initData() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mSearchBtn.setVisibility(View.GONE);
        }
        mSearchView.onActionViewExpanded();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE|WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        mSearchTextView = (SearchView.SearchAutoComplete) mSearchView.findViewById(R.id.search_src_text);
        mSearchTextView.setTextColor(getResources().getColor(R.color.black_bg));
        //设置字体大小为13sp
        mSearchTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);//13sp

        try {
            Class cls = Class.forName("android.support.v7.widget.SearchView");
            Field field = cls.getDeclaredField("mSearchSrcTextView");
            field.setAccessible(true);
            TextView tv  = (TextView) field.get(mSearchView);

            Class[] clses = cls.getDeclaredClasses();
            for(Class cls_ : clses)
            {
                if(cls_.toString().endsWith("android.support.v7.widget.SearchView$SearchAutoComplete"))
                {
                    Class targetCls = cls_.getSuperclass().getSuperclass().getSuperclass().getSuperclass();
                    Field cuosorIconField = targetCls.getDeclaredField("mCursorDrawableRes");
                    cuosorIconField.setAccessible(true);
                    cuosorIconField.set(tv, R.drawable.cursor_color);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        mData.clear();
        for (int i = 0; i < mVals.length; i++) {
            mData.add(mVals[i]);
        }
        mFlowLayout.setAdapter(mTagAdapter);

        mHot.clear();
        for (int i = 0; i < mValsHot.length; i++) {
            mHot.add(mValsHot[i]);
        }
        mHotFlowlayout.setAdapter(mTagHotAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }


    @OnClick({R.id.cancel, R.id.clear_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.clear_history:
                mData.clear();
                mTagAdapter.notifyDataChanged();
                break;
        }
    }

    private TagAdapter mTagAdapter = new TagAdapter<String>(mData) {
        @Override
        public View getView(FlowLayout parent, int position, String s) {
            TextView tv = (TextView) LayoutInflater.from(SearchActivity.this).inflate(R.layout.search_tv,
                    mFlowLayout, false);
            tv.setText(s);
            return tv;
        }
    };

    private TagAdapter mTagHotAdapter = new TagAdapter<String>(mHot) {
        @Override
        public View getView(FlowLayout parent, int position, String s) {
            TextView tv = (TextView) LayoutInflater.from(SearchActivity.this).inflate(R.layout.search_tv,
                    mHotFlowlayout, false);
            tv.setText(s);
            return tv;
        }
    };

}
