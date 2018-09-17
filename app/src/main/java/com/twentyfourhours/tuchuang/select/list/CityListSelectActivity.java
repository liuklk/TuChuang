package com.twentyfourhours.tuchuang.select.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.select.list.bean.CityInfoBean;
import com.twentyfourhours.tuchuang.select.list.sortlistview.CharacterParser;
import com.twentyfourhours.tuchuang.select.list.sortlistview.PinyinComparator;
import com.twentyfourhours.tuchuang.select.list.sortlistview.SideBar;
import com.twentyfourhours.tuchuang.select.list.sortlistview.SortAdapter;
import com.twentyfourhours.tuchuang.select.list.sortlistview.SortModel;
import com.twentyfourhours.tuchuang.select.list.utils.CityUtils;
import com.twentyfourhours.tuchuang.select.list.widget.CleanableEditView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2018/3/17.
 */

public class CityListSelectActivity extends AppCompatActivity{
    CleanableEditView mCityTextSearch;
    TextView mCurrentCityTag;
    TextView mCurrentCity;
    TextView mLocalCityTag;
    TextView mLocalCity;
    ListView sortListView;
    TextView mDialog;
    SideBar mSidrbar;
    ImageView imgBack;
    public SortAdapter adapter;
    private CharacterParser characterParser;
    private List<SortModel> sourceDateList;
    private PinyinComparator pinyinComparator;
    private List<CityInfoBean> cityListInfo = new ArrayList();
    private CityInfoBean cityInfoBean = new CityInfoBean();
    public static final int CITY_SELECT_RESULT_FRAG = 50;

    public CityListSelectActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_city_list_select);
        this.initView();
        this.initList();
        this.setCityData(CityUtils.getCityList());
    }

    private void initView() {
        this.mCityTextSearch = (CleanableEditView)this.findViewById(R.id.cityInputText);
        this.mCurrentCityTag = (TextView)this.findViewById(R.id.currentCityTag);
        this.mCurrentCity = (TextView)this.findViewById(R.id.currentCity);
        this.mLocalCityTag = (TextView)this.findViewById(R.id.localCityTag);
        this.mLocalCity = (TextView)this.findViewById(R.id.localCity);
        this.sortListView = (ListView)this.findViewById(R.id.country_lvcountry);
        this.mDialog = (TextView)this.findViewById(R.id.dialog);
        this.mSidrbar = (SideBar)this.findViewById(R.id.sidrbar);
        this.imgBack = (ImageView)this.findViewById(R.id.imgBack);
        this.imgBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CityListSelectActivity.this.finish();
            }
        });
    }

    private void setCityData(List<CityInfoBean> cityList) {
        this.cityListInfo = cityList;
        int count = cityList.size();
        String[] list = new String[count];

        for(int i = 0; i < count; ++i) {
            list[i] = ((CityInfoBean)cityList.get(i)).getName();
        }

        this.sourceDateList.addAll(this.filledData(list));
        Collections.sort(this.sourceDateList, this.pinyinComparator);
        this.adapter.notifyDataSetChanged();
    }

    private List<SortModel> filledData(String[] date) {
        ArrayList mSortList = new ArrayList();

        for(int i = 0; i < date.length; ++i) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);
            String pinyin = this.characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();
            if(sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }

        return mSortList;
    }

    private void initList() {
        this.sourceDateList = new ArrayList();
        this.adapter = new SortAdapter(this, this.sourceDateList);
        this.sortListView.setAdapter(this.adapter);
        this.characterParser = CharacterParser.getInstance();
        this.pinyinComparator = new PinyinComparator();
        this.mSidrbar.setTextView(this.mDialog);
        this.mSidrbar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            public void onTouchingLetterChanged(String s) {
                int position = CityListSelectActivity.this.adapter.getPositionForSection(s.charAt(0));
                if(position != -1) {
                    CityListSelectActivity.this.sortListView.setSelection(position);
                }

            }
        });
        this.sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String cityName = ((SortModel) CityListSelectActivity.this.adapter.getItem(position)).getName();
                CityListSelectActivity.this.cityInfoBean = CityInfoBean.findCity(CityListSelectActivity.this.cityListInfo, cityName);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putParcelable("cityinfo", CityListSelectActivity.this.cityInfoBean);
                intent.putExtras(bundle);
                CityListSelectActivity.this.setResult(-1, intent);
                CityListSelectActivity.this.finish();
            }
        });
        this.mCityTextSearch.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CityListSelectActivity.this.filterData(s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void filterData(String filterStr) {
        Object filterDateList = new ArrayList();
        if(TextUtils.isEmpty(filterStr)) {
            filterDateList = this.sourceDateList;
        } else {
            ((List)filterDateList).clear();
            Iterator var3 = this.sourceDateList.iterator();

            label22:
            while(true) {
                SortModel sortModel;
                String name;
                do {
                    if(!var3.hasNext()) {
                        break label22;
                    }

                    sortModel = (SortModel)var3.next();
                    name = sortModel.getName();
                } while(!name.contains(filterStr) && !this.characterParser.getSelling(name).startsWith(filterStr));

                ((List)filterDateList).add(sortModel);
            }
        }

        Collections.sort((List)filterDateList, this.pinyinComparator);
        this.adapter.updateListView((List)filterDateList);
    }
}
