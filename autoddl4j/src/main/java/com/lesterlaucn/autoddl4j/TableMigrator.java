package com.lesterlaucn.autoddl4j;


import com.lesterlaucn.autoddl4j.codparser.EntityScanner;
import com.lesterlaucn.autoddl4j.datasource.DatabaseMeta;
import com.lesterlaucn.autoddl4j.datasource.JdbcBound;
import com.lesterlaucn.autoddl4j.util.TableDefDiff;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * Created by liuyuancheng on 2022/10/27  <br/>
 *
 * @author liuyuancheng
 */
@Slf4j
public class TableMigrator {

    private JdbcBound jdbcBound;

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
    public List<String> toDdlList() {
        TableDef tableDefInDataBase = DatabaseMeta.create(jdbcBound).toTableDef();
        TableDef tableDefInCode = new EntityScanner(jdbcBound.getPackageScan()).getTableDef();
        return new TableDefDiff(tableDefInDataBase, tableDefInCode).toDdl();
    }

    public void executeDdl() {
        final List<String> ddlList = toDdlList();
        if (CollectionUtils.isEmpty(ddlList)) {
            log.info("[autoddl4j] No ddl to execute.");
            return;
        }
        final JdbcTemplate jdbcTemplate = jdbcBound.getJdbcTemplate();
        for (String ddl : ddlList) {
            log.info("[autoddl4j] Executing ddl {}", ddl);
            jdbcTemplate.execute(ddl);
        }
    }
}
