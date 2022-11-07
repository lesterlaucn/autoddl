package tech.mozhou.autoddl4j.ddlparser;

import tech.mozhou.autoddl4j.TableDef;

/**
 * Created by liuyuancheng on 2022/10/31  <br/>
 *
 * @author liuyuancheng
 */
public interface ITableDdlParser {
    /**
     * 解析DDL结构
     *
     * @return
     */
    public TableDef parse();
}
