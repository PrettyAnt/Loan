package com.prettyant.loan.ui.base;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.prettyant.loan.cons.ContantFields;

/**
 * @author chenyu
 * My personal blog  https://prettyant.github.io
 * <p>
 * Created on 3:33 PM  25/05/23
 * PackageName : com.prettyant.loan.ui.base
 * describle :
 */
public abstract class BaseJetViewModel extends AndroidViewModel implements DefaultLifecycleObserver {
    protected Context context;
    private MutableLiveData<String> waterMark;

    public BaseJetViewModel(@NonNull Application application) {
        super(application);
        this.context = application;
    }

    //fixme  基类里引用该字段报错??
    public MutableLiveData<String> getWaterMark() {
        if (waterMark == null) {
            waterMark = new MutableLiveData<>();
            if (!TextUtils.isEmpty(ContantFields.username)) {
                waterMark.setValue(ContantFields.username);
            }
        }
        return waterMark;
    }
}
