package com.lesterlaucn.autoddl4j.entities.parser.util;

import com.google.common.base.CaseFormat;
import com.lesterlaucn.autoddl4j.annotations.TableExtend;
import com.lesterlaucn.autoddl4j.entities.parser.EntityParserResult;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author liuyuancheng
 */
@Data
public class TableParser {

    private Class<?> type;

    private EntityParserResult.Table table;

    private TableParser() {

    }

    public TableParser(Class<?> entityClazz) {

    }

    public static void parse(Class<?> type, EntityParserResult.Table table) {
        final TableParser tableParser = new TableParser();
        tableParser.setTable(table);
        tableParser.setType(type);
        tableParser.execute();
    }

    private void execute() {
        this.table.setTableName(parseTableName(this.type))
                .setTableNameOriginal(this.type.getSimpleName())
                .setComment(parseComment(this.type))
        ;
    }

    private String parseComment(Class<?> type) {
        if (Objects.nonNull(type.getAnnotation(TableExtend.class))) {
            return type.getAnnotation(TableExtend.class).comment();
        }
        return null;
    }

    private String parseTableName(Class<?> type) {
        String tableName = "";
        if (StringUtils.isEmpty(tableName) && Objects.nonNull(type.getAnnotation(javax.persistence.Table.class))) {
            tableName = type.getAnnotation(javax.persistence.Table.class).name();
        }
        if (StringUtils.isEmpty(tableName) && Objects.nonNull(type.getAnnotation(jakarta.persistence.Table.class))) {
            tableName = type.getAnnotation(jakarta.persistence.Table.class).name();
        }
        if (StringUtils.isNotEmpty(tableName)){
            return tableName;
        }
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, type.getSimpleName());
    }
}
