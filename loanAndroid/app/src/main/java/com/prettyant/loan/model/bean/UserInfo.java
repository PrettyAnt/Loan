package com.prettyant.loan.model.bean;

import java.io.Serializable;

public class UserInfo {
//    private static final long serialVersionUID = 1447315490361028900L;
    /**
     * 用户昵称
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
