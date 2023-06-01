package com.prettyant.loan.ui.base;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.prettyant.loan.R;
import com.prettyant.loan.data.bean.FragmentEnum;
import com.prettyant.loan.ui.main.adapter.FragmentMainTabAdapter;

/**
 * @author chenyu
 * My personal blog  https://prettyant.github.io
 * Created on 4:07 PM  29/05/23
 * PackageName : com.prettyant.loan.ui.base
 * describle :
 */
public class BindAdapterUtil {
    /**
     * 设置ViewPager的数据列表
     *
     * @param viewPager2    ViewPager2
     * @param fragmentEnums 数据列表
     * @param <T>           数据类型
     */
    @SuppressWarnings("unchecked")
    @BindingAdapter("dataList")
    public static <T> void setDataList(ViewPager2 viewPager2, FragmentEnum[] fragmentEnums) {
        RecyclerView.Adapter adapter = viewPager2.getAdapter();
        if (adapter instanceof FragmentMainTabAdapter) {
//            fragmentEnums={FragmentEnum.fragbusiness,FragmentEnum.fragbusinessquery};

            ((FragmentMainTabAdapter) adapter).setFragmentEnums(fragmentEnums);
        }
    }

    @BindingAdapter(value = {"imageUrl", "placeHolder"}, requireAll = false)
    public static void loadUrl(ImageView imageView, String url, Drawable placeHolder) {

    }


    /**
     * 格式化float类型数据并且保留两位有效数字
     * @param textView
     * @param rate
     */
    @BindingAdapter("formatString2p")
    public static  void formatString2p(TextView textView,float rate) {
        String rateResult = String.format("%.2f", rate * 100) + " %";
        textView.setText(rateResult);
    }

    /**
     * 设置业务状态
     * @param imageView
     * @param status
     */
    @BindingAdapter("businessStatus")
    public static void businessStatus(ImageView imageView,int status) {
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
     * @param view
     * @param describle
     */
    @BindingAdapter(value = "businessDescrible",requireAll = false)
    public static  void businessDescrible(View view, String describle) {
        view.setVisibility(TextUtils.isEmpty(describle)?View.GONE:View.VISIBLE);
    }

    /**
     * 提交按钮是否可见
     * @param view
     * @param approve
     */
    @BindingAdapter(value = "approveButtonIsVisible",requireAll = false)
    public static  void approveButtonIsVisible(View view, boolean approve) {
        view.setVisibility(approve?View.VISIBLE:View.GONE);
    }

    /**
     * 提交按钮是否可见
     * @param view
     * @param src
     */
    @BindingAdapter(value = "businessIcon",requireAll = false)
    public static  void businessIcon(ImageView view, int src) {
        view.setImageResource(src);
    }
}
