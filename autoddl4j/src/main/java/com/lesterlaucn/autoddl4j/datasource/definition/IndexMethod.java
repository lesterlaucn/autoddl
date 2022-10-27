package com.lesterlaucn.autoddl4j.datasource.definition;

import lombok.Getter;

/**
 * Created by liuyuancheng on 2022/10/25  <br/>
 *
 * @author liuyuancheng
 */
public enum IndexMethod {
    MySQL_BTREE("btree"),
    MySQL_HASH("hash")
    ;

    @Getter
    String name;
    IndexMethod(String name) {
        this.name = name;
    }
}
