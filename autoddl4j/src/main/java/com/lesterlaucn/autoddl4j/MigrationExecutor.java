package com.lesterlaucn.autoddl4j;


import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Objects;

/**
 * Created by liuyuancheng on 2022/10/27  <br/>
 *
 * @author liuyuancheng
 */
@Slf4j
public class MigrationExecutor {

    private static volatile MigrationExecutor instance;

    private Map<String, DataSourceBound> dataSourceBoundMap = Maps.newHashMap();

    private Map<String, JdbcTemplate> jdbcTemplateMap = Maps.newHashMap();


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
    public MigrationExecutor register(DataSourceBound dataSourceBound) {
        log.info("Registering new dataSource {}", dataSourceBound.getPackageName());
        dataSourceBoundMap.put(dataSourceBound.getPackageName(), dataSourceBound);
        jdbcTemplateMap.put(dataSourceBound.getPackageName(), new JdbcTemplate(dataSourceBound.getDataSource()));
        return this;
    }

    private MigrationExecutor() {
    }

    public synchronized static MigrationExecutor create() {
        if (Objects.isNull(instance)) {
            synchronized (MigrationExecutor.class) {
                if (Objects.isNull(instance)) {
                    instance = new MigrationExecutor();
                }
            }
            return instance;
        }
        return instance;
    }


    public void execute() {

    }
}
