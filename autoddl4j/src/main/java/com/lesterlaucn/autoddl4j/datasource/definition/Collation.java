package com.lesterlaucn.autoddl4j.datasource.definition;

import lombok.Getter;

/**
 * Created by liuyuancheng on 2022/10/26  <br/>
 *
 * @author liuyuancheng
 */
public enum Collation {
    MySQL_COLLATION_UTF8("utf8_general_ci"),
    MySQL_COLLATION_UTF8MB4("utf8mb4_general_ci")
    ;

    /**
     * 名称
     */
    @Getter
    String name;

    Collation(String name) {
        this.name = name;
    }
}
