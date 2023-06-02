package com.prettyant.loan.ui.main.fragment;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.prettyant.loan.BR;
import com.prettyant.loan.R;
import com.prettyant.loan.cons.ContantFields;
import com.prettyant.loan.data.bean.BusinessInfo;
import com.prettyant.loan.databinding.FragmentBusinessQueryBinding;
import com.prettyant.loan.ui.base.BaseJetFragment;
import com.prettyant.loan.ui.main.adapter.CommonAdapter;

import java.util.ArrayList;
import java.util.List;


public class FragmentTabBusinessQuery extends BaseJetFragment<FragmentBusinessQueryBinding, TabBusinessQueryViewModel> {

    private List<BusinessInfo> businessInfos = new ArrayList<>();

    @Override
    protected String getTitle() {
        return "业务查询";
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_business_query;
    }

    @Override
    protected void initViewModel() {
        viewModel = new ViewModelProvider(this).get(TabBusinessQueryViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        dataBinding.setVm(viewModel);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void init() {
        viewModel.onRefresh();
        //刷新组件
        dataBinding.srlRefresh.setOnRefreshListener(refreshLayout -> viewModel.onRefresh());
        dataBinding.srlRefresh.setOnLoadMoreListener(refreshLayout -> viewModel.onLoadMore());

        //新recyclerView方案
        CommonAdapter<BusinessInfo> businessInfoCommonAdapter = new CommonAdapter<>(businessInfos, R.layout.list_business_info, BR.businessInfo,BR.holder);
        dataBinding.rvBusinessQuery.setAdapter(businessInfoCommonAdapter);
        dataBinding.rvBusinessQuery.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        businessInfoCommonAdapter.setItemClickListener((view, position) -> {
            String processInstanceId = businessInfos.get(position).getProcessInstanceId();
            ARouter.getInstance().build(ContantFields.ACTIVITY_DETAIL).withString("processInstanceId",processInstanceId).navigation();
        });

        //观察返回的数据
        viewModel.getBusinessInfosResponseMutableLiveData().observe(this, businessInfosResponse -> {
            if (viewModel.state == 1) {
                businessInfos.clear();
                businessInfos.addAll(businessInfosResponse.getList());
            } else {
                businessInfos.addAll(businessInfosResponse.getList());
            }
            businessInfoCommonAdapter.notifyDataSetChanged();
            dataBinding.srlRefresh.finishRefresh();
            dataBinding.srlRefresh.finishLoadMore();
            if (businessInfos.isEmpty()) {
                dataBinding.llEmptyUi.setVisibility(View.VISIBLE);
            } else {
                dataBinding.llEmptyUi.setVisibility(View.GONE);
            }
        });
    }
}
