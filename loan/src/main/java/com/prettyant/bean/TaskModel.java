package com.prettyant.bean;

import lombok.Data;

@Data
public class TaskModel {

    /**
     * 操作员昵称
     */
    private String assignee;
    /**
     * 任务编号
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String name;
    /**
     * 任务的创建时间
     */
    private String createTime;

    /**
     * 业务编号
     */
    private String processInstanceId;
    /**
     * 审批意见
     */
    private String description;

    /**
     * 是否需要审核判断
     */
    private boolean needJudge;
}
