package com.prettyant.loan.ui.base;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

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
     * @param viewPager2 ViewPager2
     * @param fragmentEnums   数据列表
     * @param <T>        数据类型
     */
    @SuppressWarnings("unchecked")
    @BindingAdapter("dataList")
    public static <T> void setDataList(ViewPager2 viewPager2, FragmentEnum[]  fragmentEnums) {
        RecyclerView.Adapter adapter = viewPager2.getAdapter();
        if (adapter instanceof FragmentMainTabAdapter) {
//            fragmentEnums={FragmentEnum.fragbusiness,FragmentEnum.fragbusinessquery};

            ((FragmentMainTabAdapter) adapter).setFragmentEnums(fragmentEnums);
        }
    }
}
