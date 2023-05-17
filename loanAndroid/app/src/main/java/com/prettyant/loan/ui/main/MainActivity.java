package com.prettyant.loan.ui.main;

import android.text.TextUtils;

import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayoutMediator;
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

    ViewPager2 viewPager2;
    TabLayout  tabLayout;

    private String[]               mTabTitles;
    private Integer[]              mTabIcons;
    private Fragment[]             mFragments;
    private FragmentMainTabAdapter fragmentMainTabAdapter;
    private TabLayoutMediator mediator;


    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        viewPager2 = (ViewPager2) $(R.id.vp_content);
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
            mTabIcons = new Integer[]{R.drawable.tab1_1_selector, R.drawable.tab2_1_selector, R.drawable.tab3_selector};
            mFragments = new Fragment[]{new FragmentTabCusFinishedTask(), new FragmentTabCusCurrentTask(), new FragmentTabMy()};
        } else {
            mTabTitles = new String[]{"业务办理", "业务查询", "我的"};
            mTabIcons = new Integer[]{R.drawable.tab1_selector, R.drawable.tab2_selector, R.drawable.tab3_selector};
            mFragments = new Fragment[]{new FragmentTabBusiness(), new FragmentTabBusinessQuery(), new FragmentTabMy()};
        }
        setupViewPager();
        setupTabLayout();
        tabLayout.getTabAt(0).select();
    }

    //设置tab并绑定
    private void setupTabLayout() {
        mediator = new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setCustomView(fragmentMainTabAdapter.getTabView(position)));
        mediator.attach();
    }

    /**
     * 设置viewPager监听
     */
    private void setupViewPager() {
        fragmentMainTabAdapter = new FragmentMainTabAdapter(this, getSupportFragmentManager(), mFragments, mTabTitles, mTabIcons);
        viewPager2.setAdapter(fragmentMainTabAdapter);
        viewPager2.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        viewPager2.registerOnPageChangeCallback(callback);
    }

    private ViewPager2.OnPageChangeCallback callback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
        }
    };

    @Override
    protected void onDestroy() {
        mediator.detach();
        viewPager2.unregisterOnPageChangeCallback(callback);
        super.onDestroy();
    }
}
