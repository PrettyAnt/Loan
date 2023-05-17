package com.prettyant.loan.ui.main.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.prettyant.loan.R;
import com.prettyant.loan.ui.main.adapter.BusinessQueryAdapter;

/**
 * @author chenyu
 * My personal blog  https://prettyant.github.io
 * <p>
 * Created on 1:43 PM  10/05/23
 * PackageName : com.prettyant.loan.ui.main.holder
 * describle :
 */
public class BusinessQueryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final RelativeLayout                         rl_business_item;
    public        TextView                               tv_createtime;
    public        ImageView                              iv_status;
    public        TextView                               tv_processInstanceId;
    public        TextView                               tv_username;
    public        TextView                               tv_businessname;
    public        TextView                               tv_duringtime;
    public        TextView                               tv_amount;
    public        TextView                               tv_rate;
    private       BusinessQueryAdapter.ItemClickListener itemClickListener;

    public BusinessQueryHolder(View inflate, BusinessQueryAdapter.ItemClickListener itemClickListener) {
        super(inflate);
        this.itemClickListener = itemClickListener;
        rl_business_item = inflate.findViewById(R.id.rl_business_item);
        tv_processInstanceId = inflate.findViewById(R.id.tv_processInstanceId);
        tv_username = inflate.findViewById(R.id.tv_username);
        tv_businessname = inflate.findViewById(R.id.tv_businessname);
        tv_duringtime = inflate.findViewById(R.id.tv_duringtime);
        tv_amount = inflate.findViewById(R.id.tv_amount);
        tv_rate = inflate.findViewById(R.id.tv_rate);
        iv_status = inflate.findViewById(R.id.iv_status);
        tv_createtime = inflate.findViewById(R.id.tv_createtime);
        rl_business_item.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.rl_business_item) {
            itemClickListener.onItemClickListener(rl_business_item, getLayoutPosition());
        }
    }
}
