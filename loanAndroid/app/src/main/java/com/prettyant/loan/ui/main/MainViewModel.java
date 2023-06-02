package com.prettyant.loan.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.prettyant.loan.data.bean.FragmentEnum;
import com.prettyant.loan.ui.base.BaseJetViewModel;

/**
 * @author chenyu
 * My personal blog  https://prettyant.github.io
 * <p>
 * Created on 4:53 PM  23/05/23
 * PackageName : com.prettyant.loan.ui.main
 * describle : 管理ui数据
 */
public class MainViewModel extends BaseJetViewModel {
    public ObservableList<FragmentEnum> dataList;

    public MainViewModel(@NonNull Application application) {
        super(application);
        dataList = new ObservableArrayList<>();
    }

    public ObservableList<FragmentEnum> getDataList() {
        return dataList;
    }
}
