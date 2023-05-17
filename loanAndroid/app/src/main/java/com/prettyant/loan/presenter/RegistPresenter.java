package com.prettyant.loan.presenter;

import android.content.Context;

import com.prettyant.loan.model.bean.UserInfo;
import com.prettyant.loan.model.mvpview.RegistMvpView;
import com.prettyant.loan.model.bean.Response;
import com.prettyant.loan.presenter.base.BasePresenter;
import com.prettyant.loan.util.LogUtil;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *Created by chenyu on 2023-05-08
 */
public class RegistPresenter extends BasePresenter<RegistMvpView> {

    Subscription mSubscription;

    public RegistPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void attachView(RegistMvpView registMvpView) {
        super.attachView(registMvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void regist(String username, String pwd) {

        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(pwd);
        String      bodyStr = new Gson().toJson(userInfo);
        RequestBody body    = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyStr);
        checkViewAttached();
        showProgressDialog();
        mSubscription = getApiService().regist(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dissmissProgressDialog();
                        getMvpView().registFail("注册失败");
                        LogUtil.e("注册失败>>" + e.getMessage());
                    }

                    @Override
                    public void onNext(Response response) {
                        dissmissProgressDialog();
                        /**
                         * code:1  注册成功
                         * code:0  注册失败
                         */
                        if (response.code == 1) {
                            getMvpView().registSuccess(response);
                        } else {
                            getMvpView().registFail(response.message);
                        }
                    }
                });
    }

}
