package com.prettyant.loan.ui.main.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
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
    private LinearLayout ll_empty_ui;
    private TextView tv_business_empty;

    @Override
    public int getContentView() {
        return R.layout.fragment_layout_finishedtask;
    }

    @Override
    public void initView() {
        srl_refresh = (SmartRefreshLayout) $(R.id.srl_refresh);
        RecyclerView recyclerView      = (RecyclerView) $(R.id.rv_cus_finish);
        ll_empty_ui = (LinearLayout) $(R.id.ll_empty_ui);
        tv_business_empty = (TextView) $(R.id.tv_business_empty);
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
        List<TaskModel> responseTaskModels = response.getTaskModels();
        taskModels.addAll(responseTaskModels);
        finishedTaskAdapter.notifyDataSetChanged();
        srl_refresh.finishRefresh();
        srl_refresh.finishLoadMore();
        if (taskModels.isEmpty()) {
            ll_empty_ui.setVisibility(View.VISIBLE);
        } else {
            ll_empty_ui.setVisibility(View.GONE);
        }
    }

    @Override
    public void queryFinishedTaskFail(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        srl_refresh.finishRefresh();
        srl_refresh.finishLoadMore();
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
