package com.prettyant.bean;

import lombok.Data;

@Data
public class FlowPathModel {
    private String processInstanceId;//业务编号

    private String taskId;//任务编号
    private String assignee;//操作员姓名

    private String activityName;//流程名称

    private String startTime;//开始时间
}
