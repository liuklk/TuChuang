package com.twentyfourhours.tuchuang.common.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {
	
	private static final String NAME = "mobilesafe";
	/**自动更新*/
	private static final String KEY_AUTO_UPDATE = "auto_update";
	private static final String KEY_SJFD_PWD = "sjfd_pwd";
	/**绑定sim卡*/
	private static final String KEY_BIND_SIM = "bind_sim";
	/**安全号码*/
	private static final String KEY_SAFE_NUMBER= "safe_number";
	/**设置向导完成*/
	private static final String KEY_SETUP_SUCCESS = "setup_success";
	/**防盗保护是否开启*/
	private static final String KEY_PROTECTING_STATUS = "protecting_status";
	/**设备管理器*/
	private static final String KEY_DEVICE_ADMIN = "device_admin";
	/**归属地样式*/
	private static final String KEY_LOCATION_STYLE = "location_style";
	/**程序锁密码*/
	private static final String KEY_APP_LOCK_PWD= "app_lock_pwd";
	/**桌面快捷图标*/
	private static final String KEY_SHORT_CUT= "short_cut";
	/**初次登录*/
	private static final String KEY_FIRST_LOGIN="first_login";

	/**初次登录*/
	public static void setFirstLogin(Context context,long value){
		SharedPreferences sp = getPreference(context);
		sp.edit().putLong(KEY_FIRST_LOGIN,value).apply();
	}

	public static long getFirstLogin(Context context){
		SharedPreferences sp = getPreference(context);
		return sp.getLong(KEY_FIRST_LOGIN,0);
	}

	public static void setAutoUpdate(Context context, boolean value){
		SharedPreferences sp = getPreference(context);
		sp.edit().putBoolean(KEY_AUTO_UPDATE, value).apply();
	}
	
	public static boolean getAutoUpdate(Context context){
		SharedPreferences sp = getPreference(context);
		return sp.getBoolean(KEY_AUTO_UPDATE, false);
	}
	
	/**手机防盗密码*/
	public static void setSjfdPwd(Context context, String value){
		setString(context, KEY_SJFD_PWD, value);
	}

	
	
	public static String getSjfdPwd(Context context){
		SharedPreferences sp = getPreference(context);
		return sp.getString(KEY_SJFD_PWD, "");
	}

	
	private static SharedPreferences getPreference(Context context) {
		SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		return sp;
	}
	
	public static String getString(Context context, String key, String defValue){
		SharedPreferences sp = getPreference(context);
		return sp.getString(key, defValue);
	}
	
	private static void setString(Context context, String key, String value) {
		SharedPreferences sp = getPreference(context);
		sp.edit().putString(key, value).apply();
	}
	
	/**
	 * 绑定sim卡
	 */
	public static void setBindSim(Context context, String value){
		SharedPreferences sp = getPreference(context);
		sp.edit().putString(KEY_BIND_SIM, value).apply();
	}
	
	/**
	 * 获取sim卡
	 */
	public static String getBindSim(Context context){
		return getString(context, KEY_BIND_SIM, "");
	}
	
	/**
	 * 设置安全号码
	 */
	public static void setSafeNumber(Context context, String value){
		SharedPreferences sp = getPreference(context);
		sp.edit().putString(KEY_SAFE_NUMBER, value).apply();
	}
	
	/**
	 * 获取安全号码
	 */
	public static String getSafeNumber(Context context){
		SharedPreferences sp = getPreference(context);
		return sp.getString(KEY_SAFE_NUMBER, "");
	}
	
	/**
	 * 设置向导完成
	 * @param context
	 * @param value
	 */
	public static void setSetupSuccess(Context context, boolean value){
		SharedPreferences sp = getPreference(context);
		sp.edit().putBoolean(KEY_SETUP_SUCCESS, value).apply();
	}
	
	
	public static boolean getSetupSuccess(Context context){
		SharedPreferences sp = getPreference(context);
		return sp.getBoolean(KEY_SETUP_SUCCESS, false);
	}
	
	/**
	 * 防盗保护
	 * @param context
	 * @param value
	 */
	public static void setProtectingStatus(Context context, boolean value){
		SharedPreferences sp = getPreference(context);
		sp.edit().putBoolean(KEY_PROTECTING_STATUS, value).apply();
	}
	
	
	public static boolean getProtectingStatus(Context context){
		SharedPreferences sp = getPreference(context);
		return sp.getBoolean(KEY_PROTECTING_STATUS, false);
	}
	
	/**
	 * 设备管理器
	 */
	public static void setDeviceAdmin(Context context, boolean value){
		SharedPreferences sp = getPreference(context);
		sp.edit().putBoolean(KEY_DEVICE_ADMIN, value).apply();
	}
	
	public static boolean getDeviceAdmin(Context context){
		SharedPreferences sp = getPreference(context);
		return sp.getBoolean(KEY_DEVICE_ADMIN, false);
	}
	
	/**归属地样式*/
	public static void setLocationStyle(Context context, int value){
		SharedPreferences sp = getPreference(context);
		sp.edit().putInt(KEY_LOCATION_STYLE, value).apply();
	}
	
	public static int getLocationStyle(Context context){
		SharedPreferences sp = getPreference(context);
		return sp.getInt(KEY_LOCATION_STYLE, 0);
	}
	
	/**
	 * 设置程序锁密码
	 * @param context
	 * @param value
	 */
	public static void setAppLockPwd(Context context, String value){
		SharedPreferences sp = getPreference(context);
		sp.edit().putString(KEY_APP_LOCK_PWD, value).apply();
	}
	
	/**
	 * 获取程序锁密码
	 */
	public static String getAppLockPwd(Context context){
		SharedPreferences sp = getPreference(context);
		return sp.getString(KEY_APP_LOCK_PWD, "");
	}

	/**
	 * 创建快捷图标
	 */
	public static void setShortcut(Context context, boolean value){
		SharedPreferences sp = getPreference(context);
		sp.edit().putBoolean(KEY_SHORT_CUT, value).apply();
	}
	
	
	public static boolean getShortcut(Context context){
		SharedPreferences sp = getPreference(context);
		return sp.getBoolean(KEY_SHORT_CUT, false);
	}
	
}
