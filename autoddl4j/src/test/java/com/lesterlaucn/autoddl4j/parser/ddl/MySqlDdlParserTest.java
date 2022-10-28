package com.lesterlaucn.autoddl4j.parser.ddl;

import com.lesterlaucn.autoddl4j.parser.EntityParserResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MySqlDdlParserTest {


    @BeforeEach
    void setup(){
    }

    @Test
    void parseDdl() {
        String ddl = "CREATE TABLE `autoddl4j_parser_test` (\n" +
                "  `id` int(11) NOT NULL,\n" +
                "  `name` varchar(255) NOT NULL DEFAULT '',\n" +
                "  `info` text NOT NULL,\n" +
                "  `create_time` datetime NOT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8\n";
        final MySqlDdlParser ddlParser = new MySqlDdlParser(ddl);
        final EntityParserResult parse = ddlParser.parse();
        System.out.println(parse.toString());
    }

}