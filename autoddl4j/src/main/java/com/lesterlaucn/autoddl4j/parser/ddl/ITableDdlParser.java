package com.lesterlaucn.autoddl4j.parser.ddl;

import com.lesterlaucn.autoddl4j.parser.EntityParserResult;

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
    public EntityParserResult parse();
}
