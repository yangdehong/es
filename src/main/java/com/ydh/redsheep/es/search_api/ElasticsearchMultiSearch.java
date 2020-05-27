package com.ydh.redsheep.es.search_api;

import com.ydh.redsheep.model.EsMultiSearchBO;
import com.ydh.redsheep.util.ElasticsearchConfig;
import com.ydh.redsheep.util.SearchBuilderUtil;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ElasticsearchMultiSearch {

    private static Logger logger = LoggerFactory.getLogger(ElasticsearchMultiSearch.class);

    private static RestHighLevelClient restHighLevelClient = ElasticsearchConfig.restHighLevelClient();

    public static void main(String[] args) throws Exception {

        List<EsMultiSearchBO> list = new ArrayList<>();

        EsMultiSearchBO esMultiSearchBO = new EsMultiSearchBO();
        esMultiSearchBO.setIndex("yiwise_test");
        esMultiSearchBO.setType("person");
//        Map<String, Object> filterFiled = new HashMap<>();
//        List<Integer> list = new ArrayList<>();
//        list.add(8);
//        list.add(-8);
//        list.add(2);
//        filterFiled.put("list", list);
//        filterFiled.put("tebaDetail.xxxId", 8);
//        esMultiSearchBO.setFilterFiled(filterFiled);

        list.add(esMultiSearchBO);
        list.add(esMultiSearchBO);

        MultiSearchResponse response = multiSearch(list);
        MultiSearchResponse.Item[] responses = response.getResponses();
        for (MultiSearchResponse.Item item : responses) {
            if (!item.isFailure()) {
                SearchResponse searchResponse = item.getResponse();
                SearchHit[] hits = searchResponse.getHits().getHits();
                for (SearchHit hit : hits) {
                    System.out.println(hit.getSourceAsString());
                }
            }
        }

        restHighLevelClient.close();

    }

    public static MultiSearchResponse multiSearch(List<EsMultiSearchBO> list) {

        MultiSearchRequest multiRequest = new MultiSearchRequest();
        list.forEach(esMultiSearchBO -> {
            SearchRequest request = new SearchRequest(esMultiSearchBO.getIndex());
            request.types(esMultiSearchBO.getType());

            SearchSourceBuilder searchSourceBuilder = SearchBuilderUtil.getSearchBuilder(esMultiSearchBO);

            if (esMultiSearchBO.getFrom() != null) {
                searchSourceBuilder.from(esMultiSearchBO.getFrom());
            }
            if (esMultiSearchBO.getSize() != null) {
                searchSourceBuilder.size(esMultiSearchBO.getSize());
            }

            request.source(searchSourceBuilder);
            multiRequest.add(request);
        });


        try {
            MultiSearchResponse response = restHighLevelClient.msearch(multiRequest, RequestOptions.DEFAULT);
            return response;
        } catch (IOException e) {
            logger.error("es复杂查询错误", e);
        }

        return null;
    }

}
