package com.prettyant.loan.model.bean;

public class FlowPathModel {
    private String progressInstanceId;//业务编号

    private String taskId;//任务编号
    private String assignee;//操作员姓名

    private String activityName;//流程名称

    private String startTime;//开始时间

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getProgressInstanceId() {
        return progressInstanceId;
    }

    public void setProgressInstanceId(String progressInstanceId) {
        this.progressInstanceId = progressInstanceId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
