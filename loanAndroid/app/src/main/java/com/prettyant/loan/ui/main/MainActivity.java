package com.prettyant.loan.ui.main;

import android.text.TextUtils;

import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.prettyant.loan.R;
import com.prettyant.loan.cons.ContantFields;
import com.prettyant.loan.ui.base.BaseActivity;
import com.prettyant.loan.ui.main.adapter.FragmentMainTabAdapter;
import com.prettyant.loan.ui.main.fragment.FragmentTabBusiness;
import com.prettyant.loan.ui.main.fragment.FragmentTabBusinessQuery;
import com.prettyant.loan.ui.main.fragment.FragmentTabCusCurrentTask;
import com.prettyant.loan.ui.main.fragment.FragmentTabCusFinishedTask;
import com.prettyant.loan.ui.main.fragment.FragmentTabMy;


/**
 * 主页
 */
public class MainActivity extends BaseActivity {

    ViewPager viewPager;
    TabLayout tabLayout;

    private String[] mTabTitles;
    private Integer[] mTabIcons;
    private Fragment[] mFragments;
    private FragmentMainTabAdapter fragmentMainTabAdapter;

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        viewPager = (ViewPager) $(R.id.vp_content);
        tabLayout = (TabLayout) $(R.id.tabl_navigation);
    }

    @Override
    public void initClick() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initData() {
        if (TextUtils.equals(ContantFields.username, "zhangsan") || TextUtils.equals(ContantFields.username, "lisi")) {
            mTabTitles = new String[]{"已完成", "待处理", "我的"};
            mTabIcons = new Integer[]{R.drawable.tab1_1_selector,R.drawable.tab2_1_selector,  R.drawable.tab3_selector};
            mFragments = new Fragment[]{new FragmentTabCusFinishedTask(), new FragmentTabCusCurrentTask(), new FragmentTabMy()};
        } else {
            mTabTitles = new String[]{"业务办理", "业务查询", "我的"};
            mTabIcons = new Integer[]{R.drawable.tab1_selector,R.drawable.tab2_selector,  R.drawable.tab3_selector};
            mFragments = new Fragment[]{new FragmentTabBusiness(), new FragmentTabBusinessQuery(), new FragmentTabMy()};
        }
        setupViewPager();
        setupTabLayout();
        tabLayout.getTabAt(0).select();
    }

    private void setupTabLayout() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(fragmentMainTabAdapter.getTabView(i));
        }
        tabLayout.requestFocus();
    }

    private void setupViewPager() {
        fragmentMainTabAdapter = new FragmentMainTabAdapter(context, getSupportFragmentManager(), mFragments, mTabTitles, mTabIcons);
        viewPager.setAdapter(fragmentMainTabAdapter);
        viewPager.setOffscreenPageLimit(2);
    }


}
