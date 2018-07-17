//package com.epitomecl.kmp.blockexplorer.config;
//
//import com.epitomecl.kmp.blockexplorer.properties.DatabaseProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
//@Configuration
//
//public abstract class DatabaseConfig {
//
//    @Bean(name="dataSource")
//    public abstract DataSource dataSource();
//
//    protected void configureDataSource(MyDataSource dataSource, DatabaseProperties databaseProperties) {
//        dataSource.setDriverClassName(databaseProperties.getDriverClassName());
//        dataSource.setJdbcUrl(databaseProperties.getUrl());
//        dataSource.setUsername(databaseProperties.getUserName());
//        dataSource.setPassword(databaseProperties.getPassword());
////        dataSource.setMaxActive(databaseProperties.getMaxActive());
////        dataSource.setMaxIdle(databaseProperties.getMaxIdle());
////        dataSource.setMinIdle(databaseProperties.getMinIdle());
////        dataSource.setMaxWait(databaseProperties.getMaxWait());
////        dataSource.setTestOnBorrow(false);
////        dataSource.setTestOnReturn(false);
//    }
//}
