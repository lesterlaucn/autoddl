package com.lesterlaucn.autoddl4j.entities.parser;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;

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

    public Set<String> getAllClasses() {
        Set<String> classesSet = Sets.newConcurrentHashSet();
        for (Map.Entry<String, ClasspathPackageScanner> scannerEntry : PACKAGE_SCANNER_MAP.entrySet()) {
            classesSet.addAll(scannerEntry.getValue().getFullyQualifiedClassNameList());
        }
        return classesSet;
    }

    /**
     * 获取类对象
     * @return
     */
    public Set<Class<?>> getTableEntities() {
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
}
