package com.twentyfourhours.tuchuang.select.pickerview.widget.wheel;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.select.pickerview.widget.wheel.adapters.WheelViewAdapter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/17.
 */

public class WheelView extends View {
    private int[] SHADOWS_COLORS = new int[]{-269882903, -806753815, 1072294377};
    private static final int ITEM_OFFSET_PERCENT = 0;
    private static final int PADDING = 5;
    private static final int DEF_VISIBLE_ITEMS = 5;
    private int currentItem = 0;
    private int visibleItems = 5;
    private int itemHeight = 0;
    private Drawable centerDrawable;
    private int wheelBackground;
    private int wheelForeground;
    private GradientDrawable topShadow;
    private GradientDrawable bottomShadow;
    private boolean drawShadows;
    private WheelScroller scroller;
    private boolean isScrollingPerformed;
    private int scrollingOffset;
    boolean isCyclic;
    private LinearLayout itemsLayout;
    private int firstItem;
    private WheelViewAdapter viewAdapter;
    private WheelRecycle recycle;
    private List<OnWheelChangedListener> changingListeners;
    private List<OnWheelScrollListener> scrollingListeners;
    private List<OnWheelClickedListener> clickingListeners;
    WheelScroller.ScrollingListener scrollingListener;
    private DataSetObserver dataObserver;

