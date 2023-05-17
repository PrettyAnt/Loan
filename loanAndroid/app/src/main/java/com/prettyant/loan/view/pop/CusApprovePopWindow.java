package com.prettyant.loan.view.pop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.prettyant.loan.R;
import com.prettyant.loan.view.CommDialog;

/**
 * @author ChenYu
 * Author's github https://github.com/PrettyAnt
 * <p>
 * Created on 11:07  2019-09-04
 * PackageName : com.example.serviceonline.View.pop
 * describle :操作员审核弹窗
 */
public class CusApprovePopWindow implements View.OnClickListener {
    private static CusApprovePopWindow INSTANCE;
    private        PopupWindow         popupWindow;
    private        Activity            activity;
    private        EditText            et_cus_message;
    private        Button              btn_submit;
    private        SubmitClickListener submitClickListener;
    private        RadioButton         rb_approve;
    private        RadioButton         rb_unapprove;
    private ImageView iv_close;
    private boolean needJudge;
    private LinearLayout ll_judge;


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
    public void approvePop(View view, Activity activity, SubmitClickListener submitClickListener, boolean needJudge) {
        this.activity = activity;
        this.submitClickListener = submitClickListener;
        this.needJudge = needJudge;
        float backgroundAlpha = 0.5f;
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
//            return;
        }
//        LayoutInflater inflater    = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View           contentView = LayoutInflater.from(activity).inflate(R.layout.pop_cusapprove_layout, null);
        initView(activity, contentView);


        popupWindow = new PopupWindow(contentView,
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

            }
        });
        initData();
    }

    private void initData() {
        ll_judge.setVisibility(needJudge?View.VISIBLE:View.GONE);
    }

    private Handler handler = new Handler(Looper.getMainLooper());
    /**
     * 初始化pop页面的数据
     *
     * @param activity
     * @param inflate
     */
    private void initView(Activity activity, View inflate) {
        iv_close = inflate.findViewById(R.id.iv_close);
        et_cus_message = inflate.findViewById(R.id.et_cus_message);
        ll_judge = inflate.findViewById(R.id.ll_judge);
        rb_approve = inflate.findViewById(R.id.rb_approve);
        rb_unapprove = inflate.findViewById(R.id.rb_unapprove);
        btn_submit = inflate.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        iv_close.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_submit) {
            String message = et_cus_message.getText().toString();
            if (!needJudge) {
                submitClickListener.onSubmitClickListener(message,true);
                hideBottomPop();
                return;
            }
            if (!rb_approve.isChecked() && !rb_unapprove.isChecked()) {
                CommDialog.getInstance().commDialog(activity, "温馨提示", "亲,请选择是否通过", null, "确定", new CommDialog.DialogClickListener() {
                    @Override
                    public void onCancleClickListener(View view, AlertDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onSureClickListener(View view, AlertDialog dialog) {
                        dialog.dismiss();
                    }
                });
                return;
            }
            boolean approve = rb_approve.isChecked();
            submitClickListener.onSubmitClickListener(message,approve);
            hideBottomPop();
        } else if (view.getId() == R.id.iv_close) {
            hideBottomPop();
        }
    }

    public interface SubmitClickListener {
        void onSubmitClickListener(String message, boolean approve);
    }
}
