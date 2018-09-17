package com.twentyfourhours.tuchuang.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2018/2/9.
 */

public class ObservableCoordinatorLayout extends CoordinatorLayout {
    private ObservableCoordinatorLayout.OnScollChangedListener onScollChangedListener = null;

    public ObservableCoordinatorLayout(Context context) {
        super(context);
    }

    public ObservableCoordinatorLayout(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
    }

    public ObservableCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnScollChangedListener(ObservableCoordinatorLayout.OnScollChangedListener onScollChangedListener) {
        this.onScollChangedListener = onScollChangedListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (onScollChangedListener != null) {
            onScollChangedListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

    public interface OnScollChangedListener {

        void onScrollChanged(ObservableCoordinatorLayout scrollView, int x, int y, int oldx, int oldy);

    }
}
