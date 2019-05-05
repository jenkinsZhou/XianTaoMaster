package com.tourcoo.xiantao.ui.goods;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SearchEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.view.navigation.KeyboardHelper;
import com.tourcoo.xiantao.ui.BaseTourCooTitleActivity;
import com.tourcoo.xiantao.ui.order.OrderDetailActivity;
import com.tourcoo.xiantao.util.StorageListSPUtils;
import com.tourcoo.xiantao.widget.custom.LabelLayout;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.tourcoo.xiantao.ui.goods.GoodsCategoryListActivity.EXTRA_KEY_WORD;
import static com.tourcoo.xiantao.ui.goods.GoodsCategoryListActivity.EXTRA_TITLE_NAME;
import static com.tourcoo.xiantao.ui.order.OrderDetailActivity.EXTRA_ORDER_ID;

/**
 * @author :zhoujian
 * @description :搜索商品页面
 * @company :翼迈科技
 * @date 2019年 05月 02日 09时02分
 * @Email: 971613168@qq.com
 */
public class SearchGoodsActivity extends BaseTourCooTitleActivity implements View.OnClickListener {
    private EditText etSearchGoods;
    private TextView tvCancel;
    private RelativeLayout rlSearchHistory;
    private LabelLayout labelLayout;

    private static final int DEFAULT_SEARCH_HISTORY_COUNT = 50;

    private static final int REQUEST_CODE_SEARCHE = 1002;
    /**
     * SharedPreferences 存取 搜索历史 的标签
     */
    private static final String TAG_SEARCH_HISTORY = "tagSearchHistory";

    /**
     * 存储 搜索历史集合 的工具类
     */
    private StorageListSPUtils mStorageListSPUtils;
    /**
     * 搜索历史
     */
    private List<String> mSearchHistoryLists;

    @Override
    public int getContentLayout() {
        return R.layout.activity_search_goods;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        etSearchGoods = findViewById(R.id.etSearchGoods);
        tvCancel = findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(this);
        findViewById(R.id.tvClearSearch).setOnClickListener(this);
        findViewById(R.id.header_back_image).setOnClickListener(this);
        rlSearchHistory = findViewById(R.id.rlSearchHistory);
        labelLayout = findViewById(R.id.labelLayout);
        init();
        initView();
        initInputListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCancel:
                finish();
                break;
            case R.id.tvClearSearch:
                clearSearch();
                break;
            case R.id.header_back_image:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 初始化
     */
    private void init() {
        // 初始化搜索历史
        mSearchHistoryLists = new ArrayList<>();
        // 初始化存储 搜索历史集合 的工具类
        mStorageListSPUtils = new StorageListSPUtils(this, TAG);
    }


    /**
     * 初始化搜索历史布局
     */
    private void initView() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        // 获取 SharedPreferences 中已存储的 搜索历史
        mSearchHistoryLists = mStorageListSPUtils.loadDataList(TAG_SEARCH_HISTORY);
        if (mSearchHistoryLists.size() != 0) {
            rlSearchHistory.setVisibility(View.VISIBLE);
            for (int i = mSearchHistoryLists.size() - 1; i >= 0; i--) {
                TextView textView = (TextView) layoutInflater.inflate(R.layout.search_history_tv, labelLayout, false);
                final String historyStr = mSearchHistoryLists.get(i);
                textView.setText(historyStr);
                // 设置搜索历史的回显
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        skipSearchResult(textView.getText().toString());
                    }
                });
                // FlowLayout 中添加 搜索历史
                labelLayout.addView(textView);
            }
        } else {
            rlSearchHistory.setVisibility(View.GONE);
        }
    }


    /**
     * 存取 SharedPreferences 中存储的搜索历史并做相应的处理
     */
    private void processAction() {
        // 获取 EditText 输入内容
        String searchInput = etSearchGoods.getText().toString().trim();
        if (TextUtils.isEmpty(searchInput)) {
            ToastUtil.show("请输入内容");
        } else {
            // 先获取之前已经存储的搜索历史
            List<String> previousLists = mStorageListSPUtils.loadDataList(TAG_SEARCH_HISTORY);
            if (previousLists.size() != 0) {
                // 如果之前有搜索历史，则添加
                mSearchHistoryLists.clear();
                mSearchHistoryLists.addAll(previousLists);
            }
            // 去除重复，如果搜索历史中已经存在则remove，然后添加到后面
            if (!mSearchHistoryLists.contains(searchInput)) {
                // 如果搜索历史超过设定的默认个数，去掉最先添加的，并把新的添加到最后
                // 这里只展示10个搜索历史，根据需要修改为你自己想要的数值
                if (mSearchHistoryLists.size() >= DEFAULT_SEARCH_HISTORY_COUNT) {
                    mSearchHistoryLists.remove(0);
                    mSearchHistoryLists.add(mSearchHistoryLists.size(), searchInput);
                } else {
                    mSearchHistoryLists.add(searchInput);
                }
            } else {
                // 如果搜索历史已存在，找到其所在的下标值
                int inputIndex = -1;
                for (int i = 0; i < mSearchHistoryLists.size(); i++) {
                    if (searchInput.equals(mSearchHistoryLists.get(i))) {
                        inputIndex = i;
                    }
                }
                // 如果搜索历史已存在，先从 List 集合中移除再添加到集合的最后
                mSearchHistoryLists.remove(inputIndex);
                mSearchHistoryLists.add(mSearchHistoryLists.size(), searchInput);
            }
            // 存储新的搜索历史到 SharedPreferences
            mStorageListSPUtils.saveDataList(TAG_SEARCH_HISTORY, mSearchHistoryLists);
        }
    }

    private void initInputListener() {
        etSearchGoods.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //按下搜索
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (etSearchGoods.getText().toString().length() == 0) {
                        ToastUtil.show("请输入结果");
                        return true;
                    }
                    processAction();
                    skipSearchResult(etSearchGoods.getText().toString().trim());
                    KeyboardHelper.closeKeyboard(mContext);
                    return true;
                }
                //返回true，保留软键盘。false，隐藏软键盘
                return false;
            }
        });
    }


    private void clearSearch() {
        // 清楚 SharedPreferences 中存储的搜索历史
        mStorageListSPUtils.removeDateList(TAG_SEARCH_HISTORY);
        mSearchHistoryLists.clear();
        labelLayout.removeAllViews();
        // 删除之后，搜索历史布局隐藏
        rlSearchHistory.setVisibility(View.GONE);
    }

    /**
     * 跳转到搜索详情
     */
    private void skipSearchResult(String keyword) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_KEY_WORD, keyword);
        intent.putExtra(EXTRA_TITLE_NAME, "搜索结果");
        intent.setClass(mContext, GoodsCategoryListActivity.class);
        //清除搜索框文本
        etSearchGoods.getText().clear();
        startActivityForResult(intent, REQUEST_CODE_SEARCHE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SEARCHE:
                labelLayout.removeAllViews();
                initView();
                break;
            default:
                break;
        }
    }
}


