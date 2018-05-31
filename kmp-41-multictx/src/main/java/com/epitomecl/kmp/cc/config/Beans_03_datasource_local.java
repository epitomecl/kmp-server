package com.epitomecl.kmp.cc.config;

import org.hibernate.dialect.HSQLDialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static com.epitomecl.kmp.core.common.AppConfInfo.*;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.epitomecl.kmp.dc.repository",
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager")
public class Beans_03_datasource_local {

    @Value("${" + CONFIG_db_def_driverClassName + "}")
    private String db_def_driverClassName;

    @Value("${" + CONFIG_db_def_url + "}")
    private String db_def_url;

    @Value("${" + CONFIG_db_def_sys_username + "}")
    private String db_def_sys_username;

    @Value("${" + CONFIG_db_def_sys_password + "}")
    private String db_def_sys_password;

    @Value("${" + CONFIG_db_def_system_username + "}")
    private String db_def_system_username;

    @Value("${" + CONFIG_db_def_system_password + "}")
    private String db_def_system_password;

    @Value("${" + CONFIG_db_def_user_username + "}")
    private String db_def_user_username;

    @Value("${" + CONFIG_db_def_user_password + "}")
    private String db_def_user_password;

    @Bean
    @Primary
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create()
                .driverClassName(db_def_driverClassName)
                .url(db_def_url)
                .username(db_def_sys_username)
                .password(db_def_sys_password)
                .build();
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
        Map<String, String> propertiesHashMap = new HashMap<>();
        propertiesHashMap.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
        propertiesHashMap.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
        propertiesHashMap.put("hibernate.dialect", HSQLDialect.class.getName());
//        propertiesHashMap.put("hibernate.show_sql", "true");
//        propertiesHashMap.put("hibernate.format_sql", "true");
        propertiesHashMap.put("hibernate.hbm2ddl.auto", "update");

        return builder.dataSource(primaryDataSource())
                .packages("com.epitomecl.kmp.dc.entity")
                .properties(propertiesHashMap)
                .build();
    }

    @Primary
    @Bean(name = "transactionManager")
    PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactory(builder).getObject());
    }

}
