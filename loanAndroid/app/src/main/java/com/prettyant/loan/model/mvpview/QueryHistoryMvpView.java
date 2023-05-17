package com.prettyant.loan.model.mvpview;

import com.prettyant.loan.model.mvpview.base.MvpView;
import com.prettyant.loan.model.bean.FlowPathModelResponse;

/**
 * Created by chenyu on 2023-05-08
 */
public interface QueryHistoryMvpView extends MvpView {

    /**
     * 查询成功
     *
     * @param flowPathModelResponse
     */
    void queryHistorySuccess(FlowPathModelResponse flowPathModelResponse);


    /**
     * 查询失败
     *
     * @param message
     */
    void queryHistoryFail(String message);
}
