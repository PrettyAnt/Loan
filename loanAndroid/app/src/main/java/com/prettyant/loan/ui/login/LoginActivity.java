package com.prettyant.loan.ui.login;

import android.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.prettyant.loan.R;
import com.prettyant.loan.cons.ContantFields;
import com.prettyant.loan.model.mvpview.LoginMvpView;
import com.prettyant.loan.model.mvpview.RegistMvpView;
import com.prettyant.loan.model.bean.Response;
import com.prettyant.loan.presenter.LoginPresenter;
import com.prettyant.loan.presenter.RegistPresenter;
import com.prettyant.loan.ui.base.BaseActivity;
import com.prettyant.loan.ui.main.MainActivity;
import com.prettyant.loan.util.SPUtils;
import com.prettyant.loan.view.CommDialog;

import java.util.HashMap;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity implements LoginMvpView, RegistMvpView {

    EditText et_account, et_pwd;
    Button         btn_login;
    LoginPresenter loginPresenter;
    private TextView        regist;
    private RegistPresenter registPresenter;
    private CheckBox        cb_remember;
    private TextView tv_envri;

    @Override
    public int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        setTheme(R.style.AppTheme_Dark);
        initTitleBar("", "登录", "", 0, this);
        title_bar.setBackgroundColor(getResources().getColor(R.color.common_title_bg));
        et_account = (EditText) $(R.id.account);
        et_pwd = (EditText) $(R.id.password);
        btn_login = (Button) $(R.id.login);
        regist = (TextView) $(R.id.tv_right);
        cb_remember = (CheckBox) $(R.id.cb_remember);
        tv_envri = (TextView) $(R.id.tv_envri);
        regist.setVisibility(View.INVISIBLE);

    }

    @Override
    public void initClick() {
        btn_login.setOnClickListener(this);
    }

    @Override
    public void initData() {
        loginPresenter = new LoginPresenter(this);
        loginPresenter.attachView(this);
        registPresenter = new RegistPresenter(this);
        registPresenter.attachView(this);
        String username = (String) SPUtils.get(this, "username", "");
        String password = (String) SPUtils.get(this, "password", "");
        et_account.setText(username);
        et_pwd.setText(password);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_right:
                Toast.makeText(LoginActivity.this, "注册", Toast.LENGTH_SHORT).show();
                break;
            case R.id.login:
                String content = ((TextView) v).getText().toString();
                if (TextUtils.equals(content, "登录")) {
                    doLogin();
                } else {
                    doRegist();
                }
                break;
        }
    }

    /**
     * 注册
     */
    private void doRegist() {
        String account = et_account.getText().toString();
        if (TextUtils.isEmpty(account)) {
            Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        String pwd = et_pwd.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        ContantFields.username = account;
        registPresenter.regist(account, pwd);
    }

    /**
     * 登录
     */
    void doLogin() {
        String account = et_account.getText().toString();
        String pwd     = et_pwd.getText().toString();
        if (TextUtils.isEmpty(account)) {
            Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        ContantFields.username = account;
        loginPresenter.login(account, pwd);
    }

    /**
     * 保存登录信息
     */
    private void saveData() {
        String  userName = et_account.getText().toString();
        String  password = et_pwd.getText().toString();
        boolean checked  = cb_remember.isChecked();
        SPUtils.put(this, "username", userName);
        if (checked) {
            SPUtils.put(this, "password", password);
        } else {
            SPUtils.remove(this,"password");
        }
        initWaterMark();
    }

    @Override
    public void userLoginSuccess(Response response) {
        saveData();
        startActivity(MainActivity.class);
        finish();
        Toast.makeText(LoginActivity.this, response.message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void customerLoginSuccess(Response response) {
        saveData();
        //操作员登录成功
        startActivity(MainActivity.class);
        finish();
        Toast.makeText(LoginActivity.this, response.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void unRegist(String message) {
        //未注册
//        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
        CommDialog.getInstance().commDialog(this, "温馨提示", message, "取消", "去注册", new CommDialog.DialogClickListener() {
            @Override
            public void onCancleClickListener(View view, AlertDialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onSureClickListener(View view, AlertDialog dialog) {
                dialog.dismiss();
                tv_title.setText("注册");
                btn_login.setText("注册");

            }
        });
    }

    @Override
    public void loginFail(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.detachView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK://返回键
                if (TextUtils.equals(tv_title.getText().toString(), "注册")) {
                    tv_title.setText("登录");
                    btn_login.setText("登录");
                    return false;
                }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void registSuccess(Response response) {
        //注册成功，跳转到业务办理页面
        saveData();
        //操作员登录成功
        startActivity(MainActivity.class);
        finish();
        Toast.makeText(LoginActivity.this, response.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registFail(String message) {
        //注册失败
        CommDialog.getInstance().commDialog(this, "温馨提示", message, "", "确定", new CommDialog.DialogClickListener() {
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

