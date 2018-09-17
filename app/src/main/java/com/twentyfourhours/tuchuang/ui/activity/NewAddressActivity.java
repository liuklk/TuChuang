package com.twentyfourhours.tuchuang.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lljjcoder.citypickerview.widget.CityPicker;
import com.twentyfourhours.tuchuang.R;
import com.twentyfourhours.tuchuang.common.base.BaseActivity;
import com.twentyfourhours.tuchuang.model.bean.AddressManageBean;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/6 0006.
 */

public class NewAddressActivity extends BaseActivity {


    @BindView(R.id.back)
    ImageView mGoldBack;
    @BindView(R.id.name)
    EditText mName;
    @BindView(R.id.phone_number)
    EditText mPhoneNumber;
    @BindView(R.id.address)
    TextView mAddress;
    @BindView(R.id.select_address)
    RelativeLayout mSelectAddress;
    @BindView(R.id.detail)
    EditText mDetail;
    @BindView(R.id.save)
    Button mSave;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.shouhuoren)
    TextView mShouhuoren;
    @BindView(R.id.dianhua)
    TextView mDianhua;
    @BindView(R.id.diqu)
    TextView mDiqu;
    @BindView(R.id.check_box)
    CheckBox mCheckBox;
    private int mType;

    @Override
    protected void initData() {
        mType = getIntent().getIntExtra("type", -1);
        AddressManageBean addressManageBean = (AddressManageBean) getIntent().getSerializableExtra("addressManageBean");
        if (mType == AddressManagerActivity.EDIT) {
            mTitle.setText("编辑");
            mCheckBox.setChecked(addressManageBean.isChecked());
            mName.setText(addressManageBean.getName());
            mPhoneNumber.setText(addressManageBean.getPhoneNumber());
            mAddress.setText(addressManageBean.getAddress());
            mDetail.setText(addressManageBean.getDetail());
            mCheckBox.setChecked(addressManageBean.isChecked());
        }else {
            mTitle.setText("添加新地址");
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_address;
    }


    @OnClick({R.id.back, R.id.select_address, R.id.save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.select_address:
                showDialog();
                break;
            case R.id.save:
                save();
                break;
        }
    }

    private void save() {
        String name = mName.getText().toString().trim();
        String phoneNumber = mPhoneNumber.getText().toString().trim();
        String address = mAddress.getText().toString().trim();
        String detail = mDetail.getText().toString().trim();
        boolean checked = mCheckBox.isChecked();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(address) || TextUtils.isEmpty(detail)) {
            toast("输入不可为空");
            return;
        }
        if (!isMobileNO(phoneNumber)) {
            toast("请输入正确的手机号");
            return;
        }
        Intent intent = new Intent();
        AddressManageBean bean = new AddressManageBean();
        bean.setName(name);
        bean.setDetail(detail);
        bean.setAddress(address);
        bean.setPhoneNumber(phoneNumber);
        bean.setChecked(checked);
        intent.putExtra("AddressManageBean", bean);
        intent.putExtra("position", getIntent().getIntExtra("position", -1));
        setResult(RESULT_OK, intent);
        finish();
    }

    private boolean isMobileNO(String phoneNumberStr) {
        String mobileNO = "((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8]))\\d{8}";
        if (TextUtils.isEmpty(phoneNumberStr)) {
            return false;
        } else {
            return phoneNumberStr.matches(mobileNO);
        }
    }

    private void showDialog() {
        CityPicker cityPicker = new CityPicker.Builder(this)
                .textSize(20)
                .title("地址选择")
//                .backgroundPop(0xa0000000)
                .titleBackgroundColor("#282828")
                .titleTextColor("#ffffff")
                .backgroundPop(0xa0000000)
                .confirTextColor("#ffffff")
                .cancelTextColor("#ffffff")
                .province("广东省")
                .city("深圳市")
                .district("保安区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();

        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                mAddress.setText(province + city + district);
            }

            @Override
            public void onCancel() {
                toast("已取消");
            }
        });
    }

}
