//package com.ydh.redsheep.es.search_api;
//
//import com.ydh.redsheep.model.EsMultiSearchBO;
//import com.ydh.redsheep.model.ParamFieldBO;
//import com.ydh.redsheep.util.SearchBuilderUtil;
//import org.elasticsearch.action.search.MultiSearchRequest;
//import org.elasticsearch.action.search.MultiSearchResponse;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
//* 多个search组合一起使用
//* @author : yangdehong
//* @date : 2020-05-29 13:54
//*/
//public class ElasticsearchMultiSearch {
//
//    private static Logger logger = LoggerFactory.getLogger(ElasticsearchMultiSearch.class);
//
//   @Resource
//    private RestHighLevelClient restHighLevelClient;
//
//    public static void main(String[] args) throws Exception {
//
//        List<EsMultiSearchBO> list = new ArrayList<>();
//
//        EsMultiSearchBO esMultiSearchBO = new EsMultiSearchBO();
//        esMultiSearchBO.setIndex("yiwise");
//        esMultiSearchBO.setType("test");
//        esMultiSearchBO.setFrom(0);
//        esMultiSearchBO.setSize(10);
//        ParamFieldBO termField = new ParamFieldBO();
//        termField.setFieldName("name.keyword");
//        termField.setValue("人才3");
//        esMultiSearchBO.setTermField(termField);
//        list.add(esMultiSearchBO);
//
//        EsMultiSearchBO esMultiSearchBO2 = new EsMultiSearchBO();
//        esMultiSearchBO2.setIndex("yiwise");
//        esMultiSearchBO2.setType("test");
//        esMultiSearchBO2.setFrom(0);
//        esMultiSearchBO2.setSize(10);
//        ParamFieldBO termField2 = new ParamFieldBO();
//        termField2.setFieldName("name.keyword");
//        termField2.setValue("人才4");
//        esMultiSearchBO2.setTermField(termField2);
//        list.add(esMultiSearchBO2);
//
//        MultiSearchResponse response = multiSearch(list);
//        MultiSearchResponse.Item[] responses = response.getResponses();
//        for (MultiSearchResponse.Item item : responses) {
//            if (!item.isFailure()) {
//                SearchResponse searchResponse = item.getResponse();
//                SearchHit[] hits = searchResponse.getHits().getHits();
//                for (SearchHit hit : hits) {
//                    System.out.println(hit.getSourceAsString());
//                }
//            }
//        }
//
//        restHighLevelClient.close();
//
//    }
//
//    public static MultiSearchResponse multiSearch(List<EsMultiSearchBO> list) {
//
//        MultiSearchRequest multiRequest = new MultiSearchRequest();
//        list.forEach(esMultiSearchBO -> {
//            SearchRequest request = new SearchRequest(esMultiSearchBO.getIndex());
//            request.types(esMultiSearchBO.getType());
//
//            SearchSourceBuilder searchSourceBuilder = SearchBuilderUtil.getSearchBuilder(esMultiSearchBO);
//
//            if (esMultiSearchBO.getFrom() != null) {
//                searchSourceBuilder.from(esMultiSearchBO.getFrom());
//            }
//            if (esMultiSearchBO.getSize() != null) {
//                searchSourceBuilder.size(esMultiSearchBO.getSize());
//            }
//            request.source(searchSourceBuilder);
//            multiRequest.add(request);
//        });
//
//
//        try {
//            MultiSearchResponse response = restHighLevelClient.msearch(multiRequest, RequestOptions.DEFAULT);
//            return response;
//        } catch (IOException e) {
//            log.error("es复杂查询错误", e);
//        }
//
//        return null;
//    }
//
//}
