package com.prettyant.loan.model.mvpview;

import com.prettyant.loan.model.mvpview.base.MvpView;
import com.prettyant.loan.model.bean.BusinessInfosResponse;

/**
 * Created by chenyu on 2023-05-08
 */
public interface UserQueryMvpView extends MvpView {

    /**
     * 查询成功
     *
     * @param businessInfosResponse
     */
    void applySuccess(BusinessInfosResponse businessInfosResponse);


    /**
     * 查询失败
     *
     * @param message
     */
    void applyFail(String message);
}
