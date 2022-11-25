package tech.mozhou.autoddl4j.util;

import com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import tech.mozhou.autoddl4j.TableDef;
import tech.mozhou.autoddl4j.target.JdbcBound;
import tech.mozhou.autoddl4j.target.ddlparser.AbstractTableMeta;
import tech.mozhou.autoddl4j.target.ddlparser.mysql.MySqlTableMeta;

import java.util.Queue;

/**
 * Created by liuyuancheng on 2022/11/4  <br/>
 *
 * @author liuyuancheng
 */
@Slf4j
public class TableDefDiff {
    private AbstractTableMeta tableMeta = null;
    private TableDef tableDefInCode = null;

    private Queue<String> ddlList = Lists.newLinkedList();

    private TableDefDiff(@NonNull JdbcBound jdbcBound, @NonNull TableDef tableDefInCode) {
        this.tableMeta = new MySqlTableMeta(jdbcBound.getJdbcTemplate());
        this.tableDefInCode = tableDefInCode;
    }

    public static TableDefDiff create(@NonNull JdbcBound jdbcBound, @NonNull TableDef tableDefInCode) {
        return new TableDefDiff(jdbcBound, tableDefInCode);
    }

    public Queue<String> toDdl() {
        for (String tableName : tableDefInCode.getTableNames()) {
            log.debug("Found java code defined table: {}", tableName);
            if (!tableMeta.isTableExists(tableName)) {
                // 代码中定义的表在数据库中不存在
                ddlList.offer(tableDefInCode.getTable(tableName).toCreateDdl());
            }
        }
        return ddlList;
    }
}
