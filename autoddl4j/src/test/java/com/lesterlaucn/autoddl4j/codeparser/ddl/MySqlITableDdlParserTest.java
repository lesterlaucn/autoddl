package com.lesterlaucn.autoddl4j.codeparser.ddl;

import com.lesterlaucn.autoddl4j.TableDef;
import com.lesterlaucn.autoddl4j.datasource.ddl.MySqlTableDdlParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MySqlITableDdlParserTest {


    @BeforeEach
    void setup() {
    }

    @Test
    void parseDdl() {
        String ddl = "CREATE TABLE `autoddl4j_parser_test` (\n" +
                "  `id` int(11) NOT NULL COMMENT '主键',\n" +
                "  `name` varchar(255) NOT NULL DEFAULT '张三' COMMENT '姓名',\n" +
                "  `info` text NOT NULL COMMENT '信息',\n" +
                "  `create_time` datetime NOT NULL COMMENT '创建时间',\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8";
        final MySqlTableDdlParser ddlParser = new MySqlTableDdlParser(ddl);
        final TableDef parse = ddlParser.parse();
        System.out.println(parse.toString());
    }

    @Test
    void parseDdl2() {
        String ddl = "CREATE TABLE `autoddl4j_test2` (\n" +
                "  `id` int(11) NOT NULL,\n" +
                "  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '姓名',\n" +
                "  `info` text NOT NULL,\n" +
                "  `create_time` datetime NOT NULL,\n" +
                "  `age` int(11) NOT NULL DEFAULT '23',\n" +
                "  `idcard` varchar(255) NOT NULL,\n" +
                "  `disabled` tinyint(1) NOT NULL,\n" +
                "  PRIMARY KEY (`id`,`name`) USING BTREE,\n" +
                "  KEY `idx_idc` (`age`,`idcard`) USING BTREE\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表注释'";
        final MySqlTableDdlParser ddlParser = new MySqlTableDdlParser(ddl);
        final TableDef parse = ddlParser.parse();
        System.out.println(parse.toString());
    }

}