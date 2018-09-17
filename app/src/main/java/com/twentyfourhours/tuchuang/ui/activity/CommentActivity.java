package com.twentyfourhours.tuchuang.ui.activity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.adapter.CommentReplyAdapter;
import com.twentyfourhours.tuchuang.adapter.Comment_Adapter;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.model.bean.StoreBeen;
import com.twentyfourhours.tuchuang.common.util.DensityUtil;
import com.twentyfourhours.tuchuang.widget.BackEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/2/9.
 */
public class CommentActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.list_view)
    ListView lv_user_comments;
    @BindView(R.id.comment_et_click)
    RelativeLayout mCommentEt;
    @BindView(R.id.comment_ll_view)
    LinearLayout mCommentLlView;
    private PopupWindow mPopupWindow1;

    private Comment_Adapter commentAdapter;
    private CommentReplyAdapter commentReplyAdapter;
    private List<StoreBeen> list_comment; // 一级评论数据
    private List<List<StoreBeen>> list_comment_child; // 二级评论数据

    @Override
    protected void initListener() {

    }

    /**
     * 弹出选择窗
     */
    public void showBackPopWindow(final int position) {
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
        mPopupWindow1 = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(this,50), true);
        mPopupWindow1.showAtLocation(mCommentLlView, Gravity.BOTTOM, 0,0);
        mPopupWindow1.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mPopupWindow1.setOutsideTouchable(true);
        mPopupWindow1.setFocusable(true);
        edt_reply.setFocusable(true);
//        setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE|WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        mPopupWindow1.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE|WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|PopupWindow.INPUT_METHOD_NEEDED);
    }

    @Override
    protected void initData() {

        list_comment = new ArrayList<StoreBeen>();
        list_comment_child = new ArrayList<List<StoreBeen>>();
        for (int i = 0; i < 20; i++) {
            StoreBeen storeBeen=new StoreBeen();
            storeBeen.setText("想起papi酱的戴森");
            storeBeen.setShow(false);
            list_comment.add(storeBeen);
            list_comment_child
                    .add(new ArrayList<StoreBeen>());
        }
        commentReplyAdapter = null;
        commentAdapter = new Comment_Adapter(this, list_comment,
                list_comment_child, myOnitemcListener, commentReplyAdapter);
        lv_user_comments.setAdapter(commentAdapter);
    }

    private View.OnClickListener myOnitemcListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int position = (Integer) v.getTag();
            showBackPopWindow(position);

        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment;
    }

    @OnClick({R.id.back,R.id.comment_et_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.comment_et_click:
               showBackPopWindow(-1);
                break;
        }
    }

}
