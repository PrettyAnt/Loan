package com.prettyant.loan.ui.base;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.prettyant.loan.R;

/**
 * @author chenyu
 * My personal blog  https://prettyant.github.io
 * Created on 4:07 PM  29/05/23
 * PackageName : com.prettyant.loan.ui.base
 * describle :
 */
public class BindAdapterUtil {
    /**
     * 格式化float类型数据并且保留两位有效数字
     *
     * @param textView
     * @param rate
     */
    @BindingAdapter("formatString2p")
    public static void formatString2p(TextView textView, float rate) {
        String rateResult = String.format("%.2f", rate * 100) + " %";
        textView.setText(rateResult);
    }

    /**
     * 设置业务状态
     *
     * @param imageView
     * @param status
     */
    @BindingAdapter("businessStatus")
    public static void businessStatus(ImageView imageView, int status) {
        if (status == 1) {
            imageView.setImageResource(R.drawable.icon_status_ok);
        } else if (status == 2) {
            imageView.setImageResource(R.drawable.icon_status_unpass);
        } else {
            imageView.setImageResource(R.drawable.icon_status_deal);
        }
    }

    /**
     * 是否可见
     *
     * @param view
     * @param describle
     */
    @BindingAdapter(value = "businessDescrible", requireAll = false)
    public static void businessDescrible(View view, String describle) {
        view.setVisibility(TextUtils.isEmpty(describle) ? View.GONE : View.VISIBLE);
    }

    /**
     * 提交按钮是否可见
     *
     * @param view
     * @param approve
     */
    @BindingAdapter(value = "approveButtonIsVisible", requireAll = false)
    public static void approveButtonIsVisible(View view, boolean approve) {
        view.setVisibility(approve ? View.VISIBLE : View.GONE);
    }

    /**
     * 业务类型
     *
     * @param view
     * @param src
     */
    @BindingAdapter(value = "businessIcon", requireAll = false)
    public static void businessIcon(ImageView view, int src) {
        view.setImageResource(src);
    }

    /**
     * 节点/处理人
     *
     * @param view
     * @param assignee
     */
    @BindingAdapter(value = "flowAssignee", requireAll = false)
    public static void flowAssignee(TextView view, String assignee) {
        if (!TextUtils.equals(assignee, "服务节点")) {
            assignee = "审批人: " + assignee;
        }
        view.setText(assignee);
    }

    /**
     * 当前状态  todo 两种写法
     *
     * @param view
     * @param activityName
     */
    @BindingAdapter(value = "flowActivityName", requireAll = false)
    public static void flowActivityName(TextView view, String activityName) {
        view.setText("当前状态:" + activityName);
    }

    /**
     * 是否为最新订单  fixme 如果不是第一个，那该如何处理??  size()-1 ??
     * @param view
     * @param position
     */
    @BindingAdapter(value = "flowHolderPosition", requireAll = false)
    public static void flowHolderPosition(TextView view, int position) {
        if (position == 0) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }
}
