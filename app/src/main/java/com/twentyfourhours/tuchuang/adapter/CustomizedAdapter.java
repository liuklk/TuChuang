package com.twentyfourhours.tuchuang.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.model.bean.HotBeen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/4.
 */

public class CustomizedAdapter extends BaseAdapter {

    private Context mContext;

    public CustomizedAdapter(Context context) {
        mContext = context;
    }

    private List<HotBeen> data = new ArrayList<>();

    public void setData(List<HotBeen> data) {
        this.data = data;
        notifyDataSetChanged();
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

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_customized_view, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setData(data.get(position),position);
        return convertView;
    }

    class ViewHolder implements View.OnClickListener {
        TextView style;
        TextView size;
        ImageView select_view;
        ImageView image;
        ImageView pull_style;
        ImageView pull_size;
        HotBeen mHotBeen;
        RelativeLayout style_click;
        RelativeLayout size_click;
        int position;
        private ListView mListView1;
        private int mLastSelectedPosition=-1;
        private int mLastSelectedPosition2=-1;
        private String[] types = {"薄款", "男款", "女款", "厚款"};
        private String[] sizes = {"L", "XL", "M", "XXL"};
        private PopupWindow mPopupWindow1;
        private boolean isPop;

        public ViewHolder(View view) {
            image = (ImageView) view.findViewById(R.id.image);
            style = (TextView) view.findViewById(R.id.style);
            size = (TextView) view.findViewById(R.id.size);
            select_view = (ImageView) view.findViewById(R.id.select_view);
            pull_style = (ImageView) view.findViewById(R.id.pull_style);
            pull_size = (ImageView) view.findViewById(R.id.pull_size);
            style_click = (RelativeLayout) view.findViewById(R.id.style_click);
            size_click = (RelativeLayout) view.findViewById(R.id.size_click);
            style_click.setOnClickListener(this);
            size_click.setOnClickListener(this);
        }

        public void setData(HotBeen hotBeen,int position) {
            this.mHotBeen = hotBeen;
            this.position=position;
            select_view.setVisibility(hotBeen.isShow()?View.VISIBLE:View.GONE);
            style.setText(hotBeen.getStyle());
            size.setText(hotBeen.getSize());
            style.setSelected(hotBeen.isShow1());
            size.setSelected(hotBeen.isShow2());
        }

        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.style_click) {
                showPopWindow(style_click,pull_style,types,true);
            }else if (v.getId()==R.id.size_click){
                showPopWindow(size_click,pull_size,sizes,false);
            }
            notifyDataSetChanged();
        }

        private void showPopWindow(RelativeLayout style_click, final ImageView pull_style, final String[] types, final boolean isStyle) {

            mListView1 = new ListView(mContext);
            mListView1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            mListView1.setPadding(getResources().getDisplayMetrics().widthPixels * 3 / 50, getResources().getDisplayMetrics().widthPixels  / 50, getResources().getDisplayMetrics().widthPixels * 3 / 50, getResources().getDisplayMetrics().widthPixels * 3 / 50);
            mListView1.setBackground(mContext.getResources().getDrawable(R.drawable.customized_style_bg));
            mListView1.setDivider(null);
            CustomizedAdapter.MyAdapter adapter = new CustomizedAdapter.MyAdapter();
            if (isStyle) {
                if (mLastSelectedPosition != -1) {
                    adapter.setPosition(mLastSelectedPosition);
                }
            }else {
                if (mLastSelectedPosition2 != -1) {
                    adapter.setPosition(mLastSelectedPosition2);
                }
            }
            adapter.setData(types);
            mListView1.setAdapter(adapter);
            mPopupWindow1 = new PopupWindow(mListView1, image.getMeasuredWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);
            ////让pop可以点击外面消失掉
            mPopupWindow1.setBackgroundDrawable(new ColorDrawable(0));
            mPopupWindow1.showAsDropDown(style_click);
            mPopupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {

                @Override
                public void onDismiss() {
                    ObjectAnimator anim = ObjectAnimator.ofFloat(pull_style, "rotation", -180f, -0);
                    anim.setDuration(150);
                    anim.start();
//                    mTabs.setVisibility(View.VISIBLE);
//                    mPindao.setVisibility(View.INVISIBLE);
                    isPop = !isPop;
                    mPopupWindow1.dismiss();
                }
            });
            mPopupWindow1.setTouchInterceptor(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                        mPopupWindow1.dismiss();
                        return true;
                    }
                    return false;
                }
            });
            mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //如果没有发生切换，就不去处理

                    if ((isStyle&&i != mLastSelectedPosition)||(!isStyle&&i!=mLastSelectedPosition2)) {
                        //选项的切换
                        //将点击位置的条目变成enable
                        View checkTextView = adapterView.getChildAt(i);
                        TextView viewById = (TextView) checkTextView.findViewById(R.id.text_view);
                        viewById.setTextColor(mContext.getResources().getColor(R.color.yellow_dot));
                        if (isStyle) {
                            if (mLastSelectedPosition!=-1) {
                                //将上次选中的条目变成disable
                                View oldView = adapterView.getChildAt(mLastSelectedPosition);
                                TextView textView = (TextView) oldView.findViewById(R.id.text_view);
                                textView.setTextColor(mContext.getResources().getColor(R.color.black_bg));

                            }
                        }else {
                            if (mLastSelectedPosition2!=-1) {
                                //将上次选中的条目变成disable
                                View oldView = adapterView.getChildAt(mLastSelectedPosition2);
                                TextView textView = (TextView) oldView.findViewById(R.id.text_view);
                                textView.setTextColor(mContext.getResources().getColor(R.color.black_bg));

                            }
                        }

                        if (isStyle) {
                            mHotBeen.setStyle(types[i]);
                            style.setTextColor(mContext.getResources().getColor(R.color.yellow_dot));
                            mHotBeen.setShow1(true);
                            //更新上次选中的位置
                            mLastSelectedPosition = i;
                        }else {
                            mHotBeen.setSize(types[i]);
                            size.setTextColor(mContext.getResources().getColor(R.color.yellow_dot));
                            mHotBeen.setShow2(true);
                            //更新上次选中的位置
                            mLastSelectedPosition2 = i;
                        }

                        notifyDataSetChanged();
                    }
                    mPopupWindow1.dismiss();
                }
            });
            if (isPop) {
                mPopupWindow1.dismiss();
            } else {
                ObjectAnimator anim = ObjectAnimator.ofFloat(pull_style, "rotation", 0f, -180f);
                anim.setDuration(150);
                anim.start();

                isPop = !isPop;
            }
        }
    }

    private class MyAdapter extends BaseAdapter {
        private String[] data;
        private int position=-1;

        public void setPosition(int position) {
            this.position = position;
        }

        public void setData(String[] data) {
            this.data = data;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int i) {
            return data[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            CustomizedAdapter.ViewStyleHolder holder;
            if (view == null) {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_list, null);
                holder = new CustomizedAdapter.ViewStyleHolder(view);
                view.setTag(holder);
            } else {
                holder = (CustomizedAdapter.ViewStyleHolder) view.getTag();
            }
            holder.type.setText(data[i]);
            holder.type.setTextColor(i == position ? mContext.getResources().getColor(R.color.yellow_dot) : mContext.getResources().getColor(R.color.black_bg));
//            holder.type.setTextColor(mContext.getResources().getColor(R.color.black_bg));
            return view;
        }
    }


    static class ViewStyleHolder {
        TextView type;

        ViewStyleHolder(View view) {
            type = (TextView) view.findViewById(R.id.text_view);
        }
    }
}
