package com.prettyant.controller;

import com.google.gson.Gson;
import com.prettyant.bean.BusinessInfo;
import com.prettyant.entity.UserInfo;
import com.prettyant.dao.BusinessInfoDao;
import com.prettyant.service.FlowableService;
import com.prettyant.service.RuleService;
import com.prettyant.utils.LogUtils;
import com.prettyant.utils.ResponseUtil;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Component
@RocketMQMessageListener(topic = "topic_loan",consumerGroup = "prettyant-loan-group")
public class UserController implements RocketMQListener<BusinessInfo> {
    @Resource
    public RedisTemplate<String, UserInfo> redisTemplate;
    @Autowired
    private FlowableService flowableService;
    @Autowired
    private RuleService ruleService;

    /**
     * 普通用户注册
     *
     * @return
     */
    @PostMapping(value = "/regist", produces = "text/html;charset=UTF-8")
    public String regist(@RequestBody UserInfo userInfo) {
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();
        System.out.println("userInfo = " + userInfo);
        if (Boolean.FALSE.equals(redisTemplate.hasKey(username))) {
            if (!StringUtils.hasText(password)) {
                //未输入密码
                return ResponseUtil.returnData(ResponseUtil.ERR, "请输入密码!");
            }
            redisTemplate.opsForValue().set(username, userInfo);
            return ResponseUtil.returnData(ResponseUtil.OK, "注册成功!");
        } else {
            return ResponseUtil.returnData(ResponseUtil.ERR, "该用户已存在,请重新注册!");
        }
    }

    /**
     * 登录
     *
     * @return
     */
    @PostMapping(value = "/login", produces = "text/html;charset=UTF-8")
    public String login(@RequestBody UserInfo userInfo) {
        System.out.println("userInfo = " + userInfo);
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();
        //操作员登录判断
        if (username.equals("zhangsan") || username.equals("lisi")) {
            if (!password.equals("123456")) {
                return ResponseUtil.returnData(ResponseUtil.ERR, "密码错误!");
            }
            return ResponseUtil.returnData(ResponseUtil.ADMIN_OK, "操作员登录成功!");
        }

        //普通用户
        if (Boolean.FALSE.equals(redisTemplate.hasKey(username))) {
            //redis中没有该用户,去注册
            return ResponseUtil.returnData(ResponseUtil.UNREGIST, "用户未注册!");
        } else {
            UserInfo redisUserInfo = redisTemplate.opsForValue().get(username);
            if (!redisUserInfo.getPassword().equals(password)) {
                return ResponseUtil.returnData(ResponseUtil.ERR, "密码错误!");
            }
            //普通用户登录成功
            return ResponseUtil.returnData(ResponseUtil.OK, "用户登录成功!");
        }
    }

    /**
     * 获取利率接口
     *
     * @param headers
     * @param businessInfo
     * @return
     */
    @PostMapping(value = "/getRate", produces = "text/html;charset=UTF-8")
    public String getRate(@RequestHeader HttpHeaders headers, @RequestBody BusinessInfo businessInfo) {
        String authorization = headers.get("Authorization").get(0);
        System.out.println("authorization = " + authorization);
        if (!StringUtils.hasText(authorization)) {
            return ResponseUtil.returnBusinessInfo(ResponseUtil.ERR, "登录信息已过期!", businessInfo);
        }
        //设置用户信息
        businessInfo.setRate(0);//给前端传来的rate初始化
        businessInfo.setUsername(authorization);
        ruleService.executeRule(businessInfo);//规则匹配
        LogUtils.w(UserController.class, new Gson().toJson(businessInfo));
        return ResponseUtil.returnBusinessInfo(ResponseUtil.OK, "success!", businessInfo);
    }

    /**
     * 收到rocketmq 用户申请办理业务的消息
     *
     * @param businessInfo  用户办理的业务
     */
    @Override
    public void onMessage(BusinessInfo businessInfo) {
        flowableService.submitBusiness(businessInfo);
        LogUtils.i(UserController.class,"rocketmq -->>> "+businessInfo.toString());
    }

    /**
     * 用户业务查询接口
     *
     * @param headers
     * @param index 分页加载，index 当第几页，
     * @return 返回所申请的业务信息  返回为的list为空时，表示无数据或最后一页
     */
    @GetMapping(value = "/userQuery", produces = "text/html;charset=UTF-8")
    public String userQuery(@RequestHeader HttpHeaders headers, @RequestParam("index") int index) {
        String authorization = headers.get("Authorization").get(0);
        if (!StringUtils.hasText(authorization)) {
            return ResponseUtil.returnData(ResponseUtil.ERR, "userQuery:  login expired");
        }
        List<BusinessInfo>   businessInfos=flowableService.queryBusinessInfos(authorization,index);
        return ResponseUtil.returnBusinessInfosData(ResponseUtil.OK, "success", businessInfos);
    }

    /**
     * 查询某个业务的历史流程
     *
     * @param processInstanceId
     * @return
     */
    @PostMapping(value = "/queryHistory", produces = "text/html;charset=UTF-8")
    public String queryHistory(@RequestHeader HttpHeaders headers,
                               @RequestParam("processInstanceId") String processInstanceId) {
        String authorization = headers.get("Authorization").get(0);
        if (!StringUtils.hasText(authorization)) {
            return ResponseUtil.returnData(ResponseUtil.ERR, "login expired");
        }
        return ResponseUtil.returnFlowPathModels(ResponseUtil.OK, "success", flowableService.queryUserProcess(processInstanceId));
    }

    /**
     * 登出接口
     *
     * @param headers
     * @return
     */
    @PostMapping(value = "/logOut", produces = "text/html;charset=UTF-8")
    public String logOut(@RequestHeader HttpHeaders headers) {
//        session.removeAttribute("username");

        return ResponseUtil.returnData(0, "log out success!");
    }


}
