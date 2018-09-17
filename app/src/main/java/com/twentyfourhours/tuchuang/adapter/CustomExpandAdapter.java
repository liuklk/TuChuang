package com.twentyfourhours.tuchuang.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.model.bean.DataBean;

import java.util.List;

import static com.twentyfourhours.tuchuang.R.id.pic;

/**
 * Created by Administrator on 2018/1/22.
 */
public class CustomExpandAdapter implements ExpandableListAdapter{
    private LayoutInflater mInflater;
    private Context mContext;
    private List<DataBean> mBoxList;

    public CustomExpandAdapter(Context context, List<DataBean> box_list) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mBoxList = box_list;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public int getGroupCount() {
        return mBoxList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        return mBoxList.get(groupPosition).getData().size();
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mBoxList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mBoxList.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        ViewHolderBox holder = null;
        if (convertView == null) {
            holder = new ViewHolderBox();
            convertView = mInflater.inflate(R.layout.recycleview_item_parent, null);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            holder.pic = (ImageView) convertView.findViewById(pic);
            holder.expand = (ImageView) convertView.findViewById(R.id.expend);
            holder.parentDashedView = convertView.findViewById(R.id.parent_dashed_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderBox) convertView.getTag();
        }
        DataBean box = mBoxList.get(groupPosition);
        holder.pic.setImageResource(box.getPic());
        holder.title.setText(box.getTitle());
        holder.content.setText(box.getContent());
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderMail holder = null;
        if (convertView == null) {
            holder = new ViewHolderMail();
            convertView = mInflater.inflate(R.layout.recycleview_item_child, null);
//            holder.ck_mail = (CheckBox) convertView.findViewById(R.id.ck_mail);
//            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderMail) convertView.getTag();
        }
//        MailItem item = mBoxList.get(groupPosition).getData().get(childPosition);
//        holder.ck_mail.setFocusable(false);
//        holder.ck_mail.setFocusableInTouchMode(false);
//        holder.ck_mail.setText(item.getMsg());
//        holder.ck_mail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                MailBox box = mBoxList.get(groupPosition);
//                MailItem item = box.getData().get(childPosition);
//                String desc = String.format("您点击了%s的邮件，标题是%s", box.getName(), item.getMsg());
//                Toast.makeText(mContext, desc, Toast.LENGTH_LONG).show();
//            }
//        });
//        holder.tv_date.setText(item.getTime());
        return convertView;
    }

    //如果子条目需要响应点击事件，这里要返回true
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    public final class ViewHolderBox {
//        public ImageView iv_box;
//        public TextView tv_box;
//        public TextView tv_count;
//        private View view;
//        private LinearLayout oder;
        private TextView title;
        private TextView content;
        private ImageView expand;
        private ImageView pic;
        private View parentDashedView;
//        private View last_view;
//        private TextView first_view;
    }

    public final class ViewHolderMail {
//        public CheckBox ck_mail;
//        public TextView tv_date;
    }

//    @Override
//    public boolean onChildClick(ExpandableListView parent, View v,
//                                int groupPosition, int childPosition, long id) {
//        ViewHolderMail holder = (ViewHolderMail) v.getTag();
//        holder.ck_mail.setChecked(!(holder.ck_mail.isChecked()));
//        return true;
//    }

//    //如果返回true，就不会展示子列表
//    @Override
//    public boolean onGroupClick(ExpandableListView parent, View v,
//                                int groupPosition, long id) {
//        String desc = String.format("您点击了%s", mBoxList.get(groupPosition).getName());
//        Toast.makeText(mContext, desc, Toast.LENGTH_LONG).show();
//        return false;
//    }


}
