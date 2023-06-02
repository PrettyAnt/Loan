package com.prettyant.loan.data.bean;


/**
 * 用户办理的业务
 */
public class BusinessInfo extends Response {
    private String processInstanceId;//业务编号
    private String username;//用户昵称 - 和 UserInfo 中的username保持统一
    private String businessName;//业务名称/贷款用途
    private String duringTime;//贷款年限
    private float  rate;//贷款利率

    private String amount;//贷款金额
    /**
     * 0 - 受理中
     * 1 - 已完成
     */
    private int    businessStatus;//业务状态

    private String createTime;//创建时间


    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getDuringTime() {
        return duringTime;
    }

    public void setDuringTime(String duringTime) {
        this.duringTime = duringTime;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(int businessStatus) {
        this.businessStatus = businessStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "BusinessInfo{" +
                "processInstanceId='" + processInstanceId + '\'' +
                ", username='" + username + '\'' +
                ", businessName='" + businessName + '\'' +
                ", duringTime='" + duringTime + '\'' +
                ", rate=" + rate +
                ", amount='" + amount + '\'' +
                '}';
    }
}
