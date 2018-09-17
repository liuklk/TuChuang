package com.twentyfourhours.tuchuang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.model.bean.StoreBeen;

import java.util.List;

public class CommentReplyAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<StoreBeen> list; // 二级评论数据
	private ViewHolder viewHolder;

	public CommentReplyAdapter(Context context,
							   List<StoreBeen> list) {
		inflater = LayoutInflater.from(context);
		// this.list = new ArrayList<HashMap<String, Object>>();
		// this.list.addAll(list);
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	public void clearList() {
		this.list.clear();
	}

	public void updateList(List<StoreBeen> list_comment) {
		this.list.addAll(list_comment);
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_comment_back_view, null);
			viewHolder = new ViewHolder();
			viewHolder.tv_comment_reply_text = (TextView) convertView
					.findViewById(R.id.tv_comment_reply_text);
			viewHolder.tv_comment_reply_writer = (TextView) convertView
					.findViewById(R.id.tv_comment_reply_writer);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
			
		}
		viewHolder.tv_comment_reply_text.setText(list.get(position)
				.getText());
		// viewHolder.tv_comment_reply_writer.setText(list.get(position)
		// .get("user").toString());

		return convertView;
	}

	public class ViewHolder {
		private TextView tv_comment_reply_writer; // 评论者昵称
		private TextView tv_comment_reply_text; // 评论 内容
	}
}
