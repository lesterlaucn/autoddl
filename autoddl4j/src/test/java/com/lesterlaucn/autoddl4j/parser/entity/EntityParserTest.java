package com.lesterlaucn.autoddl4j.parser.entity;

import com.lesterlaucn.autoddl4j.demo.entity.Javax2Swagger2Simple;
import com.lesterlaucn.autoddl4j.parser.EntityParserResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

@DisplayName("实体解析")
@Slf4j
class EntityParserTest {

    private EntityParser entityParser;

    @BeforeEach
    void setup() {
        entityParser = new EntityParser();
        entityParser.packageRegister("com.lesterlaucn.autoddl4j.demo.entity");
    }

    @Test
    void getTableTypes() {
        final Set<Class<?>> tableEntities = entityParser.getTableTypes();
        log.warn("存在@Table注解的类型：{}", tableEntities);
    }

    @Test
    void packageRegister() {
    }

    @Test
    void parserTableEntity() {
        final EntityParserResult parserResult = entityParser.parserTableEntity(
                EntityParserResult.create(),
                Javax2Swagger2Simple.class);
        System.out.println(parserResult);
    }
}