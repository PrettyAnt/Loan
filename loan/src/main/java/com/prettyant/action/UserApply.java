package com.prettyant.action;

import com.prettyant.utils.LogUtils;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * 流程引擎的自助服务管理类 - 用户申请
 */
public class UserApply implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        LogUtils.i(UserApply.class, "用户申请  " + delegateExecution.getProcessDefinitionId() + " " + delegateExecution.getCurrentActivityId());
    }
}
