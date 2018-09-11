package com.epitomecl.kmp.blockexplorer.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
//@EnableTransactionManagement
//@EntityScan(basePackages = "com.my.test.custom.domain")
public class PartnerDbConfig {

    //region dataSource
    @Bean
    @ConfigurationProperties("partner.datasource-one")
    public DataSourceProperties partnerDataSourceOneProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "partnerDataSourceOne")
    @ConfigurationProperties("partner.datasource-one")
    public DataSource partnerDataSourceOne() {
        return partnerDataSourceOneProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    @ConfigurationProperties("partner.datasource-two")
    public DataSourceProperties partnerDataSourceTwoProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "partnerDataSourceTwo")
    @ConfigurationProperties("partner.datasource-two")
    public DataSource partnerDataSourceTwo() {
        return partnerDataSourceTwoProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    @ConfigurationProperties("partner.datasource-three")
    public DataSourceProperties partnerDataSourceThreeProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "partnerDataSourceThree")
    @ConfigurationProperties("partner.datasource-three")
    public DataSource partnerDataSourceThree() {
        return partnerDataSourceThreeProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    @ConfigurationProperties("partner.datasource-four")
    public DataSourceProperties partnerDataSourceFourProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "partnerDataSourceFour")
    @ConfigurationProperties("partner.datasource-four")
    public DataSource partnerDataSourceFour() {
        return partnerDataSourceFourProperties().initializeDataSourceBuilder().build();
    }
    //endregion


    //region jpa
/*
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
*/
    //endregion


    //region mybatis
    @Autowired
    private ApplicationContext applicationContext;

    //region datasource-one
    @Bean(name = "partnerPlatformTransactionManagerOne")
    public PlatformTransactionManager transactionManagerOne() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(partnerDataSourceOne());
        transactionManager.setGlobalRollbackOnParticipationFailure(false);
        return transactionManager;
    }

    @Bean(name = "partnerSqlSessionFactoryOne")
    public SqlSessionFactory sqlSessionFactoryOne(@Qualifier("partnerDataSourceOne") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/mybatis-config.xml"));
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mappers/blockExplorerMapper.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "partnerSqlSessionTemplateOne")
    public SqlSessionTemplate sqlSessionOne(@Qualifier("partnerSqlSessionFactoryOne") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    //endregion

    //region datasource-two
    @Bean(name = "partnerPlatformTransactionManagerTwo")
    public PlatformTransactionManager transactionManagerTwo() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(partnerDataSourceTwo());
        transactionManager.setGlobalRollbackOnParticipationFailure(false);
        return transactionManager;
    }

    @Bean(name = "partnerSqlSessionFactoryTwo")
    public SqlSessionFactory sqlSessionFactoryTwo(@Qualifier("partnerDataSourceTwo") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/mybatis-config.xml"));
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mappers/secretShareMapper.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "partnerSqlSessionTemplateTwo")
    public SqlSessionTemplate sqlSessionTwo(@Qualifier("partnerSqlSessionFactoryTwo") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    //endregion

    //region datasource-three
    @Bean(name = "partnerPlatformTransactionManagerThree")
    public PlatformTransactionManager transactionManagerThree() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(partnerDataSourceThree());
        transactionManager.setGlobalRollbackOnParticipationFailure(false);
        return transactionManager;
    }

    @Bean(name = "partnerSqlSessionFactoryThree")
    public SqlSessionFactory sqlSessionFactoryThree(@Qualifier("partnerDataSourceThree") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/mybatis-config.xml"));
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mappers/secretShareMapper.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "partnerSqlSessionTemplateThree")
    public SqlSessionTemplate sqlSessionThree(@Qualifier("partnerSqlSessionFactoryThree") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "partnerSqlSessionFactoryFour")
    public SqlSessionFactory sqlSessionFactoryFour(@Qualifier("partnerDataSourceFour") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/mybatis-config.xml"));
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mappers/kmpMapper.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "partnerSqlSessionTemplateFour")
    public SqlSessionTemplate sqlSessionFour(@Qualifier("partnerSqlSessionFactoryFour") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    //endregion

    //endregion
}
