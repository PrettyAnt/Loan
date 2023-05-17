package com.prettyant.loan.model.mvpview;

import com.prettyant.loan.model.bean.TaskResponse;
import com.prettyant.loan.model.mvpview.base.MvpView;

/**
 * Created by chenyu on 202305
 */
public interface QueryFinishedTaskMvpView extends MvpView {

    /**
     * 查询成功
     *
     * @param response
     */
    void queryFinishedTaskSuccess(TaskResponse response);


    /**
     * 查询失败
     *
     * @param message
     */
    void queryFinishedTaskFail(String message);
}
