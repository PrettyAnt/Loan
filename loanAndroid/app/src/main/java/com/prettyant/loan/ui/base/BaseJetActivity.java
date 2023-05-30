package com.prettyant.loan.ui.base;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.launcher.ARouter;
import com.prettyant.loan.R;
import com.prettyant.loan.cons.ContantFields;
import com.prettyant.loan.databinding.ActivityBaseJetBinding;
import com.prettyant.loan.ui.login.LoginViewModel;


/**
 	                   _ooOoo_
 	                  o8888888o
 	                  88" . "88
 	                  (| -_- |)
 	                  O\  =  /O
 	               ____/`---'\____
 	             .'  \\|     |//  `.
 	            /  \\|||  :  |||//  \
 	           /  _||||| -:- |||||-  \
 	           |   | \\\  -  /// |   |
 	           | \_|  ''\-/''  |   |
 	           \  .-\__  `-`  ___/-. /
 	         ___`. .'  /-.-\  `. . __
 	      ."" '<  `.___\_<|>_/___.'  >'"".
 	     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 	     \  \ `-.   \_ __\ /__ _/   .-` /  /
 	======`-.____`-.___\_____/___.-`____.-'======
 	                   `=-='
 * @author chenyu
 * My personal blog  https://prettyant.github.io
 * <p>
 * Created on 3:13 PM  25/05/23
 * PackageName : com.prettyant.loan.ui.base
 * describle :
 */
public abstract class BaseJetActivity<DB extends ViewDataBinding,VM extends BaseJetViewModel>
        extends AppCompatActivity {

    public DB dataBinding;
    public VM viewModel;

    protected ActivityBaseJetBinding activityBaseJetBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //添加阿里路由跳转
        ARouter.getInstance().inject(this);

        activityBaseJetBinding = DataBindingUtil.setContentView(this, R.layout.activity_base_jet);
        dataBinding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutResId(), activityBaseJetBinding.flContent, true);
        initWaterMark();
        initViewModel();
        bindViewModel();
        dataBinding.setLifecycleOwner(this);
        init();
        if (viewModel != null) {
            getLifecycle().addObserver(viewModel);
        }
    }

    /**
     * 初始化水印
     */
    private void initWaterMark() {
        if (!TextUtils.isEmpty(ContantFields.username)) {
            activityBaseJetBinding.wmvgName.setParams(ContantFields.username);
        }
    }


    /**
     * 子页面的布局
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     * 初始化子页面的viewModel
     */
    protected abstract void initViewModel();

    /**
     * 绑定子页面的viewmodel
     */
    protected abstract void bindViewModel();

    /**
     * 初始化一些参数
     */
    protected abstract void init();

}
