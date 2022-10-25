package com.lesterlaucn.mybatis.migration.entities.parser;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@DisplayName("实体解析")
@Slf4j
class EntityParserTest {

    private EntityParser entityParser;

    @BeforeEach
    void setup() {
        entityParser = new EntityParser();
        entityParser.packageRegister("com.lesterlaucn.mybatis.migration.demo.entity");
    }

    @Test
    void getAllClasses() {
        final Set<String> entities = entityParser.getAllClasses();
        log.warn("{}", entities);
        Assertions.assertTrue(entities.size() >= 2);
    }

    @Test
    void getTableEntities() {
        final Set<Class<?>> tableEntities = entityParser.getTableEntities();
        System.out.println(tableEntities);
    }
}