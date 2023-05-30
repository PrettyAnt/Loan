package com.prettyant.loan.ui.main;

import android.text.TextUtils;

import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.tabs.TabLayoutMediator;
import com.prettyant.loan.R;
import com.prettyant.loan.cons.ContantFields;
import com.prettyant.loan.data.bean.FragmentEnum;
import com.prettyant.loan.databinding.ActivityMainBinding;
import com.prettyant.loan.ui.base.BaseJetActivity;
import com.prettyant.loan.ui.main.adapter.FragmentMainTabAdapter;


/**
 * 主页
 */
@Route(path = ContantFields.ACTIVITY_MAIN)
public class MainActivity extends BaseJetActivity<ActivityMainBinding, MainViewModel> {

    private ViewPager2.OnPageChangeCallback callback=new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
        }
    };
    private TabLayoutMediator tabLayoutMediator;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewModel() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        dataBinding.setVm(viewModel);
    }

    @Override
    protected void init() {
        initView();
    }

    private void initView() {
        /**
         * google官方导航，不支持左右滑动?
         */
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        BottomNavigationView navViewBottom = dataBinding.navViewBottom;
//        navViewBottom.getMenu().findItem(R.id.nav_mine).setVisible(false);
//        NavigationUI.setupWithNavController(navViewBottom,navController);

        //设置viewpager监听
        FragmentMainTabAdapter fragmentMainTabAdapter = new FragmentMainTabAdapter(this);
        FragmentEnum[] fragmentEnums = null;
        if (TextUtils.equals(ContantFields.username, "zhangsan") || TextUtils.equals(ContantFields.username, "lisi")) {
            fragmentEnums = new FragmentEnum[]{FragmentEnum.fragfinishtask, FragmentEnum.fragcurrenttask, FragmentEnum.fragmy};
        } else {
            fragmentEnums = new FragmentEnum[]{FragmentEnum.fragbusiness, FragmentEnum.fragbusinessquery, FragmentEnum.fragmy};
        }
        fragmentMainTabAdapter.setFragmentEnums(fragmentEnums);
        dataBinding.vpContent.setAdapter(fragmentMainTabAdapter);
        dataBinding.vpContent.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        dataBinding.vpContent.registerOnPageChangeCallback(callback);
        //设置tab并绑定
        tabLayoutMediator = new TabLayoutMediator(dataBinding.tablNavigation, dataBinding.vpContent, (tab, position) -> {
            tab.setCustomView(fragmentMainTabAdapter.getTabView(position));
        });
        tabLayoutMediator.attach();
        //设置默认选中tab
        dataBinding.tablNavigation.getTabAt(0).select();
    }

    @Override
    protected void onDestroy() {
        tabLayoutMediator.detach();
        dataBinding.vpContent.unregisterOnPageChangeCallback(callback);
        super.onDestroy();
    }
}
