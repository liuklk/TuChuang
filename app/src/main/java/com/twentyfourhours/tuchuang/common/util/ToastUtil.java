package com.twentyfourhours.tuchuang.common.util;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.twentyfourhours.tuchuang.R;

/**
 * Created by Twentyfourhours on 2018/1/4.
 */

public class ToastUtil {

    private static Toast sToast;
    private static Handler mHandler=new Handler();

    public static void toast(Context context,String msg){
        if (sToast==null){
            sToast=Toast.makeText(context,msg,Toast.LENGTH_SHORT);
        }else {
            sToast.setText(msg);
        }
        sToast.show();
    }

    public static void showStatuDialog(Context context, final String title, final OnActionListener listener) {
        // 1. 布局文件转换为View对象
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.load_success, null);
        final TextView content = (TextView) layout.findViewById(R.id.content_toast);
        final ImageView icon = (ImageView) layout.findViewById(R.id.icon_toast);
        final ProgressBar progressBar = (ProgressBar) layout.findViewById(R.id.progress_bar);

// 2. 新建对话框对象
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialog);
        //通过setView设置我们自己的布局
        builder.setView(layout);
        final AlertDialog dialog = builder.create();
        dialog.show();
        //此处设置位置窗体大小
        dialog.getWindow().setLayout(DensityUtil.dip2px(context, 180), DensityUtil.dip2px(context, 140));
        content.setText("正在"+title+"...");
        progressBar.setVisibility(View.VISIBLE);
        icon.setVisibility(View.GONE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (listener!=null){
                    listener.action();
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        icon.setVisibility(View.VISIBLE);
                        content.setText(title+"成功");
                        icon.setImageResource(R.drawable.icon_success);
                    }
                });
                SystemClock.sleep(1000);
                dialog.dismiss();
            }
        }).start();
        dialog.setCanceledOnTouchOutside(false);
    }

    public interface OnActionListener{
        void action();
    }
}
