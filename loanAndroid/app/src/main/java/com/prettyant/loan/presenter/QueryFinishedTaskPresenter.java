package com.prettyant.loan.presenter;

import android.content.Context;

import com.prettyant.loan.model.bean.TaskResponse;
import com.prettyant.loan.model.mvpview.QueryCurrentTaskMvpView;
import com.prettyant.loan.model.mvpview.QueryFinishedTaskMvpView;
import com.prettyant.loan.presenter.base.BasePresenter;
import com.prettyant.loan.util.LogUtil;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenyu on 2023-05-08
 */
public class QueryFinishedTaskPresenter extends BasePresenter<QueryFinishedTaskMvpView> {

    Subscription mSubscription;

    public QueryFinishedTaskPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void attachView(QueryFinishedTaskMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    /**
     * 查询当前任务
     * @param authorization
     * @param index  索引
     */
    public void queryFinishedTask(String authorization,int index) {
        checkViewAttached();
        mSubscription = getApiService().queryFinishedTask(authorization,index)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<TaskResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().queryFinishedTaskFail("查询失败");
                        LogUtil.e("查询失败>>" + e.getMessage());
                    }

                    @Override
                    public void onNext(TaskResponse response) {
                        /**
                         * code:1  success
                         * code:-1 error
                         */
                        if (response.code == 1) {
                            getMvpView().queryFinishedTaskSuccess(response);
                        } else {
                            getMvpView().queryFinishedTaskFail(response.message);
                        }
                    }
                });
    }

}
