package com.prettyant.loan.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.prettyant.loan.R;
import com.prettyant.loan.data.bean.FragmentEnum;

/**
 * 导航适配器
 */
public class FragmentMainTabAdapter extends FragmentStateAdapter {
    private Context mContext;
    private FragmentEnum[] fragmentEnums;

    public FragmentMainTabAdapter(FragmentActivity context) {
        super(context);
        this.mContext = context;
    }

    public void setFragmentEnums(FragmentEnum[] fragmentEnums) {
        this.fragmentEnums = fragmentEnums;
    }

    public View getTabView(int position) {
        View tab = LayoutInflater.from(mContext).inflate(R.layout.item_main_tab, null);
        TextView tabText = (TextView) tab.findViewById(R.id.tv_title);
        ImageView tabImage = (ImageView) tab.findViewById(R.id.iv_icon);
        tabText.setText(fragmentEnums[position].getTabName());
        tabImage.setBackgroundResource(fragmentEnums[position].getTabIcon());
        if (position == 0) {
            tab.setSelected(true);
        }
        return tab;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentEnums[position].getFragment();
    }

    @Override
    public int getItemCount() {
        return fragmentEnums.length;
    }
}
