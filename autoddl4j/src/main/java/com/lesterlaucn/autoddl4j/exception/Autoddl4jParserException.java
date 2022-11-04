package com.lesterlaucn.autoddl4j.exception;

/**
 * Created by liuyuancheng on 2022/10/25  <br/>
 *
 * @author liuyuancheng
 */
public class Autoddl4jParserException extends RuntimeException {
    public Autoddl4jParserException(Exception e) {
        super(e);
    }

    public Autoddl4jParserException() {
    }

    public Autoddl4jParserException(String error) {
        super(error);
    }
}
