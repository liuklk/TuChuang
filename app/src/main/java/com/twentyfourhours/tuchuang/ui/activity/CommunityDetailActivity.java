package com.twentyfourhours.tuchuang.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.CommentReplyAdapter;
import com.twentyfourhours.tuchuang.adapter.Comment_Adapter;
import com.twentyfourhours.tuchuang.adapter.LookMoreAdapter;
import com.twentyfourhours.tuchuang.adapter.MasonryAdapter;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.common.util.DensityUtil;
import com.twentyfourhours.tuchuang.model.bean.HotBeen;
import com.twentyfourhours.tuchuang.model.bean.Product;
import com.twentyfourhours.tuchuang.model.bean.StoreBeen;
import com.twentyfourhours.tuchuang.view.CustomDialog;
import com.twentyfourhours.tuchuang.view.MatchGoodDialog;
import com.twentyfourhours.tuchuang.view.SpacesItemDecoration;
import com.twentyfourhours.tuchuang.widget.BackEditText;
import com.twentyfourhours.tuchuang.widget.BannerCommunity;
import com.twentyfourhours.tuchuang.widget.MyListView;
import com.twentyfourhours.tuchuang.widget.MyRecyclerView;
import com.twentyfourhours.tuchuang.widget.ObservableScrollview;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;




/**
 * Created by Administrator on 2018/2/8.
 */
