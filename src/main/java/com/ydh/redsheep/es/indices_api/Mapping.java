package com.ydh.redsheep.es.indices_api;

import com.ydh.redsheep.util.ElasticsearchConfig;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.util.Map;

public class Mapping {

    private static RestHighLevelClient restHighLevelClient = ElasticsearchConfig.restHighLevelClient();

    public static void main(String[] args) throws Exception {
//        putMapping();
//        getMapping();
        getFieldMapping();

        restHighLevelClient.close();
    }

    /**
     * 对index添加mapping
     * @throws Exception
     */
    private static void getFieldMapping() throws Exception {
        GetFieldMappingsRequest request = new GetFieldMappingsRequest();
        request.indices("test");
        request.fields("message");
        GetFieldMappingsResponse response = restHighLevelClient.indices().getFieldMapping(request, RequestOptions.DEFAULT);

        // 返回所有请求的索引字段的映射
        final Map<String, Map<String, GetFieldMappingsResponse.FieldMappingMetaData>> mappings = response.mappings();
        // 检索特定索引的映射
        final Map<String, GetFieldMappingsResponse.FieldMappingMetaData> fieldMappings = mappings.get("test");
        // 获取该message字段的映射元数据
        final GetFieldMappingsResponse.FieldMappingMetaData metaData = fieldMappings.get("message");
        // 获取字段的全名
        final String fullName = metaData.fullName();
        // 获取字段的映射源
        final Map<String, Object> source = metaData.sourceAsMap();
        System.out.println(fullName + " " + source);
    }

    /**
     * 对index添加mapping
     * @throws Exception
     */
    private static void getMapping() throws Exception {
        GetMappingsRequest request = new GetMappingsRequest();
        request.indices("test");
        GetMappingsResponse getMappingResponse = restHighLevelClient.indices().getMapping(request, RequestOptions.DEFAULT);

        // 返回所有索引的映射
        Map<String, MappingMetaData> allMappings = getMappingResponse.mappings();
        // 检索特定索引的映射
        MappingMetaData indexMapping = allMappings.get("test");
        // 将映射作为Java Map获取
        Map<String, Object> mapping = indexMapping.sourceAsMap();
        System.out.println(mapping);
    }

    /**
     * 对index添加mapping
     * @throws Exception
     */
    private static void putMapping() throws Exception {
        PutMappingRequest request = new PutMappingRequest("test");
//        request.source("{\n" +
//                        "  \"properties\": {\n" +
//                        "    \"message\": {\n" +
//                        "      \"type\": \"text\"\n" +
//                        "    }\n" +
//                        "  }\n" +
//                        "}",
//                XContentType.JSON);
//        Map<String, Object> jsonMap = new HashMap<>();
//        Map<String, Object> message = new HashMap<>();
//        message.put("type", "text");
//        Map<String, Object> properties = new HashMap<>();
//        properties.put("message", message);
//        jsonMap.put("properties", properties);
//        request.source(jsonMap);
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("properties");
            {
                builder.startObject("message");
                {
                    builder.field("type", "text");
                }
                builder.endObject();
                builder.startObject("age");
                {
                    builder.field("type", "text");
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();
        request.source(builder);

        AcknowledgedResponse putMappingResponse = restHighLevelClient.indices().putMapping(request, RequestOptions.DEFAULT);
        boolean acknowledged = putMappingResponse.isAcknowledged();
        System.out.println(acknowledged);

    }

}
