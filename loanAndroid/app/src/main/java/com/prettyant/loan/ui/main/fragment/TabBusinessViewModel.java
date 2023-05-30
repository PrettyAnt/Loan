package com.prettyant.loan.ui.main.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.prettyant.loan.cons.ContantFields;
import com.prettyant.loan.data.repository.network.HttpRequestManager;
import com.prettyant.loan.model.bean.BusinessInfo;
import com.prettyant.loan.model.bean.Response;
import com.prettyant.loan.ui.base.BaseJetViewModel;
import com.prettyant.loan.util.LogUtil;
import com.prettyant.loan.view.CommDialog;

/**
 * @author chenyu
 * My personal blog  https://prettyant.github.io
 * Created on 9:21 AM  30/05/23
 * PackageName : com.prettyant.loan.ui.main.fragment
 * describle :
 */
public class TabBusinessViewModel extends BaseJetViewModel {
    private MutableLiveData<String> username,choose,amout,rate,total;
    private MutableLiveData<Boolean> rule;
    private MutableLiveData<BusinessInfo> businessInfoMutableLiveData;
    private String year;
    private Activity activity;
    private MutableLiveData<Response> responseMutableLiveData;

    public TabBusinessViewModel(@NonNull Application application) {
        super(application);
        username = new MutableLiveData<>();
        choose = new MutableLiveData<>();
        amout = new MutableLiveData<>();
        rate = new MutableLiveData<>();
        total = new MutableLiveData<>();
        rule = new MutableLiveData<>();
        businessInfoMutableLiveData = new MutableLiveData<>();
        responseMutableLiveData = new MutableLiveData<>();
        username.setValue(TextUtils.isEmpty(ContantFields.username) ? "" : ContantFields.username);
        rule.setValue(false);
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
    public MutableLiveData<String> getUsername() {
        return username;
    }

    public MutableLiveData<String> getChoose() {
        return choose;
    }

    public MutableLiveData<String> getAmout() {
        return amout;
    }

    public MutableLiveData<String> getRate() {
        return rate;
    }

    public MutableLiveData<String> getTotal() {
        return total;
    }

    public MutableLiveData<Boolean> getRule() {
        return rule;
    }

    public MutableLiveData<BusinessInfo> getBusinessInfoMutableLiveData() {
        return businessInfoMutableLiveData;
    }

    public MutableLiveData<Response> getResponseMutableLiveData() {
        return responseMutableLiveData;
    }

    public void onCheckChanged(RadioGroup radioGroup, int position) {
        LogUtil.v(LogUtil.LOG+"----"+position);
        RadioButton radioButton = radioGroup.findViewById(position);
        if (radioButton != null) {
            year = radioButton.getText().toString();
        }
    }
    /**
     * 试算
     */
    public void calue() {
        HttpRequestManager.getInstance().getRate(businessInfoMutableLiveData,getBusinessInfo());
    }

    @NonNull
    private BusinessInfo getBusinessInfo() {
        BusinessInfo businessInfo = new BusinessInfo();
        businessInfo.setUsername(username.getValue());
        businessInfo.setBusinessName(choose.getValue());
        businessInfo.setAmount(amout.getValue());
        businessInfo.setDuringTime(year);
        return businessInfo;
    }

    /**
     * 提交
     */
    public void submit() {
        if (TextUtils.isEmpty(choose.getValue())) {
            //请选择贷款用途
            showDialog("请选项贷款用途");
            return;
        }
        if (TextUtils.isEmpty(year)) {
           //贷款年限
            showDialog("请选项贷款年限");
            return;
        }
        if (TextUtils.isEmpty(amout.getValue())) {
            //请选项贷款金额
            showDialog("请选项贷款金额");
            return;
        }
        if (Boolean.FALSE.equals(rule.getValue())) {
            //请勾选条款
            showDialog("请勾选条款");
            return;
        }
        HttpRequestManager.getInstance().apply(responseMutableLiveData,getBusinessInfo());
    }

    /**
     * 弹窗dialog
     * @param titleMsg
     */
    public void showDialog(String titleMsg) {
        CommDialog.getInstance().commDialog(activity, "温馨提示", titleMsg, null, "确定", new CommDialog.DialogClickListener() {
            @Override
            public void onCancleClickListener(View view, AlertDialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onSureClickListener(View view, AlertDialog dialog) {
                dialog.dismiss();
            }
        });
    }


}
