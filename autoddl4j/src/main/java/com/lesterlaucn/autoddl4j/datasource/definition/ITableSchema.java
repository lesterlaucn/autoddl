package com.lesterlaucn.autoddl4j.datasource.definition;

import com.google.common.collect.Maps;
import com.lesterlaucn.autoddl4j.JdbcSourceBound;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

/**
 * Created by liuyuancheng on 2022/10/26  <br/>
 *
 * @author liuyuancheng
 */
public interface ITableSchema {

    Map<String, JdbcSourceBound> jdbcTemplateMap = Maps.newHashMap();


    /**
     * PackName与JdbcTemplate绑定
     *
     * @param packageName
     * @return
     */
    public default JdbcTemplate getJdbcTemplate(String packageName) {
        return jdbcTemplateMap.get(packageName).getJdbcTemplate();
    }

    /**
     * 包及其数据源绑定
     *
     * @param packageName
     * @param tableSchemaBound
     */
    public default void register(String packageName, JdbcSourceBound tableSchemaBound) {
        jdbcTemplateMap.put(packageName, tableSchemaBound);
    }

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
