package com.prettyant.loan.presenter;

import android.content.Context;

import com.prettyant.loan.model.mvpview.QueryHistoryMvpView;
import com.prettyant.loan.model.bean.FlowPathModelResponse;
import com.prettyant.loan.presenter.base.BasePresenter;
import com.prettyant.loan.util.LogUtil;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenyu on 2023-05-08
 */
public class QueryHistoryPresenter extends BasePresenter<QueryHistoryMvpView> {

    Subscription mSubscription;

    public QueryHistoryPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void attachView(QueryHistoryMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    /**
     * 查询历史
     * @param authorization
     * @param processInstanceId
     */
    public void queryHistory(String authorization,String processInstanceId) {
        checkViewAttached();
//        showProgressDialog();
        mSubscription = getApiService().queryHistory(authorization,processInstanceId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<FlowPathModelResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        dissmissProgressDialog();
                        getMvpView().queryHistoryFail("查询失败");
                        LogUtil.e("查询成功>>" + e.getMessage());
                    }

                    @Override
                    public void onNext(FlowPathModelResponse response) {
//                        dissmissProgressDialog();
                        /**
                         * code:1  success
                         * code:-1 error
                         */
                        if (response.code == 1) {
                            getMvpView().queryHistorySuccess(response);
                        } else {
                            getMvpView().queryHistoryFail(response.message);
                        }
                    }
                });
    }

}
