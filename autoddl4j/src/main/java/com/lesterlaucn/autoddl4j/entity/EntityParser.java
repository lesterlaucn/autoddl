package com.lesterlaucn.autoddl4j.entity;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lesterlaucn.autoddl4j.TableDef;
import com.lesterlaucn.autoddl4j.entity.common.ClasspathPackageScanner;
import com.lesterlaucn.autoddl4j.entity.common.ColumnParser;
import com.lesterlaucn.autoddl4j.entity.common.TableParser;
import lombok.extern.slf4j.Slf4j;
import org.reflections.util.ConfigurationBuilder;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by liuyuancheng on 2022/10/25  <br/>
 *
 * @author liuyuancheng
 */
@Slf4j
public class EntityParser {

    private static final Map<String, ClasspathPackageScanner> PACKAGE_SCANNER_MAP = Maps.newHashMap();

    /**
     * 获取对应包内的Class
     *
     * @param packageName
     * @return
     */
    public void packageRegister(String packageName) {
        ClasspathPackageScanner packageScanner = null;
        if (!PACKAGE_SCANNER_MAP.containsKey(packageName)) {
            packageScanner = new ClasspathPackageScanner(packageName);
            PACKAGE_SCANNER_MAP.put(packageName, packageScanner);
        }
    }

    private Set<String> getAllClasses() {
        ConfigurationBuilder.build().forPackages();
        Set<String> classesSet = Sets.newConcurrentHashSet();
        for (Map.Entry<String, ClasspathPackageScanner> scannerEntry : PACKAGE_SCANNER_MAP.entrySet()) {
            classesSet.addAll(scannerEntry.getValue().getFullyQualifiedClassNameList());
        }
        return classesSet;
    }

    /**
     * 扫描类对象
     *
     * @return 带有@Table注解的所有类型
     */
    public Set<Class<?>> getTableTypes() {
        final Set<String> allClasses = getAllClasses();
        Set<Class<?>> returning = Sets.newConcurrentHashSet();
        try {
            for (String clazz : allClasses) {
                final Class<?> aClass = Class.forName(clazz);
                final boolean isTable = Objects.nonNull(aClass.getAnnotation(javax.persistence.Table.class)) ||
                        Objects.nonNull(aClass.getAnnotation(jakarta.persistence.Table.class));
                if (isTable) {
                    log.debug("Found new @Table-ed class {}.", clazz);
                    returning.add(aClass);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return returning;
    }

    /**
     * 解析Table类型
     *
     * @param result   TableDef.create()
     * @param entities 需要解析的
     * @return
     */
    public TableDef parserTableEntity(TableDef result, Class<?>... entities) {
        for (Class<?> entity : entities) {
            TableParser.parse(entity, result.getTable(TableParser.readTableName(entity), entity.getCanonicalName()));
            ColumnParser.parse(entity, result.getTable(TableParser.readTableName(entity), entity.getCanonicalName()));
        }
        return result;
    }
}
