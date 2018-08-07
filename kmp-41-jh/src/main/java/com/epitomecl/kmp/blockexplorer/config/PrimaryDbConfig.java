package com.epitomecl.kmp.blockexplorer.config;

import org.hibernate.dialect.H2Dialect;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.epitomecl.kmp.dc.primary.repository"}
)
public class PrimaryDbConfig {

    //region dataSource
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties defaultDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource defaultDataSource() {
        return defaultDataSourceProperties().initializeDataSourceBuilder().build();
    }
    //endregion


    //region jpa
    @Bean(name = "entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean customerEntityManagerFactory(EntityManagerFactoryBuilder builder) {
//        Map<String, String> propertiesHashMap = new HashMap<>();
//        propertiesHashMap.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
//        propertiesHashMap.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
//        propertiesHashMap.put("hibernate.dialect", H2Dialect.class.getName());
////        propertiesHashMap.put("hibernate.show_sql", "true");
////        propertiesHashMap.put("hibernate.format_sql", "true");
//        propertiesHashMap.put("hibernate.hbm2ddl.auto", "update");

        return builder
                .dataSource(defaultDataSource())
                .packages("com.epitomecl.kmp.dc.primary.entity", "example.kmp.jh.domain")
//                .properties(propertiesHashMap)
                .persistenceUnit("default")
                .build();
    }

    @Bean(name = "transactionManager")
    @Primary
    public JpaTransactionManager db2TransactionManager(@Qualifier("entityManagerFactory") final EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }
    //endregion
}