    public WheelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.wheelBackground = R.drawable.wheel_bg;
        this.wheelForeground = R.drawable.wheel_val;
        this.drawShadows = true;
        this.isCyclic = false;
        this.recycle = new WheelRecycle(this);
        this.changingListeners = new LinkedList();
        this.scrollingListeners = new LinkedList();
        this.clickingListeners = new LinkedList();
        this.scrollingListener = new WheelScroller.ScrollingListener() {
            public void onStarted() {
                WheelView.this.isScrollingPerformed = true;
                WheelView.this.notifyScrollingListenersAboutStart();
            }

            public void onScroll(int distance) {
                WheelView.this.doScroll(distance);
                int height = WheelView.this.getHeight();
                if(WheelView.this.scrollingOffset > height) {
                    WheelView.this.scrollingOffset = height;
                    WheelView.this.scroller.stopScrolling();
                } else if(WheelView.this.scrollingOffset < -height) {
                    WheelView.this.scrollingOffset = -height;
                    WheelView.this.scroller.stopScrolling();
                }

            }

            public void onFinished() {
                if(WheelView.this.isScrollingPerformed) {
                    WheelView.this.notifyScrollingListenersAboutEnd();
                    WheelView.this.isScrollingPerformed = false;
                }

                WheelView.this.scrollingOffset = 0;
                WheelView.this.invalidate();
            }

            public void onJustify() {
                if(Math.abs(WheelView.this.scrollingOffset) > 1) {
                    WheelView.this.scroller.scroll(WheelView.this.scrollingOffset, 0);
                }

            }
        };
        this.dataObserver = new DataSetObserver() {
            public void onChanged() {
                WheelView.this.invalidateWheel(false);
            }

            public void onInvalidated() {
                WheelView.this.invalidateWheel(true);
            }
        };
        this.initData(context);
    }

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.wheelBackground = R.drawable.wheel_bg;
        this.wheelForeground = R.drawable.wheel_val;
        this.drawShadows = true;
        this.isCyclic = false;
        this.recycle = new WheelRecycle(this);
        this.changingListeners = new LinkedList();
        this.scrollingListeners = new LinkedList();
        this.clickingListeners = new LinkedList();
        this.scrollingListener = new WheelScroller.ScrollingListener() {
            public void onStarted() {
                WheelView.this.isScrollingPerformed = true;
                WheelView.this.notifyScrollingListenersAboutStart();
            }

            public void onScroll(int distance) {
                WheelView.this.doScroll(distance);
                int height = WheelView.this.getHeight();
                if(WheelView.this.scrollingOffset > height) {
                    WheelView.this.scrollingOffset = height;
                    WheelView.this.scroller.stopScrolling();
                } else if(WheelView.this.scrollingOffset < -height) {
                    WheelView.this.scrollingOffset = -height;
                    WheelView.this.scroller.stopScrolling();
                }

            }

            public void onFinished() {
                if(WheelView.this.isScrollingPerformed) {
                    WheelView.this.notifyScrollingListenersAboutEnd();
                    WheelView.this.isScrollingPerformed = false;
                }

                WheelView.this.scrollingOffset = 0;
                WheelView.this.invalidate();
            }

            public void onJustify() {
                if(Math.abs(WheelView.this.scrollingOffset) > 1) {
                    WheelView.this.scroller.scroll(WheelView.this.scrollingOffset, 0);
                }

            }
        };
        this.dataObserver = new DataSetObserver() {
            public void onChanged() {
                WheelView.this.invalidateWheel(false);
            }

            public void onInvalidated() {
                WheelView.this.invalidateWheel(true);
            }
        };
        this.initData(context);
    }

    public WheelView(Context context) {
        super(context);
        this.wheelBackground = R.drawable.wheel_bg;
        this.wheelForeground = R.drawable.wheel_val;
        this.drawShadows = true;
        this.isCyclic = false;
        this.recycle = new WheelRecycle(this);
        this.changingListeners = new LinkedList();
        this.scrollingListeners = new LinkedList();
        this.clickingListeners = new LinkedList();
        this.scrollingListener = new WheelScroller.ScrollingListener() {
            public void onStarted() {
                WheelView.this.isScrollingPerformed = true;
                WheelView.this.notifyScrollingListenersAboutStart();
            }

            public void onScroll(int distance) {
                WheelView.this.doScroll(distance);
                int height = WheelView.this.getHeight();
                if(WheelView.this.scrollingOffset > height) {
                    WheelView.this.scrollingOffset = height;
                    WheelView.this.scroller.stopScrolling();
                } else if(WheelView.this.scrollingOffset < -height) {
                    WheelView.this.scrollingOffset = -height;
                    WheelView.this.scroller.stopScrolling();
                }

            }

            public void onFinished() {
                if(WheelView.this.isScrollingPerformed) {
                    WheelView.this.notifyScrollingListenersAboutEnd();
                    WheelView.this.isScrollingPerformed = false;
                }

                WheelView.this.scrollingOffset = 0;
                WheelView.this.invalidate();
            }

            public void onJustify() {
                if(Math.abs(WheelView.this.scrollingOffset) > 1) {
                    WheelView.this.scroller.scroll(WheelView.this.scrollingOffset, 0);
                }

            }
        };
        this.dataObserver = new DataSetObserver() {
            public void onChanged() {
                WheelView.this.invalidateWheel(false);
            }

            public void onInvalidated() {
                WheelView.this.invalidateWheel(true);
            }
        };
        this.initData(context);
    }

    private void initData(Context context) {
        this.scroller = new WheelScroller(this.getContext(), this.scrollingListener);
    }

    public void setInterpolator(Interpolator interpolator) {
        this.scroller.setInterpolator(interpolator);
    }

    public int getVisibleItems() {
        return this.visibleItems;
    }

    public void setVisibleItems(int count) {
        this.visibleItems = count;
    }

    public WheelViewAdapter getViewAdapter() {
        return this.viewAdapter;
    }

    public void setViewAdapter(WheelViewAdapter viewAdapter) {
        if(this.viewAdapter != null) {
            this.viewAdapter.unregisterDataSetObserver(this.dataObserver);
        }

        this.viewAdapter = viewAdapter;
        if(this.viewAdapter != null) {
            this.viewAdapter.registerDataSetObserver(this.dataObserver);
        }

        this.invalidateWheel(true);
    }

    public void addChangingListener(OnWheelChangedListener listener) {
        this.changingListeners.add(listener);
    }

    public void removeChangingListener(OnWheelChangedListener listener) {
        this.changingListeners.remove(listener);
    }

    protected void notifyChangingListeners(int oldValue, int newValue) {
        Iterator var3 = this.changingListeners.iterator();

        while(var3.hasNext()) {
            OnWheelChangedListener listener = (OnWheelChangedListener)var3.next();
            listener.onChanged(this, oldValue, newValue);
        }

    }

    public void addScrollingListener(OnWheelScrollListener listener) {
        this.scrollingListeners.add(listener);
    }

    public void removeScrollingListener(OnWheelScrollListener listener) {
        this.scrollingListeners.remove(listener);
    }

    protected void notifyScrollingListenersAboutStart() {
        Iterator var1 = this.scrollingListeners.iterator();

        while(var1.hasNext()) {
            OnWheelScrollListener listener = (OnWheelScrollListener)var1.next();
            listener.onScrollingStarted(this);
        }

    }

    protected void notifyScrollingListenersAboutEnd() {
        Iterator var1 = this.scrollingListeners.iterator();

        while(var1.hasNext()) {
            OnWheelScrollListener listener = (OnWheelScrollListener)var1.next();
            listener.onScrollingFinished(this);
        }

    }

    public void addClickingListener(OnWheelClickedListener listener) {
        this.clickingListeners.add(listener);
    }

    public void removeClickingListener(OnWheelClickedListener listener) {
        this.clickingListeners.remove(listener);
    }

    protected void notifyClickListenersAboutClick(int item) {
        Iterator var2 = this.clickingListeners.iterator();

        while(var2.hasNext()) {
            OnWheelClickedListener listener = (OnWheelClickedListener)var2.next();
            listener.onItemClicked(this, item);
        }

    }

    public int getCurrentItem() {
        return this.currentItem;
    }

    public void setCurrentItem(int index, boolean animated) {
        if(this.viewAdapter != null && this.viewAdapter.getItemsCount() != 0) {
            int itemCount = this.viewAdapter.getItemsCount();
            if(index < 0 || index >= itemCount) {
                if(!this.isCyclic) {
                    return;
                }

                while(index < 0) {
                    index += itemCount;
                }

                index %= itemCount;
            }

            if(index != this.currentItem) {
                int old;
                if(animated) {
                    old = index - this.currentItem;
                    if(this.isCyclic) {
                        int scroll = itemCount + Math.min(index, this.currentItem) - Math.max(index, this.currentItem);
                        if(scroll < Math.abs(old)) {
                            old = old < 0?scroll:-scroll;
                        }
                    }

                    this.scroll(old, 0);
                } else {
                    this.scrollingOffset = 0;
                    old = this.currentItem;
                    this.currentItem = index;
                    this.notifyChangingListeners(old, this.currentItem);
                    this.invalidate();
                }
            }

        }
    }

    public void setCurrentItem(int index) {
        this.setCurrentItem(index, false);
    }

    public boolean isCyclic() {
        return this.isCyclic;
    }

    public void setCyclic(boolean isCyclic) {
        this.isCyclic = isCyclic;
        this.invalidateWheel(false);
    }

    public boolean drawShadows() {
        return this.drawShadows;
    }

    public void setDrawShadows(boolean drawShadows) {
        this.drawShadows = drawShadows;
    }

    public void setShadowColor(int start, int middle, int end) {
        this.SHADOWS_COLORS = new int[]{start, middle, end};
    }

    public void setWheelBackground(int resource) {
        this.wheelBackground = resource;
        this.setBackgroundResource(this.wheelBackground);
    }

    public void setWheelForeground(int resource) {
        this.wheelForeground = resource;
        this.centerDrawable = this.getContext().getResources().getDrawable(this.wheelForeground);
    }

    public void invalidateWheel(boolean clearCaches) {
        if(clearCaches) {
            this.recycle.clearAll();
            if(this.itemsLayout != null) {
                this.itemsLayout.removeAllViews();
            }

            this.scrollingOffset = 0;
        } else if(this.itemsLayout != null) {
            this.recycle.recycleItems(this.itemsLayout, this.firstItem, new ItemsRange());
        }

        this.invalidate();
    }

    private void initResourcesIfNecessary() {
        if(this.centerDrawable == null) {
            this.centerDrawable = this.getContext().getResources().getDrawable(this.wheelForeground);
        }

        if(this.topShadow == null) {
            this.topShadow = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, this.SHADOWS_COLORS);
        }

        if(this.bottomShadow == null) {
            this.bottomShadow = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, this.SHADOWS_COLORS);
        }

        this.setBackgroundResource(this.wheelBackground);
    }

    private int getDesiredHeight(LinearLayout layout) {
        if(layout != null && layout.getChildAt(0) != null) {
            this.itemHeight = layout.getChildAt(0).getMeasuredHeight();
        }

        int desired = this.itemHeight * this.visibleItems - this.itemHeight * 0 / 50;
        return Math.max(desired, this.getSuggestedMinimumHeight());
    }

    private int getItemHeight() {
        if(this.itemHeight != 0) {
            return this.itemHeight;
        } else if(this.itemsLayout != null && this.itemsLayout.getChildAt(0) != null) {
            this.itemHeight = this.itemsLayout.getChildAt(0).getHeight();
            return this.itemHeight;
        } else {
            return this.getHeight() / this.visibleItems;
        }
    }

    private int calculateLayoutWidth(int widthSize, int mode) {
        this.initResourcesIfNecessary();
        this.itemsLayout.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        this.itemsLayout.measure(MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        int width = this.itemsLayout.getMeasuredWidth();
        if(mode == 1073741824) {
            width = widthSize;
        } else {
            width += 10;
            width = Math.max(width, this.getSuggestedMinimumWidth());
            if(mode == -2147483648 && widthSize < width) {
                width = widthSize;
            }
        }

        this.itemsLayout.measure(MeasureSpec.makeMeasureSpec(width - 10, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        return width;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        this.buildViewForMeasuring();
        int width = this.calculateLayoutWidth(widthSize, widthMode);
        int height;
        if(heightMode == 1073741824) {
            height = heightSize;
        } else {
            height = this.getDesiredHeight(this.itemsLayout);
            if(heightMode == -2147483648) {
                height = Math.min(height, heightSize);
            }
        }

        this.setMeasuredDimension(width, height);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        this.layout(r - l, b - t);
    }

    private void layout(int width, int height) {
        int itemsWidth = width - 10;
        this.itemsLayout.layout(0, 0, itemsWidth, height);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(this.viewAdapter != null && this.viewAdapter.getItemsCount() > 0) {
            this.updateView();
            this.drawItems(canvas);
            this.drawCenterRect(canvas);
        }

        if(this.drawShadows) {
            this.drawShadows(canvas);
        }

    }

    private void drawShadows(Canvas canvas) {
        int count = this.getVisibleItems() == 2?1:this.getVisibleItems() / 2;
        int height = count * this.getItemHeight();
        this.topShadow.setBounds(0, 0, this.getWidth(), height);
        this.topShadow.draw(canvas);
        this.bottomShadow.setBounds(0, this.getHeight() - height, this.getWidth(), this.getHeight());
        this.bottomShadow.draw(canvas);
        Log.d("liji.wheel", "getItemHeight(): " + this.getItemHeight());
        Log.d("liji.wheel", "height: " + height);
        Log.d("liji.wheel", "getWidth: " + this.getWidth());
        Log.d("liji.wheel", "getHeight():" + this.getHeight());
        Log.d("liji.wheel", "visibleItems:" + this.visibleItems);
    }

    private void drawItems(Canvas canvas) {
        canvas.save();
        int top = (this.currentItem - this.firstItem) * this.getItemHeight() + (this.getItemHeight() - this.getHeight()) / 2;
        canvas.translate(5.0F, (float)(-top + this.scrollingOffset));
        this.itemsLayout.draw(canvas);
        canvas.restore();
    }

    private void drawCenterRect(Canvas canvas) {
        int center = this.getHeight() / 2;
        int offset = (int)((double)(this.getItemHeight() / 2) * 1.2D);
        Paint paint = new Paint();
        paint.setColor(this.getResources().getColor(R.color.province_line_border));
        paint.setStrokeWidth(3.0F);
        canvas.drawLine(0.0F, (float)(center - offset), (float)this.getWidth(), (float)(center - offset), paint);
        canvas.drawLine(0.0F, (float)(center + offset), (float)this.getWidth(), (float)(center + offset), paint);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if(this.isEnabled() && this.getViewAdapter() != null) {
            switch(event.getAction()) {
                case 1:
                    if(!this.isScrollingPerformed) {
                        int distance = (int)event.getY() - this.getHeight() / 2;
                        if(distance > 0) {
                            distance += this.getItemHeight() / 2;
                        } else {
                            distance -= this.getItemHeight() / 2;
                        }

                        int items = distance / this.getItemHeight();
                        if(items != 0 && this.isValidItemIndex(this.currentItem + items)) {
                            this.notifyClickListenersAboutClick(this.currentItem + items);
                        }
                    }
                    break;
                case 2:
                    if(this.getParent() != null) {
                        this.getParent().requestDisallowInterceptTouchEvent(true);
                    }
            }

            return this.scroller.onTouchEvent(event);
        } else {
            return true;
        }
    }

    private void doScroll(int delta) {
        this.scrollingOffset += delta;
        int itemHeight = this.getItemHeight();
        int count = this.scrollingOffset / itemHeight;
        int pos = this.currentItem - count;
        int itemCount = this.viewAdapter.getItemsCount();
        int fixPos = this.scrollingOffset % itemHeight;
        if(Math.abs(fixPos) <= itemHeight / 2) {
            fixPos = 0;
        }

        if(this.isCyclic && itemCount > 0) {
            if(fixPos > 0) {
                --pos;
                ++count;
            } else if(fixPos < 0) {
                ++pos;
                --count;
            }

            while(pos < 0) {
                pos += itemCount;
            }

            pos %= itemCount;
        } else if(pos < 0) {
            count = this.currentItem;
            pos = 0;
        } else if(pos >= itemCount) {
            count = this.currentItem - itemCount + 1;
            pos = itemCount - 1;
        } else if(pos > 0 && fixPos > 0) {
            --pos;
            ++count;
        } else if(pos < itemCount - 1 && fixPos < 0) {
            ++pos;
            --count;
        }

        int offset = this.scrollingOffset;
        if(pos != this.currentItem) {
            this.setCurrentItem(pos, false);
        } else {
            this.invalidate();
        }

        this.scrollingOffset = offset - count * itemHeight;
        if(this.scrollingOffset > this.getHeight()) {
            this.scrollingOffset = this.scrollingOffset % this.getHeight() + this.getHeight();
        }

    }

    public void scroll(int itemsToScroll, int time) {
        int distance = itemsToScroll * this.getItemHeight() - this.scrollingOffset;
        this.scroller.scroll(distance, time);
    }

    private ItemsRange getItemsRange() {
        if(this.getItemHeight() == 0) {
            return null;
        } else {
            int first = this.currentItem;

            int count;
            for(count = 1; count * this.getItemHeight() < this.getHeight(); count += 2) {
                --first;
            }

            if(this.scrollingOffset != 0) {
                if(this.scrollingOffset > 0) {
                    --first;
                }

                ++count;
                int emptyItems = this.scrollingOffset / this.getItemHeight();
                first -= emptyItems;
                count = (int)((double)count + Math.asin((double)emptyItems));
            }

            return new ItemsRange(first, count);
        }
    }

    private boolean rebuildItems() {
        boolean updated = false;
        ItemsRange range = this.getItemsRange();
        int first;
        if(this.itemsLayout != null) {
            first = this.recycle.recycleItems(this.itemsLayout, this.firstItem, range);
            updated = this.firstItem != first;
            this.firstItem = first;
        } else {
            this.createItemsLayout();
            updated = true;
        }

        if(!updated) {
            updated = this.firstItem != range.getFirst() || this.itemsLayout.getChildCount() != range.getCount();
        }

        if(this.firstItem > range.getFirst() && this.firstItem <= range.getLast()) {
            for(first = this.firstItem - 1; first >= range.getFirst() && this.addViewItem(first, true); this.firstItem = first--) {
                ;
            }
        } else {
            this.firstItem = range.getFirst();
        }

        first = this.firstItem;

        for(int i = this.itemsLayout.getChildCount(); i < range.getCount(); ++i) {
            if(!this.addViewItem(this.firstItem + i, false) && this.itemsLayout.getChildCount() == 0) {
                ++first;
            }
        }

        this.firstItem = first;
        return updated;
    }

    private void updateView() {
        if(this.rebuildItems()) {
            this.calculateLayoutWidth(this.getWidth(), 1073741824);
            this.layout(this.getWidth(), this.getHeight());
        }

    }

    private void createItemsLayout() {
        if(this.itemsLayout == null) {
            this.itemsLayout = new LinearLayout(this.getContext());
            this.itemsLayout.setOrientation(LinearLayout.VERTICAL);
        }

    }

    private void buildViewForMeasuring() {
        if(this.itemsLayout != null) {
            this.recycle.recycleItems(this.itemsLayout, this.firstItem, new ItemsRange());
        } else {
            this.createItemsLayout();
        }

        int addItems = this.visibleItems / 2;

        for(int i = this.currentItem + addItems; i >= this.currentItem - addItems; --i) {
            if(this.addViewItem(i, true)) {
                this.firstItem = i;
            }
        }

    }

    private boolean addViewItem(int index, boolean first) {
        View view = this.getItemView(index);
        if(view != null) {
            if(first) {
                this.itemsLayout.addView(view, 0);
            } else {
                this.itemsLayout.addView(view);
            }

            return true;
        } else {
            return false;
        }
    }

    private boolean isValidItemIndex(int index) {
        return this.viewAdapter != null && this.viewAdapter.getItemsCount() > 0 && (this.isCyclic || index >= 0 && index < this.viewAdapter.getItemsCount());
    }

    private View getItemView(int index) {
        if(this.viewAdapter != null && this.viewAdapter.getItemsCount() != 0) {
            int count = this.viewAdapter.getItemsCount();
            if(!this.isValidItemIndex(index)) {
                return this.viewAdapter.getEmptyItem(this.recycle.getEmptyItem(), this.itemsLayout);
            } else {
                while(index < 0) {
                    index += count;
                }

                index %= count;
                return this.viewAdapter.getItem(index, this.recycle.getItem(), this.itemsLayout);
            }
        } else {
            return null;
        }
    }

    public void stopScrolling() {
        this.scroller.stopScrolling();
    }
}
