package com.tourcoo.xiantao.ui.account;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.AddressPickerBean;
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

/**
 * @author :JenkinsZhou
 * @description :新增地址
 * @company :途酷科技
 * @date 2019年03月26日16:05
 * @Email: 971613168@qq.com
 */
public class AddNewAddressActivity extends BaseTourCooTitleActivity implements View.OnClickListener {
    private static final String DEFAULT_PROVINCE = "安徽";
    private static final String DEFAULT_CITY = "芜湖";
    private static final String DEFAULT_RIGION = "无为";
    private List<AddressPickerBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private TextView tvSelectAddress;
    private EditText etName;
    private EditText etPhone;
    private EditText edAddressDetail;
    private OptionsPickerView pvOptions;
    private String region;
    private int defaultPositionProvince = -1;
    private int defaultPositionCity = -1;
    private int defaultPositionRegion = -1;
    public static final int RESULT_SUCCESS_ADDRESS = 2;

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
        findViewById(R.id.tvSaveAddress).setOnClickListener(this);
        loadAddress();
        TourCooLogUtil.i(TAG, TAG + "安徽省的位置:" + defaultPositionProvince + "-" + defaultPositionCity + "-" + defaultPositionRegion);
        initPickerView();
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("添加新地址");
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
        pvOptions.setSelectOptions(11,1,7);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSelectAddress:
                pvOptions.show();
                break;
            case R.id.tvSaveAddress:
                doAddAddress();
                break;
            default:
                break;
        }
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
                //添加该省所有地区数据
                province_AreaList.add(city_AreaList);
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


    /**
     * 新增收货地址
     */

    private void addAddress(String region, String name, String phone, String detail) {
        ApiRepository.getInstance().addAddress(region, name, phone, detail).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
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


    private void doAddAddress() {
        if (TextUtils.isEmpty(getName())) {
            ToastUtil.show("请填写收货人姓名");
            return;
        }
        if (TextUtils.isEmpty(getPhone())) {
            ToastUtil.show("请填写收货人联系方式");
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
        TourCooLogUtil.i(TAG, TAG + ":" + region);
        TourCooLogUtil.i(TAG, TAG + ":" + getName());
        TourCooLogUtil.i(TAG, TAG + ":" + getPhone());
        TourCooLogUtil.i(TAG, TAG + ":" + getAddressDetail());
        addAddress(region, getName(), getPhone(), getAddressDetail());

    }
}
