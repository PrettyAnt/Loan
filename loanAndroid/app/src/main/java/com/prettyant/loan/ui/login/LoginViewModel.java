package com.prettyant.loan.ui.login;

import android.app.Application;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.prettyant.loan.data.repository.network.HttpRequestManager;
import com.prettyant.loan.data.bean.Response;
import com.prettyant.loan.data.bean.UserInfo;
import com.prettyant.loan.ui.base.BaseJetViewModel;
import com.tencent.mmkv.MMKV;

/**
 * @author chenyu
 * My personal blog  https://prettyant.github.io
 * <p>
 * Created on 4:55 PM  23/05/23
 * PackageName : com.prettyant.loan.ui.login
 * describle :  页面的信息参数   仅用来封装上送的报文和页面的参数
 */
public class LoginViewModel extends BaseJetViewModel {
    private MutableLiveData<String> account, pwd, loginOrRegist;
    private MutableLiveData<Boolean> isChecked;
    private MutableLiveData<UserInfo> userInfoMutableLiveData;

    private MutableLiveData<Response> responseMutableLiveData;

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 用户信息  也是http上送报文的必要信息
     *
     * @return
     */
    public MutableLiveData<UserInfo> getUserInfoMutableLiveData() {
        if (userInfoMutableLiveData == null) {
            userInfoMutableLiveData = new MutableLiveData<>();
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(getAccount().getValue());
        userInfo.setPassword(getPwd().getValue());
        userInfoMutableLiveData.setValue(userInfo);
        return userInfoMutableLiveData;
    }

    /**
     * 对应layout 的用户名
     *
     * @return
     */
    public MutableLiveData<String> getAccount() {
        if (account == null) {
            account = new MutableLiveData<>();
            String username = MMKV.defaultMMKV().getString("username", "");
            account.setValue(username);
        }
        return account;
    }

    /**
     * 密码
     *
     * @return
     */
    public MutableLiveData<String> getPwd() {
        if (pwd == null) {
            pwd = new MutableLiveData<>();
            String password = MMKV.defaultMMKV().getString("password", "");
            pwd.setValue(password);
        }
        return pwd;
    }

    /**
     * 点击按钮显示登录还是注册
     *
     * @return
     */
    public MutableLiveData<String> getLoginOrRegist() {
        if (loginOrRegist == null) {
            loginOrRegist = new MutableLiveData<>();
            loginOrRegist.setValue("登录");
        }
        return loginOrRegist;
    }


    /**
     * checkbox是否选中
     *
     * @return
     */
    public MutableLiveData<Boolean> getIsChecked() {
        if (isChecked == null) {
            isChecked = new MutableLiveData<>();
            isChecked.setValue(false);
        }
        return isChecked;
    }

    /**
     * 创建MutableLiveData实例
     * 然后观察MutableLiveData的数据
     * 网络请求得到报文后，将返回的报文放在LiveData中，观察者立马会得到响应
     *
     * @return
     */
    public MutableLiveData<Response> getResponseMutableLiveData() {
        if (responseMutableLiveData == null) {
            responseMutableLiveData = new MutableLiveData<>();
        }
        return responseMutableLiveData;
    }


    /**
     * 登录 or 注册
     */
    public void requestLogin() {
        if (TextUtils.isEmpty(account.getValue()) || TextUtils.isEmpty(pwd.getValue())) {
            Toast.makeText(context, "请输入用户名或密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.equals(loginOrRegist.getValue(), "登录")) {
            HttpRequestManager.getInstance().requestLoginData(getResponseMutableLiveData(), getUserInfoMutableLiveData());
        } else {
            HttpRequestManager.getInstance().requestRegistData(getResponseMutableLiveData(), getUserInfoMutableLiveData());
        }
    }

}
