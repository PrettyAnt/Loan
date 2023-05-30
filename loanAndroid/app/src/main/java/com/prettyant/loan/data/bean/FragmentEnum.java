package com.prettyant.loan.data.bean;

import androidx.fragment.app.Fragment;

import com.prettyant.loan.R;
import com.prettyant.loan.ui.main.fragment.FragmentTabBusiness;
import com.prettyant.loan.ui.main.fragment.FragmentTabBusinessQuery;
import com.prettyant.loan.ui.main.fragment.FragmentTabCusCurrentTask;
import com.prettyant.loan.ui.main.fragment.FragmentTabCusFinishedTask;
import com.prettyant.loan.ui.main.fragment.FragmentTabMy;

/**
 * @author chenyu
 * My personal blog  https://prettyant.github.io
 * Created on 4:17 PM  29/05/23
 * PackageName : com.prettyant.loan.data.bean
 * describle :
 */
public enum FragmentEnum {
    fragbusiness(new FragmentTabBusiness(), R.drawable.tab1_selector, "业务办理"),
    fragbusinessquery(new FragmentTabBusinessQuery(), R.drawable.tab2_selector, "业务查询"),
    fragmy(new FragmentTabMy(), R.drawable.tab3_selector, "我的"),
    fragfinishtask(new FragmentTabCusFinishedTask(), R.drawable.tab1_1_selector, "已完成"),
    fragcurrenttask(new FragmentTabCusCurrentTask(), R.drawable.tab2_1_selector, "待处理");

    private Fragment fragment;
    private int tabIcon;
    private String tabName;

    FragmentEnum(Fragment fragment, int tabIcon, String tabName) {
        this.fragment = fragment;
        this.tabIcon = tabIcon;
        this.tabName = tabName;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public int getTabIcon() {
        return tabIcon;
    }

    public String getTabName() {
        return tabName;
    }
}
