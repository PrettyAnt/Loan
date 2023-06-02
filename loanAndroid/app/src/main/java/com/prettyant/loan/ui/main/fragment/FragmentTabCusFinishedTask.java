package com.prettyant.loan.ui.main.fragment;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prettyant.loan.BR;
import com.prettyant.loan.R;
import com.prettyant.loan.data.bean.TaskModel;
import com.prettyant.loan.databinding.FragmentLayoutFinishedtaskBinding;
import com.prettyant.loan.ui.base.BaseJetFragment;
import com.prettyant.loan.ui.main.adapter.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作员任务列表页面
 */
public class FragmentTabCusFinishedTask extends BaseJetFragment<FragmentLayoutFinishedtaskBinding, TabCusFinishedViewModel> {
    private List<TaskModel> taskInfos = new ArrayList<>();

    @Override
    protected String getTitle() {
        return "已完成";
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_layout_finishedtask;
    }

    @Override
    protected void initViewModel() {
        viewModel = new ViewModelProvider(this).get(TabCusFinishedViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        dataBinding.setCusFinishViewModel(viewModel);
    }

    @Override
    protected void init() {
        viewModel.onRefresh();
        //刷新组件
        dataBinding.srlRefresh.setOnRefreshListener(refreshLayout -> viewModel.onRefresh());
        dataBinding.srlRefresh.setOnLoadMoreListener(refreshLayout -> viewModel.onLoadMore());
        //新recyclerView方案
        CommonAdapter<TaskModel> commonAdapter = new CommonAdapter<>(taskInfos, R.layout.list_finished_task, BR.taskInfo, BR.holder);
        dataBinding.rvCusFinish.setAdapter(commonAdapter);
        dataBinding.rvCusFinish.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        //观察返回的数据
        viewModel.getTaskResponseMutableLiveData().observe(this, taskResponse -> {
            if (viewModel.state == 1) {
                taskInfos.clear();
                taskInfos.addAll(taskResponse.getTaskModels());
            } else {
                taskInfos.addAll(taskResponse.getTaskModels());
            }
            commonAdapter.notifyDataSetChanged();
            dataBinding.srlRefresh.finishRefresh();
            dataBinding.srlRefresh.finishLoadMore();
            if (taskInfos.isEmpty()) {
                dataBinding.llEmptyUi.setVisibility(View.VISIBLE);
            } else {
                dataBinding.llEmptyUi.setVisibility(View.GONE);
            }
        });
    }

}
