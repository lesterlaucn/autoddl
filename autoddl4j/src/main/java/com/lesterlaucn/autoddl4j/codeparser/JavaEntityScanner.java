package com.lesterlaucn.autoddl4j.codeparser;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lesterlaucn.autoddl4j.TableDef;
import com.lesterlaucn.autoddl4j.codeparser.java.ClasspathPackageScanner;
import com.lesterlaucn.autoddl4j.codeparser.java.ColumnParser;
import com.lesterlaucn.autoddl4j.codeparser.java.TableParser;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reflections.util.ConfigurationBuilder;

import java.util.*;

/**
 * Created by liuyuancheng on 2022/10/25  <br/>
 *
 * @author liuyuancheng
 */
@Slf4j
@NoArgsConstructor
public class JavaEntityScanner {

    private final Map<String, ClasspathPackageScanner> PACKAGE_SCANNER_MAP = Maps.newHashMap();

    private void init(List<String> packageScans) {
        ClasspathPackageScanner packageScanner = null;
        for (String packageScan : packageScans) {
            if (!PACKAGE_SCANNER_MAP.containsKey(packageScan)) {
                packageScanner = new ClasspathPackageScanner(packageScan);
                PACKAGE_SCANNER_MAP.put(packageScan, packageScanner);
            }
        }
    }

    public JavaEntityScanner(List<String> packageScans) {
        init(packageScans);
    }

    public JavaEntityScanner(Set<String> packageScans) {
        init(Lists.newArrayList(packageScans));
    }

    /**
     * 获取对应包内的Class
     *
     * @param packageScans
     * @return
     */
    public JavaEntityScanner(String... packageScans) {
        init(Arrays.asList(packageScans));
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
    private List<Class<?>> getEntityTypes() {
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
        return Lists.newArrayList(returning);
    }

    /**
     * 解析Table类型
     *
     * @param result   TableDef.create()
     * @param entities 需要解析的
     * @return
     */
    private TableDef parserTableEntity(TableDef result, List<Class<?>> entities) {
        for (Class<?> entity : entities) {
            TableParser.parse(entity, result.getTable(TableParser.readTableName(entity), entity.getCanonicalName()));
            ColumnParser.parse(entity, result.getTable(TableParser.readTableName(entity), entity.getCanonicalName()));
        }
        return result;
    }

    /**
     * 读取代码中Entity的定义
     *
     * @return
     */
    public TableDef getTableDef() {
        TableDef tableDef = TableDef.create();
        final List<Class<?>> entityTypes = getEntityTypes();
        parserTableEntity(tableDef, entityTypes);
        return tableDef;
    }
}
