package com.prettyant.loan.presenter;

import android.content.Context;

import com.prettyant.loan.model.mvpview.UserQueryMvpView;
import com.prettyant.loan.model.bean.BusinessInfosResponse;
import com.prettyant.loan.presenter.base.BasePresenter;
import com.prettyant.loan.util.LogUtil;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenyu on 2023-05-08
 */
public class UserQueryPresenter extends BasePresenter<UserQueryMvpView> {

    Subscription mSubscription;

    public UserQueryPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void attachView(UserQueryMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    /**
     * 用户业务查询接口
     *
     * @param authorization
     */
    public void query(String authorization,int index) {
        checkViewAttached();
//        showProgressDialog();
        mSubscription = getApiService().userQuery(authorization,index)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<BusinessInfosResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        dissmissProgressDialog();
                        getMvpView().applyFail("查询失败");
                        LogUtil.e("查询失败>>" + e.getMessage());
                    }

                    @Override
                    public void onNext(BusinessInfosResponse response) {
//                        dissmissProgressDialog();
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
