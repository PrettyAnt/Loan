package com.prettyant.loan.data.bean;


import java.util.ArrayList;
import java.util.List;

public class BusinessInfosResponse extends Response {
    public List<BusinessInfo> list;

    public List<BusinessInfo> getList() {
        return list==null?new ArrayList<>():list;
    }

    public void setList(List<BusinessInfo> list) {
        this.list = list;
    }
}
