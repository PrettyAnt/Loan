package com.prettyant.loan.ui.main.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prettyant.loan.R;
import com.prettyant.loan.cons.ContantFields;
import com.prettyant.loan.model.bean.TaskModel;
import com.prettyant.loan.model.bean.TaskResponse;
import com.prettyant.loan.model.mvpview.QueryFinishedTaskMvpView;
import com.prettyant.loan.presenter.QueryFinishedTaskPresenter;
import com.prettyant.loan.ui.base.BaseFragment;
import com.prettyant.loan.ui.main.adapter.FinishedTaskAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作员任务列表页面
 */
public class FragmentTabCusFinishedTask extends BaseFragment implements QueryFinishedTaskMvpView, OnRefreshLoadMoreListener {

    private List<TaskModel>            taskModels = new ArrayList<>();
    private FinishedTaskAdapter        finishedTaskAdapter;
    private QueryFinishedTaskPresenter queryFinishedTaskPresenter;
    private SmartRefreshLayout         srl_refresh;
    private int                        index      = 0;
    private boolean                    isRefresh  = true;

    @Override
    public int getContentView() {
        return R.layout.fragment_layout_finishedtask;
    }

    @Override
    public void initView() {
        srl_refresh = (SmartRefreshLayout) $(R.id.srl_refresh);
        RecyclerView        recyclerView        = (RecyclerView) $(R.id.rv_cus_finish);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        finishedTaskAdapter = new FinishedTaskAdapter(getActivity(), taskModels);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(finishedTaskAdapter);
    }

    @Override
    public void initClick() {
        srl_refresh.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void initData() {
        queryFinishedTaskPresenter = new QueryFinishedTaskPresenter(getActivity());
        queryFinishedTaskPresenter.attachView(this);
        queryFinishedTaskPresenter.queryFinishedTask(ContantFields.username, index);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        queryFinishedTaskPresenter.detachView();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void queryFinishedTaskSuccess(TaskResponse response) {
        if (isRefresh) {
            taskModels.clear();
        }
        taskModels.addAll(response.getTaskModels());
        finishedTaskAdapter.notifyDataSetChanged();
        srl_refresh.finishRefresh();
        srl_refresh.finishLoadMore();
    }

    @Override
    public void queryFinishedTaskFail(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        index++;
        isRefresh = false;
        queryFinishedTaskPresenter.queryFinishedTask(ContantFields.username, index);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        index = 0;
        isRefresh = true;
        queryFinishedTaskPresenter.queryFinishedTask(ContantFields.username, index);
    }
}
