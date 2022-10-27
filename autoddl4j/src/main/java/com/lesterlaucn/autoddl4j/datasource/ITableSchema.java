package com.lesterlaucn.autoddl4j.datasource;

/**
 * Created by liuyuancheng on 2022/10/26  <br/>
 *
 * @author liuyuancheng
 */
public interface ITableSchema {


    /**
     * 表是否存在
     *
     * @param tableName
     * @return
     */
    public Boolean isTableExists(String tableName);

    /**
     * 返回表结构
     *
     * @param tableName 表名称
     * @return
     */
    public String showCreateTable(String tableName);
}
