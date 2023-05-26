package com.prettyant.loan.ui.base;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.DefaultLifecycleObserver;

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
    public BaseJetViewModel(@NonNull Application application) {
        super(application);
        this.context = application;
    }
}
