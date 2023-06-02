package com.prettyant.loan.ui.main.holder;

import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.prettyant.loan.BR;
import com.prettyant.loan.imp.ItemClickListener;

/**
 * @author chenyu
 * My personal blog  https://prettyant.github.io
 * <p>
 * Created on 1:43 PM  10/05/23
 * PackageName : com.prettyant.loan.ui.main.holder
 * describle :
 */
public class CommonHolder extends RecyclerView.ViewHolder {
    public ViewDataBinding dataBinding;
    private ItemClickListener itemClickListener;

    public CommonHolder(ViewDataBinding dataBinding, ItemClickListener itemClickListener) {
        super(dataBinding.getRoot());
        this.itemClickListener = itemClickListener;
        this.dataBinding = dataBinding;
    }

    public <T> void bind(int brId, int holdId, T t) {
        dataBinding.setVariable(brId, t);
        dataBinding.setVariable(holdId, this);
        dataBinding.executePendingBindings();
    }

    /**
     * item的点击事件
     *
     * @param view
     */
    public void onClick(View view) {
        if (itemClickListener != null) {
            itemClickListener.onItemClickListener(view, getLayoutPosition());
        }
    }

}
