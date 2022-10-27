package com.lesterlaucn.autoddl4j.datasource.mysql;

import com.lesterlaucn.autoddl4j.datasource.definition.ITableSchema;

/**
 * Created by liuyuancheng on 2022/10/26  <br/>
 *
 * @author liuyuancheng
 */
public class MySqlTableSchema implements ITableSchema {

    @Override
    public Boolean isTableExists(String tableName) {
        return null;
    }

    /**
     * show create table table_name
     * @return
     */
    @Override
    public String showCreateTable(String tableName){
        return null;
    }
}
