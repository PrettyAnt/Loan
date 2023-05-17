package com.prettyant.loan.view;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.prettyant.loan.R;


/**
 * Create by ChenYu
 * Create Time 2017/7/18 16:23
 * Author's GitHub  https://github.com/PrettyAnt
 * Description :
 */

public class CommDialog implements View.OnClickListener {
    public static CommDialog          instance;
    private       TextView            commonSure;
    private       TextView            commonCancle;
    private       DialogClickListener dialogClickListener;
    private       AlertDialog         dialog;

    public static CommDialog getInstance() {
        if (instance == null) {
            synchronized (CommDialog.class) {
                if (instance == null) {
                    instance = new CommDialog();
                }
            }
        }
        return instance;
    }

    /**
     * @param context
     * @param titleMsg  提示的信息
     * @param cancleStr 取消按钮的显示文字，null 时默认为取消
     * @param sureStr   确定按钮的显示文字，null时默认显示确定
     */
    public void commDialog(Context context,
                           String title,
                           String titleMsg,
                           String cancleStr,
                           String sureStr,
                           DialogClickListener dialogClickListener
    ) {
        this.dialogClickListener = dialogClickListener;
        AlertDialog.Builder builder        = new AlertDialog.Builder(context, R.style.Theme_Transparent);
        LayoutInflater      layoutInflater = LayoutInflater.from(context);
        View                inflate        = layoutInflater.inflate(R.layout.dialog_common, null);
        builder.setView(inflate);
        TextView commonTitle   = inflate.findViewById(R.id.tv_common_title);
        TextView commonContent = inflate.findViewById(R.id.tv_common_content);
        commonSure = inflate.findViewById(R.id.tv_common_sure);
        commonCancle = inflate.findViewById(R.id.tv_common_cancle);
        View line = inflate.findViewById(R.id.v_common_line);
        dialog = builder.show();
        if (!TextUtils.isEmpty(title)) {
            commonTitle.setText(title);
        }
        if (!TextUtils.isEmpty(titleMsg)) {
            commonContent.setText(titleMsg);
        }
        if (!TextUtils.isEmpty(cancleStr)) {
            commonCancle.setText(cancleStr);
            commonCancle.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
        }else {
            commonCancle.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(sureStr)) {
            commonSure.setText(sureStr);
            commonSure.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
        }else{
            commonSure.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        }
        commonSure.setOnClickListener(this);
        commonCancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_common_sure) {
            dialogClickListener.onSureClickListener(commonSure,dialog);
        } else if (v.getId() == R.id.tv_common_cancle) {
            dialogClickListener.onCancleClickListener(commonCancle,dialog);
        }
    }

    /**
     * dialog 确定、取消事件的回调接口
     */
    public interface DialogClickListener {
        void onCancleClickListener(View view, AlertDialog dialog);
        void onSureClickListener(View view, AlertDialog dialog);
    }


}
