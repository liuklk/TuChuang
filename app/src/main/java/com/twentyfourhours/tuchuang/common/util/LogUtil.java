package com.twentyfourhours.tuchuang.common.util;

import android.util.Log;

/**
 * Created by Administrator on 2018/1/4.
 */

public class LogUtil {

    private static boolean show=true;//开启打印log

    public static void logD(String tag,String msg){
        if (show){
            Log.d(tag,msg);
        }
    }
}
