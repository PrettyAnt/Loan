package com.prettyant.loan.data.bean;

public class Response {
    /**
     * 返回报文
     */
    public int code;
    public String message="";

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
