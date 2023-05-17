package com.prettyant.loan.model.bean;

import java.util.List;

public class TaskResponse extends Response {
    /**
     * 任务模型的列表 】
     */
    private List<TaskModel> taskModels;

    public List<TaskModel> getTaskModels() {
        return taskModels;
    }

    public void setTaskModels(List<TaskModel> taskModels) {
        this.taskModels = taskModels;
    }
}