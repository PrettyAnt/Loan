package com.prettyant.loan.ui.main.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prettyant.loan.R;
import com.prettyant.loan.model.bean.BusinessInfo;
import com.prettyant.loan.ui.main.holder.BusinessQueryHolder;

import java.util.List;

/**
 * @author chenyu
 * My personal blog  https://prettyant.github.io
 * <p>
 * Created on 1:41 PM  10/05/23
 * PackageName : com.prettyant.loan.ui.main.adapter
 * describle :
 */
public class BusinessQueryAdapter extends RecyclerView.Adapter<BusinessQueryHolder> {
    private Activity           activity;
    private List<BusinessInfo> businessInfos;
    private ItemClickListener  itemClickListener;

    public BusinessQueryAdapter(Activity activity, List<BusinessInfo> businessInfos) {
        this.activity = activity;
        this.businessInfos = businessInfos;
    }

    @NonNull
    @Override
    public BusinessQueryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BusinessQueryHolder businessQueryHolder = new BusinessQueryHolder(LayoutInflater.from(activity).inflate(R.layout.list_business_info, parent, false), itemClickListener);
        return businessQueryHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessQueryHolder holder, int position) {
        TextView  tv_processInstanceId = holder.tv_processInstanceId;
        TextView  tv_username          = holder.tv_username;
        TextView  tv_businessname      = holder.tv_businessname;
        TextView  tv_duringtime        = holder.tv_duringtime;
        TextView  tv_amount            = holder.tv_amount;
        TextView  tv_rate              = holder.tv_rate;
        ImageView iv_status            = holder.iv_status;//fixme
        TextView  tv_createtime        = holder.tv_createtime;

        BusinessInfo businessInfo      = businessInfos.get(position);
        String       processInstanceId = businessInfo.getProcessInstanceId();
        String       username          = businessInfo.getUsername();
        String       businessName      = businessInfo.getBusinessName();
        String       duringTime        = businessInfo.getDuringTime();
        String       amount            = businessInfo.getAmount();
        float        rate              = businessInfo.getRate();
        String       createTime        = businessInfo.getCreateTime();
        int          businessStatus    = businessInfo.getBusinessStatus();

        tv_processInstanceId.setText(processInstanceId);
        tv_username.setText(username);
        tv_businessname.setText(businessName);
        tv_duringtime.setText(duringTime);
        tv_amount.setText(amount);
        String rateResult = String.format("%.2f", rate * 100) + " %";
        tv_rate.setText(rateResult);
        if (businessStatus == 1) {
            iv_status.setImageResource(R.drawable.icon_status_ok);
        } else {
            iv_status.setImageResource(R.drawable.icon_status_deal);
        }
        tv_createtime.setText(createTime);
    }

    @Override
    public int getItemCount() {
        return businessInfos.size();
    }

    public interface ItemClickListener {
        void onItemClickListener(View view, int position);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
