package tech.mozhou.autoddl4j.target;

import tech.mozhou.autoddl4j.TableMigrator;
import tech.mozhou.autoddl4j.target.ddlparser.DatabaseMeta;
import tech.mozhou.autoddl4j.target.definition.DbType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class DatabaseMetaTest {

    public static final String TEST_ENTITY = "tech.mozhou.autoddl4j.demo.entity";

    private TableMigrator migrationExecutor;

    private DatabaseMeta dataSourceMeta;

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
        migrationExecutor = new TableMigrator(dataSourceBound);
        dataSourceMeta = DatabaseMeta.create(dataSourceBound);
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