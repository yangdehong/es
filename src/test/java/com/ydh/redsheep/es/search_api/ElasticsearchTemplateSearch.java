//package com.ydh.redsheep.es.search_api;
//
//import com.ydh.redsheep.model.EsSearchPageBO;
//import com.ydh.redsheep.model.ParamFieldBO;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.script.ScriptType;
//import org.elasticsearch.script.mustache.SearchTemplateRequest;
//import org.elasticsearch.script.mustache.SearchTemplateResponse;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class ElasticsearchTemplateSearch {
//
//    private static Logger logger = LoggerFactory.getLogger(ElasticsearchTemplateSearch.class);
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
//        List<ParamFieldBO> list = new ArrayList<>();
//        ParamFieldBO paramFieldBO = new ParamFieldBO();
//        paramFieldBO.setFieldName("field");
//        paramFieldBO.setValue("name");
//        list.add(paramFieldBO);
//        ParamFieldBO paramFieldBO2 = new ParamFieldBO();
//        paramFieldBO2.setFieldName("value");
//        paramFieldBO2.setValue("人才");
//        list.add(paramFieldBO2);
//        ParamFieldBO paramFieldBO3 = new ParamFieldBO();
//        paramFieldBO3.setFieldName("size");
//        paramFieldBO3.setValue("5");
//        list.add(paramFieldBO3);
//
//        SearchTemplateResponse response = searchCondition(esSearchPageBO, list);
//        SearchHits hits = response.getResponse().getHits();
//        for (SearchHit hit : hits) {
//            System.out.println(hit.getSourceAsString());
//        }
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
//    public static SearchTemplateResponse searchCondition(EsSearchPageBO esSearchPageBO, List<ParamFieldBO> list) {
//        SearchRequest searchRequest = new SearchRequest(esSearchPageBO.getIndex());
//        searchRequest.types(esSearchPageBO.getType());
//
//
//        SearchTemplateRequest request = new SearchTemplateRequest();
//        request.setRequest(searchRequest);
//
//        request.setScriptType(ScriptType.INLINE);
//        request.setScript("{" +
//                        "  \"query\": { \"match\" : { \"{{field}}\" : \"{{value}}\" } }," +
//                        "  \"size\" : \"{{size}}\"" +
//                        "}");
//
//        Map<String, Object> scriptParams = new HashMap<>();
//        list.forEach(item->{
//            scriptParams.put(item.getFieldName(), item.getValue());
//        });
//        request.setScriptParams(scriptParams);
//
//        try {
//            SearchTemplateResponse searchResponse = restHighLevelClient.searchTemplate(request, RequestOptions.DEFAULT);
//            return searchResponse;
//        } catch (IOException e) {
//            log.error("search所有的时候错误", e);
//        }
//        return null;
//    }
//
//
//}
