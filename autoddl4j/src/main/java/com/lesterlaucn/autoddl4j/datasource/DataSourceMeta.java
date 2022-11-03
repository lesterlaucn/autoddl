package com.lesterlaucn.autoddl4j.datasource;


import com.lesterlaucn.autoddl4j.datasource.definition.DbType;
import com.lesterlaucn.autoddl4j.datasource.mysql.MySqlTableMeta;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuyuancheng on 2022/10/25  <br/>
 *
 * @author liuyuancheng
 */
@Slf4j
public class DataSourceMeta {

    private DbType dbType;

    private AbstractTableMeta table;

    private DataSourceMeta() {
    }

    public static DataSourceMeta create(DbType dbType, JdbcTemplate jdbcTemplate) {
        final DataSourceMeta dataSourceMeta = new DataSourceMeta();
        dataSourceMeta.dbType = dbType;
        switch (dbType){
            case MySQL:
                dataSourceMeta.table = new MySqlTableMeta(jdbcTemplate);
                break;
            case ClickHouse:
                log.warn("ClickHouse is not supported yet.");
                break;
            default:
                throw new RuntimeException();
        }
        return dataSourceMeta;
    }

    public AbstractTableMeta table(){
        return table;
    }
}
