package com.twentyfourhours.tuchuang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.model.bean.StoreBeen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/8.
 */
public class SelectVideoAdapter extends BaseAdapter{

    private Context mContext;

    private List<StoreBeen> data=new ArrayList<>();

    public void setData(List<StoreBeen> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public SelectVideoAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        if (data!=null){
            return data.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (data!=null){
            return data.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_select_video_view,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.setData(data.get(position),position);
        return convertView;
    }

    class ViewHolder{
        TextView mTime;
        ImageView mImg;
        int position;

        public ViewHolder(View view) {
            mImg= (ImageView) view.findViewById(R.id.img);
            mTime= (TextView) view.findViewById(R.id.time);
        }

        public void setData(StoreBeen storeBeen,int position) {
            this.position=position;
            int time = Integer.parseInt(storeBeen.getDuration());
            int h = time / 1000 / 60 / 60;
            int m = time / 1000 / 60;
            int s = time / 1000 % 60;
            mTime.setText(h+":"+m+":"+s);
            mImg.setImageBitmap(storeBeen.getBitmap());
        }
    }
}
