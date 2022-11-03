package com.lesterlaucn.autoddl4j.datasource;

import com.lesterlaucn.autoddl4j.DataSourceBound;
import com.lesterlaucn.autoddl4j.MigrationExecutor;
import com.lesterlaucn.autoddl4j.datasource.definition.DbType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Objects;

class DataSourceMetaTest {

    public static final String TEST_ENTITY = "com.lesterlaucn.autoddl4j.demo.entity";

    private MigrationExecutor migrationExecutor;

    private DataSourceMeta dataSourceMeta;

    @BeforeEach
    void setup() {
        migrationExecutor = MigrationExecutor.create();

        DataSourceBound dataSourceBound = DataSourceBound.builder()
                .username("root")
                .url("jdbc:mysql://192.168.0.36:3306/autoddl4j?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8")
                .password("123456")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .dbType(DbType.MySQL)
                .packageScan(new String[]{TEST_ENTITY})
                .build();
        migrationExecutor.register(dataSourceBound);
        final JdbcTemplate jdbcTemplate = migrationExecutor.getJdbcTemplate(TEST_ENTITY);
        Objects.requireNonNull(jdbcTemplate);
        dataSourceMeta = DataSourceMeta.create(DbType.MySQL, jdbcTemplate);
    }

    @Test
    void create() {
        dataSourceMeta.table().showCreateTable("autoddl4j_test1");
    }

    @Test
    void table() {
    }

    @Test
    void tableNames() {
        final List<String> strings = dataSourceMeta.table().showTableNames();
        System.out.println(strings);
    }
}