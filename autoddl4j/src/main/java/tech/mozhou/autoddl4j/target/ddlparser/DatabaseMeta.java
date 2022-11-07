package tech.mozhou.autoddl4j.target.ddlparser;

import lombok.extern.slf4j.Slf4j;
import tech.mozhou.autoddl4j.TableDef;
import tech.mozhou.autoddl4j.target.JdbcBound;
import tech.mozhou.autoddl4j.exception.Autoddl4jParserException;
import tech.mozhou.autoddl4j.target.ddlparser.mysql.MySqlTableMeta;

/**
 * Created by liuyuancheng on 2022/10/25  <br/>
 *
 * @author liuyuancheng
 */
@Slf4j
public class DatabaseMeta {

    private AbstractTableMeta table;

    private DatabaseMeta() {
    }

    public static DatabaseMeta create(JdbcBound jdbcBound) {
        final DatabaseMeta dataSourceMeta = new DatabaseMeta();
        switch (jdbcBound.getDbType()) {
            case MySQL:
                dataSourceMeta.table = new MySqlTableMeta(jdbcBound.getJdbcTemplate());
                break;
            case ClickHouse:
                log.warn("ClickHouse is not supported yet.");
                break;
            default:
                throw new Autoddl4jParserException();
        }
        return dataSourceMeta;
    }

    public AbstractTableMeta table() {
        return table;
    }

    public TableDef toTableDef() {
        return TableDef.create();
    }
}
