package com.lesterlaucn.autoddl4j.database.definition;

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
}
