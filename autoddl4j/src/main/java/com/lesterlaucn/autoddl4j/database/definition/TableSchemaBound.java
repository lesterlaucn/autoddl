package com.lesterlaucn.autoddl4j.database.definition;

import lombok.Getter;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by liuyuancheng on 2022/10/27  <br/>
 *
 * @author liuyuancheng
 */
@Getter
public class TableSchemaBound {
    private JdbcTemplate jdbcTemplate;
    private DbType dbType;
    private String packageName;

    private TableSchemaBound(JdbcTemplate jdbcTemplate, DbType dbType, String packageName) {
        this.jdbcTemplate = jdbcTemplate;
        this.dbType = dbType;
        this.packageName = packageName;
    }

    public static TableSchemaBound create(JdbcTemplate jdbcTemplate, DbType dbType, String packageName) {
        return new TableSchemaBound(jdbcTemplate, dbType, packageName);
    }
}
