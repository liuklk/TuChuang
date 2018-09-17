package com.twentyfourhours.tuchuang.adapter;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.model.bean.StoreBeen;
import com.twentyfourhours.tuchuang.ui.activity.CartActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/8.
 */
public class CartStoreAdapter extends BaseAdapter{

    private Context mContext;

    private List<StoreBeen> data=new ArrayList<>();

    public List<StoreBeen> getData() {
        return data;
    }

    public void setData(List<StoreBeen> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public CartStoreAdapter(Context context) {
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_cart_store_view,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.setData(data.get(position),position);
        return convertView;
    }

    class ViewHolder implements View.OnClickListener {
        CheckBox mCheckBox;
        TextView mAdd;
        TextView mMinus;
        TextView mShopNumber;
        TextView mDeletePrice;
        Button mDeleteBtn;
        RelativeLayout mRelativeLayout;
        boolean isLeft;
        boolean isRight;
        StoreBeen storeBeen;
        int position;
        ObjectAnimator animator;
        ObjectAnimator animator2;

        public ViewHolder(final View view) {
            mCheckBox= (CheckBox) view.findViewById(R.id.check_box);
            mAdd= (TextView) view.findViewById(R.id.add_tv);
            mMinus= (TextView) view.findViewById(R.id.minus_tv);
            mShopNumber= (TextView) view.findViewById(R.id.shop_number_tv);
            mDeletePrice= (TextView) view.findViewById(R.id.delete_price);
            mDeleteBtn= (Button) view.findViewById(R.id.delete_btn);
            mRelativeLayout= (RelativeLayout) view.findViewById(R.id.rl_view);


            view.setOnTouchListener(new View.OnTouchListener() {

                private float mY;
                private float mX;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            mX = event.getX();
                            mY = event.getY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            float moveX = event.getX();
                            float moveY = event.getY();
                            if (mX-moveX>Math.abs(mY-moveY)&&!isLeft){
                                mDeleteBtn.setVisibility(View.VISIBLE);
                                storeBeen.setAnim(true);
                                animator = ObjectAnimator.ofFloat(mRelativeLayout, "translationX", 0, -mDeleteBtn.getMeasuredWidth());
                                animator.setDuration(100);
                                animator.start();
                                animator2 = ObjectAnimator.ofFloat(mDeleteBtn, "translationX", mDeleteBtn.getMeasuredWidth(), 0);
                                animator2.setDuration(100);
                                animator2.start();
                                isLeft=true;
                                isRight=false;
                            }
                            if ((moveX-mX>Math.abs(mY-moveY)&&!isRight)&&isLeft==true){
                                storeBeen.setAnim(false);
                                animator = ObjectAnimator.ofFloat(mRelativeLayout, "translationX", -mDeleteBtn.getMeasuredWidth(), 0);
                                animator.setDuration(100);
                                animator.start();
                                animator2 = ObjectAnimator.ofFloat(mDeleteBtn, "translationX", 0, mDeleteBtn.getMeasuredWidth());
                                animator2.setDuration(100);
                                animator2.start();
                                isRight=true;
                                isLeft=false;
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                    }
                    return true;
                }
            });

            mAdd.setOnClickListener(this);
            mMinus.setOnClickListener(this);
            mDeleteBtn.setOnClickListener(this);
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    storeBeen.setCheck(isChecked);
                    if (isChecked){
                        if (((CartActivity)mContext).mCartNumber!=null) {
                            ((CartActivity) mContext).mCartNumber.setText((Integer.parseInt(((CartActivity) mContext).mCartNumber.getText().toString()) + 1)+"");
                        }
                        if (((CartActivity)mContext).mTotalTv!=null) {
                            ((CartActivity) mContext).mTotalTv.setText((Integer.parseInt(((CartActivity) mContext).mTotalTv.getText().toString()) + storeBeen.getItemCount()*99) + "");
                        }
                    }else {
                        if (((CartActivity)mContext).mCartNumber!=null) {
                            ((CartActivity) mContext).mCartNumber.setText((Integer.parseInt(((CartActivity) mContext).mCartNumber.getText().toString()) - 1)+"");
                        }
                        if (((CartActivity)mContext).mTotalTv!=null) {
                            ((CartActivity) mContext).mTotalTv.setText((Integer.parseInt(((CartActivity) mContext).mTotalTv.getText().toString()) - storeBeen.getItemCount()*99) + "");
                        }
                    }
                    notifyDataSetChanged();
                }
            });
        }

        public void setData(StoreBeen storeBeen,int position) {
            this.position=position;
            this.storeBeen=storeBeen;
            mCheckBox.setChecked(storeBeen.isCheck());
            mDeletePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            mShopNumber.setText(storeBeen.getItemCount()+"");
//            if (storeBeen.isAnim()){
//                animator = ObjectAnimator.ofFloat(mRelativeLayout, "translationX", 0, -260f);
//                animator.start();
//                animator.setDuration(0);
//                animator2 = ObjectAnimator.ofFloat(mDeleteBtn, "translationX", 260f, 0);
//                animator2.setDuration(0);
//                animator2.start();
//            }else {
//                animator = ObjectAnimator.ofFloat(mRelativeLayout, "translationX", -260f, 0);
//                animator.setDuration(0);
//                animator.start();
//                animator2 = ObjectAnimator.ofFloat(mDeleteBtn, "translationX", 0, 260f);
//                animator2.setDuration(0);
//                animator2.start();
//            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.add_tv:
                    if (storeBeen.getItemCount()==0){
                        return;
                    }
                    storeBeen.setItemCount(storeBeen.getItemCount()-1);
                    if (storeBeen.isCheck()&&((CartActivity)mContext).mTotalTv!=null) {
                        ((CartActivity) mContext).mTotalTv.setText((Integer.parseInt(((CartActivity) mContext).mTotalTv.getText().toString()) - 99) + "");
                    }
                    break;
                case R.id.minus_tv:
                    storeBeen.setItemCount(storeBeen.getItemCount()+1);
                    if (storeBeen.isCheck()&&((CartActivity)mContext).mTotalTv!=null) {
                        ((CartActivity) mContext).mTotalTv.setText((Integer.parseInt(((CartActivity) mContext).mTotalTv.getText().toString()) + 99) + "");
                    }
                    break;
                case R.id.delete_btn:
                    showDialog();
                    break;
            }
            notifyDataSetChanged();
        }

        private void showDialog() {
            AlertDialog dialog=new AlertDialog.Builder(mContext)
                    .setTitle("删除")
                    .setMessage("确认删除?")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            animator = ObjectAnimator.ofFloat(mRelativeLayout, "translationX", -mDeleteBtn.getMeasuredWidth(), 0);//260f
                            animator.setDuration(0);
                            animator.start();
                            animator2 = ObjectAnimator.ofFloat(mDeleteBtn, "translationX", 0, mDeleteBtn.getMeasuredWidth());
                            animator2.setDuration(0);
                            animator2.start();
                            isRight=true;
                            isLeft=false;
                            if (((CartActivity)mContext).mCartNumber!=null) {
                                ((CartActivity) mContext).mCartNumber.setText((Integer.parseInt(((CartActivity) mContext).mCartNumber.getText().toString()) - 1)+"");
                            }
                            if (((CartActivity)mContext).mTotalTv!=null) {
                                ((CartActivity) mContext).mTotalTv.setText((Integer.parseInt(((CartActivity) mContext).mTotalTv.getText().toString()) - storeBeen.getItemCount()*99) + "");
                            }
                            if (animator!=null) {
                                animator=null;
                            }
                            if (animator2!=null) {
                                animator2=null;
                            }
                            data.remove(position);
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
    }
}
