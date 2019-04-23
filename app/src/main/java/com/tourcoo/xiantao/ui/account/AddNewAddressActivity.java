package com.tourcoo.xiantao.ui.account;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.AddressBean;
import com.tourcoo.xiantao.ui.BaseTourCooTitleActivity;
import com.tourcoo.xiantao.util.AddressHelper;
import com.tourcoo.xiantao.widget.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.tourcoo.xiantao.widget.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.tourcoo.xiantao.widget.bigkoo.pickerview.view.OptionsPickerView;

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
    private List<AddressBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private TextView tvSelectAddress;

    @Override
    public int getContentLayout() {
        return R.layout.activity_add_new_address;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tvSelectAddress = findViewById(R.id.tvSelectAddress);
        tvSelectAddress.setOnClickListener(this);
        loadAddress();

    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("新增地址");
    }

    /**
     * 弹出选择器
     */
    private void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
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
                Toast.makeText(mContext, tx, Toast.LENGTH_SHORT).show();
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                //设置选中项文字颜色
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        //三级选择器
        pvOptions.setPicker(options1Items, options2Items, options3Items);
        pvOptions.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSelectAddress:
                showPickerView();
                break;
            default:
                break;
        }
    }


    private void loadAddress() {
        List<AddressBean> addressBeans = AddressHelper.getInstance().getAddressInfo();
        options1Items = addressBeans;
        for (int i = 0; i < addressBeans.size(); i++) {
            //遍历省份
            ArrayList<String> cityList = new ArrayList<>();
            //该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();
            //该省的所有地区列表（第三极）
            for (int c = 0; c < addressBeans.get(i).getCityList().size(); c++) {
                //遍历该省份的所有城市
                String cityName = addressBeans.get(i).getCityList().get(c).getName();
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
                city_AreaList.addAll(addressBeans.get(i).getCityList().get(c).getArea());
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
}
