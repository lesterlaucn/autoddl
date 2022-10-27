package com.lesterlaucn.autoddl4j.annotations;

import com.lesterlaucn.autoddl4j.database.definition.CharacterSet;
import com.lesterlaucn.autoddl4j.database.definition.Collation;
import com.lesterlaucn.autoddl4j.database.definition.DataBaseType;
import com.lesterlaucn.autoddl4j.database.definition.TableEngine;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by liuyuancheng on 2022/10/19  <br/>
 *
 * @author liuyuancheng
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface TableExtend {

    DataBaseType dbType() default DataBaseType.MySQL;

    /**
     * 表曾用名
     * @return array
     */
    String[] formerName() default {};

    String prefix() default "";

    String comment() default "";

    TableEngine engine() default TableEngine.MySQL_InnoDB;

    CharacterSet charset() default CharacterSet.MySQL_UTF8;

    Collation collation() default Collation.MySQL_COLLATION_UTF8;
}
