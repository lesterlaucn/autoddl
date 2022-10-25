package com.lesterlaucn.autoddl4j.database.mysql;

import lombok.Data;

import java.util.Date;

@Data
public class SysMysqlTable {

    /**
     * 表注释
     */
    /**
     * 字符集的后缀
     */
    public static final String TABLE_COLLATION_SUFFIX = "_general_ci";
    /**
     * 字符集
     */
    public static final String TABLE_COLLATION_KEY = "table_collation";
    /**
     * 注释
     */
    public static final String TABLE_COMMENT_KEY = "table_comment";
    /**
     * 引擎
     */
    public static final String TABLE_ENGINE_KEY = "engine";

    private String tableCatalog;
    private String tableSchema;
    private String tableName;
    private String tableType;
    private String engine;
    private Long version;
    private String rowFormat;
    private Long tableRows;
    private Long avgRowLength;
    private Long dataLength;
    private Long maxDataLength;
    private Long indexLength;
    private Long dataFree;
    private Long autoIncrement;
    private Date createTime;
    private Date updateTime;
    private Date checkTime;
    private String tableCollation;
    private Long checksum;
    private String createOptions;
    private String tableComment;


}
