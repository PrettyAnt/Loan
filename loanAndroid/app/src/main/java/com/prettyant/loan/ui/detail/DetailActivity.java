package com.prettyant.loan.ui.detail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.prettyant.loan.BR;
import com.prettyant.loan.R;
import com.prettyant.loan.cons.ContantFields;
import com.prettyant.loan.data.bean.FlowPathModel;
import com.prettyant.loan.databinding.ActivityDetailBinding;
import com.prettyant.loan.ui.base.BaseJetActivity;
import com.prettyant.loan.ui.main.adapter.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户办理详情页面
 */
@Route(path = ContantFields.ACTIVITY_DETAIL)
public class DetailActivity extends BaseJetActivity<ActivityDetailBinding,DetailViewModel>{

    private List<FlowPathModel> flowPathModels = new ArrayList<>();
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initViewModel() {
        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        dataBinding.setDetailViewModel(viewModel);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void init() {
        //设置参数
        Intent intent            = getIntent();
        String processInstanceId = intent.getStringExtra("processInstanceId");
        viewModel.setProcessInstanceId(processInstanceId);

        RecyclerView recyclerView = dataBinding.rvDetail;
        LinearLayoutManager   linearLayoutManager   = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        CommonAdapter<FlowPathModel> commonAdapter = new CommonAdapter<>(flowPathModels, R.layout.item_business_status, BR.flowPathModel, BR.detailHolder);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(commonAdapter);
        viewModel.onRefresh();
        viewModel.getFlowPathModelMutableLiveData().observe(this, flowPathModelResponse -> {
            dataBinding.srlRefresh.finishRefresh();
            if (flowPathModelResponse.code == 1) {
                flowPathModels.clear();
                flowPathModels.addAll(flowPathModelResponse.getFlowPathModels());
                commonAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this,flowPathModelResponse.message,Toast.LENGTH_SHORT).show();
            }
        });

        dataBinding.srlRefresh.setOnRefreshListener(refreshLayout -> viewModel.onRefresh());
    }
}

