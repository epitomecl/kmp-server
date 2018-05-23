package com.epitomecl.kmp.core.common;

public class AppConfInfo {
    //#############################
    //# server
    //#############################
    public static final String CONFIG_server_jdbc_dbms = "server_jdbc_dbms";

    // #############################
    // # DB
    // #############################
    public static final String CONFIG_dbtype = "dbtype";
    // for defdb
    public static final String CONFIG_db_def_hostname = "db_def_hostname";
    public static final String CONFIG_db_def_driverClassName = "db_def_driverClassName";
    public static final String CONFIG_db_def_xaDataSourceClassName = "db_def_xaDataSourceClassName";
    public static final String CONFIG_db_def_url = "db_def_url";
    // for defdb.sys
    public static final String CONFIG_db_def_sys_username = "db_def_sys_username";
    public static final String CONFIG_db_def_sys_password = "db_def_sys_password";
    // for defdb.system
    public static final String CONFIG_db_def_system_url = "db_def_system_url";
    public static final String CONFIG_db_def_system_dbname = "db_def_system_dbname";
    public static final String CONFIG_db_def_system_username = "db_def_system_username";
    public static final String CONFIG_db_def_system_password = "db_def_system_password";
    // for defdb.user
    // ## username should be uppercase in hsqldb
    public static final String CONFIG_db_def_user_url = "db_def_user_url";
    public static final String CONFIG_db_def_user_dbname = "db_def_user_dbname";
    public static final String CONFIG_db_def_user_username = "db_def_user_username";
    public static final String CONFIG_db_def_user_password = "db_def_user_password";
    public static final String CONFIG_db_def_tablespace = "db_def_tablespace";
    public static final String CONFIG_db_def_xaMinPoolSize = "db_def_xaMinPoolSize";
    public static final String CONFIG_db_def_xaMaxPoolSize = "db_def_xaMaxPoolSize";


    // #############################
    // # created properties
    // #############################
    public static final String CONFIG_SERVER_HOME = "EPITOMECL_KMP_HOME";


}
