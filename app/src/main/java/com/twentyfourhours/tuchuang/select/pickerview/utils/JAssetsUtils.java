package com.twentyfourhours.tuchuang.select.pickerview.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/3/17.
 */

public class JAssetsUtils {
    public JAssetsUtils() {
    }

    public static String getJsonDataFromAssets(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = context.getClass().getClassLoader().getResourceAsStream("assets/" + fileName);

        try {
            byte[] e = new byte[inputStream.available()];
            inputStream.read(e);
            String json = new String(e, "utf-8");
            stringBuilder = stringBuilder.append(json);
        } catch (IOException var14) {
            var14.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException var13) {
                var13.printStackTrace();
            }

        }

        return stringBuilder.toString();
    }
}
