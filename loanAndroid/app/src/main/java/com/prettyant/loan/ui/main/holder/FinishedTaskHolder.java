package com.prettyant.loan.ui.main.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prettyant.loan.R;

/**
 * @author chenyu
 * My personal blog  https://prettyant.github.io
 * <p>
 * Created on 10:56 PM  10/05/23
 * PackageName : com.prettyant.loan.ui.main.holder
 * describle :
 */
public class FinishedTaskHolder extends RecyclerView.ViewHolder{
    public TextView tv_processInstanceId;
    public TextView tv_describle;
    public LinearLayout ll_dealtask_item;
    public  TextView                              tv_assignee;
    public  TextView                              tv_taskid;
    public  TextView                              tv_taskname;
    public  TextView                             tv_createtime;

    public FinishedTaskHolder(@NonNull View itemView) {
        super(itemView);
        ll_dealtask_item = itemView.findViewById(R.id.ll_dealtask_item);
        tv_processInstanceId = itemView.findViewById(R.id.tv_processInstanceId);
        tv_assignee = itemView.findViewById(R.id.tv_assignee);
        tv_taskid = itemView.findViewById(R.id.tv_taskid);
        tv_taskname = itemView.findViewById(R.id.tv_taskname);
        tv_createtime = itemView.findViewById(R.id.tv_createtime);
        tv_describle = itemView.findViewById(R.id.tv_describle);
    }


}
