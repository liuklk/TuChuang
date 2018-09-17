package com.twentyfourhours.tuchuang.model.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/6 0006.
 */

public class AddressManageBean implements Serializable{
    private String name;
    private String address;
    private String phoneNumber;
    private boolean isChecked;
    private String detail;

    @Override
    public String toString() {
        return "AddressManageBean{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", isChecked=" + isChecked +
                ", detail='" + detail + '\'' +
                '}';
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
