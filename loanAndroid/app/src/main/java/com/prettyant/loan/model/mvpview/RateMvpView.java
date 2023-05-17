package com.prettyant.loan.model.mvpview;

import com.prettyant.loan.model.mvpview.base.MvpView;
import com.prettyant.loan.model.bean.BusinessInfo;

/**
 * Created by chenyu on 2023-05-08
 */
public interface RateMvpView extends MvpView {

    /**
     * 查询利率成功
     *
     * @param response
     */
    void getRateSuccess(BusinessInfo businessInfo);


    /**
     * 查询利率失败
     *
     * @param message
     */
    void getRateFail(String message);
}
