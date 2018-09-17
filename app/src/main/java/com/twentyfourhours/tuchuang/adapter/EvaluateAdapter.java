package com.twentyfourhours.tuchuang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.image.ImagePagerActivity;
import com.twentyfourhours.tuchuang.image.MyGridAdapter;
import com.twentyfourhours.tuchuang.image.NoScrollGridView;
import com.twentyfourhours.tuchuang.model.bean.StoreBeen;

import java.util.ArrayList;
import java.util.List;

import static com.twentyfourhours.tuchuang.R.id.gridView;

/**
 * Created by Administrator on 2018/1/8.
 */
public class EvaluateAdapter extends BaseAdapter{

    private Context mContext;
    private String[] urls;

    private List<StoreBeen> data=new ArrayList<>();

    public List<StoreBeen> getData() {
        return data;
    }

    public void setData(List<StoreBeen> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public EvaluateAdapter(Context context,String[] urls) {
        mContext = context;
        this.urls=urls;
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_evaluate_view,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.setData(data.get(position),position);
        return convertView;
    }

    class ViewHolder{
        NoScrollGridView mGridView;
        LinearLayout mStars;
        public ViewHolder(View view) {
            mGridView= (NoScrollGridView) view.findViewById(gridView);
            mStars= (LinearLayout) view.findViewById(R.id.star);

            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    imageBrower(position,urls);
                }
            });
        }

        private void imageBrower(int position, String[] urls) {
            Intent intent = new Intent(mContext, ImagePagerActivity.class);
            // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
            mContext.startActivity(intent);
        }

        public void setData(StoreBeen storeBeen,int position) {
            mGridView.setAdapter(new MyGridAdapter(urls, mContext));
            mGridView.setVisibility(storeBeen.isCheck()?View.VISIBLE:View.GONE);
            for (int i = 0; i < mStars.getChildCount(); i++) {
                if (i!=mStars.getChildCount()-1){
                    ImageView select = (ImageView) mStars.getChildAt(i);
                    select.setSelected(true);
                }
            }
        }

    }
}
