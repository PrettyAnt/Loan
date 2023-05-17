package com.prettyant.loan.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.prettyant.loan.model.mvpview.RateMvpView;
import com.prettyant.loan.model.bean.BusinessInfo;
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
public class RatePresenter extends BasePresenter<RateMvpView> {

    Subscription mSubscription;

    public RatePresenter(Context context) {
        this.context = context;
    }

    @Override
    public void attachView(RateMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    /**
     * 获取利率
     *
     * @param businessInfo
     */
    public void getRate(BusinessInfo businessInfo) {
        String      bodyStr = new Gson().toJson(businessInfo);
        RequestBody body    = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyStr);
        checkViewAttached();
        showProgressDialog();
        mSubscription = getApiService().getRate(businessInfo.getUsername(), body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<BusinessInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dissmissProgressDialog();
                        getMvpView().getRateFail("获取失败");
                        LogUtil.e("获取失败>>" + e.getMessage());
                    }

                    @Override
                    public void onNext(BusinessInfo response) {
                        dissmissProgressDialog();
                        /**
                         * code:1  success
                         * code:-1 error
                         */
                        if (response.code == 1) {
                            getMvpView().getRateSuccess(response);
                        } else {
                            getMvpView().getRateFail(response.message);
                        }
                    }
                });
    }

}
