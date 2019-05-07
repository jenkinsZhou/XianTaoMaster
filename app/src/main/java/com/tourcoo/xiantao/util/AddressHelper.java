package com.tourcoo.xiantao.util;


import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.tourcoo.xiantao.XianTaoApplication;
import com.tourcoo.xiantao.core.frame.entity.DataEntity;
import com.tourcoo.xiantao.core.frame.entity.NeedEntity;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.threadpool.ThreadPoolManager;
import com.tourcoo.xiantao.entity.AddressPickerBean;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :zhoujian
 * @description : 地址管理类
 * @company :途酷科技
 * @date 2019年04月023日上午 10:12
 * @Email: 971613168@qq.com
 */
public class AddressHelper {
    private static final String TAG = "AddressHelper";
    private static volatile AddressHelper singleton;

    private ArrayList<AddressPickerBean> mAddressPickerBean = new ArrayList<>();
    /**
     * 省份
     */
    private List<String> provinceList = new ArrayList<>();

    public ArrayList<String> getCityList() {
        return cityList;
    }

    public void setCityList(ArrayList<String> cityList) {
        this.cityList = cityList;
    }

    public ArrayList<String> getAreaList() {
        return areaList;
    }

    public void setAreaList(ArrayList<String> areaList) {
        this.areaList = areaList;
    }

    /**
     * 城市
     */
    private ArrayList<String> cityList = new ArrayList<>();

    /**
     * 行政区
     */
    private ArrayList<String> areaList = new ArrayList<>();

    ArrayList<ArrayList<String>> wholeAddressInfo = new ArrayList<>();


    private AddressHelper() {
    }

    public static AddressHelper getInstance() {
        if (singleton == null) {
            synchronized (AddressHelper.class) {
                if (singleton == null) {
                    singleton = new AddressHelper();
                }
            }
        }
        return singleton;
    }

    /**
     * 解析数据
     */
    public void initAddressData() {
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         * */
        ThreadPoolManager.getThreadPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
//                String addressInfo = new GetJsonDataUtil().getJson(XianTaoApplication.getContext(), "province.json");
                String addressInfo = new GetJsonDataUtil().getJson(XianTaoApplication.getContext(), "city_new.json");
                //获取assets目录下的json文件数据
                //转成实体
                mAddressPickerBean.addAll(parseData(addressInfo));
                loadAddress(mAddressPickerBean);
                /**
                 * 添加省份数据
                 * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
                 * PickerView会通过getPickerViewText方法获取字符串显示出来。
                 */TourCooLogUtil.i(TAG, TAG + ":" + "加载完毕");
            }
        });
    }


    private ArrayList<AddressPickerBean> parseData(String result) {
        ArrayList<AddressPickerBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                AddressPickerBean entity = gson.fromJson(data.optJSONObject(i).toString(), AddressPickerBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            TourCooLogUtil.e(TAG, TAG + ":" + "解析失败");
        }
        return detail;
    }


    public List<AddressPickerBean> getAddressInfo() {
        return mAddressPickerBean;
    }


    private void loadAddress(List<AddressPickerBean> addressPickerBeanList) {
        if (addressPickerBeanList == null) {
            return;
        }
        //遍历省份
        cityList.clear();
        for (int i = 0; i < addressPickerBeanList.size(); i++) {
            //该省的城市列表（第二级）
            //该省的所有地区列表（第三极）
            for (int c = 0; c < addressPickerBeanList.get(i).getCityList().size(); c++) {
                //遍历该省份的所有城市
                String cityName = addressPickerBeanList.get(i).getCityList().get(c).getName();
                //添加城市
                cityList.add(cityName);
                //该城市的所有地区列表
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/

                //区
                areaList.addAll(addressPickerBeanList.get(i).getCityList().get(c).getArea());
                //添加该省所有地区数据
                wholeAddressInfo.add(areaList);
            }
        }
    }

    public ArrayList<ArrayList<String>> getWholeAddressInfo() {
        return wholeAddressInfo;
    }

    public void setWholeAddressInfo(ArrayList<ArrayList<String>> wholeAddressInfo) {
        this.wholeAddressInfo = wholeAddressInfo;
    }


    public void loadAddress() {
        ThreadPoolManager.getThreadPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                loadJsonData();
            }
        });
    }


    private void loadJsonData() {
        try {
            String addressInfo = new GetJsonDataUtil().getJson(XianTaoApplication.getContext(), "city_data_new.json");
            List<DataEntity> dataEntityList = JSON.parseArray(addressInfo, DataEntity.class);
            if (dataEntityList == null) {
                TourCooLogUtil.i(TAG, TAG + "数据为空:");
                return;
            }
            List<NeedEntity> entityList = new ArrayList<>();
            NeedEntity needEntity;
            NeedEntity.CityBean cityBean;
            List<NeedEntity.CityBean> cityList;
            List<String> areaList;
            for (DataEntity dataEntity : dataEntityList) {
                if (dataEntity == null) {
                    continue;
                }
                needEntity = new NeedEntity();
                cityList = new ArrayList<>();
                for (DataEntity.CityListBean cityListBean : dataEntity.getCityList()) {
                    cityBean = new NeedEntity.CityBean();
                    cityBean.setName(cityListBean.getName());
                    areaList = new ArrayList<>();
                    for (DataEntity.CityListBean.AreaListBean areaListBean : cityListBean.getAreaList()) {
                        areaList.add(areaListBean.getName());
                    }
                    cityBean.setArea(areaList);
                    cityList.add(cityBean);
                }
                needEntity.setCity(cityList);
                needEntity.setName(dataEntity.getName());
                entityList.add(needEntity);
            }
            TourCooLogUtil.i(TAG, TAG + "数据长度:" + entityList.size());
            String info = JSON.toJSONString(entityList);
            String filePath = FileUtil.getExternalStorageDirectory() + "JenkinsZhou/" + "city.txt";
            LogUtils.i("解析的地址:" + info);
            ThreadPoolManager.getThreadPoolProxy().execute(new Runnable() {
                @Override
                public void run() {
                    FileUtil.writeStringToLocal(info, filePath);
                }
            });
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, TAG + ":" + "解析失败：" + e.toString());
        }
    }


}