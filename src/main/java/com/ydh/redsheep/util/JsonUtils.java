package com.ydh.redsheep.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2016/10/10
 *
 * @author annpeter.it@gmail.com
 */
public class JsonUtils {
    private static final ObjectMapper objectMapper = new CustomObjectMapper();

    private static final ObjectMapper nullObjectMapper = new CustomObjectMapper();

    static {
        nullObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    }

    private static class CustomObjectMapper extends ObjectMapper {

        private static final String dateFormatPattern = "yyyy-MM-dd HH:mm:ss";

        public CustomObjectMapper() {
            // 设置时间格式
            this.setDateFormat(new SimpleDateFormat(dateFormatPattern));

            // 反序列化时可能出现bean对象中没有的字段, 忽略
            this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            // 设置序列化的可见域
            this.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.PUBLIC_ONLY);
            this.registerModule(new JavaTimeModule());
            this.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            //this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        }
    }
    public static JsonNode string2JsonNode(String jsonStr) {
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return jsonNode;
    }

    public static JsonNode bytes2JsonNode(byte[] bytes) {
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return jsonNode;
    }

    public static <T> T string2Object(String jsonStr, Class<T> clazz) {
        T t;
        try {
            t = objectMapper.readValue(jsonStr, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return t;
    }

    public static <T> T string2Object(InputStream inputStream, Class<T> clazz) {
        T t;
        try {
            t = objectMapper.readValue(inputStream, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return t;
    }

    /**
     * 传入 new TypeReference<List<Integer>>(){}
     **/
    public static <T> T string2Object(String jsonString, TypeReference type) {
        try {
            return objectMapper.readValue(jsonString, type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T bytes2Object(byte[] bytes, Class<T> clazz) {
        T t;
        try {
            t = objectMapper.readValue(bytes, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return t;
    }

    public static <T> T bytes2Object(byte[] bytes, TypeReference type) {
        T t;
        try {
            t = objectMapper.readValue(bytes, type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return t;
    }

    public static String object2String(Object obj) {
        String s;
        try {
            s = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
        return s;
    }

    public static String object2StringNotNull(Object obj) {
        String s;
        try {
            s = nullObjectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
        return s;
    }

    public static String object2PrettyString(Object obj) {
        String s;
        try {
            s = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
        return s;
    }

    public static byte[] object2Bytes(Object obj) {
        byte[] bytes;
        try {
            bytes = objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
        return bytes;
    }

    public static <K, V>Map<K, V> string2MapObject(String jsonStr, Class<K> keyClazz, Class<V> valueClazz) {
        JavaType javaType = getCollectionType(HashMap.class, keyClazz, valueClazz);
        try {
            return objectMapper.readValue(jsonStr, javaType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T>List<T> string2ListObject(String jsonStr, Class<T> tClass) {
        JavaType javaType = getCollectionType(ArrayList.class, tClass);
        try {
            return objectMapper.readValue(jsonStr, javaType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
}
