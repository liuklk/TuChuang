package com.twentyfourhours.tuchuang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scrat.app.selectorlibrary.ImageSelector;
import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.model.bean.WriteHeartBeen;
import com.twentyfourhours.tuchuang.ui.activity.EditTextActivity;
import com.twentyfourhours.tuchuang.ui.activity.SelectVideoActivity;
import com.twentyfourhours.tuchuang.ui.activity.WriteHeartTextActivity;
import com.twentyfourhours.tuchuang.common.util.BitmapToRoundUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/8.
 */
public class WriteHeartAdapter extends BaseAdapter {

    public static final int WRITE_HEART_REQ = 111;
    public static final int REQUEST_CODE_SELECT_IMG2 = 112;
    private static final int MAX_SELECT_COUNT = 1;
    public static final int REQUEST_CODE_SELECT_IMG3 = 113;
    public static final int REQUEST_VIDEO_CODE = 10;
    public static final int REQUEST_CODE_SELECT_IMG5 = 115;
    private Context mContext;

    private ViewGroup mParent;

    private List<WriteHeartBeen> data = new ArrayList<>();

    public List<WriteHeartBeen> getData() {
        return data;
    }

    public void setData(List<WriteHeartBeen> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public WriteHeartAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (data != null) {
            return data.get(position);
        }
        return null;
    }

    public void remove(int arg0) {// 删除指定位置的item
        data.remove(arg0);
        this.notifyDataSetChanged();// 不要忘记更改适配器对象的数据源
    }

    public void insert(WriteHeartBeen item, int arg0) {// 在指定位置插入item
        data.add(arg0, item);
        this.notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_write_head_view, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setData(data.get(position), position);
        return convertView;
    }

    class ViewHolder implements View.OnClickListener {
        ImageView mAddView;
        ImageView mText;
        ImageView mPhoto;
        ImageView mVideo;
        ImageView mAddView2;
        ImageView mClose;
        ImageView mPic;
        TextView mAddText;
        ImageView mUp;
        ImageView mDown;
        ImageView mVideoIv;
        RelativeLayout mDragHandle;
        RelativeLayout mLastView;
        RelativeLayout mPicRl;
        LinearLayout mSelectView;
        LinearLayout mSelectView2;
        View mAddTextClick;

        ImageView mText2;
        ImageView mPhoto2;
        ImageView mVideo2;
        int position;

        public ViewHolder(View view) {
            mAddView = (ImageView) view.findViewById(R.id.add_view);
            mText = (ImageView) view.findViewById(R.id.text);
            mPhoto = (ImageView) view.findViewById(R.id.photo);
            mVideo = (ImageView) view.findViewById(R.id.video);
            mAddView2 = (ImageView) view.findViewById(R.id.add_view2);
            mClose = (ImageView) view.findViewById(R.id.click_remove);
            mPic = (ImageView) view.findViewById(R.id.pic);
            mAddText = (TextView) view.findViewById(R.id.add_text);
            mUp = (ImageView) view.findViewById(R.id.up);
            mVideoIv = (ImageView) view.findViewById(R.id.video_iv);
            mDown = (ImageView) view.findViewById(R.id.down);
            mLastView = (RelativeLayout) view.findViewById(R.id.last_view);
            mPicRl = (RelativeLayout) view.findViewById(R.id.pic_rl);
            mDragHandle = (RelativeLayout) view.findViewById(R.id.drag_handle);
            mAddTextClick = view.findViewById(R.id.add_text_click);
            mSelectView = (LinearLayout) view.findViewById(R.id.select_view);
            mSelectView2 = (LinearLayout) view.findViewById(R.id.select_view2);

            mText2 = (ImageView) view.findViewById(R.id.text2);
            mPhoto2 = (ImageView) view.findViewById(R.id.photo2);
            mVideo2 = (ImageView) view.findViewById(R.id.video2);

            initListener();
        }

        private void initListener() {
            mAddView.setOnClickListener(this);
            mAddView2.setOnClickListener(this);
            mUp.setOnClickListener(this);
            mDown.setOnClickListener(this);
            mAddTextClick.setOnClickListener(this);
            mText.setOnClickListener(this);
            mPhoto.setOnClickListener(this);
            mVideo.setOnClickListener(this);
            mPicRl.setOnClickListener(this);
            mText2.setOnClickListener(this);
            mPhoto2.setOnClickListener(this);
            mVideo2.setOnClickListener(this);
        }

