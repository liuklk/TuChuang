package com.twentyfourhours.tuchuang.select.pickerview.utils;


import com.twentyfourhours.tuchuang.select.pickerview.model.CityModel;
import com.twentyfourhours.tuchuang.select.pickerview.model.DistrictModel;
import com.twentyfourhours.tuchuang.select.pickerview.model.ProvinceModel;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/17.
 */

public class XmlParserHandler extends DefaultHandler {
    private List<ProvinceModel> provinceList = new ArrayList();
    ProvinceModel provinceModel = new ProvinceModel();
    CityModel cityModel = new CityModel();
    DistrictModel districtModel = new DistrictModel();

    public XmlParserHandler() {
    }

    public List<ProvinceModel> getDataList() {
        return this.provinceList;
    }

    public void startDocument() throws SAXException {
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(qName.equals("province")) {
            this.provinceModel = new ProvinceModel();
            this.provinceModel.setName(attributes.getValue(0));
            this.provinceModel.setCityList(new ArrayList());
        } else if(qName.equals("city")) {
            this.cityModel = new CityModel();
            this.cityModel.setName(attributes.getValue(0));
            this.cityModel.setDistrictList(new ArrayList());
        } else if(qName.equals("district")) {
            this.districtModel = new DistrictModel();
            this.districtModel.setName(attributes.getValue(0));
            this.districtModel.setZipcode(attributes.getValue(1));
        }

    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equals("district")) {
            this.cityModel.getDistrictList().add(this.districtModel);
        } else if(qName.equals("city")) {
            this.provinceModel.getCityList().add(this.cityModel);
        } else if(qName.equals("province")) {
            this.provinceList.add(this.provinceModel);
        }

    }

    public void characters(char[] ch, int start, int length) throws SAXException {
    }
}
