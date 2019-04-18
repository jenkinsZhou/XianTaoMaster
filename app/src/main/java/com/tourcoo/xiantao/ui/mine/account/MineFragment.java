package com.tourcoo.xiantao.ui.mine.account;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.MenuAdapter;
import com.tourcoo.xiantao.core.frame.base.fragment.BaseTitleFragment;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.util.TourCoolUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.core.widget.dialog.alert.ConfirmDialog;
import com.tourcoo.xiantao.core.widget.divider.TourCoolRecycleViewDivider;
import com.tourcoo.xiantao.entity.MenuItem;
import com.tourcoo.xiantao.ui.mine.goods.CollectionGoodsListActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author :JenkinsZhou
 * @description :个人中心
 * @company :途酷科技
 * @date 2019年03月28日17:40
 * @Email: 971613168@qq.com
 */
public class MineFragment extends BaseTitleFragment {
    private MenuAdapter mMenuAdapter;
    private List<MenuItem> mMenuItemList = new ArrayList<>();
    private RecyclerView rvMineMenu;

    @Override
    public int getContentLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        rvMineMenu = mContentView.findViewById(R.id.rvMineMenu);
        TourCoolRecycleViewDivider divider = new TourCoolRecycleViewDivider(
                mContext, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(mContext, R.color.grayDivider));
        rvMineMenu.addItemDecoration(divider);
        rvMineMenu.setLayoutManager(new GridLayoutManager(mContext, 4));
    }


    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("我的");
    }

    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void loadData() {
        super.loadData();
        init();
    }


    private void initMenu() {
        mMenuItemList.clear();
        mMenuItemList.add(new MenuItem(R.mipmap.ic_account_balance, "账户余额"));
        mMenuItemList.add(new MenuItem(R.mipmap.ic_spell_group_records, "拼团记录"));
        mMenuItemList.add(new MenuItem(R.mipmap.ic_collection_goods, "收藏商品"));
        mMenuItemList.add(new MenuItem(R.mipmap.ic_shipping_address, "收货地址"));
        mMenuItemList.add(new MenuItem(R.mipmap.ic_customer_service_telephone, "客服电话"));
        mMenuItemList.add(new MenuItem(R.mipmap.ic_invoice_information, "发票信息"));
        mMenuItemList.add(new MenuItem(R.mipmap.ic_problem_feedback, "问题反馈"));
    }

    private void init() {
        initMenu();
        mMenuAdapter = new MenuAdapter(mMenuItemList);
        mMenuAdapter.bindToRecyclerView(rvMineMenu);
        mMenuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                        //账户余额
                        TourCoolUtil.startActivity(mContext, AccountBalanceActivity.class);
                        break;
                    case 1:
                        //拼团记录
                        break;
                    case 2:
                        //收藏商品
                        TourCoolUtil.startActivity(mContext, CollectionGoodsListActivity.class);
                        break;
                    case 3:
                        //收货地址
                        TourCoolUtil.startActivity(mContext, ShippingAddressActivity.class);
                        break;
                    case 4:
                        //客服电话
                        showPhoneDialog();
                        break;
                    case 5:
                        //发票信息
                        TourCoolUtil.startActivity(mContext, InvoiceInformationActivity.class);
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    default:
                        break;
                }
            }
        });
    }


    private void showPhoneDialog() {
        //客服电话
        ConfirmDialog.Builder builder = new ConfirmDialog.Builder(mContext);
        builder.setTitle("客服电话").setFirstMessage(getString(R.string.customer_service_telephone_numbers))
                .setFirstMsgSize(15).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        })
                .setPositiveButton("呼叫", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToastUtil.show("呼叫");
                        dialog.dismiss();
//                        ApiRepository.getInstance().updateApp()
                    }
                });
        showConfirmDialog(builder);
    }
}
