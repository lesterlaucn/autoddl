package com.lesterlaucn.autoddl4j.database.definition;

import lombok.Getter;

/**
 * Created by liuyuancheng on 2022/10/26  <br/>
 *
 * @author liuyuancheng
 */
public enum CharacterSet {
    MySQL_UTF8("utf8"),
    MySQL_COLLATION_UTF8("utf8_general_ci"),
    MySQL_UTF8MB4("utf8mb4"),
    MySQL_COLLATION_UTF8MB4("utf8mb4_general_ci")
    ;

    /**
     * 名称
     */
    @Getter
    String name;

    CharacterSet(String name) {
        this.name = name;
    }
}
