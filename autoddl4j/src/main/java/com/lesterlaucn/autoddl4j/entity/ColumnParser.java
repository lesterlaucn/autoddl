package com.lesterlaucn.autoddl4j.entity;


import com.google.common.base.CaseFormat;
import com.lesterlaucn.autoddl4j.TableDef;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author liuyuancheng
 */
@Data
public class ColumnParser {

    private Class<?> type;

    private List<Field> fields;

    private TableDef.Table table;

    private ColumnParser() {

    }

    private void execute() {
        this.fields = Arrays.asList(type.getDeclaredFields());
        for (int i = 0; i < this.fields.size(); i++) {
            Field field = this.fields.get(i);
            final TableDef.Column columnDef = new TableDef.Column()
                    .setJavaType(field.getType())
                    .setColumnOriginal(field.getName())
                    .setColumnName(parseColumnName(field))
                    .setUnique(parseUnique(field))
                    .setLength(parseLength(field))
                    .setOrdinalPosition(i + 1)
                    .setIsNullable(parseIsNullable(field))
                    .setDefaultValue(parseDefaultValue(field))
                    .setIsPrimaryKey(parseIsPrimaryKey(field))
                    .setDecimalScale(parseDecimalScale(field))
                    .setComment(parseComment(field));
            table.addColumn(columnDef);
        }
    }

    private Boolean parseIsNullable(Field field) {
        Boolean isNullable = null;
        if (Objects.nonNull(field.getAnnotation(javax.persistence.Column.class))) {
            isNullable = field.getAnnotation(javax.persistence.Column.class).nullable();
        }
        if (Objects.isNull(isNullable) && Objects.nonNull(field.getAnnotation(jakarta.persistence.Column.class))) {
            isNullable = field.getAnnotation(jakarta.persistence.Column.class).nullable();
        }
        if (Objects.isNull(isNullable) && Objects.nonNull(field.getAnnotation(NotNull.class))) {
            isNullable = true;
        }
        if (Objects.isNull(isNullable) && Objects.nonNull(field.getAnnotation(jakarta.validation.constraints.NotNull.class))) {
            isNullable = true;
        }
        if (Objects.nonNull(isNullable)) {
            return isNullable;
        }
        return true;
    }

    private String parseColumnName(Field field) {
        String columnName = "";
        if (StringUtils.isEmpty(columnName) && Objects.nonNull(field.getAnnotation(javax.persistence.Column.class))) {
            columnName = field.getAnnotation(javax.persistence.Column.class).name();
        }
        if (StringUtils.isEmpty(columnName) && Objects.nonNull(field.getAnnotation(jakarta.persistence.Column.class))) {
            columnName = field.getAnnotation(jakarta.persistence.Column.class).name();
        }
        if (StringUtils.isNotEmpty(columnName)) {
            return columnName;
        }
        return StringUtils.wrap(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName()), '`');
    }

    private Integer parseDecimalPrecision(Field field) {
        Integer precision = null;
        if (Objects.nonNull(field.getAnnotation(javax.persistence.Column.class))) {
            precision = field.getAnnotation(javax.persistence.Column.class).precision();
        }
        if (Objects.isNull(precision) && Objects.nonNull(field.getAnnotation(jakarta.persistence.Column.class))) {
            precision = field.getAnnotation(jakarta.persistence.Column.class).precision();
        }
        if (Objects.nonNull(precision)) {
            return precision;
        }
        return 0;
    }

    private Integer parseDecimalScale(Field field) {
        Integer scale = null;
        if (Objects.nonNull(field.getAnnotation(javax.persistence.Column.class))) {
            scale = field.getAnnotation(javax.persistence.Column.class).scale();
        }
        if (Objects.isNull(scale) && Objects.nonNull(field.getAnnotation(jakarta.persistence.Column.class))) {
            scale = field.getAnnotation(jakarta.persistence.Column.class).scale();
        }
        if (Objects.nonNull(scale)) {
            return scale;
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
        String defaultValue = null;
        if (Objects.nonNull(field.getAnnotation(Schema.class))) {
            defaultValue = field.getAnnotation(Schema.class).defaultValue();
        }
        if (Objects.isNull(defaultValue) && Objects.nonNull(field.getAnnotation(javax.persistence.Column.class))) {
            defaultValue = field.getAnnotation(javax.persistence.Column.class).columnDefinition();
        }
        if (Objects.isNull(defaultValue) && Objects.nonNull(field.getAnnotation(jakarta.persistence.Column.class))) {
            defaultValue = field.getAnnotation(jakarta.persistence.Column.class).columnDefinition();
        }
        if (Objects.isNull(defaultValue) && field.getType().equals(String.class)) {
            defaultValue = "";
        }
        if (Objects.isNull(defaultValue) && Arrays.asList(LocalDateTime.class, Date.class).contains(field.getType())) {
            defaultValue = "1970-01-01 08:00:00";
        }
        if (Objects.nonNull(defaultValue)) {
            return defaultValue;
        }
        return null;
    }

    private Integer parseLength(Field field) {
        Integer length = null;
        if (Objects.nonNull(field.getAnnotation(javax.persistence.Column.class))) {
            length = field.getAnnotation(javax.persistence.Column.class).length();
        }
        if (Objects.isNull(length) && Objects.nonNull(field.getAnnotation(jakarta.persistence.Column.class))) {
            length = field.getAnnotation(jakarta.persistence.Column.class).length();
        }
        if (Objects.nonNull(field.getAnnotation(javax.persistence.Column.class))) {
            length = field.getAnnotation(javax.persistence.Column.class).precision();
        }
        if (Objects.isNull(length) && Objects.nonNull(field.getAnnotation(jakarta.persistence.Column.class))) {
            length = field.getAnnotation(jakarta.persistence.Column.class).precision();
        }
        if (Objects.isNull(length) && field.getType().equals(String.class)) {
            length = 255;
        }
        if (Objects.nonNull(length)) {
            return length;
        }
        return 11;
    }

    private Boolean parseUnique(Field field) {
        Boolean unique = null;
        if (Objects.nonNull(field.getAnnotation(javax.persistence.Column.class))) {
            unique = field.getAnnotation(javax.persistence.Column.class).unique();
        }
        if (Objects.isNull(unique) && Objects.nonNull(field.getAnnotation(jakarta.persistence.Column.class))) {
            unique = field.getAnnotation(jakarta.persistence.Column.class).unique();
        }
        if (Objects.nonNull(unique)) {
            return unique;
        }
        return false;
    }

    private String parseComment(Field field) {
        String comment = "";
        if (Objects.nonNull(field.getAnnotation(Schema.class))) {
            comment = field.getAnnotation(Schema.class).description();
        }
        if (StringUtils.isEmpty(comment) && Objects.nonNull(field.getAnnotation(ApiModelProperty.class))) {
            comment = field.getAnnotation(ApiModelProperty.class).name();
        }
        if (StringUtils.isEmpty(comment) && Objects.nonNull(field.getAnnotation(javax.persistence.Column.class))) {
            comment = field.getAnnotation(javax.persistence.Column.class).columnDefinition();
        }
        if (StringUtils.isNotEmpty(comment)) {
            return comment;
        }
        return "";
    }

    public static void parse(Class<?> type, TableDef.Table result) {
        final ColumnParser columnParser = new ColumnParser();
        columnParser.setTable(result);
        columnParser.setType(type);
        columnParser.execute();
    }
}
