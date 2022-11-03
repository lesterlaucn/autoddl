package com.lesterlaucn.autoddl4j.datasource;


import com.lesterlaucn.autoddl4j.datasource.definition.DbType;
import com.lesterlaucn.autoddl4j.datasource.mysql.MySqlTableMeta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by liuyuancheng on 2022/10/25  <br/>
 *
 * @author liuyuancheng
 */
@Slf4j
public class DatabaseMeta {

    private DbType dbType;

    private AbstractTableMeta table;

    private DatabaseMeta() {
    }

    public static DatabaseMeta create(DbType dbType, JdbcTemplate jdbcTemplate) {
        final DatabaseMeta dataSourceMeta = new DatabaseMeta();
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
