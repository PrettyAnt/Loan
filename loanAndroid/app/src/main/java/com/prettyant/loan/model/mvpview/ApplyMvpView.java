package com.prettyant.loan.model.mvpview;

import com.prettyant.loan.model.mvpview.base.MvpView;
import com.prettyant.loan.model.bean.Response;

/**
 * Created by chenyu on 2016-7-14.
 */
public interface ApplyMvpView extends MvpView {

    /**
     * 提交成功
     *
     * @param response
     */
    void applySuccess(Response response);


    /**
     * 提交失败
     *
     * @param message
     */
    void applyFail(String message);
}
