package com.prettyant.loan.view.pop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.prettyant.loan.BR;
import com.prettyant.loan.R;
import com.prettyant.loan.databinding.PopCusapproveLayoutBinding;
import com.prettyant.loan.model.bean.Response;
import com.prettyant.loan.model.bean.TaskModel;
import com.prettyant.loan.ui.main.fragment.FragmentTabCusCurrentTask;

/**
 * @author ChenYu
 * Author's github https://github.com/PrettyAnt
 * <p>
 * Created on 11:07  2019-09-04
 * PackageName : com.example.serviceonline.View.pop
 * describle :操作员审核弹窗
 */
public class CusApprovePopWindow {
    private static CusApprovePopWindow INSTANCE;
    private PopDismissCallBack popDismissCallBack;
    private PopupWindow popupWindow;
    private Activity activity;
    private TaskModel taskModel;
    private PopCusapproveLayoutBinding popCusapproveLayoutBinding;
    private CusApprovePopWindowViewModel popHolderViewModel;
    private FragmentTabCusCurrentTask fragmentTabCusCurrentTask;


    public static CusApprovePopWindow getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CusApprovePopWindow();
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
    public void approvePop(FragmentTabCusCurrentTask fragmentTabCusCurrentTask, View view, Activity activity, TaskModel taskModel, PopDismissCallBack popDismissCallBack) {
        this.fragmentTabCusCurrentTask = fragmentTabCusCurrentTask;
        this.activity = activity;
        this.taskModel = taskModel;
        this.popDismissCallBack = popDismissCallBack;
        float backgroundAlpha = 0.5f;
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
//            return;
        }
        popCusapproveLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.pop_cusapprove_layout, null, false);

        popupWindow = new PopupWindow(popCusapproveLayoutBinding.getRoot(),
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
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
                popDismissCallBack.onPopDismissCallBack();
            }
        });
        init();
        initData();
    }


    private void initData() {
        popHolderViewModel.getShowRadioGorup().setValue(taskModel.isNeedJudge());
    }

    /**
     * 初始化pop页面的数据
     */
    private void init() {
        popCusapproveLayoutBinding.setVariable(BR.taskInfo, taskModel);
        popHolderViewModel = new CusApprovePopWindowViewModel(activity.getApplication());
        popCusapproveLayoutBinding.setPopHolderViewModel(popHolderViewModel);
        popHolderViewModel.setActivity(activity);
        popHolderViewModel.setPop(popupWindow);
        popHolderViewModel.setTaskModel(taskModel);
        popHolderViewModel.getResponseMutableLiveData().observe(fragmentTabCusCurrentTask, response -> popupWindow.dismiss());
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

    public interface PopDismissCallBack {
       public void onPopDismissCallBack();
    }


}
