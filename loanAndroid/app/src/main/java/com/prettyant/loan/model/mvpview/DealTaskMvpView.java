package com.prettyant.loan.model.mvpview;

import com.prettyant.loan.model.mvpview.base.MvpView;
import com.prettyant.loan.model.bean.Response;

/**
 * Created by chenyu on 2023-05-08
 */
public interface DealTaskMvpView extends MvpView {

    /**
     * 处理任务成功
     * @param response
     */
    void dealTaskSuccess(Response response);


    /**
     * 处理任务失败
     * @param message
     */
    void dealTaskFail(String message);
}
