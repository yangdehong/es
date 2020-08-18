//package com.ydh.redsheep.es.search_api;
//
//import com.ydh.redsheep.model.EsRangeBO;
//import com.ydh.redsheep.model.EsSearchPageBO;
//import com.ydh.redsheep.util.SearchBuilderUtil;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//
//public class ElasticsearchBoolSearch {
//
//    private static Logger logger = LoggerFactory.getLogger(ElasticsearchBoolSearch.class);
//
//   @Resource
//    private RestHighLevelClient restHighLevelClient;
//
//    public static void main(String[] args) throws Exception {
//        EsSearchPageBO esSearchPageBO = new EsSearchPageBO();
//        esSearchPageBO.setIndex("yiwise");
//        esSearchPageBO.setType("test");
//        esSearchPageBO.setFrom(0);
//        esSearchPageBO.setSize(10);
//
//        Map<String, Object> filterFiled = new HashMap<>();
////        filterFiled.put("name", "3");
////        filterFiled.put("email.keyword", "2680266117436425216@qq.com");
//        EsRangeBO esRangeBO = new EsRangeBO();
//        esRangeBO.setMaxValue(LocalDateTime.now());
//        esRangeBO.setMinValue(LocalDateTime.of(2020, 5, 25, 0, 0));
//        filterFiled.put("birthDay", esRangeBO);
//        esSearchPageBO.setFilterFiled(filterFiled);
//
////        Map<String, Object> mustFiled = new HashMap<>();
////        mustFiled.put("name", "才3");
////        mustFiled.put("email", "2680266117436425216@qq.com");
////        esSearchPageBO.setMustFiled(mustFiled);
//
////        Map<String, Object> mustNotField = new HashMap<>();
////        mustNotField.put("name", "3");
//////        mustNotField.put("email", "2680266117436425216@qq.com");
////        esSearchPageBO.setNotMustFiled(mustNotField);
//
////        Map<String, Object> shouldField = new HashMap<>();
////        shouldField.put("name", "3");
////        shouldField.put("email", "1779048719887237034@qq.com");
////        esSearchPageBO.setShouldFiled(shouldField);
//
//        SearchResponse response = searchCondition(esSearchPageBO);
//        SearchHits hits = response.getHits();
//        for (SearchHit hit : hits) {
//            System.out.println(hit.getScore());
//            System.out.println(hit.getSourceAsString());
//        }
//
//
//        restHighLevelClient.close();
//
//    }
//
//
//    /**
//     * 普通查询
//     *
//     * @param esSearchPageBO
//     * @return
//     */
//    public static SearchResponse searchCondition(EsSearchPageBO esSearchPageBO) {
//        SearchRequest request = new SearchRequest(esSearchPageBO.getIndex());
//        request.types(esSearchPageBO.getType());
//        SearchSourceBuilder searchSourceBuilder = SearchBuilderUtil.getSearchBuilder(esSearchPageBO);
//        if (esSearchPageBO.getFrom() != null) {
//            searchSourceBuilder.from(esSearchPageBO.getFrom());
//        }
//        if (esSearchPageBO.getSize() != null) {
//            searchSourceBuilder.size(esSearchPageBO.getSize());
//        }
//        request.source(searchSourceBuilder);
//        try {
//            SearchResponse searchResponse = restHighLevelClient.search(request, RequestOptions.DEFAULT);
//            return searchResponse;
//        } catch (IOException e) {
//            log.error("search所有的时候错误", e);
//        }
//        return null;
//    }
//
//
//}
