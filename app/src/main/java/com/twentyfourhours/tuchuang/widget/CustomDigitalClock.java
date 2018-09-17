package com.twentyfourhours.tuchuang.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.Html;
import android.text.Spanned;
import android.util.AttributeSet;
import android.widget.TextClock;

import java.util.Calendar;

/**
 * Created by Administrator on 2018/2/2.
 */

public class CustomDigitalClock extends TextClock {
    Calendar mCalendar;
    private final static String m12 = "h:mm aa";
    private final static String m24 = "k:mm";
    private FormatChangeObserver mFormatChangeObserver;
    private Runnable mTicker;
    private Handler mHandler;
    private long endTime;
    public static long distanceTime;
    private ClockListener mClockListener;
    private static boolean isFirst;
    private boolean mTickerStopped;
    @SuppressWarnings("unused")
    private String mFormat;
    public CustomDigitalClock(Context context) {
        super(context);
        initClock(context);
    }
    public CustomDigitalClock(Context context, AttributeSet attrs) {
        super(context, attrs);
        initClock(context);
    }

    @TargetApi(24)
    private void getInstance(){
        mCalendar = Calendar.getInstance();
    }

    private void initClock(Context context) {
        if (mCalendar == null) {
            getInstance();
        }
        mFormatChangeObserver = new FormatChangeObserver();
        getContext().getContentResolver().registerContentObserver(
                Settings.System.CONTENT_URI, true, mFormatChangeObserver);
        setFormat();
    }

    private int count;
    private long firstTime;

    @Override
    protected void onAttachedToWindow() {
        mTickerStopped = false;
        super.onAttachedToWindow();
        mHandler = new Handler();
/**
 * requests a tick on the next hard-second boundary
 */
        mTicker = new Runnable() {
            public void run() {
                if (mTickerStopped)
                    return;
                long currentTime = System.currentTimeMillis();
//                if (currentTime / 1000 == endTime / 1000 - 5 * 60) {
//
//                }
                if (currentTime == endTime - 5 * 60) {

                }
                distanceTime = endTime - currentTime;
                if (count==0){
                    firstTime=distanceTime;
                }
                count++;
                distanceTime /= 100;
//                distanceTime /= 1000;
                if (distanceTime == 0) {
                    setText("00:00:00");
//                    onDetachedFromWindow();
                } else if (distanceTime < 0) {
                    setText(dealTime(firstTime));
                    endTime=endTime+firstTime;
//                    onDetachedFromWindow();
                } else {
                    setText(dealTime(distanceTime));
                }
                if (mClockListener!=null) {
                    mClockListener.remainFiveMinutes();
                }
                invalidate();
                long now = SystemClock.uptimeMillis();
                long next = now + (100 - now % 100);
                mHandler.postAtTime(mTicker, next);
            }
        };
        mTicker.run();
    }
    /**
     * deal time string
     *
     * @param time
     * @return
     */
    public Spanned dealTime(long time) {
        Spanned str;
        StringBuffer returnString = new StringBuffer();//00:00:00:00:00
        long day = time / (24 * 60 * 60*10);
        long hours = (time % (24 * 60 * 60*10)) / (60 * 60*10);
        long minutes = ((time % (24 * 60 * 60*10)) % (60 * 60*10)) / (60*10);
        long second = ((time % (24 * 60 * 60*10)) % (60 * 60*10)) % (60*10)/10;
        long lastTime = ((time % (24 * 60 * 60*10)) % (60 * 60*10)) % (60*10)%10;
        String dayStr = String.valueOf(day);
        String lastTimeStr =String.valueOf(lastTime);
        String hoursStr = timeStrFormat(String.valueOf(hours));
        String minutesStr = timeStrFormat(String.valueOf(minutes));
        String secondStr = timeStrFormat(String.valueOf(second));
        returnString.append(hoursStr).append(":")
                .append(minutesStr).append(":").append(secondStr);
        str = Html.fromHtml(returnString.toString());

        if (mClockListener!=null){
            mClockListener.lastTime(lastTimeStr);
        }

//        if (day >= 10) {
//            ((Spannable) str).setSpan(new AbsoluteSizeSpan(16), 2, 3,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ((Spannable) str).setSpan(new AbsoluteSizeSpan(16), 5, 7,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ((Spannable) str).setSpan(new AbsoluteSizeSpan(16), 9, 11,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ((Spannable) str).setSpan(new AbsoluteSizeSpan(16), 13, 14,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        } else {
//            ((Spannable) str).setSpan(new AbsoluteSizeSpan(16), 1, 2,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ((Spannable) str).setSpan(new AbsoluteSizeSpan(16), 4, 6,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ((Spannable) str).setSpan(new AbsoluteSizeSpan(16), 8, 10,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            ((Spannable) str).setSpan(new AbsoluteSizeSpan(16), 12, 13,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        }
// return returnString.toString();
        return str;
    }
    /**
     * format time
     *
     * @param timeStr
     * @return
     */
    private static String timeStrFormat(String timeStr) {
        switch (timeStr.length()) {
            case 1:
                timeStr = "0" + timeStr;
                break;
        }
        return timeStr;
    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mTickerStopped = true;
    }
    /**
     * Clock end time from now on.
     *
     * @param endTime
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
    /**
     * Pulls 12/24 mode from system settings
     */
    private boolean get24HourMode() {
        return android.text.format.DateFormat.is24HourFormat(getContext());
    }
    private void setFormat() {
        if (get24HourMode()) {
            mFormat = m24;
        } else {
            mFormat = m12;
        }
    }
    private class FormatChangeObserver extends ContentObserver {
        public FormatChangeObserver() {
            super(new Handler());
        }
        @Override
        public void onChange(boolean selfChange) {
            setFormat();
        }
    }
    public void setClockListener(ClockListener clockListener) {
        this.mClockListener = clockListener;
    }
    public interface ClockListener {
        void lastTime(String str);
        void remainFiveMinutes();
    }
}
