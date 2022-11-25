package tech.mozhou.autoddl4j;

import lombok.extern.slf4j.Slf4j;
import tech.mozhou.autoddl4j.target.JdbcBound;
import tech.mozhou.autoddl4j.target.definition.type.DbType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
class TableMigratorTest {

    public static final String TEST_ENTITY = "tech.mozhou.autoddl4j.demo.entity";

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
        log.debug("toDdlList {}",strings);
    }

    @Test
    void register() {
    }

    @Test
    void create() {
    }

}