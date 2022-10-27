package com.lesterlaucn.autoddl4j;

import com.lesterlaucn.autoddl4j.datasource.definition.DbType;
import lombok.Getter;

import javax.sql.DataSource;

/**
 * Created by liuyuancheng on 2022/10/27  <br/>
 *
 * @author liuyuancheng
 */
@Getter
public class DataSourceBound {
    private DataSource dataSource;
    private DbType dbType;
    private String packageName;

    private DataSourceBound(DataSource dataSource, DbType dbType, String packageName) {
        this.dataSource = dataSource;
        this.dbType = dbType;
        this.packageName = packageName;
    }

    public static DataSourceBound create(DbType dbType, String packageName, DataSource dataSource) {
        return new DataSourceBound(dataSource, dbType, packageName);
    }
}
