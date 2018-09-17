package com.twentyfourhours.tuchuang.select.list.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2018/3/17.
 */

public class CleanableEditView extends AppCompatEditText implements TextWatcher ,View.OnFocusChangeListener {
    private Drawable left;
    private Drawable right;
    private boolean hasFocus;
    private int xUp;

    public CleanableEditView(Context context) {
        this(context, (AttributeSet)null);
    }

    public CleanableEditView(Context context, AttributeSet attrs) {
        this(context, attrs, 16842862);
    }

    public CleanableEditView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.hasFocus = false;
        this.xUp = 0;
        this.initWedgits(attrs);
    }

    private void initWedgits(AttributeSet attrs) {
        try {
            this.left = this.getCompoundDrawables()[0];
            this.right = this.getCompoundDrawables()[2];
            this.initDatas();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    private void initDatas() {
        try {
            this.setCompoundDrawablesWithIntrinsicBounds(this.left, (Drawable)null, (Drawable)null, (Drawable)null);
            this.addListeners();
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    private void addListeners() {
        try {
            this.setOnFocusChangeListener(this);
            this.addTextChangedListener(this);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int after) {
        if(this.hasFocus) {
            if(TextUtils.isEmpty(s)) {
                this.setCompoundDrawablesWithIntrinsicBounds(this.left, (Drawable)null, (Drawable)null, (Drawable)null);
            } else {
                if(null == this.right) {
                    this.right = this.getCompoundDrawables()[2];
                }

                this.setCompoundDrawablesWithIntrinsicBounds(this.left, (Drawable)null, this.right, (Drawable)null);
            }
        }

    }

    public boolean onTouchEvent(MotionEvent event) {
        try {
            switch(event.getAction()) {
                case 1:
                    this.xUp = (int)event.getX();
                    if(this.getWidth() - this.xUp <= this.getCompoundPaddingRight() && !TextUtils.isEmpty(this.getText().toString())) {
                        this.setText("");
                    }
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return super.onTouchEvent(event);
    }

    public void afterTextChanged(Editable s) {
    }

    public void onFocusChange(View v, boolean hasFocus) {
        try {
            this.hasFocus = hasFocus;
            String e = this.getText().toString();
            if(hasFocus) {
                if(e.equalsIgnoreCase("")) {
                    this.setCompoundDrawablesWithIntrinsicBounds(this.left, (Drawable)null, (Drawable)null, (Drawable)null);
                } else {
                    this.setCompoundDrawablesWithIntrinsicBounds(this.left, (Drawable)null, this.right, (Drawable)null);
                }
            }

            if(!hasFocus) {
                this.setCompoundDrawablesWithIntrinsicBounds(this.left, (Drawable)null, (Drawable)null, (Drawable)null);
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public String text_String() {
        return this.getText().toString();
    }
}
