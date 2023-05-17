package com.prettyant.loan.ui.main.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prettyant.loan.R;
import com.prettyant.loan.model.bean.TaskModel;
import com.prettyant.loan.ui.main.holder.FinishedTaskHolder;

import java.util.List;

/**
 * @author chenyu
 * My personal blog  https://prettyant.github.io
 * <p>
 * Created on 10:55 PM  10/05/23
 * PackageName : com.prettyant.loan.ui.main.adapter
 * describle :
 */
public class FinishedTaskAdapter extends RecyclerView.Adapter<FinishedTaskHolder> {
    private Activity        activity;
    private List<TaskModel> taskModels;

    public FinishedTaskAdapter(Activity activity, List<TaskModel> taskModels) {
        this.activity = activity;
        this.taskModels = taskModels;
    }

    @NonNull
    @Override
    public FinishedTaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FinishedTaskHolder finishedTaskHolder = new FinishedTaskHolder(LayoutInflater.from(activity).inflate(R.layout.list_finished_task, parent, false));
        return finishedTaskHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FinishedTaskHolder holder, int position) {
        TextView tv_processInstanceId = holder.tv_processInstanceId;
        TextView tv_assignee          = holder.tv_assignee;
        TextView tv_taskid            = holder.tv_taskid;
        TextView tv_taskname          = holder.tv_taskname;
        TextView tv_createtime        = holder.tv_createtime;
        TextView tv_describle         = holder.tv_describle;

        TaskModel taskModel         = taskModels.get(position);
        String    processInstanceId = taskModel.getProcessInstanceId();
        String    assignee          = taskModel.getAssignee();
        String    taskId            = taskModel.getTaskId();
        String    name              = taskModel.getName();
        String    createTime        = taskModel.getCreateTime();
        String    description       = taskModel.getDescription();

        tv_processInstanceId.setText(processInstanceId);
        tv_assignee.setText(assignee);
        tv_taskid.setText(taskId);
        tv_taskname.setText(name);
        tv_createtime.setText(createTime);
        tv_describle.setText(TextUtils.isEmpty(description) ? "无" : description);
    }

    @Override
    public int getItemCount() {
        return taskModels.size();
    }

}
