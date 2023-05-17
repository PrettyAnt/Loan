package com.prettyant.loan.view.pop.holder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.prettyant.loan.R;
import com.prettyant.loan.view.pop.adapter.BusinessTypeAdapter;

/**
 * @author chenyu
 * My personal blog  https://prettyant.github.io
 * <p>
 * Created on 4:41 PM  9/05/23
 * PackageName : com.prettyant.loan.view.pop.holder
 * describle :
 */
public class BusinessTypeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView iv_business_icon;
    public LinearLayout ll_business_item;
    public TextView tv_business_type;
    private BusinessTypeAdapter.ItemClickListener itemClickCallBack;

    public BusinessTypeHolder(View inflate, BusinessTypeAdapter.ItemClickListener itemClickCallBack) {
        super(inflate);
        this.itemClickCallBack = itemClickCallBack;
        ll_business_item = inflate.findViewById(R.id.ll_business_item);
        iv_business_icon = inflate.findViewById(R.id.iv_business_icon);
        tv_business_type = inflate.findViewById(R.id.tv_business_type);
        ll_business_item.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_business_item:
                itemClickCallBack.onItemClickListener(ll_business_item,getLayoutPosition());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }
}
