package com.prettyant.loan.model.mvpview;

import com.prettyant.loan.model.mvpview.base.MvpView;
import com.prettyant.loan.model.bean.Response;

/**
 * Created by chenyu on 2023-05-08
 */
public interface LoginMvpView extends MvpView {

    /**
     * 普通用户登录成功
     * @param response
     */
    void userLoginSuccess(Response response);

    /**
     * 操作员登录成功
     * @param response
     */
    void customerLoginSuccess(Response response);

    /**
     * 未注册
     *  @param message
     */
    void unRegist(String message);

    /**
     * 登录失败
     * @param message
     */
    void loginFail(String message);
}
