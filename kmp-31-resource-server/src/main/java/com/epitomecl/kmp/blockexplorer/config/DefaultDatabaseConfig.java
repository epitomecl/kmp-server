//package com.epitomecl.kmp.blockexplorer.config;
//
//import com.epitomecl.kmp.blockexplorer.properties.DatabaseProperties;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableTransactionManagement
//@EnableConfigurationProperties(DatabaseProperties.class)
//public class DefaultDatabaseConfig extends DatabaseConfig {
//    @Autowired
//    private ApplicationContext applicationContext;
//
//    @Autowired
//    private DatabaseProperties databaseProperties;
//
//    @Bean(destroyMethod="close")
//    public MyDataSource dataSource() {
//        MyDataSource dataSource = new MyDataSource();
//        configureDataSource(dataSource, databaseProperties);
//        return dataSource;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
//        transactionManager.setGlobalRollbackOnParticipationFailure(false);
//        return transactionManager;
//    }
//
//    @Bean(name = "sqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws  Exception {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setDataSource(dataSource);
//        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/mybatis-config.xml"));
//        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mappers/**/*Mapper.xml"));
//        return sqlSessionFactoryBean.getObject();
//    }
//
//    @Bean(name="sqlSession")
//    public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//}
