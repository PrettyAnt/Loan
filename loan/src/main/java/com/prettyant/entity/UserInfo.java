package com.prettyant.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1447315490361028900L;
    /**
     * 用户昵称
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;

}
