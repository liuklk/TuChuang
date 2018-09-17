package com.twentyfourhours.tuchuang.select.pickerview.model;


import java.util.List;

/**
 * Created by Administrator on 2018/3/17.
 */

public class CityModel {
    private String name;
    private List<DistrictModel> districtList;

    public CityModel() {
    }

    public CityModel(String name, List<DistrictModel> districtList) {
        this.name = name;
        this.districtList = districtList;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DistrictModel> getDistrictList() {
        return this.districtList;
    }

    public void setDistrictList(List<DistrictModel> districtList) {
        this.districtList = districtList;
    }

    public String toString() {
        return "CityModel [name=" + this.name + ", districtList=" + this.districtList + "]";
    }
}
