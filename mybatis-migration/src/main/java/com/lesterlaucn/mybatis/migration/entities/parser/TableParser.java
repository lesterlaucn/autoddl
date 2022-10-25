package com.lesterlaucn.mybatis.migration.entities.parser;

import com.lesterlaucn.mybatis.migration.EntityParserException;

import java.lang.reflect.Array;
import java.util.Objects;

/**
 * @author liuyuancheng
 */
public class TableParser {

    public TableParser(Class<?> entityClazz) {
        final boolean isTable = Objects.nonNull(entityClazz.getAnnotation(javax.persistence.Table.class)) ||
                Objects.nonNull(entityClazz.getAnnotation(jakarta.persistence.Table.class));
        if (!isTable) {
            throw new EntityParserException();
        }
    }

    public static Object[] addAll(Object[] array1, Object[] array2) {
        if (array1 == null) {
            return array2.clone();
        } else if (array2 == null) {
            return array1.clone();
        }
        Object[] joinedArray = (Object[]) Array.newInstance(array1.getClass().getComponentType(),
                array1.length + array2.length);
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }
}
