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
public class JdbcSourceBound {
    private JdbcTemplate jdbcTemplate;
    private DbType dbType;
    private String packageName;

    private JdbcSourceBound(JdbcTemplate jdbcTemplate, DbType dbType, String packageName) {
        this.jdbcTemplate = jdbcTemplate;
        this.dbType = dbType;
        this.packageName = packageName;
    }

    public static JdbcSourceBound create(DbType dbType, String packageName, JdbcTemplate jdbcTemplate) {
        return new JdbcSourceBound(jdbcTemplate, dbType, packageName);
    }
}