        public void setData(WriteHeartBeen been, int position) {
            this.position = position;
            mAddText.setText(been.getContent());
            mVideoIv.setVisibility(been.isShowVideo() ? View.VISIBLE : View.GONE);
            mAddView.setVisibility(been.isShow() ? View.VISIBLE : View.GONE);
            mSelectView.setVisibility(been.isShow() ? View.GONE : View.VISIBLE);
            if (position!=data.size()-1) {
                mSelectView2.setVisibility(View.GONE);
                mAddView2.setVisibility(been.isShow() ? View.VISIBLE : View.GONE);
            }else {
                mAddView2.setVisibility(been.isShowLast() ? View.VISIBLE : View.GONE);
                mSelectView2.setVisibility(been.isShowLast() ? View.GONE : View.VISIBLE);
            }
            mPic.setImageBitmap(BitmapToRoundUtils.toRoundLitBitmap(been.getBitmap()));
            mUp.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
            mDown.setVisibility(position == data.size() - 1 ? View.GONE : View.VISIBLE);
            mLastView.setVisibility(position == data.size() - 1 ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.add_view:
                    for (int i = 0; i < data.size(); i++) {
                        if (i == position) {
                            data.get(i).setShow(false);
                        } else {
                            data.get(i).setShow(true);
                        }
                        data.get(i).setShowLast(true);
                    }
                    break;
                case R.id.add_view2:
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).setShow(true);
                        data.get(i).setShowLast(false);
                    }
                    break;
                case R.id.up:
//                    data.get(position)
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).setShow(true);
                        data.get(i).setShowLast(true);
                    }
                    startAnimUp();
                    WriteHeartBeen cus = data.get(position);
                    WriteHeartBeen old = data.get(position - 1);
                    data.set(position, old);
                    data.set(position - 1, cus);
                    break;
                case R.id.down:
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).setShow(true);
                        data.get(i).setShowLast(true);
                    }
                    startAnimDown();
                    WriteHeartBeen cus2 = data.get(position);
                    WriteHeartBeen old2 = data.get(position + 1);
                    data.set(position, old2);
                    data.set(position + 1, cus2);
                    break;
                case R.id.add_text_click:
                    Intent intent2 = new Intent(mContext, EditTextActivity.class);
                    intent2.putExtra("mode", 1);
                    intent2.putExtra("position", position);
                    intent2.putExtra("editContent", mAddText.getText().toString());
                    ((WriteHeartTextActivity) mContext).startActivityForResult(intent2, WRITE_HEART_REQ);
                    break;
                case R.id.text:
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).setShow(true);
                        data.get(i).setShowLast(true);
                    }
                    Intent intent1 = new Intent(mContext, EditTextActivity.class);
                    intent1.putExtra("mode", 0);
                    intent1.putExtra("position", position);
                    intent1.putExtra("editContent", "");
                    ((WriteHeartTextActivity) mContext).startActivityForResult(intent1, WRITE_HEART_REQ);
                    break;
                case R.id.photo:
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).setShow(true);
                        data.get(i).setShowLast(true);
                    }
                    ((WriteHeartTextActivity) mContext).setPosition(position);
                    selectAddImg();
                    break;
                case R.id.pic_rl:
                    ((WriteHeartTextActivity) mContext).setPosition(position);
                    if (data.get(position).isShowVideo()) {
                        Intent intent = new Intent(mContext, SelectVideoActivity.class);
                        intent.putExtra("type",1);
                        ((WriteHeartTextActivity) mContext).startActivityForResult(intent, REQUEST_VIDEO_CODE);
                    } else {
                        selectImg();
                    }
                    break;
                case R.id.video:
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).setShow(true);
                        data.get(i).setShowLast(true);
                    }
                    ((WriteHeartTextActivity) mContext).setPosition(position);
                    Intent intent = new Intent(mContext, SelectVideoActivity.class);
                    intent.putExtra("type",0);
                    ((WriteHeartTextActivity) mContext).startActivityForResult(intent, REQUEST_VIDEO_CODE);
                    break;
                case R.id.text2:
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).setShow(true);
                        data.get(i).setShowLast(true);
                    }
                    Intent intent3 = new Intent(mContext, EditTextActivity.class);
                    intent3.putExtra("mode", 0);
                    intent3.putExtra("last",true);
                    intent3.putExtra("position", position);
                    intent3.putExtra("editContent", "");
                    ((WriteHeartTextActivity) mContext).startActivityForResult(intent3, WRITE_HEART_REQ);
                    break;
                case R.id.photo2:
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).setShow(true);
                        data.get(i).setShowLast(true);
                    }
                    ((WriteHeartTextActivity) mContext).setPosition(position);
                    selectAddImg2();
                    break;
                case R.id.video2:
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).setShow(true);
                        data.get(i).setShowLast(true);
                    }
                    ((WriteHeartTextActivity) mContext).setPosition(position);
                    Intent intent4 = new Intent(mContext, SelectVideoActivity.class);
                    intent4.putExtra("type",0);
                    intent4.putExtra("last",true);
                    ((WriteHeartTextActivity) mContext).startActivityForResult(intent4, REQUEST_VIDEO_CODE);
                    break;
            }
            notifyDataSetChanged();
        }

        private void selectImg() {//修改图片
            ImageSelector.show((WriteHeartTextActivity) mContext, REQUEST_CODE_SELECT_IMG2, MAX_SELECT_COUNT);
        }

        private void selectAddImg() {
            ImageSelector.show((WriteHeartTextActivity) mContext, REQUEST_CODE_SELECT_IMG3, WriteHeartTextActivity.MAX_SELECT_COUNT);
        }

        private void selectAddImg2() {//最后一个条目添加
            ImageSelector.show((WriteHeartTextActivity) mContext, REQUEST_CODE_SELECT_IMG5, WriteHeartTextActivity.MAX_SELECT_COUNT);
        }
    }

    private void startAnimDown() {

    }

    private void startAnimUp() {

    }
}
