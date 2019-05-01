package com.tourcoo.xiantao.core.frame.impl;

import android.accounts.AccountsException;
import android.accounts.NetworkErrorException;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tourcoo.xiantao.XianTaoApplication;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.interfaces.HttpRequestControl;
import com.tourcoo.xiantao.core.frame.interfaces.HttpRequestListener;
import com.tourcoo.xiantao.core.frame.interfaces.IRequestControl;
import com.tourcoo.xiantao.core.frame.util.NetworkUtil;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import retrofit2.HttpException;

/**
 * @author :zhoujian
 * @description :网络请求成功实现类
 * @company :途酷科技
 * @date 2019年 03月 05日 21时23分
 * @Email: 971613168@qq.com
 */
public class HttpRequestControlImpl implements HttpRequestControl {

    private static String TAG = "HttpRequestControlImpl";

    @SuppressWarnings("unchecked")
    @Override
    public void httpRequestSuccess(IRequestControl requestControl, List<?> list, HttpRequestListener listener) {
        if (requestControl == null) {
            return;
        }
        SmartRefreshLayout smartRefreshLayout = requestControl.getRefreshLayout();
        BaseQuickAdapter adapter = requestControl.getRecyclerAdapter();
        StatusLayoutManager statusLayoutManager = requestControl.getStatusLayoutManager();
        int page = requestControl.getCurrentPage();
        int size = requestControl.getPageSize();

        TourCooLogUtil.i(TAG, "smartRefreshLayout:" + smartRefreshLayout + ";adapter:" + adapter + ";status:" + ";page:" + page + ";class:" + requestControl.getRequestClass());
        if (smartRefreshLayout != null) {
            smartRefreshLayout.finishRefresh();
        }
        if (adapter == null) {
            return;
        }
        adapter.loadMoreComplete();
        if (list == null || list.size() == 0) {
            //第一页没有
            if (page == 1 || page == 0) {
                adapter.setNewData(new ArrayList());
                statusLayoutManager.showEmptyLayout();
                if (listener != null) {
                    listener.onEmpty();
                }
            } else {
                adapter.loadMoreEnd();
                if (listener != null) {
                    listener.onNoMore();
                }
            }
            return;
        }
        statusLayoutManager.showSuccessLayout();
        boolean refresh = smartRefreshLayout != null && (smartRefreshLayout.isRefreshing() || page == 1);
        if (refresh) {
            adapter.setNewData(new ArrayList());
        }
        adapter.addData(list);
        if (listener != null) {
            listener.onNext();
        }
        if (list.size() < size) {
            adapter.loadMoreEnd();
            if (listener != null) {
                listener.onNoMore();
            }
        }
    }

    @Override
    public void httpRequestError(IRequestControl requestControl, Throwable e) {
        int reason = R.string.exception_other_error;
        TourCooLogUtil.e(TAG, TAG + ":" + e.toString());

//        int code = FastError.EXCEPTION_OTHER_ERROR;
        if (!NetworkUtil.isConnected(XianTaoApplication.getContext())) {
            reason = R.string.exception_network_not_connected;
        } else {
            //网络异常--继承于AccountsException
            if (e instanceof NetworkErrorException) {
                reason = R.string.exception_network_error;
                //账户异常
            } else if (e instanceof AccountsException) {
                reason = R.string.exception_accounts;
                //连接异常--继承于SocketException
            } else if (e instanceof ConnectException) {
                reason = R.string.exception_connect;
                //socket异常
            } else if (e instanceof SocketException) {
                reason = R.string.exception_socket;
                // http异常
            } else if (e instanceof HttpException) {
                reason = R.string.exception_http;
                //DNS错误
            } else if (e instanceof UnknownHostException) {
                reason = R.string.exception_unknown_host;
            } else if (e instanceof JsonSyntaxException
                    || e instanceof JsonIOException
                    || e instanceof JsonParseException) {
                //数据格式化错误
                reason = R.string.exception_json_syntax;
            } else if (e instanceof SocketTimeoutException || e instanceof TimeoutException) {
                reason = R.string.exception_time_out;
            } else if (e instanceof ClassCastException) {
                reason = R.string.exception_class_cast;
            }
        }
        if (requestControl == null || requestControl.getStatusLayoutManager() == null) {
            ToastUtil.show(reason);
            return;
        }
        SmartRefreshLayout smartRefreshLayout = requestControl.getRefreshLayout();
        BaseQuickAdapter adapter = requestControl.getRecyclerAdapter();
        StatusLayoutManager statusLayoutManager = requestControl.getStatusLayoutManager();
        int page = requestControl.getCurrentPage();
        if (smartRefreshLayout != null) {
            smartRefreshLayout.finishRefresh(false);
        }
        if (adapter != null) {
            adapter.loadMoreComplete();
            if (statusLayoutManager == null) {
                return;
            }
            //初始页
            if (page == 1 || page == 0) {
                if (!NetworkUtil.isConnected(XianTaoApplication.getContext())) {
                    //可自定义网络错误页面展示
                    statusLayoutManager.showCustomLayout(R.layout.layout_status_layout_manager_error);
                } else {
                    statusLayoutManager.showErrorLayout();
                }
                return;
            }
            //可根据不同错误展示不同错误布局  showCustomLayout(R.layout.xxx);
            statusLayoutManager.showErrorLayout();
        }
    }
}
