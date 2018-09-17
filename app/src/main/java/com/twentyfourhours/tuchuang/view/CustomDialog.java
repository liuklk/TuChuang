package com.twentyfourhours.tuchuang.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.twentyfourhours.tuchuang.R;

/**
 * Created by Administrator on 2017/10/24 0024.
 */

public class CustomDialog extends Dialog {

    public CustomDialog(Context context) {
        super(context, R.style.CustomDialog);
        init();
    }

    private void init() {
        Window window = this.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.windowAnimations = R.style.MyDialogAnimation;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);
        show();
    }
}
