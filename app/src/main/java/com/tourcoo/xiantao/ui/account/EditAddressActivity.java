package com.tourcoo.xiantao.ui.account;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.AddressPickerBean;
import com.tourcoo.xiantao.entity.address.AddressEntity;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooTitleActivity;
import com.tourcoo.xiantao.util.AddressHelper;
import com.tourcoo.xiantao.widget.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.tourcoo.xiantao.widget.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.tourcoo.xiantao.widget.bigkoo.pickerview.view.OptionsPickerView;
import com.trello.rxlifecycle3.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import static com.tourcoo.xiantao.ui.account.AddNewAddressActivity.RESULT_SUCCESS_ADDRESS;
import static com.tourcoo.xiantao.ui.account.AddressManagerActivity.EXTRA_ADDRESS_INFO;

/**
 * @author :JenkinsZhou
 * @description :编辑地址
 * @company :途酷科技
 * @date 2019年04月24日13:00
 * @Email: 971613168@qq.com
 */
public class EditAddressActivity extends BaseTourCooTitleActivity implements View.OnClickListener {
    private int provincePosition = -1;
    private int cityPosition = -1;
    private int regionPosition = -1;
    private AddressEntity mAddressEntity;
    private List<AddressPickerBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private TextView tvSelectAddress;
    private EditText etName;
    private EditText etPhone;
    private EditText edAddressDetail;
    private OptionsPickerView pvOptions;
    private String region;
    private TextView tvSaveAddress;
    private List<String> provinceNameList = new ArrayList<>();
    private List<String> cityNameList = new ArrayList<>();
    private List<String> rigionNameList = new ArrayList<>();

    @Override
    public int getContentLayout() {
        return R.layout.activity_add_new_address;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        tvSelectAddress = findViewById(R.id.tvSelectAddress);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        edAddressDetail = findViewById(R.id.edAddressDetail);
        tvSelectAddress.setOnClickListener(this);
        tvSaveAddress = findViewById(R.id.tvSaveAddress);
        tvSaveAddress.setOnClickListener(this);
        tvSaveAddress.setGravity(Gravity.CENTER);
        tvSaveAddress.setText("保存");
        loadAddress();
        initPickerView();
        mAddressEntity = (AddressEntity) getIntent().getSerializableExtra(EXTRA_ADDRESS_INFO);
        if (mAddressEntity == null) {
            ToastUtil.showFailed("未获取到地址信息");
            return;
        }
        showAddressInfo(mAddressEntity);
    }


