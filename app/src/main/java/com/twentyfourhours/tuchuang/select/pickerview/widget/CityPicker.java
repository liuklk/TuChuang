package com.twentyfourhours.tuchuang.select.pickerview.widget;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.select.pickerview.model.CityModel;
import com.twentyfourhours.tuchuang.select.pickerview.model.DistrictModel;
import com.twentyfourhours.tuchuang.select.pickerview.model.ProvinceModel;
import com.twentyfourhours.tuchuang.select.pickerview.utils.XmlParserHandler;
import com.twentyfourhours.tuchuang.select.pickerview.widget.wheel.OnWheelChangedListener;
import com.twentyfourhours.tuchuang.select.pickerview.widget.wheel.WheelView;
import com.twentyfourhours.tuchuang.select.pickerview.widget.wheel.adapters.ArrayWheelAdapter;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Administrator on 2018/3/17.
 */

public class CityPicker implements CanShow ,OnWheelChangedListener {
    private Context context;
    private PopupWindow popwindow;
    private View popview;
    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private RelativeLayout mRelativeTitleBg;
    private TextView mTvOK;
    private TextView mTvTitle;
    private TextView mTvCancel;
    protected String[] mProvinceDatas;
    protected Map<String, String[]> mCitisDatasMap;
    protected Map<String, String[]> mDistrictDatasMap;
    protected Map<String, String> mZipcodeDatasMap;
    protected String mCurrentProviceName;
    protected String mCurrentCityName;
    protected String mCurrentDistrictName;
    protected String mCurrentZipCode;
    private CityPicker.OnCityItemClickListener listener;
    public static final int DEFAULT_TEXT_COLOR = -10987432;
    public static final int DEFAULT_TEXT_SIZE = 18;
    private int textColor;
    private int textSize;
    private static final int DEF_VISIBLE_ITEMS = 5;
    private int visibleItems;
    private boolean isProvinceCyclic;
    private boolean isCityCyclic;
    private boolean isDistrictCyclic;
    private int padding;
    private String cancelTextColorStr;
    private String confirmTextColorStr;
    private String titleBackgroundColorStr;
    private String titleTextColorStr;
    private String defaultProvinceName;
    private String defaultCityName;
    private String defaultDistrict;
    private boolean showProvinceAndCity;
    private String mTitle;
    private int backgroundPop;

    public void setOnCityItemClickListener(CityPicker.OnCityItemClickListener listener) {
        this.listener = listener;
    }

