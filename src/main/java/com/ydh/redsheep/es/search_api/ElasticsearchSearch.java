package com.ydh.redsheep.es.search_api;

import com.ydh.redsheep.model.EsMultiSearchBO;
import com.ydh.redsheep.model.EsSearchPageBO;
import com.ydh.redsheep.model.EsSearchScrollBO;
import com.ydh.redsheep.util.ElasticsearchConfig;
import com.ydh.redsheep.util.SearchBuilderUtil;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class ElasticsearchSearch {

    private static Logger logger = LoggerFactory.getLogger(ElasticsearchSearch.class);

    private static RestHighLevelClient restHighLevelClient = ElasticsearchConfig.restHighLevelClient();

    public static void main(String[] args) throws Exception {
        EsSearchPageBO esSearchPageBO = new EsSearchPageBO();
        esSearchPageBO.setIndex("yiwise");
        esSearchPageBO.setType("test");
        esSearchPageBO.setFrom(0);
        esSearchPageBO.setSize(10);
//        Map<String, SortOrder> sortFiled = new LinkedHashMap<>();
//        sortFiled.put("sort", SortOrder.ASC);
//        esSearchPageBO.setSortFiled(sortFiled);

//        String[] includeFields = new String[] {"name", "sex", "age", "email"};
//        String[] excludeFields = new String[] {"email"};
//        esSearchPageBO.setIncludeFields(includeFields);
//        esSearchPageBO.setExcludeFields(excludeFields);

//        ParamFieldBO termField = new ParamFieldBO();
//        termField.setFieldName("name.keyword");
//        termField.setValue("人才3");
//        esSearchPageBO.setTermField(termField);

//        ParamFieldBO prefixField = new ParamFieldBO();
//        prefixField.setFieldName("name.keyword");
//        prefixField.setValue("人才3");
//        esSearchPageBO.setPrefixField(prefixField);

//        ParamFieldFuzzyBO fuzzyField = new ParamFieldFuzzyBO();
//        fuzzyField.setFieldName("name.keyword");
//        fuzzyField.setValue("人才3");
//        fuzzyField.setFuzziness(Fuzziness.ONE);
//        fuzzyField.setPrefixLength(0);
//        fuzzyField.setMaxExpansions(50);
//        esSearchPageBO.setFuzzyField(fuzzyField);

//        ParamFieldBO phraseField = new ParamFieldBO();
//        phraseField.setFieldName("address.keyword");
//        phraseField.setValue("中国浙江省杭州市-4583156876345471917");
//        esSearchPageBO.setPhraseField(phraseField);

//        ParamFieldBO phrasePrefixField = new ParamFieldBO();
//        phrasePrefixField.setFieldName("address.keyword");
//        phrasePrefixField.setValue("中国浙江省杭州市-4583156876345471917");
//        esSearchPageBO.setPhraseField(phrasePrefixField);

//        ParamFieldBO matchField = new ParamFieldBO();
//        matchField.setFieldName("address");
//        matchField.setValue("中国杭州市");
//        esSearchPageBO.setMatchField(matchField);

//        EsRangeBO esRangeBO = new EsRangeBO();
//        esRangeBO.setFiledName("sort");
//        esRangeBO.setMaxValue(3);
//        esRangeBO.setMinValue(3);
//        esSearchPageBO.setEsRangeBO(esRangeBO);

//        esSearchPageBO.setExistsField("name1");

//        ParamFieldBO regexField = new ParamFieldBO();
//        regexField.setFieldName("address.keyword");
//        regexField.setValue(".*杭州市.*");
//        esSearchPageBO.setRegexpField(regexField);


//        esSearchPageBO.setFilterFiled(filterFiled);
//        esSearchPageBO.setShouldFiled(shouldFiled);

        SearchResponse response = searchCondition(esSearchPageBO);
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
            // 高亮
//            System.out.println(hit.getHighlightFields());
        }

//        // 聚合
//        Aggregation sort = response.getAggregations().get("by_sex");
//        System.out.println(sort);

//        // 提示
//        Suggest.Suggestion<? extends Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>> suggest_address = response.getSuggest().getSuggestion("suggest_address");
//        System.out.println(suggest_address);


//        EsSearchScrollBO esSearchScrollBO = new EsSearchScrollBO();
//        esSearchScrollBO.setIndex("yiwise_test");
//        esSearchScrollBO.setType("person");
//        esSearchScrollBO.setSize(100);
////        Map<String, SortOrder> sortFiled = new LinkedHashMap<>();
////        sortFiled.put("sex.keyword", null);
////        sortFiled.put("age", SortOrder.ASC);
////        esSearchScrollBO.setSortFiled(sortFiled);
////        String[] includeFields = new String[] {"name", "sex", "age", "email"};
////        String[] excludeFields = new String[] {"email"};
////        esSearchScrollBO.setIncludeFields(includeFields);
////        esSearchScrollBO.setExcludeFields(excludeFields);
//////        Map<String, Object> fullMatchField = new HashMap<>();
//////        fullMatchField.put("name", "yiwise");
//////        esSearchScrollBO.setFullMatchField(fullMatchField);
////        Map<String, String> queryField = new HashMap<>();
////        queryField.put("name", "yiwise");
////        esSearchScrollBO.setFuzzyField(queryField);
//        Map<String, Object> filterFiled = new HashMap<>();
//        List<Integer> list = new ArrayList<>();
//        list.add(8);
//        list.add(-8);
//        list.add(2);
//        filterFiled.put("list", list);
//        esSearchScrollBO.setFilterFiled(filterFiled);
//
//        final Scroll scroll = new Scroll(TimeValue.timeValueSeconds(60L));
//
//        SearchResponse response = searchScroll(esSearchScrollBO, scroll);
//        String scrollId = response.getScrollId();
//        SearchHit[] searchHits = response.getHits().getHits();
//
//        while (searchHits != null && searchHits.length > 0) {
//            System.out.println("========");
//            for (SearchHit hit : searchHits) {
//                System.out.println(hit.getSourceAsString());
//            }
//            response = scroll(scroll, scrollId);
//            searchHits = response.getHits().getHits();
//            scrollId = response.getScrollId();
//        }
//
//        ClearScrollResponse clearScrollResponse = clearScroll(scrollId);
//        System.out.println(clearScrollResponse.isSucceeded());
//        System.out.println(clearScrollResponse.getNumFreed());

//        List<EsMultiSearchBO> list = new ArrayList<>();
//
//        EsMultiSearchBO esMultiSearchBO = new EsMultiSearchBO();
//        esMultiSearchBO.setIndex("yiwise_test");
//        esMultiSearchBO.setType("person");
////        Map<String, Object> filterFiled = new HashMap<>();
////        List<Integer> list = new ArrayList<>();
////        list.add(8);
////        list.add(-8);
////        list.add(2);
////        filterFiled.put("list", list);
////        filterFiled.put("tebaDetail.xxxId", 8);
////        esMultiSearchBO.setFilterFiled(filterFiled);
//
//        list.add(esMultiSearchBO);
//        list.add(esMultiSearchBO);
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

    /**
     * 滚动查询-pre
     * @param esSearchScrollBO
     * @param scroll
     * @return
     */
    public static SearchResponse searchScroll(EsSearchScrollBO esSearchScrollBO, Scroll scroll) {
        SearchRequest searchRequest = new SearchRequest(esSearchScrollBO.getIndex());
        searchRequest.types(esSearchScrollBO.getType());
        searchRequest.scroll(scroll);
        SearchSourceBuilder searchSourceBuilder = SearchBuilderUtil.getSearchBuilder(esSearchScrollBO);
        searchSourceBuilder.size(esSearchScrollBO.getSize());
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            return response;
        } catch (IOException e) {
            logger.error("启用scroll错误", e);
        }
        return null;
    }
    /**
     * 持续滚动
     * @param scroll
     * @param scrollId
     * @return
     */
    public static SearchResponse scroll(Scroll scroll, String scrollId) {
        SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
        scrollRequest.scroll(scroll);
        try {
            SearchResponse response = restHighLevelClient.scroll(scrollRequest, RequestOptions.DEFAULT);
            return response;
        } catch (IOException e) {
            logger.error("滚动scroll错误", e);
        }
        return null;
    }
    /**
     * 清理滚动
     * @param scrollId
     * @return
     */
    public static ClearScrollResponse clearScroll(String scrollId) {
        ClearScrollRequest request = new ClearScrollRequest();
        request.addScrollId(scrollId);
        try {
            ClearScrollResponse response = restHighLevelClient.clearScroll(request, RequestOptions.DEFAULT);
            return response;
        } catch (IOException e) {
            logger.error("清理scroll错误", e);
        }
        return null;
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
