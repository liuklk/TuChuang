package com.twentyfourhours.tuchuang.select.list.Toast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.twentyfourhours.tuchuang.R;

/**
 * Created by Administrator on 2018/3/17.
 */

public class AlarmDailog extends Toast {
    private Toast toast;
    private Context context;
    private TextView noticeText;

    public AlarmDailog(Context context) {
        super(context);
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.dialog_alarm_ui, (ViewGroup)null);
        this.noticeText = (TextView)layout.findViewById(R.id.noticeText);
        this.toast = new Toast(context);
        this.toast.setGravity(17, 0, 0);
        this.toast.setDuration(Toast.LENGTH_SHORT);
        this.toast.setView(layout);
    }

    public void setShowText(String dialogNotice) {
        this.noticeText.setText(dialogNotice);
    }

    public void show() {
        this.toast.show();
    }
}
