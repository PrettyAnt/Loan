package com.prettyant;

import com.prettyant.bean.BusinessInfo;
import com.prettyant.entity.UserInfo;
import com.prettyant.dao.BusinessInfoDao;
import com.prettyant.service.FlowableService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class LoanApplicationTests {
    @Autowired
    private FlowableService flowableService;
    @Resource
    public RedisTemplate<String, UserInfo> redisTemplate;
    @Test
    void contextLoads() {
//        System.out.println("test : "+flowableService.result());
//		testAdd();
		testDelete();
//        testUpdate();
//		queryById();
//        queryByName();
//        test02("chenyu");
    }

    private void queryByName() {
        System.out.println("查询结果:" + businessInfoDao.queryBusinessInfoByName("ChenYu"));
    }

    private void testDelete() {
        businessInfoDao.deleteBusinessInfoById("56502");
    }

    private void testUpdate() {
        businessInfoDao.updateBusinessStatusById("477520",0);
    }

    private void queryById() {
        System.out.println("查询结果:" + businessInfoDao.queryBusinessInfoById("00001"));
    }

    @Autowired
    private BusinessInfoDao businessInfoDao;
    public String test02(String key) {
        UserInfo andDelete = redisTemplate.opsForValue().getAndDelete(key);
        return andDelete.toString();
    }
    public void testAdd() {
        BusinessInfo businessInfo = new BusinessInfo();
        businessInfo.setProcessInstanceId("00002");
        businessInfo.setRate(0.05f);
        businessInfo.setBusinessName("车贷");
        businessInfo.setAmount("20000");
        businessInfo.setUsername("ChenYu");
        businessInfoDao.addBusinessInfo(businessInfo);
        System.out.println("insert success");
    }
}
