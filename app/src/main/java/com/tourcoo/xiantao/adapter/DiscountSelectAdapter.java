package com.tourcoo.xiantao.adapter;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.log.widget.utils.DateUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.entity.discount.DiscountInfo;

import org.apache.commons.lang.time.FastDateFormat;

import java.util.Date;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年05月10日15:41
 * @Email: 971613168@qq.com
 */
public class DiscountSelectAdapter extends BaseQuickAdapter<DiscountInfo, BaseViewHolder> {
    private static final String STATUS_TEXT_NORMAL = "normal";
    private static final int STATUS_NOT_USE = 1;
    private static final int STATUS_HAS_USE = 2;
    private static final int STATUS_TIME_OUT = 3;
    private final static String PATTERN = "yyyy.MM.dd";

    public DiscountSelectAdapter() {
        super(R.layout.item_discount_select);
    }


    @Override
    protected void convert(BaseViewHolder helper, DiscountInfo discountInfo) {
        RelativeLayout rlSelect = helper.getView(R.id.rlSelect);
        RelativeLayout rlPrice = helper.getView(R.id.rlPrice);
        helper.addOnClickListener(R.id.rlSelect);
        rlSelect.setClickable(discountInfo.isClickEnable());
        String cost = "满" + discountInfo.getCost() + "元可用";
        helper.setText(R.id.tvPrice, discountInfo.getWorth()+"");
        helper.setText(R.id.tvDiscount, cost);
        String name = "" + discountInfo.getName();
        TextView tvDIscountName = helper.getView(R.id.tvDiscountName);
        TextView tvCanAdd = helper.getView(R.id.tvCanAdd);
        TextView tvDeadLine = helper.getView(R.id.tvDeadLine);
        TextView tvPrice = helper.getView(R.id.tvPrice);
        TextView yuan = helper.getView(R.id.yuan);
        helper.setText(R.id.tvDiscountName, name);
        if (discountInfo.getNum() > 1) {
            helper.setText(R.id.tvCanAdd, "[可叠加]");
        } else {
            helper.setText(R.id.tvCanAdd, "[不可叠加]");
        }
        helper.setText(R.id.tvDiscount, cost);
        helper.setText(R.id.tvDeadLine, "有效期至" + parseDate(discountInfo.getDeadline()));
        ImageView imageView = helper.getView(R.id.ivSelect);
        if (discountInfo.isSelect()) {
            imageView.setImageResource(R.mipmap.ic_checked);
        } else {
            imageView.setImageResource(R.mipmap.ic_unchecked);
        }
        int status = parseStatus(discountInfo);
        switch (status) {
            case STATUS_NOT_USE:
                setTextColor(tvDIscountName, R.color.greenCommon);
                setTextColor(tvCanAdd, R.color.greenCommon);
                setTextColor(tvCanAdd, R.color.greenCommon);
                setTextColor(tvDeadLine, R.color.colorTextGray);
                setTextColor(tvPrice, R.color.greenCommon);
                setTextColor(yuan, R.color.greenCommon);
                rlPrice.setBackground(TourCooUtil.getDrawable(R.mipmap.bg_discount));
                break;
            case STATUS_HAS_USE:
                setTextColor(tvDeadLine, R.color.gray_CCCCCC);
                setTextColor(tvDIscountName, R.color.gray_AAAAAA);
                setTextColor(tvCanAdd, R.color.gray_AAAAAA);
                setTextColor(tvCanAdd, R.color.gray_AAAAAA);
                setTextColor(tvPrice, R.color.gray_AAAAAA);
                setTextColor(yuan, R.color.gray_CCCCCC);
                rlPrice.setBackground(TourCooUtil.getDrawable(R.mipmap.bg_discount_time_out));
                break;
            case STATUS_TIME_OUT:
                setTextColor(tvDeadLine, R.color.gray_CCCCCC);
                setTextColor(tvDIscountName, R.color.gray_AAAAAA);
                setTextColor(tvCanAdd, R.color.gray_AAAAAA);
                setTextColor(tvPrice, R.color.gray_AAAAAA);
                setTextColor(yuan, R.color.gray_CCCCCC);
                rlPrice.setBackground(TourCooUtil.getDrawable(R.mipmap.bg_discount_time_out));
                break;
            default:
                break;
        }


    }


    private int parseStatus(DiscountInfo discountInfo) {
        if (discountInfo == null) {
            return -1;
        }
        if (STATUS_TEXT_NORMAL.equalsIgnoreCase(discountInfo.getStatus())) {
            //需要判断截止时间是否大于当前时间
            long lineTime = discountInfo.getDeadline() * 1000;
            long curentTime = System.currentTimeMillis();
            TourCooLogUtil.i(TAG, TAG + ":" + "截止时间:" + lineTime);
            TourCooLogUtil.i(TAG, TAG + ":" + "当前时间:" + curentTime);
            if (lineTime < curentTime) {
                return STATUS_TIME_OUT;
            } else {
                return STATUS_NOT_USE;
            }
        } else {
            return STATUS_HAS_USE;
        }
    }


    public String parseDate(long timeMillis) {
        timeMillis = timeMillis * 1000;
        final FastDateFormat df = FastDateFormat.getInstance(PATTERN);
        try {
            return df.format(new Date(timeMillis));
        } catch (Exception e) {
            return "";
        }
    }
    private void setTextColor(TextView textView, int colorId) {
        textView.setTextColor(TourCooUtil.getColor(colorId));
    }
}
