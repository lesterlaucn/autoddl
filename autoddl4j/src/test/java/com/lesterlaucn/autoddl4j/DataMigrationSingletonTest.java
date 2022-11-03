package com.lesterlaucn.autoddl4j;

import com.lesterlaucn.autoddl4j.datasource.JdbcBound;
import com.lesterlaucn.autoddl4j.datasource.definition.DbType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

class DataMigrationSingletonTest {

    public static final String TEST_ENTITY = "com.lesterlaucn.autoddl4j.demo.entity";

    private DataMigrationSingleton migrationExecutor;

    @BeforeEach
    void setup() {
        migrationExecutor = DataMigrationSingleton.create();

        JdbcBound dataSourceBound = JdbcBound.builder()
                .username("root")
                .url("jdbc:mysql://192.168.0.36:3306/autoddl4j?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8")
                .password("123456")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .dbType(DbType.MySQL)
                .packageScan(new String[]{TEST_ENTITY})
                .build();
        migrationExecutor.register(dataSourceBound);
    }

    @Test
    void getJdbcTemplate() {
        final JdbcTemplate jdbcTemplate = migrationExecutor.getJdbcTemplate(TEST_ENTITY);
        final List<Map<String, Object>> tables = jdbcTemplate.queryForList("show tables");
        System.out.println(tables);
//        final Map<String, Object> parserTest = jdbcTemplate.queryForMap("show create table " + tables.get(0).get("Tables_in_autoddl4j"));
//        System.out.println(parserTest.get("Create Table"));
    }

    @Test
    void register() {

    }

    @Test
    void execute() {
    }
}