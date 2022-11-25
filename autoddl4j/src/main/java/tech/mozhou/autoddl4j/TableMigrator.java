package tech.mozhou.autoddl4j;


import com.google.common.collect.Lists;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import tech.mozhou.autoddl4j.source.JavaSource;
import tech.mozhou.autoddl4j.target.JdbcBound;
import tech.mozhou.autoddl4j.target.ddlparser.DatabaseMeta;
import tech.mozhou.autoddl4j.util.TableDefDiff;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by liuyuancheng on 2022/10/27  <br/>
 *
 * @author liuyuancheng
 */
@Slf4j
public class TableMigrator {

    private JdbcBound jdbcBound;

    private Queue<String> ddlQueue = Lists.newLinkedList();

    /**
     * 包及其数据源绑定
     *
     * @param jdbcBound
     */
    public TableMigrator(@NonNull JdbcBound jdbcBound) {
        this.jdbcBound = jdbcBound;
    }


    /**
     * 生成DDL (依据数据源和entity的绑定关系生成DDL)
     *
     * @return
     */
    public void toDdlList() {
        TableDef tableDefInCode = new JavaSource(jdbcBound.getPackageScan()).getTableDef();
        ddlQueue =  TableDefDiff.create(jdbcBound, tableDefInCode).toDdl();
        log.debug("ddlQueue {}",ddlQueue);
    }

    /**
     * 执行所有迁移
     */
    public void executeMigrations() {
        while (!ddlQueue.isEmpty()){
            final String sql = ddlQueue.poll();
            jdbcBound.getJdbcTemplate().execute(sql);
        }
    }
}
