package com.twentyfourhours.tuchuang.select.list.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2018/3/17.
 */

public class CityInfoBean implements Parcelable {
    private int id;
    private String name;
    private String latitude;
    private String longitude;
    private String fistLetter;
    private int sort;
    public static final Creator<CityInfoBean> CREATOR = new Creator() {
        public CityInfoBean createFromParcel(Parcel source) {
            return new CityInfoBean(source);
        }

        public CityInfoBean[] newArray(int size) {
            return new CityInfoBean[size];
        }
    };

    public CityInfoBean() {
    }

    public static CityInfoBean findCity(List<CityInfoBean> list, String cityName) {
        try {
            for(int e = 0; e < list.size(); ++e) {
                CityInfoBean city = (CityInfoBean)list.get(e);
                if(cityName.equals(city.getName())) {
                    return city;
                }
            }

            return null;
        } catch (Exception var4) {
            return null;
        }
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name == null?"":this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return this.latitude == null?"":this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return this.longitude == null?"":this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getFistLetter() {
        return this.fistLetter == null?"":this.fistLetter;
    }

    public void setFistLetter(String fistLetter) {
        this.fistLetter = fistLetter;
    }

    public int getSort() {
        return this.sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String toString() {
        return "CityInfoBean{id=" + this.id + ", name=\'" + this.name + '\'' + ", latitude=\'" + this.latitude + '\'' + ", longitude=\'" + this.longitude + '\'' + ", fistLetter=\'" + this.fistLetter + '\'' + ", sort=" + this.sort + '}';
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeString(this.fistLetter);
        dest.writeInt(this.sort);
    }

    protected CityInfoBean(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.fistLetter = in.readString();
        this.sort = in.readInt();
    }
}
