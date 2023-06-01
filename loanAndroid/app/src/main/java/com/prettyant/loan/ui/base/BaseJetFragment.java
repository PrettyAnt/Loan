package com.prettyant.loan.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.prettyant.loan.R;
import com.prettyant.loan.databinding.FragmentJetBaseBinding;

/**
 * @author chenyu
 * My personal blog  https://prettyant.github.io
 * Created on 8:58 AM  30/05/23
 * PackageName : com.prettyant.loan.ui.base
 * describle :
 */
public abstract class BaseJetFragment<DB extends ViewDataBinding, VM extends BaseJetViewModel> extends Fragment {
    protected DB dataBinding;
    protected VM viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            handleArguments(args);
        }
        initViewModel();
        //订阅生命周期事件
        if (viewModel != null) {
            getLifecycle().addObserver(viewModel);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentJetBaseBinding fragmentJetBaseBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_jet_base, container, false);
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutResId(), fragmentJetBaseBinding.flContentContainer, true);
        bindViewModel();
        dataBinding.setLifecycleOwner(this);
        init();
        return fragmentJetBaseBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (viewModel != null) {
            getLifecycle().removeObserver(viewModel);
        }
    }

    /**
     * 处理参数
     *
     * @param args
     */
    protected void handleArguments(Bundle args) {

    }

    /**
     * 获取当前页面的布局id
     *
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     * 初始化viewModel
     */
    protected abstract void initViewModel();

    /**
     * 绑定viewModel
     */
    protected abstract void bindViewModel();

    /**
     * 参数初始化
     */
    protected abstract void init();
}
