package com.prettyant.loan.ui.main.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prettyant.loan.R;
import com.prettyant.loan.cons.ContantFields;
import com.prettyant.loan.model.mvpview.DealTaskMvpView;
import com.prettyant.loan.model.mvpview.QueryCurrentTaskMvpView;
import com.prettyant.loan.model.bean.Response;
import com.prettyant.loan.model.bean.TaskModel;
import com.prettyant.loan.model.bean.TaskResponse;
import com.prettyant.loan.presenter.DealTaskPresenter;
import com.prettyant.loan.presenter.QueryCurrentTaskPresenter;
import com.prettyant.loan.ui.base.BaseFragment;
import com.prettyant.loan.ui.main.adapter.CurrentTaskAdapter;
import com.prettyant.loan.view.pop.CusApprovePopWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作员任务列表页面
 */
public class FragmentTabCusCurrentTask extends BaseFragment implements QueryCurrentTaskMvpView, CurrentTaskAdapter.ItemClickListener, CusApprovePopWindow.SubmitClickListener, DealTaskMvpView, OnRefreshLoadMoreListener {

    private QueryCurrentTaskPresenter queryCurrentTaskPresenter;
    private List<TaskModel>           taskModels = new ArrayList<>();
    private CurrentTaskAdapter        currentTaskAdapter;
    private RelativeLayout            rl_title;
    private DealTaskPresenter         dealTaskPresenter;
    private TaskModel                 taskModel;
    private SmartRefreshLayout        srl_refresh;
    private int                       index      = 0;
    private boolean                   isRefresh  = true;

    @Override
    public int getContentView() {
        return R.layout.fragment_layout_currenttask;
    }

    @Override
    public void initView() {
        rl_title = (RelativeLayout) $(R.id.rl_title);
        srl_refresh = (SmartRefreshLayout) $(R.id.srl_refresh);
        RecyclerView        rv_cus_deal         = (RecyclerView) $(R.id.rv_cus_deal);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        currentTaskAdapter = new CurrentTaskAdapter(getActivity(), taskModels);
        rv_cus_deal.setLayoutManager(linearLayoutManager);
        rv_cus_deal.setAdapter(currentTaskAdapter);

    }

    @Override
    public void initClick() {
        currentTaskAdapter.setItemClickListener(this);
        srl_refresh.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void initData() {
        queryCurrentTaskPresenter = new QueryCurrentTaskPresenter(getActivity());
        queryCurrentTaskPresenter.attachView(this);
        queryCurrentTaskPresenter.queryCurrentTask(ContantFields.username, index);

        dealTaskPresenter = new DealTaskPresenter(getActivity());
        dealTaskPresenter.attachView(this);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        queryCurrentTaskPresenter.detachView();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void queryCurrentTaskSuccess(TaskResponse response) {
        if (isRefresh) {
            taskModels.clear();
        }
        taskModels.addAll(response.getTaskModels());
        currentTaskAdapter.notifyDataSetChanged();
        srl_refresh.finishRefresh();
        srl_refresh.finishLoadMore();
    }

    @Override
    public void queryCurrentTaskFail(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClickListener(View view, int position) {
        if (view.getId() == R.id.ll_dealtask_item) {
            //跳转到处理页面
            taskModel = taskModels.get(position);
            System.out.println("taskModel.toString() = " + taskModel.toString());
            CusApprovePopWindow.getInstance().approvePop(rl_title, getActivity(), this);
        }
    }

    @Override
    public void onSubmitClickListener(String message, boolean approve) {
        String taskId            = taskModel.getTaskId();
        String processInstanceId = taskModel.getProcessInstanceId();
        dealTaskPresenter.dealTask(taskId, processInstanceId, message, approve);
    }

    @Override
    public void dealTaskSuccess(Response response) {
        //刷新列表
        index = 0;
        isRefresh = true;
        queryCurrentTaskPresenter.queryCurrentTask(ContantFields.username, index);
    }

    @Override
    public void dealTaskFail(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        index++;
        isRefresh = false;
        queryCurrentTaskPresenter.queryCurrentTask(ContantFields.username, index);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        index = 0;
        isRefresh = true;
        queryCurrentTaskPresenter.queryCurrentTask(ContantFields.username, index);
    }
}
