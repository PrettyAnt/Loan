package com.prettyant.loan.ui.detail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prettyant.loan.R;
import com.prettyant.loan.cons.ContantFields;
import com.prettyant.loan.model.mvpview.QueryHistoryMvpView;
import com.prettyant.loan.model.bean.FlowPathModel;
import com.prettyant.loan.model.bean.FlowPathModelResponse;
import com.prettyant.loan.presenter.QueryHistoryPresenter;
import com.prettyant.loan.ui.base.BaseActivity;
import com.prettyant.loan.ui.main.adapter.BusinessDetailAdatper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户办理详情页面
 */
public class DetailActivity extends BaseActivity implements QueryHistoryMvpView, OnRefreshListener {

    private QueryHistoryPresenter queryHistoryPresenter;
    private List<FlowPathModel> flowPathModels = new ArrayList<>();
    private BusinessDetailAdatper businessDetailAdatper;
    private SmartRefreshLayout srl_refresh;
    private String processInstanceId;
    private ImageView iv_back;

    @Override
    public int getContentView() {
        return R.layout.activity_detail;
    }

    @Override
    public void initView() {
        iv_back = (ImageView) $(R.id.iv_back);
        srl_refresh = (SmartRefreshLayout) $(R.id.srl_refresh);
        RecyclerView       recyclerView = (RecyclerView) $(R.id.rv_detail);
        LinearLayoutManager   linearLayoutManager   = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        businessDetailAdatper = new BusinessDetailAdatper(this,flowPathModels);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(businessDetailAdatper);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initClick() {
        iv_back.setOnClickListener(this);
        srl_refresh.setOnRefreshListener(this);
    }

    @Override
    public void initData() {
        Intent intent            = getIntent();
        processInstanceId = intent.getStringExtra("processInstanceId");
        queryHistoryPresenter = new QueryHistoryPresenter(this);
        queryHistoryPresenter.attachView(this);
        queryHistoryPresenter.queryHistory(ContantFields.username, processInstanceId);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        queryHistoryPresenter.detachView();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void queryHistorySuccess(FlowPathModelResponse flowPathModelResponse) {
        this.flowPathModels.clear();
        this.flowPathModels.addAll(flowPathModelResponse.getFlowPathModels());
        businessDetailAdatper.notifyDataSetChanged();
    }

    @Override
    public void queryHistoryFail(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        srl_refresh.finishRefresh();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        queryHistoryPresenter.queryHistory(ContantFields.username, processInstanceId);
        srl_refresh.finishRefresh();
    }
}

