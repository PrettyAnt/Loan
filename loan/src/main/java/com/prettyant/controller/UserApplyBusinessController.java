package com.prettyant.controller;

import com.prettyant.bean.BusinessInfo;
import com.prettyant.utils.ResponseUtil;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class UserApplyBusinessController {
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 用户申请贷款业务
     * {
     * "username": "chenyu",
     * "business": {
     * "businessName": "房贷",
     * "rate": "0",
     * "amount": "2000"
     * }
     * }
     *
     * @return
     */
    @PostMapping(value = "/apply", produces = "text/html;charset=UTF-8")
    public String apply(@RequestHeader HttpHeaders headers, @RequestBody BusinessInfo businessInfo) {
        String authorization = headers.get("Authorization").get(0);
        if (!StringUtils.hasText(authorization)) {
            return ResponseUtil.returnData(ResponseUtil.ERR, "登录信息已过期");
        }
        //设置用户信息
        businessInfo.setUsername(authorization);
        rocketMQTemplate.convertAndSend("topic_loan",businessInfo);
        return ResponseUtil.returnData(ResponseUtil.OK, "申请成功");
    }

}
