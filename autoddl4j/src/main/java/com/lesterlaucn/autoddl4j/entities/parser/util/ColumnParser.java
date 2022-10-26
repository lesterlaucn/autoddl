package com.lesterlaucn.autoddl4j.entities.parser.util;


import com.google.common.base.CaseFormat;
import com.lesterlaucn.autoddl4j.entities.parser.EntityParserResult;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Set;

/**
 * @author liuyuancheng
 */
@Data
public class ColumnParser {

    private Class<?> type;

    private Set<Field> fields;

    private EntityParserResult.Table table;

    private ColumnParser() {

    }

    private void execute() {
        this.fields = ReflectionUtils.getFields(type);
        for (Field field : this.fields) {
            final EntityParserResult.Column columnDef = EntityParserResult.Column.builder()
                    .javaType(field.getType())
                    .columnNameInLowerCamel(field.getName())
                    .columnNameInSnake(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName()))
                    .unique(parseUnique(field))
                    .length(parseLength(field))
                    .defaultValue(parseDefaultValue(field))
                    .isPrimaryKey(parseIsPrimaryKey(field))
                    .decimalScale(parseDecimalScale(field))
                    .decimalPrecision(parseDecimalPrecision(field))
                    .comment(parseComment(field))
                    .build();
            table.addColumn(columnDef);
        }
    }

    private Integer parseDecimalPrecision(Field field) {
        if (Objects.nonNull(field.getAnnotation(javax.persistence.Column.class))) {
            return field.getAnnotation(javax.persistence.Column.class).precision();
        }
        if (Objects.nonNull(field.getAnnotation(jakarta.persistence.Column.class))) {
            return field.getAnnotation(jakarta.persistence.Column.class).precision();
        }
        return 0;
    }

    private Integer parseDecimalScale(Field field) {
        if (Objects.nonNull(field.getAnnotation(javax.persistence.Column.class))) {
            return field.getAnnotation(javax.persistence.Column.class).scale();
        }
        if (Objects.nonNull(field.getAnnotation(jakarta.persistence.Column.class))) {
            return field.getAnnotation(jakarta.persistence.Column.class).scale();
        }
        return 0;
    }

    private Boolean parseIsPrimaryKey(Field field) {
        if (Objects.nonNull(field.getAnnotation(javax.persistence.Id.class)) ||
                Objects.nonNull(field.getAnnotation(jakarta.persistence.Id.class))) {
            return true;
        }
        return false;
    }

    private String parseDefaultValue(Field field) {
        if (Objects.nonNull(field.getAnnotation(Schema.class))) {
            return field.getAnnotation(Schema.class).defaultValue();
        }
        if (Objects.nonNull(field.getAnnotation(javax.persistence.Column.class))) {
            return field.getAnnotation(javax.persistence.Column.class).columnDefinition();
        }
        return null;
    }

    private Integer parseLength(Field field) {
        if (Objects.nonNull(field.getAnnotation(javax.persistence.Column.class))) {
            return field.getAnnotation(javax.persistence.Column.class).length();
        }
        return 255;
    }

    private Boolean parseUnique(Field field) {
        if (Objects.nonNull(field.getAnnotation(javax.persistence.Column.class))) {
            return field.getAnnotation(javax.persistence.Column.class).unique();
        }
        return false;
    }

    private String parseComment(Field field) {
        if (Objects.nonNull(field.getAnnotation(Schema.class))) {
            return field.getAnnotation(Schema.class).description();
        }
        if (Objects.nonNull(field.getAnnotation(ApiModelProperty.class))) {
            return field.getAnnotation(ApiModelProperty.class).name();
        }
        if (Objects.nonNull(field.getAnnotation(javax.persistence.Column.class))) {
            return field.getAnnotation(javax.persistence.Column.class).columnDefinition();
        }
        return "";
    }

    public static void parse(Class<?> type, EntityParserResult.Table result) {
        final ColumnParser columnParser = new ColumnParser();
        columnParser.setTable(result);
        columnParser.setType(type);
        columnParser.execute();
    }
}
