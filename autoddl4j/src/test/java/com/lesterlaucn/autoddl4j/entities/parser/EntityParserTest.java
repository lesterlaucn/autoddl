package com.lesterlaucn.autoddl4j.entities.parser;

import com.lesterlaucn.autoddl4j.demo.entity.Javax2Swagger2Simple;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
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
        log.warn("存在@Table注解的类型：{}",tableEntities);
    }

    @Test
    void packageRegister() {
    }

    @Test
    void parserTableType() {
        EntityParserResult result = new EntityParserResult();
        final EntityParserResult parserResult = entityParser.parserTableType(Javax2Swagger2Simple.class,result);
        System.out.println(parserResult);
    }
}