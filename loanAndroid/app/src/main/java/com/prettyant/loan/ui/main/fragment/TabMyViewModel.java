package com.prettyant.loan.ui.main.fragment;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.prettyant.loan.cons.ContantFields;
import com.prettyant.loan.data.bean.Response;
import com.prettyant.loan.data.repository.network.HttpRequestManager;
import com.prettyant.loan.ui.base.BaseJetViewModel;

/**
 * @author chenyu
 * My personal blog  https://prettyant.github.io
 * Created on 11:23 AM  2/06/23
 * PackageName : com.prettyant.loan.ui.main.fragment
 * describle :
 */
public class TabMyViewModel extends BaseJetViewModel {
    private MutableLiveData<String> username;
    private MutableLiveData<Response> responseMutableLiveData;

    public TabMyViewModel(@NonNull Application application) {
        super(application);
        username = new MutableLiveData<>();
        responseMutableLiveData = new MutableLiveData<>();
        username.setValue(ContantFields.username);
        title.setValue("测试一下");
    }

    public MutableLiveData<String> getUsername() {
        return username;
    }

    public MutableLiveData<Response> getResponseMutableLiveData() {
        return responseMutableLiveData;
    }

    public void onClick(View view) {
        HttpRequestManager.getInstance().logOut(responseMutableLiveData);
    }
}
