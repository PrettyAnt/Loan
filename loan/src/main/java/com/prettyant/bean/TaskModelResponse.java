package com.prettyant.bean;

import com.prettyant.bean.base.Response;
import lombok.Data;

import java.util.List;

/**
 * 任务列表
 */
@Data
public class TaskModelResponse extends Response {
    /**
     * 任务模型的列表
     */
    private List<TaskModel> taskModels;
}
