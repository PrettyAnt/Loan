package com.prettyant.config;

import com.prettyant.service.FlowableService;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
//@EnableWebMvc
public class ProcessEngieneConfig {
    @Bean
    @ConditionalOnMissingBean
    public  StandaloneProcessEngineConfiguration configuration(){
        // 获取 ProcessEngineConfiguration 对象
        StandaloneProcessEngineConfiguration    configuration = new StandaloneProcessEngineConfiguration();
        // 配置 相关的数据库连接信息
        configuration.setJdbcDriver("com.mysql.cj.jdbc.Driver");
        configuration.setJdbcUsername("root");
        configuration.setJdbcPassword("123456");
        // UTC是统一标准世界时间
        configuration.setJdbcUrl("jdbc:mysql://localhost:3306/flowable-learn?serverTimezone=UTC");
        // 如果数据库中的表结构不存在就新建
        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        System.out.println("--------- configuration ---------" );
        return configuration;
    }


}
