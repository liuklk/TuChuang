package com.twentyfourhours.tuchuang.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.model.bean.StoreBeen;
import com.twentyfourhours.tuchuang.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

public class Comment_Adapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ViewHolder viewHolder;
    private List<StoreBeen> list_comment; // 一级评论数据
    private List<List<StoreBeen>> list_comment_child; // 二级评论数据
    private Context context;
    private OnClickListener myOnitemcListener;
    private CommentReplyAdapter myAdapter;

    public Comment_Adapter(Context context,
                           List<StoreBeen> list_comment,
                           List<List<StoreBeen>> list_comment_child,
                           OnClickListener myOnitemcListener, CommentReplyAdapter myAdapter) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.myAdapter = myAdapter;
        this.list_comment = new ArrayList<StoreBeen>();
        this.list_comment_child = new ArrayList<List<StoreBeen>>();
        this.myOnitemcListener = myOnitemcListener;
        this.list_comment.addAll(list_comment);
        this.list_comment_child.addAll(list_comment_child);

    }

    public void clearList() {
        this.list_comment.clear();
        this.list_comment_child.clear();
    }

    public void updateList(List<StoreBeen> list_comment,
                           List<List<StoreBeen>> list_comment_child) {
        this.list_comment.addAll(list_comment);
        this.list_comment_child.addAll(list_comment_child);
    }

    @Override
    public int getCount() {
        return list_comment.size();
    }

    @Override
    public Object getItem(int position) {
        return list_comment.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_comment_view, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.setData(position);
        return convertView;
    }

    public class ViewHolder {
        private ImageView iv_user_photo; // 品论者 头像
        private CheckBox mGood;
        private TextView tv_user_name; // 品论者 昵称
        private TextView tv_user_comment; // 品论者 一级品论内容
        private TextView tv_user_reply; // 品论者 二级品论内容
        private MyListView lv_user_comment_replys; // 品论者 二级品论内容列表
        private int position;

        public ViewHolder(View view) {
            iv_user_photo = (ImageView) view.findViewById(R.id.iv_user_photo);
            tv_user_name = (TextView) view
                    .findViewById(R.id.tv_user_name);
            tv_user_comment = (TextView) view
                    .findViewById(R.id.tv_user_comment);
            mGood = (CheckBox) view.findViewById(R.id.good);

            tv_user_reply = (TextView) view
                    .findViewById(R.id.tv_user_reply);
            lv_user_comment_replys = (MyListView) view
                    .findViewById(R.id.lv_user_comment_replys);
            tv_user_reply.setTag(position);

            mGood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    StoreBeen been = list_comment.get(position);
                    been.setShow(b);
                    list_comment.set(position,been);
                    notifyDataSetChanged();
                }
            });
        }

        public void setData(final int position) {
            this.position = position;
            tv_user_name.setText("Seedlings");
            mGood.setChecked(list_comment.get(position).isShow());
            mGood.setText(list_comment.get(position).isShow() ? "1" : "赞");
            tv_user_comment.setText(list_comment.get(position)
                    .getText());
            lv_user_comment_replys.setSelector(new ColorDrawable(
                    Color.TRANSPARENT));
            if (list_comment_child.get(position) != null)
                lv_user_comment_replys
                        .setAdapter(new CommentReplyAdapter(context,
                                list_comment_child.get(position)));

            tv_user_reply.setTag(position);
            tv_user_reply.setOnClickListener(myOnitemcListener);
        }
    }
}
