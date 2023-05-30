package com.prettyant.loan.ui.main.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.prettyant.loan.imp.ItemClickListener;
import com.prettyant.loan.model.bean.BusinessInfo;
import com.prettyant.loan.model.bean.FlowPathModel;
import com.prettyant.loan.ui.main.holder.CommonHolder;

import java.util.List;

/**
 * @author chenyu
 * My personal blog  https://prettyant.github.io
 * <p>
 * Created on 1:41 PM  10/05/23
 * PackageName : com.prettyant.loan.ui.main.adapter
 * describle :
 */
public class CommonAdapter<T> extends RecyclerView.Adapter<CommonHolder> {
    private List<T> list;
    private int defaultLayout;
    private int brId;
    private ItemClickListener itemClickListener;

    public CommonAdapter(int defaultLayout, int brId) {
        this.defaultLayout = defaultLayout;
        this.brId = brId;
    }

    public CommonAdapter(List<T> list, int defaultLayout, int brId) {
        this.list = list;
        this.defaultLayout = defaultLayout;
        this.brId = brId;
    }

    @NonNull
    @Override
    public CommonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false);
        return new CommonHolder(dataBinding,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonHolder holder, int position) {
        //绑定数据
        holder.bind(brId,list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getItemLayout(list.get(position));
    }

    private int getItemLayout(T t) {
        //可以在这里进行自定义Layout填充布局
        //fixme
        if (t instanceof BusinessInfo) {
            //伪代码
            //businessInfo.getType()


        } else if (t instanceof FlowPathModel) {
        }
        return defaultLayout;
    }



    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

}
