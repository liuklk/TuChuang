package com.twentyfourhours.tuchuang.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.model.bean.Product;
import com.twentyfourhours.tuchuang.ui.activity.CommunityDetailActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/2/7.
 */
public class MasonryAdapter extends RecyclerView.Adapter<MasonryAdapter.MasonryView>{
    private List<Product> products;

    public MasonryAdapter(Context context) {
        mContext = context;
    }

    private Context mContext;

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }



    @Override
    public MasonryView onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.masonry_item, viewGroup, false);
        return new MasonryView(view);
    }

    @Override
    public void onBindViewHolder(MasonryView masonryView, int position) {
        masonryView.setData(products.get(position),position);
//        masonryView.textView.setText(products.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class MasonryView extends  RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        CheckBox good;
        View view;
        Product mProduct;

        public MasonryView(View itemView){
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.masonry_item_img );
            textView= (TextView) itemView.findViewById(R.id.masonry_item_title);
            good= (CheckBox) itemView.findViewById(R.id.good);
            view= itemView.findViewById(R.id.hide_view);

            good.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mProduct.setGood(isChecked);
                    mProduct.setTitle(isChecked?"1":"赞");
                    good.setText(isChecked?"1":"赞");
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,CommunityDetailActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }

        public void setData(Product product,int position) {
            mProduct=product;
            good.setText(product.getTitle());
            imageView.setImageResource(product.getImg());
            good.setChecked(product.isGood());
            view.setVisibility(position==1?View.VISIBLE:View.GONE);
        }
    }

}