    private CityPicker(CityPicker.Builder builder) {
        this.mCitisDatasMap = new HashMap();
        this.mDistrictDatasMap = new HashMap();
        this.mZipcodeDatasMap = new HashMap();
        this.mCurrentDistrictName = "";
        this.mCurrentZipCode = "";
        this.textColor = -10987432;
        this.textSize = 18;
        this.visibleItems = 5;
        this.isProvinceCyclic = true;
        this.isCityCyclic = true;
        this.isDistrictCyclic = true;
        this.padding = 5;
        this.cancelTextColorStr = "#000000";
        this.confirmTextColorStr = "#0000FF";
        this.titleBackgroundColorStr = "#E9E9E9";
        this.titleTextColorStr = "#E9E9E9";
        this.defaultProvinceName = "江苏";
        this.defaultCityName = "常州";
        this.defaultDistrict = "新北区";
        this.showProvinceAndCity = false;
        this.mTitle = "选择地区";
        this.backgroundPop = -1610612736;
        this.textColor = builder.textColor;
        this.textSize = builder.textSize;
        this.visibleItems = builder.visibleItems;
        this.isProvinceCyclic = builder.isProvinceCyclic;
        this.isDistrictCyclic = builder.isDistrictCyclic;
        this.isCityCyclic = builder.isCityCyclic;
        this.context = builder.mContext;
        this.padding = builder.padding;
        this.mTitle = builder.mTitle;
        this.titleBackgroundColorStr = builder.titleBackgroundColorStr;
        this.confirmTextColorStr = builder.confirmTextColorStr;
        this.cancelTextColorStr = builder.cancelTextColorStr;
        this.defaultDistrict = builder.defaultDistrict;
        this.defaultCityName = builder.defaultCityName;
        this.defaultProvinceName = builder.defaultProvinceName;
        this.showProvinceAndCity = builder.showProvinceAndCity;
        this.backgroundPop = builder.backgroundPop;
        this.titleTextColorStr = builder.titleTextColorStr;
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        this.popview = layoutInflater.inflate(R.layout.pop_citypicker, (ViewGroup)null);
        this.mViewProvince = (WheelView)this.popview.findViewById(R.id.id_province);
        this.mViewCity = (WheelView)this.popview.findViewById(R.id.id_city);
        this.mViewDistrict = (WheelView)this.popview.findViewById(R.id.id_district);
        this.mRelativeTitleBg = (RelativeLayout)this.popview.findViewById(R.id.rl_title);
        this.mTvOK = (TextView)this.popview.findViewById(R.id.tv_confirm);
        this.mTvTitle = (TextView)this.popview.findViewById(R.id.tv_title);
        this.mTvCancel = (TextView)this.popview.findViewById(R.id.tv_cancel);
        this.popwindow = new PopupWindow(this.popview, -1, -1);
        this.popwindow.setBackgroundDrawable(new ColorDrawable(this.backgroundPop));
        this.popwindow.setAnimationStyle(R.style.AnimBottom);
        this.popwindow.setTouchable(true);
        this.popwindow.setOutsideTouchable(false);
        this.popwindow.setFocusable(true);
        if(!TextUtils.isEmpty(this.titleBackgroundColorStr)) {
            this.mRelativeTitleBg.setBackgroundColor(Color.parseColor(this.titleBackgroundColorStr));
        }

        if(!TextUtils.isEmpty(this.mTitle)) {
            this.mTvTitle.setText(this.mTitle);
        }

        if(!TextUtils.isEmpty(this.titleTextColorStr)) {
            this.mTvTitle.setTextColor(Color.parseColor(this.titleTextColorStr));
        }

        if(!TextUtils.isEmpty(this.confirmTextColorStr)) {
            this.mTvOK.setTextColor(Color.parseColor(this.confirmTextColorStr));
        }

        if(!TextUtils.isEmpty(this.cancelTextColorStr)) {
            this.mTvCancel.setTextColor(Color.parseColor(this.cancelTextColorStr));
        }

        if(this.showProvinceAndCity) {
            this.mViewDistrict.setVisibility(View.GONE);
        } else {
            this.mViewDistrict.setVisibility(View.VISIBLE);
        }

        this.initProvinceDatas(this.context);
        this.mViewProvince.addChangingListener(this);
        this.mViewCity.addChangingListener(this);
        this.mViewDistrict.addChangingListener(this);
        this.mTvCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CityPicker.this.listener.onCancel();
                CityPicker.this.hide();
            }
        });
        this.mTvOK.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(CityPicker.this.showProvinceAndCity) {
                    CityPicker.this.listener.onSelected(new String[]{CityPicker.this.mCurrentProviceName, CityPicker.this.mCurrentCityName, "", CityPicker.this.mCurrentZipCode});
                } else {
                    CityPicker.this.listener.onSelected(new String[]{CityPicker.this.mCurrentProviceName, CityPicker.this.mCurrentCityName, CityPicker.this.mCurrentDistrictName, CityPicker.this.mCurrentZipCode});
                }

                CityPicker.this.hide();
            }
        });
    }

    private void setUpData() {
        int provinceDefault = -1;
        if(!TextUtils.isEmpty(this.defaultProvinceName) && this.mProvinceDatas.length > 0) {
            for(int arrayWheelAdapter = 0; arrayWheelAdapter < this.mProvinceDatas.length; ++arrayWheelAdapter) {
                if(this.mProvinceDatas[arrayWheelAdapter].contains(this.defaultProvinceName)) {
                    provinceDefault = arrayWheelAdapter;
                    break;
                }
            }
        }

        ArrayWheelAdapter var3 = new ArrayWheelAdapter(this.context, this.mProvinceDatas);
        this.mViewProvince.setViewAdapter(var3);
        if(-1 != provinceDefault) {
            this.mViewProvince.setCurrentItem(provinceDefault);
        }

        this.mViewProvince.setVisibleItems(this.visibleItems);
        this.mViewCity.setVisibleItems(this.visibleItems);
        this.mViewDistrict.setVisibleItems(this.visibleItems);
        this.mViewProvince.setCyclic(this.isProvinceCyclic);
        this.mViewCity.setCyclic(this.isCityCyclic);
        this.mViewDistrict.setCyclic(this.isDistrictCyclic);
        var3.setPadding(this.padding);
        var3.setTextColor(this.textColor);
        var3.setTextSize(this.textSize);
        this.updateCities();
        this.updateAreas();
    }

    protected void initProvinceDatas(Context context) {
        List provinceList = null;
        AssetManager asset = context.getAssets();

        try {
            try {
                InputStream e = asset.open("province_data.xml");
                SAXParserFactory spf = SAXParserFactory.newInstance();
                SAXParser parser = spf.newSAXParser();
                XmlParserHandler handler = new XmlParserHandler();
                parser.parse(e, handler);
                e.close();
                provinceList = handler.getDataList();
                List cityList;
                if(provinceList != null && !provinceList.isEmpty()) {
                    this.mCurrentProviceName = ((ProvinceModel)provinceList.get(0)).getName();
                    List i = ((ProvinceModel)provinceList.get(0)).getCityList();
                    if(i != null && !i.isEmpty()) {
                        this.mCurrentCityName = ((CityModel)i.get(0)).getName();
                        cityList = ((CityModel)i.get(0)).getDistrictList();
                        this.mCurrentDistrictName = ((DistrictModel)cityList.get(0)).getName();
                        this.mCurrentZipCode = ((DistrictModel)cityList.get(0)).getZipcode();
                    }
                }

                this.mProvinceDatas = new String[provinceList.size()];

                for(int var22 = 0; var22 < provinceList.size(); ++var22) {
                    this.mProvinceDatas[var22] = ((ProvinceModel)provinceList.get(var22)).getName();
                    cityList = ((ProvinceModel)provinceList.get(var22)).getCityList();
                    String[] cityNames = new String[cityList.size()];

                    for(int j = 0; j < cityList.size(); ++j) {
                        cityNames[j] = ((CityModel)cityList.get(j)).getName();
                        List districtList = ((CityModel)cityList.get(j)).getDistrictList();
                        String[] distrinctNameArray = new String[districtList.size()];
                        DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];

                        for(int k = 0; k < districtList.size(); ++k) {
                            DistrictModel districtModel = new DistrictModel(((DistrictModel)districtList.get(k)).getName(), ((DistrictModel)districtList.get(k)).getZipcode());
                            this.mZipcodeDatasMap.put(this.mProvinceDatas[var22] + cityNames[j] + ((DistrictModel)districtList.get(k)).getName(), ((DistrictModel)districtList.get(k)).getZipcode());
                            distrinctArray[k] = districtModel;
                            distrinctNameArray[k] = districtModel.getName();
                        }

                        this.mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                    }

                    this.mCitisDatasMap.put(((ProvinceModel)provinceList.get(var22)).getName(), cityNames);
                }
            } catch (Throwable var20) {
                var20.printStackTrace();
            }

        } finally {
            ;
        }
    }

    private void updateAreas() {
        int pCurrent = this.mViewCity.getCurrentItem();
        this.mCurrentCityName = ((String[])this.mCitisDatasMap.get(this.mCurrentProviceName))[pCurrent];
        String[] areas = (String[])this.mDistrictDatasMap.get(this.mCurrentCityName);
        if(areas == null) {
            areas = new String[]{""};
        }

        int districtDefault = -1;
        if(!TextUtils.isEmpty(this.defaultDistrict) && areas.length > 0) {
            for(int districtWheel = 0; districtWheel < areas.length; ++districtWheel) {
                if(areas[districtWheel].contains(this.defaultDistrict)) {
                    districtDefault = districtWheel;
                    break;
                }
            }
        }

        ArrayWheelAdapter var5 = new ArrayWheelAdapter(this.context, areas);
        var5.setTextColor(this.textColor);
        var5.setTextSize(this.textSize);
        this.mViewDistrict.setViewAdapter(var5);
        if(-1 != districtDefault) {
            this.mViewDistrict.setCurrentItem(districtDefault);
            this.mCurrentDistrictName = this.defaultDistrict;
        } else {
            this.mViewDistrict.setCurrentItem(0);
            this.mCurrentDistrictName = ((String[])this.mDistrictDatasMap.get(this.mCurrentCityName))[0];
        }

        var5.setPadding(this.padding);
        this.mCurrentZipCode = (String)this.mZipcodeDatasMap.get(this.mCurrentProviceName + this.mCurrentCityName + this.mCurrentDistrictName);
    }

    private void updateCities() {
        int pCurrent = this.mViewProvince.getCurrentItem();
        this.mCurrentProviceName = this.mProvinceDatas[pCurrent];
        String[] cities = (String[])this.mCitisDatasMap.get(this.mCurrentProviceName);
        if(cities == null) {
            cities = new String[]{""};
        }

        int cityDefault = -1;
        if(!TextUtils.isEmpty(this.defaultCityName) && cities.length > 0) {
            for(int cityWheel = 0; cityWheel < cities.length; ++cityWheel) {
                if(cities[cityWheel].contains(this.defaultCityName)) {
                    cityDefault = cityWheel;
                    break;
                }
            }
        }

        ArrayWheelAdapter var5 = new ArrayWheelAdapter(this.context, cities);
        var5.setTextColor(this.textColor);
        var5.setTextSize(this.textSize);
        this.mViewCity.setViewAdapter(var5);
        if(-1 != cityDefault) {
            this.mViewCity.setCurrentItem(cityDefault);
        } else {
            this.mViewCity.setCurrentItem(0);
        }

        var5.setPadding(this.padding);
        this.updateAreas();
    }

    public void setType(int type) {
    }

    public void show() {
        if(!this.isShow()) {
            this.setUpData();
            this.popwindow.showAtLocation(this.popview, 80, 0, 0);
        }

    }

    public void hide() {
        if(this.isShow()) {
            this.popwindow.dismiss();
        }

    }

    public boolean isShow() {
        return this.popwindow.isShowing();
    }

    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if(wheel == this.mViewProvince) {
            this.updateCities();
        } else if(wheel == this.mViewCity) {
            this.updateAreas();
        } else if(wheel == this.mViewDistrict) {
            this.mCurrentDistrictName = ((String[])this.mDistrictDatasMap.get(this.mCurrentCityName))[newValue];
            this.mCurrentZipCode = (String)this.mZipcodeDatasMap.get(this.mCurrentProviceName + this.mCurrentCityName + this.mCurrentDistrictName);
        }

    }

    public static class Builder {
        public static final int DEFAULT_TEXT_COLOR = -10987432;
        public static final int DEFAULT_TEXT_SIZE = 18;
        private int textColor = -10987432;
        private int textSize = 18;
        private static final int DEF_VISIBLE_ITEMS = 5;
        private int visibleItems = 5;
        private boolean isProvinceCyclic = true;
        private boolean isCityCyclic = true;
        private boolean isDistrictCyclic = true;
        private Context mContext;
        private int padding = 5;
        private String cancelTextColorStr = "#000000";
        private String confirmTextColorStr = "#0000FF";
        private String titleBackgroundColorStr = "#E9E9E9";
        private String titleTextColorStr = "#E9E9E9";
        private String defaultProvinceName = "衣服";
        private String defaultCityName = "男装";
        private String defaultDistrict = "外套";
        private String mTitle = "选择分类";
        private boolean showProvinceAndCity = false;
        private int backgroundPop = -1610612736;

        public Builder(Context context) {
            this.mContext = context;
        }

        public CityPicker.Builder backgroundPop(int backgroundPopColor) {
            this.backgroundPop = backgroundPopColor;
            return this;
        }

        public CityPicker.Builder titleBackgroundColor(String colorBg) {
            this.titleBackgroundColorStr = colorBg;
            return this;
        }

        public CityPicker.Builder titleTextColor(String titleTextColorStr) {
            this.titleTextColorStr = titleTextColorStr;
            return this;
        }

        public CityPicker.Builder title(String mtitle) {
            this.mTitle = mtitle;
            return this;
        }

        public CityPicker.Builder onlyShowProvinceAndCity(boolean flag) {
            this.showProvinceAndCity = flag;
            return this;
        }

        public CityPicker.Builder province(String defaultProvinceName) {
            this.defaultProvinceName = defaultProvinceName;
            return this;
        }

        public CityPicker.Builder city(String defaultCityName) {
            this.defaultCityName = defaultCityName;
            return this;
        }

        public CityPicker.Builder district(String defaultDistrict) {
            this.defaultDistrict = defaultDistrict;
            return this;
        }

        public CityPicker.Builder confirTextColor(String color) {
            this.confirmTextColorStr = color;
            return this;
        }

        public CityPicker.Builder cancelTextColor(String color) {
            this.cancelTextColorStr = color;
            return this;
        }

        public CityPicker.Builder textColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public CityPicker.Builder textSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        public CityPicker.Builder visibleItemsCount(int visibleItems) {
            this.visibleItems = visibleItems;
            return this;
        }

        public CityPicker.Builder provinceCyclic(boolean isProvinceCyclic) {
            this.isProvinceCyclic = isProvinceCyclic;
            return this;
        }

        public CityPicker.Builder cityCyclic(boolean isCityCyclic) {
            this.isCityCyclic = isCityCyclic;
            return this;
        }

        public CityPicker.Builder districtCyclic(boolean isDistrictCyclic) {
            this.isDistrictCyclic = isDistrictCyclic;
            return this;
        }

        public CityPicker.Builder itemPadding(int itemPadding) {
            this.padding = itemPadding;
            return this;
        }

        public CityPicker build() {
            CityPicker cityPicker = new CityPicker(this);
            return cityPicker;
        }
    }

    public interface OnCityItemClickListener {
        void onSelected(String... var1);

        void onCancel();
    }
}
