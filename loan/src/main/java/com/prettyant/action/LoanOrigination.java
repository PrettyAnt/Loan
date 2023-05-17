package com.prettyant.action;

import com.prettyant.utils.LogUtils;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * 流程引擎的自助服务管理类 - 贷款发放
 */
public class LoanOrigination implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        LogUtils.i(LoanOrigination.class, "贷款发放 " + delegateExecution.getProcessDefinitionId() + " " + delegateExecution.getCurrentActivityId());
    }
}
