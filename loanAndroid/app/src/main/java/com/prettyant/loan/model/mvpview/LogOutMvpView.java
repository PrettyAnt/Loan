package com.prettyant.loan.model.mvpview;

import com.prettyant.loan.model.mvpview.base.MvpView;
import com.prettyant.loan.model.bean.Response;

/**
 * Created by chenyu on 2023-05-08
 */
public interface LogOutMvpView extends MvpView {

    /**
     * 普通用户登出成功
     * @param response
     */
    void logOutSuccess(Response response);


    /**
     * 登出失败
     * @param message
     */
    void logOutFail(String message);
}
