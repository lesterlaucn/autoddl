package com.lesterlaucn.autoddl4j;

import com.lesterlaucn.autoddl4j.datasource.definition.DbType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;
import java.util.Map;

class MigrationExecutorTest {

    public static final String TEST_ENTITY = "com.lesterlaucn.autoddl4j.demo.entity";

    private MigrationExecutor migrationExecutor;

    @BeforeEach
    void setup() {
        migrationExecutor = MigrationExecutor.create();
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername("root");
        dataSource.setUrl("jdbc:mysql://192.168.0.36:3306/autoddl4j?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8");
        dataSource.setPassword("123456");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        DataSourceBound dataSourceBound = DataSourceBound.create(DbType.MySQL, TEST_ENTITY, dataSource);
        migrationExecutor.register(dataSourceBound);
    }

    @Test
    void getJdbcTemplate() {
        final JdbcTemplate jdbcTemplate = migrationExecutor.getJdbcTemplate(TEST_ENTITY);
        final List<Map<String, Object>> tables = jdbcTemplate.queryForList("show tables");
        System.out.println(tables);
        final List<Map<String, Object>> parserTest = jdbcTemplate.queryForList("show create table autoddl4j_parser_test");
        System.out.println(parserTest);
    }

    @Test
    void register() {

    }

    @Test
    void execute() {
    }
}