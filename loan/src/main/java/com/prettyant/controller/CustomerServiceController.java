package com.prettyant.controller;

import com.prettyant.service.FlowableService;
import com.prettyant.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * value = "/queryCurrentTask", produces = "text/html;charset=UTF-8" 解决了中文乱码的问题
 * tips:可以通过前端的head 查看获取到后端的报文的编码格式
 */
@RestController
public class CustomerServiceController {

    /**
     * 流程引擎服务
     */
    @Autowired
    private FlowableService flowableService;

    /**
     * 查询已完成的任务
     *
     * @return
     */
    @GetMapping(value = "/queryFinishedTask", produces = "text/html;charset=UTF-8")
    public String queryFinishedTask(@RequestHeader HttpHeaders headers, @RequestParam("index") int index) {
        String authorization = headers.get("Authorization").get(0);
        if (!StringUtils.hasText(authorization)) {
            return ResponseUtil.returnData(ResponseUtil.ERR, "login outDate");
        }
        return ResponseUtil.returnTaskInfo(ResponseUtil.OK, "query success", flowableService.queryCusFinishedTask(authorization,index));
    }

    /**
     * 查询当前的任务
     *
     * @return
     */
    @GetMapping(value = "/queryCurrentTask", produces = "text/html;charset=UTF-8")
    public String queryCurrentTask(@RequestHeader HttpHeaders headers, @RequestParam("index") int index) {
        String authorization = headers.get("Authorization").get(0);
        if (!StringUtils.hasText(authorization)) {
            return ResponseUtil.returnData(ResponseUtil.ERR, "login outDate");
        }
        return ResponseUtil.returnTaskInfo(ResponseUtil.OK, "query success", flowableService.queryCurrentTask(authorization,index));
    }

    /**
     * 处理当前的任务
     * @param taskId   任务id
     * @param processInstanceId
     * @param message 审批意见
     * @param approved  同意/拒绝
     * @return
     */
    @PostMapping(value = "/dealTask", produces = "text/html;charset=UTF-8")
    public String dealTask(@RequestParam("taskId") String taskId,
                           @RequestParam("processInstanceId") String processInstanceId,
                           @RequestParam("message") String message,
                           @RequestParam("approved") boolean approved) {
        System.out.println("收到前端--->>  taskId:"+taskId+" processInstanceId:"+processInstanceId+"  message:"+message+" approved:"+approved);
        return flowableService.dealTask(taskId,processInstanceId,message, approved);
    }
}
