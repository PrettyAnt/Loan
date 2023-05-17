package com.prettyant.loan.presenter;

import android.content.Context;

import com.prettyant.loan.model.mvpview.DealTaskMvpView;
import com.prettyant.loan.model.bean.Response;
import com.prettyant.loan.presenter.base.BasePresenter;
import com.prettyant.loan.util.LogUtil;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenyu on 2023-05-08
 */
public class DealTaskPresenter extends BasePresenter<DealTaskMvpView> {

    Subscription mSubscription;

    public DealTaskPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void attachView(DealTaskMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    /**
     * @param taskId
     * @param approved
     */
    public void dealTask(String taskId, String processInstanceId, String message, boolean approved) {
        checkViewAttached();
        showProgressDialog();
        mSubscription = getApiService().dealTask(taskId, processInstanceId, message, approved)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dissmissProgressDialog();
                        getMvpView().dealTaskFail("处理失败");
                        LogUtil.e("处理失败>>" + e.getMessage());
                    }

                    @Override
                    public void onNext(Response response) {
                        dissmissProgressDialog();
                        /**
                         * code:1  success
                         * code:-1 error
                         */
                        if (response.code == 1) {
                            getMvpView().dealTaskSuccess(response);
                        } else {
                            getMvpView().dealTaskFail(response.message);
                        }
                    }
                });
    }

}