    private void loadAddress() {
        List<AddressPickerBean> addressPickerBeans = AddressHelper.getInstance().getAddressInfo();
        options1Items = addressPickerBeans;
        for (int i = 0; i < addressPickerBeans.size(); i++) {
            //遍历省份
            ArrayList<String> cityList = new ArrayList<>();
            //该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();
            //该省的所有地区列表（第三极）
            for (int c = 0; c < addressPickerBeans.get(i).getCityList().size(); c++) {
                //遍历该省份的所有城市
                String cityName = addressPickerBeans.get(i).getCityList().get(c).getName();
                //添加城市
                cityList.add(cityName);
                //该城市的所有地区列表
                ArrayList<String> city_AreaList = new ArrayList<>();

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(addressPickerBeans.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }
    }


    /**
     * 弹出选择器
     */
    @SuppressWarnings("unchecked")
    private void initPickerView() {
        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";

                String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";

                String tx = opt1tx + opt2tx + opt3tx;
                //显示选中
                region = opt1tx + "," + opt2tx + "," + opt3tx;
                setSelect(tx);
            }
        })
                .setTitleText("城市选择")
                .setCancelColor(TourCooUtil.getColor(R.color.colorPrimary))
                .setSubmitColor(TourCooUtil.getColor(R.color.colorPrimary))
                .setDividerColor(Color.BLACK)
                //设置选中项文字颜色
                .setTextColorCenter(TourCooUtil.getColor(R.color.colorPrimary))
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        //三级选择器
        pvOptions.setPicker(options1Items, options2Items, options3Items);

    }

    private String getName() {
        return etName.getText().toString();
    }

    private String getRegion() {
        return tvSelectAddress.getText().toString();
    }

    private String getPhone() {
        return etPhone.getText().toString();
    }

    private String getAddressDetail() {
        return edAddressDetail.getText().toString();
    }

    private void setSelect(String info) {
        tvSelectAddress.setText(info);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSelectAddress:
                pvOptions.show();
                break;
            case R.id.tvSaveAddress:
                doSaveAddress(mAddressEntity.getAddress_id());
                break;
            default:
                break;
        }
    }


    private void showAddressInfo(AddressEntity addressEntity) {
        if (addressEntity == null) {
            return;
        }
        etName.setText(addressEntity.getName());
        etName.setSelection(etName.getText().length());
        etPhone.setText(addressEntity.getPhone());
        AddressEntity.AreaBean areaBean = addressEntity.getArea();
        if (areaBean != null) {
            String wholeAddress = areaBean.getProvince() + "," + areaBean.getCity() + "," + areaBean.getRegion();
            String addInfo = areaBean.getProvince() + areaBean.getCity() + areaBean.getRegion();
            region = wholeAddress;
            tvSelectAddress.setText(addInfo);
            loadSelect(areaBean.getProvince(), areaBean.getCity(), areaBean.getRegion());
        }
        edAddressDetail.setText(addressEntity.getDetail());
        if (pvOptions != null && provincePosition > -1 && cityPosition > -1 && regionPosition > -1) {
            pvOptions.setSelectOptions(provincePosition, cityPosition, regionPosition);
        }
    }


    private void doSaveAddress(int id) {
        if (TextUtils.isEmpty(getName())) {
            ToastUtil.show("请填写收货人姓名");
            return;
        }
        if (TextUtils.isEmpty(getPhone())) {
            ToastUtil.show("请填写收货人联系方式");
            return;
        }
        if (!TourCooUtil.isMobileNumber(getPhone())) {
            ToastUtil.show("请填写正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(getRegion())) {
            ToastUtil.show("请选择地区");
            return;
        }
        if (TextUtils.isEmpty(getAddressDetail())) {
            ToastUtil.show("请填写详细地址地区");
            return;
        }
        TourCooLogUtil.i(TAG, TAG + ":" + id);
        TourCooLogUtil.i(TAG, TAG + ":" + region);
        TourCooLogUtil.i(TAG, TAG + ":" + getName());
        TourCooLogUtil.i(TAG, TAG + ":" + getPhone());
        TourCooLogUtil.i(TAG, TAG + ":" + getAddressDetail());
        editAddress(id, region, getName(), getPhone(), getAddressDetail());
    }


    /**
     * 新增收货地址
     */

    private void editAddress(int addressId, String region, String name, String phone, String detail) {
        ApiRepository.getInstance().editAddress(addressId, region, name, phone, detail).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            ToastUtil.showSuccess(entity.msg);
                            setResult(RESULT_SUCCESS_ADDRESS);
                            finish();
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        TourCooLogUtil.e(TAG, TAG + "异常:" + e.toString());
                    }
                });
    }

    private void loadSelect(String provice, String city, String region) {
        try {
            AddressPickerBean pickerBean;
            for (int i = 0; i < options1Items.size(); i++) {
                pickerBean = options1Items.get(i);
                if (pickerBean == null || pickerBean.getName() == null) {
                    continue;
                }
                if (pickerBean.getName().contains(provice)) {
                    provincePosition = i;
                    break;
                }
            }
            if (provincePosition > -1 && provincePosition < options2Items.size()) {
                //说明已经找到省份直接遍历省下面的市
                List<String> cityList = options2Items.get(provincePosition);
                String cityName;
                for (int i = 0; i < cityList.size(); i++) {
                    cityName = cityList.get(i);
                    if (TextUtils.isEmpty(cityName)) {
                        continue;
                    }
                    if (cityName.contains(city)) {
                        //说明已经找到市直接遍历市下面的区
                        cityPosition = i;
                        break;
                    }
                }
                if (provincePosition > -1 && cityPosition > -1 && cityPosition < options3Items.size()) {
                    //当前地区集合
                    List<String> regionList = options3Items.get(provincePosition).get(cityPosition);
                    String regionName;
                    for (int i = 0; i < regionList.size(); i++) {
                        regionName = regionList.get(i);
                        TourCooLogUtil.i(TAG, TAG + "当前区名称:" + regionName);
                        if (TextUtils.isEmpty(regionName)) {
                            continue;
                        }
                        if (regionName.contains(region)) {
                            //说明已经找到当前城市对应的区
                            regionPosition = i;
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, TAG + "查找异常:" + e.toString());
        }
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("编辑地址");
    }
}
