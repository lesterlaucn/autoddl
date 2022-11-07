package tech.mozhou.autoddl4j.datasource.definition;

import lombok.Getter;

/**
 * Created by liuyuancheng on 2022/10/26  <br/>
 *
 * @author liuyuancheng
 */
public enum CharacterSet {
    MySQL_UTF8("utf8"),
    MySQL_UTF8MB4("utf8mb4"),
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
