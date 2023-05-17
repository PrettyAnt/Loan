package com.prettyant.loan.model.mvpview;

import com.prettyant.loan.model.mvpview.base.MvpView;
import com.prettyant.loan.model.bean.TaskResponse;

/**
 * Created by chenyu on 2016-7-14.
 */
public interface QueryCurrentTaskMvpView extends MvpView {

    /**
     * 查询成功
     *
     * @param response
     */
    void queryCurrentTaskSuccess(TaskResponse response);


    /**
     * 查询失败
     *
     * @param message
     */
    void queryCurrentTaskFail(String message);
}
