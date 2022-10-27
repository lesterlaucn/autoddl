package com.lesterlaucn.autoddl4j.parser.entity.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Created by liuyuancheng on 2022/10/26  <br/>
 *
 * @author liuyuancheng
 */
public class JsonUtil {
    private static ObjectMapper objectMapper;

    static {
       objectMapper = new ObjectMapper();
       objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    public static String toJsonStr(Object o){
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
