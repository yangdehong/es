package com.ydh.redsheep.es.search_api;

import com.ydh.redsheep.util.ElasticsearchConfig;
import org.elasticsearch.action.fieldcaps.FieldCapabilities;
import org.elasticsearch.action.fieldcaps.FieldCapabilitiesRequest;
import org.elasticsearch.action.fieldcaps.FieldCapabilitiesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
* 对索引字段属性查看
* @author : yangdehong
* @date : 2020-06-01 13:59
*/
public class FileCapabilities {

    private static Logger logger = LoggerFactory.getLogger(FileCapabilities.class);

    private static RestHighLevelClient restHighLevelClient = ElasticsearchConfig.restHighLevelClient();

    public static void main(String[] args) throws Exception {
        // field可以使用*等通配符
        FieldCapabilitiesRequest request = new FieldCapabilitiesRequest().fields("phoneNumber", "createUserId")
                .indices("customer_test", "nlp_customer_test");

        FieldCapabilitiesResponse response = restHighLevelClient.fieldCaps(request, RequestOptions.DEFAULT);
        Map<String, FieldCapabilities> phoneNumber = response.getField("phoneNumber");

        // 字段可能类型的条目的映射，重点是类型text、keyword、date这些
        FieldCapabilities textCapabilities = phoneNumber.get("text");
        // 是否可以搜索
        boolean isSearchable = textCapabilities.isSearchable();
        // 是否可以聚合查询
        boolean isAggregatable = textCapabilities.isAggregatable();
        // 该字段具有相同类型的索引列表，如果所有索引具有相同的字段类型，则为null。
        String[] indices = textCapabilities.indices();
        // 字段在那些子集中不可以搜索，如果始终能search，则为null；下面聚合同理
        String[] nonSearchableIndices = textCapabilities.nonSearchableIndices();
        String[] nonAggregatableIndices = textCapabilities.nonAggregatableIndices();

        System.out.println(isSearchable+" "+isAggregatable+" "+indices+" "+nonSearchableIndices+" "+nonAggregatableIndices);


        restHighLevelClient.close();

    }

}
