package com.prettyant.loan.data.bean;

/**
 * @author chenyu
 * My personal blog  https://prettyant.github.io
 * <p>
 * Created on 4:43 PM  9/05/23
 * PackageName : com.prettyant.loan.model
 * describle :
 */
public class BusinessTypeModel extends Response {
    private String businessName;
    private int businessIcon;

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public int getBusinessIcon() {
        return businessIcon;
    }

    public void setBusinessIcon(int businessIcon) {
        this.businessIcon = businessIcon;
    }
}
