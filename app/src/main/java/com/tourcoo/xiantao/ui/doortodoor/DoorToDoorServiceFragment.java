package com.tourcoo.xiantao.ui.doortodoor;

import android.os.Bundle;

import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.base.fragment.BaseTitleFragment;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;

/**
 * @author :JenkinsZhou
 * @description :上门服务fragment
 * @company :途酷科技
 * @date 2019年03月15日16:47
 * @Email: 971613168@qq.com
 */
public class DoorToDoorServiceFragment extends BaseTitleFragment {


    @Override
    
    public int getContentLayout() {
        return R.layout.fragment_door_to_door_service;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("上门服务");
    }

    public static DoorToDoorServiceFragment newInstance() {
        Bundle args = new Bundle();
        DoorToDoorServiceFragment fragment = new DoorToDoorServiceFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
