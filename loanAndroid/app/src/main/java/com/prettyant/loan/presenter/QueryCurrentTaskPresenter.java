package com.prettyant.loan.presenter;

import android.content.Context;

import com.prettyant.loan.model.mvpview.QueryCurrentTaskMvpView;
import com.prettyant.loan.model.bean.TaskResponse;
import com.prettyant.loan.presenter.base.BasePresenter;
import com.prettyant.loan.util.LogUtil;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenyu on 2023-05-08
 */
public class QueryCurrentTaskPresenter extends BasePresenter<QueryCurrentTaskMvpView> {

    Subscription mSubscription;

    public QueryCurrentTaskPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void attachView(QueryCurrentTaskMvpView mvpView) {
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
     * @param index
     */
    public void queryCurrentTask(String authorization,int index) {
        checkViewAttached();
        mSubscription = getApiService().queryCurrentTask(authorization,index)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<TaskResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().queryCurrentTaskFail("查询失败");
                        LogUtil.e("查询成功>>" + e.getMessage());
                    }

                    @Override
                    public void onNext(TaskResponse response) {
                        /**
                         * code:1  success
                         * code:-1 error
                         */
                        if (response.code == 1) {
                            getMvpView().queryCurrentTaskSuccess(response);
                        } else {
                            getMvpView().queryCurrentTaskFail(response.message);
                        }
                    }
                });
    }

}
