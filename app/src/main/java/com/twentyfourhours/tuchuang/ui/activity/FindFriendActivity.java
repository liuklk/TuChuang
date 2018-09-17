package com.twentyfourhours.tuchuang.ui.activity;

import android.widget.ImageView;
import android.widget.ListView;

import com.leon.loopviewpagerlib.FunBanner;
import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.FindFriendAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.model.bean.StoreBeen;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/2/27.
 */
public class FindFriendActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.list_view)
    ListView mListView;
    private int[] imageResIds = {R.drawable.banner_img1, R.drawable.banner_img1, R.drawable.banner_img1, R.drawable.banner_img1, R.drawable.banner_img1, R.drawable.banner_img1, R.drawable.banner_img1};
    private List<StoreBeen> mData=new ArrayList<>();
    private FindFriendAdapter mFindFriendAdapter;

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        FunBanner.Builder builder = new FunBanner.Builder(this);
        FunBanner funBanner = builder.setEnableAutoLoop(true)
                .setImageResIds(imageResIds)
                .setDotSelectedColor(getResources().getColor(R.color.black_bg))
                .setHeightWidthRatio(0.528f)
                .setLoopInterval(8000)
                .setEnableAutoLoop(true)
                .build();
        mListView.addHeaderView(funBanner);

        //设置adapter
        for (int i = 0; i < 10; i++) {
            StoreBeen been = new StoreBeen();
//            been.setBitmap(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888));
            mData.add(been);
        }
        mFindFriendAdapter = new FindFriendAdapter(this);
        mFindFriendAdapter.setData(mData);
        mListView.setAdapter(mFindFriendAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_find_friend;
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
