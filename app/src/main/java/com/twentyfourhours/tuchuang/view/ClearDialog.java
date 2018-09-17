package com.twentyfourhours.tuchuang.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/10/24 0024.
 */

public class ClearDialog extends Dialog {

    MyDialogClickListener myDialogClickListener;
    @BindView(R.id.clear_cache)
    TextView mClearCache;
    @BindView(R.id.cancel)
    TextView mCancel;

    public ClearDialog(Context context, MyDialogClickListener myDialogClickListener) {
        super(context, R.style.MyDialogStyle);
        this.myDialogClickListener = myDialogClickListener;
        init();
    }

    private void init() {
        Window window = this.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_logout_confirm);
        ButterKnife.bind(this);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.windowAnimations = R.style.MyDialogAnimation;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);
        show();
    }

    @OnClick({R.id.clear_cache, R.id.cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.clear_cache:
                dismiss();
                myDialogClickListener.clearCache();
                break;
            case R.id.cancel:
                dismiss();
                break;
        }
    }

    public interface MyDialogClickListener {
        void clearCache();
    }
}
