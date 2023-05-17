package com.prettyant.utils;

import com.google.gson.Gson;
import com.prettyant.bean.*;
import com.prettyant.bean.base.Response;

import java.util.List;

/**
 * 将实体类包装一下，添加返回码等信息
 */
public class ResponseUtil {
    public static final int ADMIN_OK = 0;//管理员登录成功
    public static final int OK = 1;//请求成功
    public static final int ERR = -1;//请求失败
    public static final int UNREGIST = 2;//用户未注册

    /**
     * 一般性的返回报文
     * @param code
     * @param message
     * @return
     */
    public static String returnData(int code, String message) {
        Gson gson = new Gson();
        Response response = new Response();
        response.setCode(code);
        response.setMessage(message);
        String result = gson.toJson(response).replace("\\", "");
        System.out.println("发出去的报文:" + result);
        return result;
    }

    /**
     * 业务
     *
     * @param code
     * @param message
     * @param businessInfos
     * @return
     */
    public static String returnBusinessInfosData(int code, String message, List<BusinessInfo> businessInfos) {
        Gson gson = new Gson();
        BusinessInfosResponse response = new BusinessInfosResponse();
        response.setCode(code);
        response.setMessage(message);
        response.setList(businessInfos);
        String result = gson.toJson(response);
        System.out.println("发出去的报文:" + result);
        return result;
    }

    /**
     * 利率
     * @param code
     * @param message
     * @param businessInfo
     * @return
     */
    public  static String returnBusinessInfo(int code, String message, BusinessInfo businessInfo) {
        Gson gson = new Gson();
        businessInfo.setCode(code);
        businessInfo.setMessage(message);
        String result = gson.toJson(businessInfo);
        System.out.println("发出去的报文:" + result);
        return result;
    }

    /**
     * 某个业务的历史流程
     * @param code
     * @param message
     * @param flowPathModels
     * @return
     */
    public static String returnFlowPathModels(int code, String message, List<FlowPathModel> flowPathModels) {
        Gson gson = new Gson();
        FlowPathModelResponse response = new FlowPathModelResponse();
        response.setCode(code);
        response.setMessage(message);
        response.setFlowPathModels(flowPathModels);
        String result = gson.toJson(response);
        System.out.println("发出去的报文:" + result);
        return result;
    }

    /**
     * 当前所有的任务
     * @param code
     * @param success
     * @param queryCurrentTask
     * @return
     */
    public static String returnTaskInfo(int code, String success, List<TaskModel> queryCurrentTask) {
        Gson gson = new Gson();
        TaskModelResponse response = new TaskModelResponse();
        response.setCode(code);
        response.setMessage(success);
        response.setTaskModels(queryCurrentTask);
        String result = gson.toJson(response);
        System.out.println("所有任务 result -------->>>>>>>>  " + result);
        return result;
    }
}
