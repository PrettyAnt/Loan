package com.prettyant.loan.ui.main.holder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.prettyant.loan.R;

/**
 * @author chenyu
 * My personal blog  https://prettyant.github.io
 * <p>
 * Created on 3:59 PM  10/05/23
 * PackageName : com.prettyant.loan.ui.main.holder
 * describle :
 */
public class BusinessDetailHolder extends RecyclerView.ViewHolder {

    public TextView tv_newtip;
    public TextView tv_starttime;
    public TextView tv_activityname;
    public TextView tv_assignee;

    public BusinessDetailHolder(View inflate) {
        super(inflate);
        tv_newtip = inflate.findViewById(R.id.tv_newtip);
        tv_starttime = inflate.findViewById(R.id.tv_starttime);
        tv_activityname = inflate.findViewById(R.id.tv_activityname);
        tv_assignee = inflate.findViewById(R.id.tv_assignee);
    }
}
