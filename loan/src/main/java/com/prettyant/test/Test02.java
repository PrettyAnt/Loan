package com.prettyant.test;

import com.prettyant.bean.BusinessInfo;
import com.prettyant.bean.Varis;
import com.prettyant.dao.BusinessInfoDao;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.junit.Test;
import org.kie.api.KieBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Test02 {
    private ArrayList<String> data = new ArrayList<>();
    private int pageSize = 10;
    private int index = 2;

    @Test
    public void testAdd() {
        for (int i = 0; i < 21; i++) {
            data.add("数据" + i);
        }
        if ((index + 1) * 10 <= data.size()) {
            List<String> strings = data.subList(index, ((index + 1) * pageSize));
            System.out.println("strings1 = " + strings);
        } else {
            if (index * pageSize > data.size()) {
                System.out.println("string2 = " + new ArrayList<>());
                return;
            }
            List<String> strings = data.subList(index * pageSize, data.size());
            System.out.println("strings3 = " + strings);
        }
    }

    @Test
    public void testEnum() {
        System.out.println("name = " + Varis.pass.name() + "\n");
        System.out.println("status = " + Varis.pass.getStatus() + "\n");
        Varis[] values = Varis.values();
        for (Varis varis : values) {
            System.out.println("varis.getStatus() = " + varis.getStatus() + "   ---  " + varis.name());
        }
    }

    /**
     * 放松以下 ^-^
     * 获取1~100内的质数
     */
    @Test
    public void getNum() {
        for (int i = 2; i < 100; i++) {
            if (!canDivide(i)) {
                System.out.println("i = " + i);
            }
        }
    }

    /**
     * 判断i是否为质数
     * @param i
     * @return
     */
    public boolean canDivide(int i) {
        for (int j = 2; j < i + 1; j++) {
            //判断余数是否为0
            if (i % j == 0 && i != j) {
                return true;
            }
        }
        return false;
    }
}
