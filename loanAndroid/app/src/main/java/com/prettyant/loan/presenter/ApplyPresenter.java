package com.prettyant.loan.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.prettyant.loan.model.mvpview.ApplyMvpView;
import com.prettyant.loan.model.bean.BusinessInfo;
import com.prettyant.loan.model.bean.Response;
import com.prettyant.loan.presenter.base.BasePresenter;
import com.prettyant.loan.util.LogUtil;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenyu on 2023-05-08
 */
public class ApplyPresenter extends BasePresenter<ApplyMvpView> {

    Subscription mSubscription;

    public ApplyPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void attachView(ApplyMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    /**
     * 提交
     *
     * @param businessInfo
     */
    public void apply(BusinessInfo businessInfo) {
        String      bodyStr = new Gson().toJson(businessInfo);
        RequestBody body    = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyStr);
        checkViewAttached();
        showProgressDialog();
        mSubscription = getApiService().apply(businessInfo.getUsername(), body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dissmissProgressDialog();
                        getMvpView().applyFail("提交失败");
                        LogUtil.e("提交失败>>" + e.getMessage());
                    }

                    @Override
                    public void onNext(Response response) {
                        dissmissProgressDialog();
                        /**
                         * code:1  success
                         * code:-1 error
                         */
                        if (response.code == 1) {
                            getMvpView().applySuccess(response);
                        } else {
                            getMvpView().applyFail(response.message);
                        }
                    }
                });
    }

}
