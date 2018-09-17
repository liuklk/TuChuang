package com.twentyfourhours.tuchuang.select.pickerview.model;

import java.util.List;

/**
 * Created by Administrator on 2018/3/17.
 */

public class ProvinceModel {
    private String name;
    private List<CityModel> cityList;

    public ProvinceModel() {
    }

    public ProvinceModel(String name, List<CityModel> cityList) {
        this.name = name;
        this.cityList = cityList;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityModel> getCityList() {
        return this.cityList;
    }

    public void setCityList(List<CityModel> cityList) {
        this.cityList = cityList;
    }

    public String toString() {
        return "ProvinceModel [name=" + this.name + ", cityList=" + this.cityList + "]";
    }
}
