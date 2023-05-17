package com.prettyant.loan.view.pop.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prettyant.loan.R;
import com.prettyant.loan.model.bean.BusinessTypeModel;
import com.prettyant.loan.view.pop.holder.BusinessTypeHolder;

import java.util.ArrayList;

/**
 * @author ChenYu
 * My personal blog  https://prettyant.github.io
 * <p>
 * Created on 3:24 PM  2020/7/2
 * PackageName : com.example.serviceonline.textonline.ui.adapter
 * describle :
 */
public class BusinessTypeAdapter extends RecyclerView.Adapter<BusinessTypeHolder> {
    private  Activity                     activity;
    private  ArrayList<BusinessTypeModel> businessTypeModels;
    private ItemClickListener itemClickCallBack;

    public BusinessTypeAdapter(Activity activity, ArrayList<BusinessTypeModel> businessTypeModels) {
        this.activity = activity;
        this.businessTypeModels = businessTypeModels;
    }

    @NonNull
    @Override
    public BusinessTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BusinessTypeHolder businessTypeHolder = new BusinessTypeHolder(
                LayoutInflater.from(activity).inflate(R.layout.item_business_type,parent,false),itemClickCallBack
        );
        return businessTypeHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessTypeHolder holder, int position) {
        TextView tv_business_type = holder.tv_business_type;
        ImageView iv_business_icon = holder.iv_business_icon;
        BusinessTypeModel businessTypeModel = businessTypeModels.get(position);
        tv_business_type.setText(businessTypeModel.getBusinessName());
        iv_business_icon.setImageResource(businessTypeModel.getBusinessIcon());
    }

    @Override
    public int getItemCount() {
        return businessTypeModels.size();
    }

    public void setItemClickCallBack(ItemClickListener itemClickCallBack) {
        this.itemClickCallBack = itemClickCallBack;
    }

    public interface ItemClickListener{
        void onItemClickListener(View view, int position);
    }
}
