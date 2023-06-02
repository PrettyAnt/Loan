package com.prettyant.loan.ui.main.fragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.prettyant.loan.data.repository.network.HttpRequestManager;
import com.prettyant.loan.data.bean.TaskResponse;
import com.prettyant.loan.ui.base.BaseJetViewModel;

/**
 * @author chenyu
 * My personal blog  https://prettyant.github.io
 * Created on 3:26 PM  1/06/23
 * PackageName : com.prettyant.loan.ui.main.fragment
 * describle :
 */
public class TabCusFinishedViewModel extends BaseJetViewModel {
    public int state = 1;//1：刷新标识   2 加载标识
    private int index = 0;//当前索引
    private MutableLiveData<TaskResponse> taskResponseMutableLiveData;

    public TabCusFinishedViewModel(@NonNull Application application) {
        super(application);
        taskResponseMutableLiveData = new MutableLiveData<>();
    }


    public MutableLiveData<TaskResponse> getTaskResponseMutableLiveData() {
        return taskResponseMutableLiveData;
    }

    /**
     * 加载更多
     */
    public void onLoadMore() {
        index++;
        state = 2;
        loadData();
    }

    /**
     * 下拉刷新
     */
    public void onRefresh() {
        index = 0;
        state = 1;
        loadData();
    }

    private void loadData() {
        HttpRequestManager.getInstance().queryFinishedTask(taskResponseMutableLiveData, index);
    }
}
