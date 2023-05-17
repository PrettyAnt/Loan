package com.prettyant.loan.ui.detail;

import android.content.Intent;

import com.prettyant.loan.R;
import com.prettyant.loan.ui.base.BaseActivity;

/**
 * 操作员审核页面
 */
public class ApproveActivity extends BaseActivity{


    @Override
    public int getContentView() {
        return R.layout.activity_approve;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initClick() {

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        String processInstanceId = intent.getStringExtra("processInstanceId");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

