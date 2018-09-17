package com.twentyfourhours.tuchuang.common.base;


import android.content.Context;
import android.support.multidex.MultiDex;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.google.gson.Gson;

import com.twentyfourhours.tuchuang.entity.AlbumTypeInfo;
import com.twentyfourhours.tuchuang.entity.RegionPointsInfo;
import com.twentyfourhours.tuchuang.view.Content;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by szq on 2017/7/30.
 */

public class Application extends android.app.Application {
    public static Application sInstace;
    public static Map<String, Map<String, RegionPointsInfo>> photoTypes;
//    public static User sUser; //内存常驻的
//    private String device;

//    public String getDevice() {
//        return device;
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Retrofit
        //NetWorkManager.init(this);


        photoTypes = new TreeMap<String, Map<String, RegionPointsInfo>>();
        String albumType = getStringFromAssert(Content.ALBUM_FILE_NAME);
        AlbumTypeInfo albumTypeInfos = new Gson().fromJson(albumType,
                AlbumTypeInfo.class);
        for (int i = 0; i < albumTypeInfos.getData().size(); i++) {
            AlbumTypeInfo.Types types = albumTypeInfos.getData().get(i);
            Map<String, RegionPointsInfo> map = new TreeMap<String, RegionPointsInfo>();
            for (int j = 0; j < types.getFileNames().size(); j++) {
                AlbumTypeInfo.FileNames fileName = types.getFileNames().get(j);
                RegionPointsInfo regionPointsInfo = new Gson().fromJson(
                        getStringFromAssert(fileName.getFileName()),
                        RegionPointsInfo.class);
                map.put(fileName.getFileName(), regionPointsInfo);
            }
            photoTypes.put(types.getType(), map);
        }

        /**地图 */
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());

        //初始化全局的网络接口
//        NetWorkManager.init(getApplicationContext());
//        sInstace = this;
//        sUser = new User();
//        sUser.setLoginId("1");  //未登录的

        //主功能完成再实现推送功能
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
    }

    public String getStringFromAssert(String fileName) {
        String content = null; // 结果字符串
        try {
            InputStream is = this.getResources().getAssets().open(fileName); // 打开文件
            int ch = 0;
            ByteArrayOutputStream out = new ByteArrayOutputStream(); // 实现了一个输出流
            while ((ch = is.read()) != -1) {
                out.write(ch); // 将指定的字节写入此 byte 数组输出流
            }
            byte[] buff = out.toByteArray();// 以 byte 数组的形式返回此输出流的当前内容
            out.close(); // 关闭流
            is.close(); // 关闭流
            content = new String(buff, "UTF-8"); // 设置字符串编码
        } catch (Exception e) {
            Toast.makeText(this, "对不起，没有找到指定文件！", Toast.LENGTH_SHORT).show();
        }
        return content;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base); MultiDex.install(this);
    }
}
