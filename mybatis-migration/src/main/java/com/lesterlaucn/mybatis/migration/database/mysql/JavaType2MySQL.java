package com.lesterlaucn.mybatis.migration.database.mysql;

import java.util.HashMap;
import java.util.Map;

public class JavaType2MySQL {
    public static Map<String, MySQLTypeEnum> javaToMysqlTypeMap = new HashMap<String, MySQLTypeEnum>();
    static {
        javaToMysqlTypeMap.put("class java.lang.String", MySQLTypeEnum.VARCHAR);
        javaToMysqlTypeMap.put("class java.lang.Long", MySQLTypeEnum.BIGINT);
        javaToMysqlTypeMap.put("class java.lang.Integer", MySQLTypeEnum.INT);
        javaToMysqlTypeMap.put("class java.lang.Boolean", MySQLTypeEnum.BIT);
        javaToMysqlTypeMap.put("class java.math.BigInteger", MySQLTypeEnum.BIGINT);
        javaToMysqlTypeMap.put("class java.lang.Float", MySQLTypeEnum.FLOAT);
        javaToMysqlTypeMap.put("class java.lang.Double", MySQLTypeEnum.DOUBLE);
        javaToMysqlTypeMap.put("class java.lang.Short", MySQLTypeEnum.SMALLINT);
        javaToMysqlTypeMap.put("class java.math.BigDecimal", MySQLTypeEnum.DECIMAL);
        javaToMysqlTypeMap.put("class java.sql.Date", MySQLTypeEnum.DATE);
        javaToMysqlTypeMap.put("class java.util.Date", MySQLTypeEnum.DATE);
        javaToMysqlTypeMap.put("class java.sql.Timestamp", MySQLTypeEnum.DATETIME);
        javaToMysqlTypeMap.put("class java.sql.Time", MySQLTypeEnum.TIME);
        javaToMysqlTypeMap.put("class java.time.LocalDateTime", MySQLTypeEnum.DATETIME);
        javaToMysqlTypeMap.put("class java.time.LocalDate", MySQLTypeEnum.DATE);
        javaToMysqlTypeMap.put("class java.time.LocalTime", MySQLTypeEnum.TIME);
        javaToMysqlTypeMap.put("long", MySQLTypeEnum.BIGINT);
        javaToMysqlTypeMap.put("int", MySQLTypeEnum.INT);
        javaToMysqlTypeMap.put("boolean", MySQLTypeEnum.BIT);
        javaToMysqlTypeMap.put("float", MySQLTypeEnum.FLOAT);
        javaToMysqlTypeMap.put("double", MySQLTypeEnum.DOUBLE);
        javaToMysqlTypeMap.put("short", MySQLTypeEnum.SMALLINT);
        javaToMysqlTypeMap.put("char", MySQLTypeEnum.VARCHAR);
    }
}
