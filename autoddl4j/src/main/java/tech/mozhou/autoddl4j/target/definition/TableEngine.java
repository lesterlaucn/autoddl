package tech.mozhou.autoddl4j.target.definition;

import lombok.Getter;

/**
 * Created by liuyuancheng on 2022/10/25  <br/>
 *
 * @author liuyuancheng
 */
public enum TableEngine {

    /**
     * MySQL
     */
    MySQL_ARCHIVE("ARCHIVE"),
    MySQL_BLACKHOLE("BLACKHOLE"),
    MySQL_CSV("CSV"),
    MySQL_InnoDB("InnoDB"),
    MySQL_MEMORY("MEMORY"),
    MySQL_MRG_MYISAM("MRG_MYISAM"),
    MySQL_MyISAM("MyISAM"),
    MySQL_PERFORMANCE_SCHEMA("PERFORMANCE_SCHEMA"),

    /**
     * ClickHouse
     */
    ClickHouse_MergeTree("MergeTree"),
    ClickHouse_ReplacingMergeTree("ReplacingMergeTree"),
    ClickHouse_SummingMergeTree("SummingMergeTree"),
    ClickHouse_AggregatingMergeTree("AggregatingMergeTree"),
    ClickHouse_CollapsingMergeTree("CollapsingMergeTree"),
    ClickHouse_GraphiteMergeTree("GraphiteMergeTree"),
    ;

    /**
     * 名称
     */
    @Getter
    String name;
    TableEngine(String name) {
        this.name = name;
    }
}
