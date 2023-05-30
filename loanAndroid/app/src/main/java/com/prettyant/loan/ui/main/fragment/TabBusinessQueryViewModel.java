package com.prettyant.loan.ui.main.fragment;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.prettyant.loan.data.repository.network.HttpRequestManager;
import com.prettyant.loan.model.bean.BusinessInfo;
import com.prettyant.loan.model.bean.BusinessInfosResponse;
import com.prettyant.loan.ui.base.BaseJetViewModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenyu
 * My personal blog  https://prettyant.github.io
 * Created on 2:47 PM  30/05/23
 * PackageName : com.prettyant.loan.ui.main.fragment
 * describle :
 */
public class TabBusinessQueryViewModel extends BaseJetViewModel {
    private MutableLiveData<BusinessInfosResponse> businessInfosResponseMutableLiveData;
    public int state = 1;//1：刷新标识   2 加载标识
    private int index = 0;//当前索引

    public TabBusinessQueryViewModel(@NonNull Application application) {
        super(application);
        businessInfosResponseMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<BusinessInfosResponse> getBusinessInfosResponseMutableLiveData() {
        return businessInfosResponseMutableLiveData;
    }

    public void onLoadMore() {
        index++;
        state = 2;
        loadData();
    }

    public void onRefresh() {
        index = 0;
        state = 1;
        loadData();
    }

    /**
     * 获取数据
     */
    private void loadData() {
        HttpRequestManager.getInstance().query(businessInfosResponseMutableLiveData, index);
    }
}
