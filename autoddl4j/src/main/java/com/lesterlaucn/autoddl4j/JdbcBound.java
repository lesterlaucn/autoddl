package com.lesterlaucn.autoddl4j;

import com.lesterlaucn.autoddl4j.datasource.definition.DbType;
import lombok.Getter;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by liuyuancheng on 2022/10/27  <br/>
 *
 * @author liuyuancheng
 */
@Getter
public class JdbcBound {
    private JdbcTemplate jdbcTemplate;
    private DbType dbType;
    private String packageName;

    private JdbcBound(JdbcTemplate jdbcTemplate, DbType dbType, String packageName) {
        this.jdbcTemplate = jdbcTemplate;
        this.dbType = dbType;
        this.packageName = packageName;
    }

    public static JdbcBound create(DbType dbType, String packageName, JdbcTemplate jdbcTemplate) {
        return new JdbcBound(jdbcTemplate, dbType, packageName);
    }
}
