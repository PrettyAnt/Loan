package com.prettyant.loan.ui.main.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.prettyant.loan.R;
import com.prettyant.loan.cons.ContantFields;
import com.prettyant.loan.model.mvpview.UserQueryMvpView;
import com.prettyant.loan.model.bean.BusinessInfo;
import com.prettyant.loan.model.bean.BusinessInfosResponse;
import com.prettyant.loan.presenter.UserQueryPresenter;
import com.prettyant.loan.ui.base.BaseFragment;
import com.prettyant.loan.ui.detail.DetailActivity;
import com.prettyant.loan.ui.main.adapter.BusinessQueryAdapter;
import com.prettyant.loan.util.LogUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;


public class FragmentTabBusinessQuery extends BaseFragment implements UserQueryMvpView, BusinessQueryAdapter.ItemClickListener, OnRefreshLoadMoreListener {

    private RecyclerView       recyclerView;
    private UserQueryPresenter userQueryPresenter;
    List<BusinessInfo> businessInfos = new ArrayList<>();
    private BusinessQueryAdapter businessQueryAdapter;
    private int                  index = 0;//当前索引
    private SmartRefreshLayout   srl_refresh;

    private int state = 1;//1：刷新标识   2 加载标识
    private LinearLayout ll_empty_ui;
    private TextView tv_business_empty;

    @Override
    public int getContentView() {
        return R.layout.fragment_business_query;
    }

    @Override
    public void initView() {
        recyclerView = (RecyclerView) $(R.id.rv_business_query);
        srl_refresh = (SmartRefreshLayout) $(R.id.srl_refresh);
        ll_empty_ui = (LinearLayout) $(R.id.ll_empty_ui);
        tv_business_empty = (TextView) $(R.id.tv_business_empty);
        srl_refresh.setOnRefreshLoadMoreListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        businessQueryAdapter = new BusinessQueryAdapter(getActivity(), businessInfos);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(businessQueryAdapter);
        businessQueryAdapter.setItemClickListener(this);
    }

    @Override
    public void initClick() {

    }

    @Override
    public void initData() {
        userQueryPresenter = new UserQueryPresenter(getActivity());
        userQueryPresenter.attachView(this);
        userQueryPresenter.query(ContantFields.username, index);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        userQueryPresenter.detachView();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void applySuccess(BusinessInfosResponse businessInfosResponse) {
        if (state == 1) {
            businessInfos.clear();
        }
        businessInfos.addAll(businessInfosResponse.list);
        businessQueryAdapter.notifyDataSetChanged();
//        srl_refresh.finishRefreshWithNoMoreData();
        srl_refresh.finishRefresh();
        srl_refresh.finishLoadMore();
        if (businessInfos.isEmpty()) {
            ll_empty_ui.setVisibility(View.VISIBLE);
        } else {
            ll_empty_ui.setVisibility(View.GONE);
        }

    }

    @Override
    public void applyFail(String message) {
        srl_refresh.finishRefresh();
        srl_refresh.finishLoadMore();
    }

    @Override
    public void onItemClickListener(View view, int position) {
        if (view.getId() == R.id.rl_business_item) {
            LogUtil.i("跳转到详情页面--->>>" + businessInfos.get(position));
            String processInstanceId = businessInfos.get(position).getProcessInstanceId();
            ARouter.getInstance().build(ContantFields.ACTIVITY_DETAIL).withString("processInstanceId",processInstanceId).navigation();
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        state = 2;
        index++;
        userQueryPresenter.query(ContantFields.username, index);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        state = 1;
        index = 0;
        userQueryPresenter.query(ContantFields.username, index);
    }
}
