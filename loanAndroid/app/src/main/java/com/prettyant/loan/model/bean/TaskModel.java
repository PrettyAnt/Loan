package com.prettyant.loan.model.bean;


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

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isNeedJudge() {
        return needJudge;
    }

    public void setNeedJudge(boolean needJudge) {
        this.needJudge = needJudge;
    }

    @Override
    public String toString() {
        return "TaskModel{" +
                "assignee='" + assignee + '\'' +
                ", taskId='" + taskId + '\'' +
                ", name='" + name + '\'' +
                ", createTime='" + createTime + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
