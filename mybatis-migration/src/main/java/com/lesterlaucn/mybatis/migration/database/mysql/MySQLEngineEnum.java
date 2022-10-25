package com.lesterlaucn.mybatis.migration.database.mysql;

/**
 * mysql支持的引擎
 */
public enum MySQLEngineEnum {

    DEFAULT,
    ARCHIVE,
    BLACKHOLE,
    CSV,
    InnoDB,
    MEMORY,
    MRG_MYISAM,
    MyISAM,
    PERFORMANCE_SCHEMA;
}