public class CommunityDetailActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.share)
    ImageView mShare;
    @BindView(R.id.pic2)
    ImageView mPic2;
    @BindView(R.id.detail2)
    TextView mDetail2;
    @BindView(R.id.title2)
    TextView mTitle2;
    @BindView(R.id.follow)
    TextView mFollow;
    @BindView(R.id.business2)
    RelativeLayout mBusiness2;
    @BindView(R.id.write_comment)
    TextView mWriteComment;
    @BindView(R.id.list_view)
    MyListView lv_user_comments;
    @BindView(R.id.all_comment)
    TextView mAllComment;
    @BindView(R.id.commit_comment)
    TextView mCommitComment;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.my_recycler_view)
    MyRecyclerView mMyRecyclerView;
    @BindView(R.id.good)
    CheckBox mGood;
    @BindView(R.id.comment_total)
    TextView mCommentTotal;
    @BindView(R.id.cart_number)
    TextView mCartNumber;
    @BindView(R.id.match_good)
    RelativeLayout mMatchGood;
    @BindView(R.id.follow_iv)
    TextView mFollowIv;
    @BindView(R.id.top_view)
    RelativeLayout mTopView;
    @BindView(R.id.scroll_view)
    ObservableScrollview mScrollView;
    @BindView(R.id.banner)
    BannerCommunity mBanner;
    @BindView(R.id.top_blank_view)
    View mTopBlankView;
    @BindView(R.id.title_view)
    RelativeLayout mTitleView;
    @BindView(R.id.community_ll_view)
    LinearLayout mCommunityLlView;
    @BindView(R.id.commit_comment_et)
    EditText mCommitCommentEt;
    private PopupWindow mPopupWindow1;


    private List<Product> productList = new ArrayList<>();
    private MatchGoodDialog mMachDialog;
    private CustomDialog mShaDialog;
    private int mPreHeight;
    private int mPreTopHeight;

    private Comment_Adapter commentAdapter;
    private CommentReplyAdapter commentReplyAdapter;
    private List<StoreBeen> list_comment; // 一级评论数据
    private List<List<StoreBeen>> list_comment_child; // 二级评论数据

    @Override
    protected void initListener() {
        ViewTreeObserver vto = mBanner.getViewTreeObserver();
        ViewTreeObserver vto2 = mTopView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mBanner.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mPreHeight = mBanner.getHeight();
            }
        });
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTopView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mPreTopHeight = mTopView.getHeight();
            }
        });

        mScrollView.setOnScollChangedListener(new ObservableScrollview.OnScollChangedListener() {
            @Override
            public void onScrollChanged(ObservableScrollview scrollView, int x, int y, int oldx, int oldy) {
                if (y <= mPreHeight - mPreTopHeight) {
                    mTopView.setAlpha((float) y / (float) (mPreHeight - mPreTopHeight));
                    mFollowIv.setAlpha(0);
                } else {
                    mTopView.setAlpha(1);
                    if (y >= mPreHeight - mPreTopHeight + DensityUtil.dip2px(CommunityDetailActivity.this, 20) && y < mPreHeight - mPreTopHeight + DensityUtil.dip2px(CommunityDetailActivity.this, 50)) {
                        mFollowIv.setAlpha((float) (y - (mPreHeight - mPreTopHeight + DensityUtil.dip2px(CommunityDetailActivity.this, 20))) / (float) DensityUtil.dip2px(CommunityDetailActivity.this, 30));
                    } else if (y >= mPreHeight - mPreTopHeight + DensityUtil.dip2px(CommunityDetailActivity.this, 50)) {
                        mFollowIv.setAlpha(1);
                    } else {
                        mFollowIv.setAlpha(0);
                    }
                }

            }
        });
    }

    private Bitmap rsBlur(int radius) {
        RenderScript renderScript = RenderScript.create(this);
        final Allocation input = Allocation.createFromBitmap(renderScript, ((BitmapDrawable) getResources().getDrawable(R.drawable.img1_community_detail2)).getBitmap());
        final Allocation output = Allocation.createTyped(renderScript, input.getType());
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setInput(input);
        scriptIntrinsicBlur.setRadius(radius);
        scriptIntrinsicBlur.forEach(output);
        output.copyTo(((BitmapDrawable) getResources().getDrawable(R.drawable.img1_community_detail2)).getBitmap());
        renderScript.destroy();
        return ((BitmapDrawable) getResources().getDrawable(R.drawable.img1_community_detail2)).getBitmap();

    }

    @Override
    protected void initData() {
        mTopView.setAlpha(0);
        mFollowIv.setAlpha(0);
        lv_user_comments.setFocusable(false);
        mMyRecyclerView.setFocusable(false);
        list_comment = new ArrayList<StoreBeen>();
        list_comment_child = new ArrayList<List<StoreBeen>>();
        for (int i = 0; i < 3; i++) {
            StoreBeen map = new StoreBeen();
            map.setText("想起papi酱的戴森");
            map.setShow(false);
            list_comment.add(map);
            list_comment_child
                    .add(new ArrayList<StoreBeen>());
        }
        commentReplyAdapter = null;
        commentAdapter = new Comment_Adapter(this, list_comment,
                list_comment_child, myOnitemcListener, commentReplyAdapter);
        lv_user_comments.setAdapter(commentAdapter);
        //相关商品
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(lm);
        LookMoreAdapter adapter = new LookMoreAdapter(this);
        List<HotBeen> data = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            HotBeen hotBeen = new HotBeen();
            hotBeen.setShowLimitTime(i == 0 || i == 5);
            hotBeen.setShow1(i == 1 || i == 2 || i == 4);
            hotBeen.setShow2(i == 0 || i == 1 || i == 2);
            hotBeen.setShow3(i == 0 || i == 3);
            data.add(hotBeen);
        }
        adapter.setData(data);
        mRecyclerView.setAdapter(adapter);

        //更多内容
        //设置layoutManager
        mMyRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        //设置adapter
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setGood(false);
            product.setTitle("赞");
            if (i % 2 == 0) {
                product.setImg(R.drawable.img1_community);
            } else {
                product.setImg(R.drawable.img2_community);
            }
            productList.add(product);
        }
        MasonryAdapter moreAdapter = new MasonryAdapter(this);
        moreAdapter.setProducts(productList);
        mMyRecyclerView.setAdapter(moreAdapter);
        //设置item之间的间隔
        SpacesItemDecoration decoration = new SpacesItemDecoration(DensityUtil.dip2px(this, 5));
        mMyRecyclerView.addItemDecoration(decoration);

        rsBlur(25);
    }

    private View.OnClickListener myOnitemcListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int position = (Integer) v.getTag();
            showPopWindow(position);

        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_community_detail;
    }

    @OnClick({R.id.back, R.id.share, R.id.follow, R.id.write_comment, R.id.all_comment, R.id.commit_comment, R.id.comment_total, R.id.match_good})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.share:
                showShareDialog();

                break;
            case R.id.follow:

                break;
            case R.id.write_comment:
                showPopWindow(-1);
                break;
            case R.id.all_comment:
                goTo(CommentActivity.class, false);
                break;
            case R.id.commit_comment:
                if (!mCommitCommentEt.getText().toString().equals("")) {
                    StoreBeen storeBeen = new StoreBeen();
                    storeBeen.setText(mCommitCommentEt.getText().toString());
                    list_comment.add(storeBeen);
                    list_comment_child
                            .add(new ArrayList<StoreBeen>());
                    commentAdapter.clearList();
                    commentAdapter.updateList(list_comment,
                            list_comment_child);
                    commentAdapter.notifyDataSetChanged();
                    mCommitCommentEt.setText("");
                }
                break;
            case R.id.comment_total:
                showPopWindow(-1);
                break;
            case R.id.match_good:
                showMatchGoodDialog();
                break;
        }
    }

    //匹配商品
    private void showMatchGoodDialog() {
        View shareDialog = View.inflate(this, R.layout.dialog_match_good, null);
        TextView comment = (TextView) shareDialog.findViewById(R.id.comment_total);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopWindow(-1);
                mMachDialog.dismiss();
            }
        });
        mMachDialog = new MatchGoodDialog(this);
        mMachDialog.setContentView(shareDialog);
        mMachDialog.show();
    }

    private void showShareDialog() {
        View shareDialog = View.inflate(this, R.layout.dialog_share, null);
        TextView wechat = (TextView) shareDialog.findViewById(R.id.wechat);
        TextView friends = (TextView) shareDialog.findViewById(R.id.friends);
        TextView qq = (TextView) shareDialog.findViewById(R.id.qq);
        TextView QZone = (TextView) shareDialog.findViewById(R.id.QZone);
        TextView sina = (TextView) shareDialog.findViewById(R.id.sina_weibo);
        Button cancel = (Button) shareDialog.findViewById(R.id.cancel);
        mShaDialog = new CustomDialog(this);
        mShaDialog.setContentView(shareDialog);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShaDialog.dismiss();
            }
        });
        wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        QZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        sina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mShaDialog.show();
    }

    /**
     * 弹出选择窗
     */
    public void showPopWindow(final int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_comment_call_back, null);
        final BackEditText edt_reply = (BackEditText) view.findViewById(R.id.edt_comments);
        TextView send = (TextView) view.findViewById(R.id.btn_send);
        edt_reply.setBackListener(new BackEditText.BackListener() {
            @Override
            public void back(TextView textView) {
                mPopupWindow1.dismiss();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edt_reply.getText().toString().equals("")) {
                    StoreBeen storeBeen = new StoreBeen();
                    storeBeen.setText(edt_reply.getText().toString());
                    if (position != -1) {
                        list_comment_child.get(position).add(storeBeen);
                    } else {
                        list_comment.add(storeBeen);
                        list_comment_child
                                .add(new ArrayList<StoreBeen>());
                    }
                    commentAdapter.clearList();
                    commentAdapter.updateList(list_comment,
                            list_comment_child);
                    commentAdapter.notifyDataSetChanged();
                    mPopupWindow1.dismiss();
                    edt_reply.setText("");
                }
            }
        });
        mPopupWindow1 = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(this, 50), true);
        mPopupWindow1.showAtLocation(mCommunityLlView, Gravity.BOTTOM, 0, 0);
        mPopupWindow1.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mPopupWindow1.setOutsideTouchable(true);
        mPopupWindow1.setFocusable(true);
        edt_reply.setFocusable(true);
//        setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        mPopupWindow1.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | PopupWindow.INPUT_METHOD_NEEDED);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
