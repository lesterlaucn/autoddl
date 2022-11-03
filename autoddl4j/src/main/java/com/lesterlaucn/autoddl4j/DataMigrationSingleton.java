package com.lesterlaucn.autoddl4j;


import com.google.common.collect.Maps;
import com.lesterlaucn.autoddl4j.datasource.JdbcBound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;
import java.util.Objects;

/**
 * Created by liuyuancheng on 2022/10/27  <br/>
 *
 * @author liuyuancheng
 */
@Slf4j
public class DataMigrationSingleton {

    private static volatile DataMigrationSingleton instance;

    private final Map<String, JdbcTemplate> jdbcTemplateMap = Maps.newHashMap();


    /**
     * PackName与JdbcTemplate绑定
     *
     * @param packageName
     * @return
     */
    public JdbcTemplate getJdbcTemplate(String packageName) {
        return jdbcTemplateMap.get(packageName);
    }

    /**
     * 包及其数据源绑定
     *
     * @param dataSourceBound
     */
    public DataMigrationSingleton register(JdbcBound dataSourceBound) {
        Objects.requireNonNull(dataSourceBound, "数据源必须配置");
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourceBound.getDataSource());
        for (String packageScan : dataSourceBound.getPackageScan()) {
            log.info("Registering new jdbcTemplate for entity directory {}", packageScan);
            jdbcTemplateMap.put(packageScan.trim(), jdbcTemplate);
        }
        return this;
    }


    private DataMigrationSingleton() {
    }

    public synchronized static DataMigrationSingleton create() {
        if (Objects.isNull(instance)) {
            synchronized (DataMigrationSingleton.class) {
                if (Objects.isNull(instance)) {
                    instance = new DataMigrationSingleton();
                }
            }
            return instance;
        }
        return instance;
    }


    public void execute() {

    }
}