package com.prettyant.loan.model.mvpview;

import com.prettyant.loan.model.mvpview.base.MvpView;
import com.prettyant.loan.model.bean.Response;

/**
 * Created by chenyu on 2023-05-08
 */
public interface RegistMvpView extends MvpView {

    /**
     * 注册成功
     * @param response
     */
    void registSuccess(Response response);

    /**
     * 注册失败
     * @param message
     */
    void registFail(String message);
}
