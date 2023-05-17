package com.prettyant.action;

import com.prettyant.utils.LogUtils;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * 流程引擎的自助服务管理类 - -------审核通过---签订合同
 */
public class SignContract implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {
        LogUtils.f(SignContract.class,"-------审核通过---签订合同 " + delegateExecution.getProcessDefinitionId() + " " + delegateExecution.getCurrentActivityId());
    }
}
