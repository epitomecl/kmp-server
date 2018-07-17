package com.epitomecl.kmp.core.common;

import org.jooq.SQLDialect;

public enum DBTYPE {
    // commercial dbms not supproted by jooq open source license.
    H2DB, MARIADB, MYSQL, PGSQL, /* ORACLE, MSSQL */;

    private final String initSqlPathPrefix;
    private final String db_properties_filename;

    DBTYPE() {
        String dbtypename = name().toLowerCase();
        initSqlPathPrefix = "/config/" + dbtypename + "/init/";
        db_properties_filename = "/db/" + dbtypename + "/db.properties";
    }

    public String getInitSqlPathPrefix() {
        return initSqlPathPrefix;
    }

    public String get_db_properties_filename() {
        return db_properties_filename;
    }

    public SQLDialect getSQLDialect() {
        switch (this) {
            case H2DB:
                return SQLDialect.H2;
            case MARIADB:
                return SQLDialect.MARIADB;
            case MYSQL:
                return SQLDialect.MYSQL;
            case PGSQL:
                return SQLDialect.POSTGRES;
        }
        return SQLDialect.DEFAULT;
    }

}