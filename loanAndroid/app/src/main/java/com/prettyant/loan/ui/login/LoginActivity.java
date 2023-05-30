package com.prettyant.loan.ui.login;

import android.text.TextUtils;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.launcher.ARouter;
import com.prettyant.loan.R;
import com.prettyant.loan.cons.ContantFields;
import com.prettyant.loan.databinding.ActivityLoginBinding;
import com.prettyant.loan.ui.base.BaseJetActivity;
import com.prettyant.loan.util.LogUtil;
import com.tencent.mmkv.MMKV;

/**
 * 登录页面
 */
public class LoginActivity extends BaseJetActivity<ActivityLoginBinding, LoginViewModel> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViewModel() {
//        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);//结果来看，两种方式都可以
        //页面的viewmodel
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        dataBinding.setVm(viewModel);
//        dataBinding.setClick(new ProxyClick());
    }

    @Override
    protected void init() {
        viewModel.getResponseMutableLiveData().observe(this, response -> {
            LogUtil.v(LogUtil.LOG + " : " + response.toString());
            if (response.code == 0 || response.code == 1) {
                ARouter.getInstance().build(ContantFields.ACTIVITY_MAIN).withString("account", dataBinding.etAccount.getText().toString()).navigation();
                if (Boolean.TRUE.equals(viewModel.getIsChecked().getValue())) {
                    MMKV.defaultMMKV().putString("username", viewModel.getAccount().getValue());
                    MMKV.defaultMMKV().putString("password", viewModel.getPwd().getValue());
                } else {
                    MMKV.defaultMMKV().remove("password");
                }
                ContantFields.username = viewModel.getAccount().getValue();
                finish();
            } else if (response.code == 2) {
                //用户未注册
                viewModel.getLoginOrRegist().setValue("注册");
            }
            Toast.makeText(LoginActivity.this, response.message, Toast.LENGTH_SHORT).show();
        });
    }

}

