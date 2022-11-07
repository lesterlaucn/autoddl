package tech.mozhou.autoddl4j.target.definition;

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
