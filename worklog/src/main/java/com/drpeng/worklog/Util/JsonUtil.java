package com.drpeng.worklog.util;

import com.drpeng.worklog.util.json.CommonObjectMapper;
import com.drpeng.worklog.util.json.ObjectFilterMixIn;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * JSON 工具类
 *
 * @author zhaoyp
 * @since 1.0.0
 */
public final class JsonUtil {

    //private static ObjectMapper objectMapper = new CommonObjectMapper();

    /**
     * 将 POJO 对象转为 JSON 字符串
     */
    public static <T> String toJson(T pojo) {
        ObjectMapper objectMapper = new CommonObjectMapper();
        String json;
        try {
            json = objectMapper.writeValueAsString(pojo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * 过滤想要的
     * @param pojo
     * @param target
     * @param property
     * @param <T>
     * @return
     */
    public  static <T> String toJsonWithFilterAllExcept(T pojo, Class<?> target, String... property){
        String json;
        ObjectMapper objectMapper = new CommonObjectMapper();
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.setDefaultFilter(SimpleBeanPropertyFilter.filterOutAllExcept(property));
        objectMapper.addMixIn(target,ObjectFilterMixIn.class);
        objectMapper.setFilterProvider(filterProvider);
        try {
            json = objectMapper.writeValueAsString(pojo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * 过滤不想要的
     * @param pojo
     * @param target
     * @param property
     * @param <T>
     * @return
     */
    public  static <T> String toJsonWithSerializeAllExcept(T pojo, Class<?> target, String... property){
        String json;
        ObjectMapper objectMapper = new CommonObjectMapper();
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.setDefaultFilter(SimpleBeanPropertyFilter.serializeAllExcept(property));
        objectMapper.addMixIn(target,ObjectFilterMixIn.class);
        objectMapper.setFilterProvider(filterProvider);
        try {
            json = objectMapper.writeValueAsString(pojo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * 将 JSON 字符串转为 POJO 对象
     */
    public static <T> T fromJson(String json, Class<T> type) {
        T pojo;
        ObjectMapper objectMapper = new CommonObjectMapper();
        try {
            pojo = objectMapper.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pojo;
    }
}
