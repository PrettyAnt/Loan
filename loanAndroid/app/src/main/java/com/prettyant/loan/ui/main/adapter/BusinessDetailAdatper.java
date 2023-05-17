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
import com.prettyant.loan.model.bean.FlowPathModel;
import com.prettyant.loan.ui.main.holder.BusinessDetailHolder;

import java.util.List;

/**
 * @author chenyu
 * My personal blog  https://prettyant.github.io
 * <p>
 * Created on 3:58 PM  10/05/23
 * PackageName : com.prettyant.loan.ui.main.adapter
 * describle :
 */
public class BusinessDetailAdatper extends RecyclerView.Adapter<BusinessDetailHolder> {

    private Activity            activity;
    private List<FlowPathModel> flowPathModels;

    public BusinessDetailAdatper(Activity activity, List<FlowPathModel> flowPathModels) {
        this.activity = activity;
        this.flowPathModels = flowPathModels;
    }

    @NonNull
    @Override
    public BusinessDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BusinessDetailHolder businessDetailHolder = new BusinessDetailHolder(LayoutInflater.from(activity).inflate(R.layout.item_business_status, parent, false));
        return businessDetailHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessDetailHolder holder, int position) {
        TextView      tv_newtip       = holder.tv_newtip;
        TextView      tv_starttime    = holder.tv_starttime;
        TextView      tv_assignee     = holder.tv_assignee;
        TextView      tv_activityname = holder.tv_activityname;
        FlowPathModel flowPathModel   = flowPathModels.get(position);
        String        startTime       = flowPathModel.getStartTime();
        String        assignee        = flowPathModel.getAssignee();
        String        activityName    = flowPathModel.getActivityName();
        tv_newtip.setVisibility(View.INVISIBLE);
        if (position == 0) {
            tv_starttime.setText("最新: " + startTime);
            tv_newtip.setVisibility(View.VISIBLE);
        } else {
            tv_starttime.setText(startTime);
        }
        String resultAssignee = assignee;
        if (!TextUtils.equals(assignee, "服务节点")) {
            resultAssignee = "审批人: " + assignee;
        }
        tv_assignee.setText(resultAssignee);

        tv_activityname.setText("当前状态 : "+activityName);
    }

    @Override
    public int getItemCount() {
        return flowPathModels.size();
    }
}
