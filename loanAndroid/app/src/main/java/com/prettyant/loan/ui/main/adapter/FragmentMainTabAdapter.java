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

public class FragmentMainTabAdapter extends FragmentStateAdapter {
    private Context mContext;

    private Fragment[] mFragments = null;
    private String[] mFragmentTitles =null;
    private Integer[] mFragmentIcons = null;

    public FragmentMainTabAdapter(FragmentActivity context, FragmentManager fm, Fragment[] fragments, String[] titles, Integer[] icons) {
        super(context);
        this.mContext = context;
        mFragments = fragments;
        mFragmentTitles = titles;
        mFragmentIcons = icons;
    }


    public View getTabView(int position) {
        View tab = LayoutInflater.from(mContext).inflate(R.layout.item_main_tab, null);
        TextView tabText = (TextView) tab.findViewById(R.id.tv_title);
        ImageView tabImage = (ImageView) tab.findViewById(R.id.iv_icon);
        tabText.setText(mFragmentTitles[position]);
        tabImage.setBackgroundResource(mFragmentIcons[position]);
        if (position == 0) {
            tab.setSelected(true);
        }
        return tab;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments[position];
    }

    @Override
    public int getItemCount() {
        return mFragments.length;
    }
}
