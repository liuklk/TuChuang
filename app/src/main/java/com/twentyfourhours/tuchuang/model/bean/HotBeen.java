package com.twentyfourhours.tuchuang.model.bean;

/**
 * Created by Administrator on 2018/1/4.
 */
public class HotBeen {

    private int type;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private boolean show;//显示正面和反面
    private boolean showLimitTime;//显示正面和反面

    public boolean isShowLimitTime() {
        return showLimitTime;
    }

    public void setShowLimitTime(boolean showLimitTime) {
        this.showLimitTime = showLimitTime;
    }

    private boolean show1;
    private boolean show2;
    private boolean show3;
    private boolean isEdit;
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    private String style;
    private String size;

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public boolean isShow1() {
        return show1;
    }

    public void setShow1(boolean show1) {
        this.show1 = show1;
    }

    public boolean isShow2() {
        return show2;
    }

    public void setShow2(boolean show2) {
        this.show2 = show2;
    }

    public boolean isShow3() {
        return show3;
    }

    public void setShow3(boolean show3) {
        this.show3 = show3;
    }

    private int pic1;//测试图一
    private int pic2;

    public int getPic2() {
        return pic2;
    }

    public void setPic2(int pic2) {
        this.pic2 = pic2;
    }

    public int getPic1() {
        return pic1;
    }

    public void setPic1(int pic1) {
        this.pic1 = pic1;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
