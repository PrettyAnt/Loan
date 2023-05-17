package com.prettyant.service;

import com.prettyant.bean.BusinessInfo;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuleService {

    @Autowired
    private KieBase kieBase;

    /**
     *
     * @param businessInfo  需要更新的配置模型
     * @return  更新后的模型
     */
    public BusinessInfo executeRule(BusinessInfo businessInfo) {
        KieSession kieSession = kieBase.newKieSession();
        //插入事实对象
        kieSession.insert(businessInfo);
        kieSession.fireAllRules();
        kieSession.dispose();

        return businessInfo;
    }
}
