package com.lesterlaucn.autoddl4j;

import com.lesterlaucn.autoddl4j.datasource.JdbcBound;
import com.lesterlaucn.autoddl4j.datasource.definition.DbType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class TableMigratorTest {

    public static final String TEST_ENTITY = "com.lesterlaucn.autoddl4j.demo.entity";

    private TableMigrator tableMigrator;

    @BeforeEach
    void setup() {
        JdbcBound dataSourceBound = JdbcBound.builder()
                .username("root")
                .url("jdbc:mysql://192.168.0.36:3306/autoddl4j?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8")
                .password("123456")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .dbType(DbType.MySQL)
                .packageScan(new String[]{TEST_ENTITY})
                .build();
        tableMigrator = new TableMigrator(dataSourceBound);
    }


    @Test
    void toDdlList() {
        final List<String> strings = tableMigrator.toDdlList();
        System.out.println(strings);
    }

    @Test
    void register() {
    }

    @Test
    void create() {
    }

}