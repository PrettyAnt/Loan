package com.prettyant.loan.ui.main.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prettyant.loan.R;
import com.prettyant.loan.cons.ContantFields;
import com.prettyant.loan.ui.base.BaseFragment;
import com.prettyant.loan.ui.login.LoginActivity;
import com.prettyant.loan.util.LogUtil;


/**
 */
public class FragmentTabMy extends BaseFragment {

    LinearLayout ll_head;
    private TextView tv_username;
    private TextView tv_logout;

    @Override
    public int getContentView() {
        return R.layout.fragment_3;
    }

    @Override
    public void initView() {
        initTitleBar("", "我的", "", 0, this);
        ll_head = (LinearLayout) $(R.id.ll_head);
        ll_head.setBackgroundColor(getResources().getColor(R.color.common_title_bg));
        tv_username = (TextView) $(R.id.tv_username);
        tv_logout = (TextView) $(R.id.tv_logout);
    }

    @Override
    public void initClick() {
        tv_logout.setOnClickListener(this);
    }

    @Override
    public void initData() {
        tv_username.setText(ContantFields.username);
        LogUtil.e("FragmentTabMy");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_logout) {
            ContantFields.username = "";
            Intent intent = new Intent(getActivity(),LoginActivity.class);
            getActivity().startActivity(intent);
            getActivity().finish();
        }
    }
}
