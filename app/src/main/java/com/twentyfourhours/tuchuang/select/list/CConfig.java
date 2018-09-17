package com.twentyfourhours.tuchuang.select.list;


import com.twentyfourhours.tuchuang.select.list.bean.CityInfoBean;

/**
 * Created by Administrator on 2018/3/17.
 */

public class CConfig {
    private static CityInfoBean sCityInfoBean;

    public CConfig() {
    }

    public static void setCity(CityInfoBean city) {
        sCityInfoBean = city;
    }

    public static CityInfoBean getCitySelected() {
        return sCityInfoBean;
    }
}
