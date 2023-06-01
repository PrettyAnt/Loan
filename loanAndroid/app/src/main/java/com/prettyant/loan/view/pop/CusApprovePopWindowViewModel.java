package com.prettyant.loan.view.pop;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.view.View;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.prettyant.loan.data.repository.network.HttpRequestManager;
import com.prettyant.loan.model.bean.Response;
import com.prettyant.loan.model.bean.TaskModel;
import com.prettyant.loan.ui.base.BaseJetViewModel;
import com.prettyant.loan.view.CommDialog;

/**
 * @author chenyu
 * My personal blog  https://prettyant.github.io
 * Created on 4:05 PM  1/06/23
 * PackageName : com.prettyant.loan.view.pop
 * describle :
 */
public class CusApprovePopWindowViewModel extends BaseJetViewModel {
    private MutableLiveData<String> cusMessage;
    private MutableLiveData<Boolean> showRadioGorup,approveChecked,unapproveChecked;
    private MutableLiveData<Response> responseMutableLiveData;
    private Activity activity;
    private PopupWindow popupWindow;
    private TaskModel taskModel;

    public CusApprovePopWindowViewModel(@NonNull Application application) {
        super(application);
        cusMessage = new MutableLiveData<>();
        showRadioGorup = new MutableLiveData<>();
        approveChecked = new MutableLiveData<>();
        unapproveChecked = new MutableLiveData<>();
        responseMutableLiveData = new MutableLiveData<>();

        approveChecked.setValue(false);
        unapproveChecked.setValue(false);
        cusMessage.setValue("这是一段测试文字.......");
    }
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setPop(PopupWindow popupWindow) {
        this.popupWindow = popupWindow;
    }
    public MutableLiveData<String> getCusMessage() {
        return cusMessage;
    }

    public MutableLiveData<Boolean> getShowRadioGorup() {
        return showRadioGorup;
    }

    public MutableLiveData<Boolean> getApproveChecked() {
        return approveChecked;
    }

    public MutableLiveData<Boolean> getUnapproveChecked() {
        return unapproveChecked;
    }

    public MutableLiveData<Response> getResponseMutableLiveData() {
        return responseMutableLiveData;
    }

    public void submit(View view) {
        if (Boolean.FALSE.equals(showRadioGorup.getValue())) {
            HttpRequestManager.getInstance().dealTask(responseMutableLiveData,taskModel.getTaskId(),taskModel.getProcessInstanceId(),cusMessage.getValue(),true);
            return;
        }
        if (Boolean.FALSE.equals(approveChecked.getValue()) && Boolean.FALSE.equals(unapproveChecked.getValue())) {
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
        HttpRequestManager.getInstance().dealTask(responseMutableLiveData,taskModel.getTaskId(),taskModel.getProcessInstanceId(),cusMessage.getValue(),approveChecked.getValue());
    }

    public void close(View view) {
        popupWindow.dismiss();
    }


    public void setTaskModel(TaskModel taskModel) {
        this.taskModel = taskModel;
    }
}
