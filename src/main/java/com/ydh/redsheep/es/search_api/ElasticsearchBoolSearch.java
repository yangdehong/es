package com.ydh.redsheep.es.search_api;

import com.ydh.redsheep.model.EsSearchPageBO;
import com.ydh.redsheep.util.ElasticsearchConfig;
import com.ydh.redsheep.util.SearchBuilderUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ElasticsearchBoolSearch {

    private static Logger logger = LoggerFactory.getLogger(ElasticsearchBoolSearch.class);

    private static RestHighLevelClient restHighLevelClient = ElasticsearchConfig.restHighLevelClient();

    public static void main(String[] args) throws Exception {
        EsSearchPageBO esSearchPageBO = new EsSearchPageBO();
        esSearchPageBO.setIndex("yiwise");
        esSearchPageBO.setType("test");
        esSearchPageBO.setFrom(0);
        esSearchPageBO.setSize(10);

//        Map<String, Object> filterFiled = new HashMap<>();
//        filterFiled.put("name", "人才3");
//        filterFiled.put("email", "2680266117436425216@qq.com");
//        esSearchPageBO.setFilterFiled(filterFiled);

//        Map<String, Object> mustFiled = new HashMap<>();
//        mustFiled.put("name", "人才3");
//        mustFiled.put("email", "2680266117436425216@qq.com");
//        esSearchPageBO.setMustFiled(mustFiled);

//        Map<String, Object> mustNotField = new HashMap<>();
//        mustNotField.put("name", "人才3");
//        mustNotField.put("email", "2680266117436425216@qq.com");
//        esSearchPageBO.setNotMustFiled(mustNotField);

        Map<String, Object> shouldField = new HashMap<>();
        shouldField.put("name", "人才3");
        shouldField.put("email", "2680266117436425216@qq.com");
        esSearchPageBO.setShouldFiled(shouldField);

        SearchResponse response = searchCondition(esSearchPageBO);
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getScore());
            System.out.println(hit.getSourceAsString());
        }


        restHighLevelClient.close();

    }


    /**
     * 普通查询
     *
     * @param esSearchPageBO
     * @return
     */
    public static SearchResponse searchCondition(EsSearchPageBO esSearchPageBO) {
        SearchRequest request = new SearchRequest(esSearchPageBO.getIndex());
        request.types(esSearchPageBO.getType());
        SearchSourceBuilder searchSourceBuilder = SearchBuilderUtil.getSearchBuilder(esSearchPageBO);
        if (esSearchPageBO.getFrom() != null) {
            searchSourceBuilder.from(esSearchPageBO.getFrom());
        }
        if (esSearchPageBO.getSize() != null) {
            searchSourceBuilder.size(esSearchPageBO.getSize());
        }
        request.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            return searchResponse;
        } catch (IOException e) {
            logger.error("search所有的时候错误", e);
        }
        return null;
    }


}
