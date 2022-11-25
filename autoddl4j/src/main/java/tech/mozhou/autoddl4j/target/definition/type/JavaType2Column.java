package tech.mozhou.autoddl4j.target.definition.type;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuyuancheng on 2022/10/31  <br/>
 * Java类型与字段类型的映射
 *
 * @author liuyuancheng
 */
public enum JavaType2Column {
    MySQL_int(Integer.class, "int", 11, 2),
    MySQL_bigint(Long.class, "bigint", 20, 2),
    MySQL_datetime(LocalDateTime.class, "datetime", 11, 2),
    MySQL_date(LocalDate.class, "date", 11, 2),
    MySQL_double(Double.class, "double", 8, 2),
    MySQL_float(Double.class, "float", 8, 2),
    MySQL_text(String.class, "text", 65535, 2),
    MySQL_tinyint(Short.class, "tinyint", 4, 2),
    MySQL_bool(Boolean.class, "bool", 1, 2),
    MySQL_decimal(String.class, "decimal", 2, 2),
    MySQL_varchar(String.class, "varchar", 255, 2);

    private static Map<Class<?>, JavaType2Column> javaType2Column = new HashMap<Class<?>, JavaType2Column>() {{
        put(Integer.class, MySQL_int);
        put(Long.class, MySQL_bigint);
        put(LocalDateTime.class, MySQL_datetime);
        put(Date.class, MySQL_datetime);
        put(LocalDate.class, MySQL_date);
        put(Double.class, MySQL_double);
        put(String.class, MySQL_text);
        put(Boolean.class, MySQL_tinyint);
        put(BigDecimal.class, MySQL_varchar);
    }};

    public static JavaType2Column getType(Class<?> clazz) {
        return javaType2Column.get(clazz);
    }

    @Getter
    Class<?> javaType;

    @Getter
    String columnType;

    @Getter
    Integer defaultLength;

    @Getter
    Integer defaultScale;

    JavaType2Column(Class<?> javaType, String columnType, Integer defaultLength, Integer defaultScale) {
        this.javaType = javaType;
        this.columnType = columnType;
        this.defaultLength = defaultLength;
        this.defaultScale = defaultScale;
    }

    public String toDefinition(){
        return columnType+'('+defaultLength+')';
    }
}
