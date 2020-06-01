package com.ydh.redsheep.es.search_api;

import com.ydh.redsheep.util.ElasticsearchConfig;
import org.elasticsearch.action.search.ShardSearchFailure;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
*
* @author : yangdehong
* @date : 2020-06-01 14:59
*/
public class Count {

    private static Logger logger = LoggerFactory.getLogger(Count.class);

    private static RestHighLevelClient restHighLevelClient = ElasticsearchConfig.restHighLevelClient();

    public static void main(String[] args) throws Exception {
        CountRequest countRequest = new CountRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("name.keyword", "人才3"));
        countRequest.source(searchSourceBuilder);

        CountResponse countResponse = restHighLevelClient.count(countRequest, RequestOptions.DEFAULT);

        long count = countResponse.getCount();
        RestStatus status = countResponse.status();
        Boolean terminatedEarly = countResponse.isTerminatedEarly();
        System.out.println(count+" "+status+" "+terminatedEarly);

        int totalShards = countResponse.getTotalShards();
        int skippedShards = countResponse.getSkippedShards();
        int successfulShards = countResponse.getSuccessfulShards();
        int failedShards = countResponse.getFailedShards();
        for (ShardSearchFailure failure : countResponse.getShardFailures()) {
            // failures should be handled here
        }

    }

}
