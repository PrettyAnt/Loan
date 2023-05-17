package com.prettyant.bean;

import com.prettyant.bean.base.Response;
import lombok.Data;

/**
 * 用户办理的业务
 */
@Data
public class BusinessInfo extends Response {
    private String processInstanceId;//业务编号
    private String username;//用户昵称 - 和 UserInfo 中的username保持统一
    private String businessName;//业务名称/贷款用途
    private String duringTime;//贷款年限
    private float rate;//贷款利率

    private String amount;//贷款金额
    /**
     * 0 - 受理中
     * 1 - 已完成
     */
    private int businessStatus;//业务状态
    private String createTime;//创建时间



}
