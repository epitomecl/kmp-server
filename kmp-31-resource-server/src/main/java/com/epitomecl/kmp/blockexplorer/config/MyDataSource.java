//package com.epitomecl.kmp.blockexplorer.config;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//
//import java.util.Properties;
//
//public class MyDataSource extends HikariDataSource {
//    public static MyDataSource getDataSource(
//            String db_url,
//            String db_username,
//            String db_password,
//            String name
//    ) {
//        return getDataSource(db_url, db_username, db_password, name, null);
//    }
//
//    //region hikaricp
//    public static MyDataSource getDataSource(
//            String db_url,
//            String db_username,
//            String db_password,
//            String name,
//            Properties connection_properties
//    ) {
//        HikariConfig config = new HikariConfig();
//        config.setJdbcUrl(db_url);
//        config.setUsername(db_username);
//        config.setPassword(db_password);
//        config.setInitializationFailTimeout(-1);
//        config.setPoolName("HikariPool-" + name);
//        if (connection_properties != null) {
//            config.setDataSourceProperties(connection_properties);
//        }
//        return new MyDataSource(config);
//    }
//
//    public MyDataSource(HikariConfig config) {
//        super(config);
//    }
//    //endregion
//
//}
