package com.prettyant.loan.presenter;

import android.content.Context;

import com.prettyant.loan.model.bean.UserInfo;
import com.prettyant.loan.model.mvpview.LoginMvpView;
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
 * Created by chenyu on 2023-05-08
 */
public class LoginPresenter extends BasePresenter<LoginMvpView> {

    Subscription mSubscription;

    public LoginPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void attachView(LoginMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void login(String username,String pwd) {

        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(pwd);
        String      bodyStr    = new Gson().toJson(userInfo);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyStr);
        checkViewAttached();
        showProgressDialog();
        mSubscription = getApiService().login(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dissmissProgressDialog();
                        getMvpView().loginFail("登录失败");
                        LogUtil.e("登录失败>>" + e.getMessage());
                    }

                    @Override
                    public void onNext(Response response) {
                        dissmissProgressDialog();
                        /**
                         * code:1  普通用户登录成功
                         * code:0  管理员用户登录成功
                         */
                        switch (response.code) {
                            case -1://error
                                getMvpView().loginFail(response.message);
                                break;
                            case 0://管理员登录成功
                                getMvpView().customerLoginSuccess(response);
                                break;
                            case 1://普通用户登录成功
                                getMvpView().userLoginSuccess(response);
                                break;
                            case 2://用户未注册
                                getMvpView().unRegist(response.message);
                                break;
                        }
                    }
                });
    }

}
