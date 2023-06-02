package com.prettyant.loan.ui.main.fragment;

import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.launcher.ARouter;
import com.prettyant.loan.R;
import com.prettyant.loan.cons.ContantFields;
import com.prettyant.loan.databinding.FragmentMyBinding;
import com.prettyant.loan.ui.base.BaseJetFragment;


/**
 */
public class FragmentTabMy extends BaseJetFragment<FragmentMyBinding,TabMyViewModel> {
    @Override
    protected String getTitle() {
        return "我的";
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initViewModel() {
        viewModel = new ViewModelProvider(this).get(TabMyViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        dataBinding.setMyViewModel(viewModel);
    }

    @Override
    protected void init() {
        viewModel.getResponseMutableLiveData().observe(this, response -> {
            Toast.makeText(getActivity(), response.message, Toast.LENGTH_SHORT).show();
            getActivity().finish();
            ContantFields.username = "";
            ARouter.getInstance().build(ContantFields.ACTIVITY_LOGIN).navigation();
        });
    }
}
