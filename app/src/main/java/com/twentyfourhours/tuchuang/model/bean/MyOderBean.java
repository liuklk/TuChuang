package com.twentyfourhours.tuchuang.model.bean;

/**
 * Created by Administrator on 2017/11/7 0007.
 */

public class MyOderBean {
    private String name;
    private String statu;
    private String stone;
    private String number;
    private String weight;
    private String account;
    private String price;
    private String action;
    private boolean isShow;
    private boolean isShowAction;

    public boolean isShowAction() {
        return isShowAction;
    }

    public void setShowAction(boolean showAction) {
        isShowAction = showAction;
    }

    private String logistics;

    @Override
    public String toString() {
        return "MyOderBean{" +
                "name='" + name + '\'' +
                ", statu='" + statu + '\'' +
                ", stone='" + stone + '\'' +
                ", number='" + number + '\'' +
                ", weight='" + weight + '\'' +
                ", account='" + account + '\'' +
                ", price='" + price + '\'' +
                ", action='" + action + '\'' +
                ", isShow=" + isShow +
                ", logistics='" + logistics + '\'' +
                '}';
    }

    public String getLogistics() {
        return logistics;
    }

    public void setLogistics(String logistics) {
        this.logistics = logistics;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public String getStone() {
        return stone;
    }

    public void setStone(String stone) {
        this.stone = stone;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
