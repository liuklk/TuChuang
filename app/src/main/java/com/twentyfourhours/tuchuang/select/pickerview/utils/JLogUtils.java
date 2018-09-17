package com.twentyfourhours.tuchuang.select.pickerview.utils;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/3/17.
 */

public class JLogUtils {
    public static final String SUFFIX = ".java";
    private static String className;
    private static String fileName;
    private static String methodName;
    private static String lineNumber;
    public static final int V = 1;
    public static final int D = 2;
    public static final int I = 3;
    public static final int W = 4;
    public static final int E = 5;
    public static final int A = 6;
    public static final int JSON = 7;
    private static boolean IS_SHOW_LOG = true;
    public static final int JSON_INDENT = 4;
    private static final int STACK_TRACE_INDEX = 3;
    public static final String NULL_MSG = "Log with null object";
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static String TAG = "com.liji";

    public JLogUtils() {
    }

    public static void setDebug(boolean SHOW_LOG) {
        IS_SHOW_LOG = SHOW_LOG;
    }

    public static void D(String logMsg) {
        if(IS_SHOW_LOG) {
            printLog(2, (String)null, logMsg);
        }

    }

    public static void D(String Tag, String logMsg) {
        if(IS_SHOW_LOG) {
            printLog(2, Tag, logMsg);
        }

    }

    public static void E(String errorMsg) {
        if(IS_SHOW_LOG) {
            printLog(5, (String)null, errorMsg);
        }

    }

    public static void E(Exception exception) {
        if(IS_SHOW_LOG) {
            exception.printStackTrace();
        }

    }

    public static void E(String Tag, String errorMsg) {
        if(IS_SHOW_LOG) {
            printLog(5, Tag, errorMsg);
        }

    }

    public static void Json(String jsonStr) {
        if(IS_SHOW_LOG) {
            printLog(7, (String)null, jsonStr);
        }

    }

    public static void Json(String Tag, String jsonStr) {
        if(IS_SHOW_LOG) {
            printLog(7, Tag, jsonStr);
        }

    }

    private static String[] wrapperContent(String tagStr, String obj) {
        StackTraceElement[] sElements = (new Throwable()).getStackTrace();
        StackTraceElement targetElement = sElements[3];
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if(classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1] + ".java";
        }

        String methodName = targetElement.getMethodName();
        int lineNumber = targetElement.getLineNumber();
        if(lineNumber < 0) {
            lineNumber = 0;
        }

        String methodNameShort = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
        String tag = tagStr == null?className:tagStr;
        if(TextUtils.isEmpty(tag)) {
            tag = TAG;
        }

        String msg = obj == null?"Log with null object":obj;
        String headString = "[ (" + className + ":" + lineNumber + ") # " + methodNameShort + " ] ";
        return new String[]{tag, msg, headString};
    }

    private static void printLog(int type, String Tag, String logMsg) {
        String[] contents = wrapperContent(Tag, logMsg);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];
        switch(type) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                printDefaultLog(type, tag, headString + msg);
                break;
            case 7:
                printJsonLog(TAG, msg, headString);
        }

    }

    private static void printJsonLog(String tag, String msg, String headString) {
        String message;
        try {
            if(msg.startsWith("{")) {
                JSONObject lines = new JSONObject(msg);
                message = lines.toString(4);
            } else if(msg.startsWith("[")) {
                JSONArray var10 = new JSONArray(msg);
                message = var10.toString(4);
            } else {
                message = msg;
            }
        } catch (JSONException var9) {
            message = msg;
        }

        printLine(tag, true);
        message = headString + LINE_SEPARATOR + message;
        String[] var11 = message.split(LINE_SEPARATOR);
        String[] var5 = var11;
        int var6 = var11.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            String line = var5[var7];
            Log.d(tag, "║ " + line);
        }

        printLine(tag, false);
    }

    private static void printDefaultLog(int type, String tag, String logMsg) {
        switch(type) {
            case 1:
                Log.v(tag, logMsg);
                break;
            case 2:
                Log.d(tag, logMsg);
                break;
            case 3:
                Log.i(tag, logMsg);
            case 4:
            default:
                break;
            case 5:
                Log.e(tag, logMsg);
                break;
            case 6:
                Log.wtf(tag, logMsg);
        }

    }

    public static void printLine(String tag, boolean isTop) {
        if(isTop) {
            Log.d(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            Log.d(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }

    }
}
