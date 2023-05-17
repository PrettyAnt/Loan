package com.prettyant.bean;

public enum Varis {
    progress(0),//审批中
    pass(1),//已完成
    refuse(2);//未通过

    private int status;

    Varis(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
