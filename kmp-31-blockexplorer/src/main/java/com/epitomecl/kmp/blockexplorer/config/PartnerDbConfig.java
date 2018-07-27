package com.epitomecl.kmp.blockexplorer.config;

import com.epitomecl.kmp.dc.partner.TestPost;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "com.my.test.custom.domain")
public class PartnerDbConfig {

    //region dataSource
    @Bean
    @ConfigurationProperties("partner.datasource")
    public DataSourceProperties partnerDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "partnerDataSource")
    @ConfigurationProperties("partner.datasource")
    public DataSource partnerDataSource() {
        return partnerDataSourceProperties().initializeDataSourceBuilder().build();
    }
    //endregion


    //region jpa
    @Bean(name = "partnerEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean customerEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", org.hibernate.dialect.PostgreSQLDialect.class.getName());

        LocalContainerEntityManagerFactoryBean emf = builder
                .dataSource(partnerDataSource())
                .packages(TestPost.class)
                .persistenceUnit("partner")
                .build();
        emf.setJpaProperties(properties);
        return emf;
    }

    @Bean(name = "partnerTransactionManager")
    public JpaTransactionManager db2TransactionManager(@Qualifier("partnerEntityManagerFactory") final EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }
    //endregion


    //region mybatis
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(partnerDataSource());
        transactionManager.setGlobalRollbackOnParticipationFailure(false);
        return transactionManager;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("partnerDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/mybatis-config.xml"));
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mappers/**/*Mapper.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "sqlSession")
    public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    //endregion

}