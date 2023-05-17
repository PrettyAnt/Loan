package com.prettyant.bean;

import com.prettyant.bean.base.Response;
import lombok.Data;

import java.util.List;

@Data
public class BusinessInfosResponse extends Response {
    public List<BusinessInfo> list;
}
