package com.prettyant.action;

import com.prettyant.bean.Varis;
import com.prettyant.dao.BusinessInfoDao;
import com.prettyant.utils.LogUtils;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 流程引擎的自助服务管理类 - 档案管理&合同
 */
@Service
public class FileAndLoanManage implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) {
        //标记业务状态完成
        delegateExecution.setVariable("businessStatus", Varis.pass.getStatus());
        LogUtils.i(FileAndLoanManage.class,"档案管理 " + delegateExecution.getProcessInstanceId() + " " + delegateExecution.getCurrentActivityId());
    }
}
