package com.prettyant.loan.ui.detail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.prettyant.loan.data.bean.FlowPathModel;
import com.prettyant.loan.data.bean.FlowPathModelResponse;
import com.prettyant.loan.data.repository.network.HttpRequestManager;
import com.prettyant.loan.ui.base.BaseJetViewModel;

/**
 * @author chenyu
 * My personal blog  https://prettyant.github.io
 * Created on 9:10 AM  2/06/23
 * PackageName : com.prettyant.loan.ui.detail
 * describle :
 */
public class DetailViewModel extends BaseJetViewModel {
    private MutableLiveData<FlowPathModelResponse> flowPathModelMutableLiveData;
    private String processInstanceId;

    public DetailViewModel(@NonNull Application application) {
        super(application);
        flowPathModelMutableLiveData = new MutableLiveData<>();
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public MutableLiveData<FlowPathModelResponse> getFlowPathModelMutableLiveData() {
        return flowPathModelMutableLiveData;
    }

    public void onRefresh() {
        HttpRequestManager.getInstance().queryHistory(flowPathModelMutableLiveData,processInstanceId);
    }
}
