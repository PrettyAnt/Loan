package com.prettyant.loan.presenter;

import android.content.Context;

import com.prettyant.loan.model.mvpview.LogOutMvpView;
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
public class LogOutPresenter extends BasePresenter<LogOutMvpView> {

    Subscription mSubscription;

    public LogOutPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void attachView(LogOutMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    /**
     * 登出
     * @param authorization
     */
    public void logOut(String authorization) {
        checkViewAttached();
        showProgressDialog();
        mSubscription = getApiService().logOut(authorization)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dissmissProgressDialog();
                        getMvpView().logOutFail("登出失败");
                        LogUtil.e("登出失败>>" + e.getMessage());
                    }

                    @Override
                    public void onNext(Response response) {
                        dissmissProgressDialog();
                        /**
                         * code:1  success
                         * code:-1 error
                         */
                        if (response.code == 1) {
                            getMvpView().logOutSuccess(response);
                        } else {
                            getMvpView().logOutFail(response.message);
                        }
                    }
                });
    }

}
