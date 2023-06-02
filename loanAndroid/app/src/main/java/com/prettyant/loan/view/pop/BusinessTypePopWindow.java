package com.prettyant.loan.view.pop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prettyant.loan.BR;
import com.prettyant.loan.R;
import com.prettyant.loan.imp.ItemClickListener;
import com.prettyant.loan.data.bean.BusinessTypeModel;
import com.prettyant.loan.ui.main.adapter.CommonAdapter;

import java.util.ArrayList;

/**
 * @author ChenYu
 * Author's github https://github.com/PrettyAnt
 * <p>
 * Created on 11:07  2019-09-04
 * PackageName : com.example.serviceonline.View.pop
 * describle :
 */
public class BusinessTypePopWindow {
    private static BusinessTypePopWindow                 INSTANCE;
    private        PopupWindow                           popupWindow;
    private        Activity                              activity;
    private        ItemClickListener itemClickCallBack;
    private        ArrayList<BusinessTypeModel>          businessTypeModels;


    public static BusinessTypePopWindow getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BusinessTypePopWindow();
        }
        return INSTANCE;
    }

    /**
     * pop是否显示
     *
     * @return
     */
    public boolean isShow() {
        if (popupWindow == null) {
            return false;
        } else {
            return popupWindow.isShowing();
        }
    }

    /**
     *      * 创建popupWindow
     * <p>
     *      *popupWindow 是全局定义的，根W据自己需要定义
     *      * @param view View 比如：btn_ok的点击后触发popupWindow，则view就是id为btn_ok对应的view
     *      
     */
    @SuppressLint("WrongConstant")
    public void showBusinessPop(View view, Activity activity, ArrayList<BusinessTypeModel> businessTypeModels, ItemClickListener itemClickCallBack) {
        this.businessTypeModels = businessTypeModels;
        this.activity = activity;
        float backgroundAlpha = 0.5f;
        this.itemClickCallBack = itemClickCallBack;
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
//            return;
        }
        LayoutInflater inflater    = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View           contentView = inflater.inflate(R.layout.pop_businesstype_layout, null);
        initView(activity, contentView);


        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setTouchable(true);
        setBackgroundAlpha(backgroundAlpha);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //添加弹出、弹入的动画
        popupWindow.setAnimationStyle(R.style.center_popwindow);
//        popupWindow.showAtLocation(view, Gravity.LEFT | Gravity.BOTTOM, 0, 0);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        //添加按键事件监听
//        setButtonListeners(layout);
        //添加pop窗口关闭事件，主要是实现关闭时改变背景的透明度
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);

            }
        });
        initData();
    }

    private void initData() {
    }

    /**
     * 初始化pop页面的数据
     *
     * @param activity
     * @param inflate
     */
    private void initView(Activity activity, View inflate) {
        RecyclerView        recyclerView        = inflate.findViewById(R.id.rv_business);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        CommonAdapter<BusinessTypeModel> businessTypeModelCommonAdapter = new CommonAdapter<>(businessTypeModels, R.layout.item_business_type, BR.businessType, BR.holder);

        recyclerView.setAdapter(businessTypeModelCommonAdapter);
        businessTypeModelCommonAdapter.setItemClickListener((view, position) -> {
            itemClickCallBack.onItemClickListener(view,position);
            popupWindow.dismiss();
        });
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }


    /**
     * 隐藏popupWindow
     */
    public void hideBottomPop() {
        if (popupWindow == null) {
            return;
        }
        popupWindow.dismiss();
    }

}
