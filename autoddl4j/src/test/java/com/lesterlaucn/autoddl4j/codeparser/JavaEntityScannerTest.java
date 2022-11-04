package com.lesterlaucn.autoddl4j.codeparser;

import com.lesterlaucn.autoddl4j.TableDef;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("实体解析")
@Slf4j
class JavaEntityScannerTest {

    private JavaEntityScanner entityParser;

    @BeforeEach
    void setup() {
        entityParser = new JavaEntityScanner("com.lesterlaucn.autoddl4j.demo.entity");
    }

    @Test
    void packageRegister() {
    }

    @Test
    void addPackage() {
    }

    @Test
    void getTableDef() {
        final TableDef tableDef = entityParser.getTableDef();
        System.out.println(tableDef);
    }
}