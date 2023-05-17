package com.prettyant.dao;

import com.prettyant.bean.BusinessInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper// 告诉springboot这是一个mybatis的mapper
@Repository// 交由spring容器管理
public interface BusinessInfoDao {
    /**
     * 新增业务信息
     *
     * @param businessInfo
     */
    public void addBusinessInfo(BusinessInfo businessInfo);

    /**
     * 通过编号删除信息
     *
     * @param processInstanceId
     * @return
     */
    public void deleteBusinessInfoById(String processInstanceId);

    /**
     * 更新业务信息
     * @param processInstanceId
     * @param businessStatus
     */
    public void updateBusinessStatusById(String processInstanceId,int businessStatus);

    /**
     * 通过编号查询信息
     *
     * @param processInstanceId
     * @return
     */
    public BusinessInfo queryBusinessInfoById(String processInstanceId);

    /**
     * 通过用户名查询信息
     *
     * @param username
     * @return
     */
    public List<BusinessInfo> queryBusinessInfoByName(String username);


}
