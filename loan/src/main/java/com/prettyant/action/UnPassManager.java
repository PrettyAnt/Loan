package com.prettyant.action;

import com.prettyant.bean.Varis;
import com.prettyant.utils.LogUtils;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

/**
 * 流程引擎的自助服务管理类 - 档案管理&合同
 */
@Service
public class UnPassManager implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) {
        //标记业务状态完成
        /**
         * businessStatus 0   审核中
         * businessStatus 1   已完成
         * businessStatus 2   已驳回
         */
        delegateExecution.setVariable("businessStatus", Varis.refuse.getStatus());
        LogUtils.i(UnPassManager.class,"已驳回 ------>>>>>>" + delegateExecution.getProcessInstanceId() + " " + delegateExecution.getCurrentActivityId());
    }
}
