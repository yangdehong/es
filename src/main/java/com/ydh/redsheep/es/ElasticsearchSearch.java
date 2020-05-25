package com.ydh.redsheep.es;

import com.ydh.redsheep.model.*;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ElasticsearchSearch {

    private static Logger logger = LoggerFactory.getLogger(ElasticsearchSearch.class);

    private static RestHighLevelClient restHighLevelClient = ElasticsearchConfig.restHighLevelClient();

    public static void main(String[] args) {
//        EsSearchPageBO esSearchPageBO = new EsSearchPageBO();
//        esSearchPageBO.setIndex("customer_test");
//        esSearchPageBO.setType("customer_level_tag");
//        esSearchPageBO.setFrom(0);
//        esSearchPageBO.setSize(100);
////        Map<String, SortOrder> sortFiled = new LinkedHashMap<>();
////        sortFiled.put("age", SortOrder.ASC);
////        sortFiled.put("sex.keyword", SortOrder.ASC);
////        esSearchPageBO.setSortFiled(sortFiled);
////        String[] includeFields = new String[] {"name", "sex", "age", "email"};
////        String[] excludeFields = new String[] {"email"};
////        esSearchPageBO.setIncludeFields(includeFields);
////        esSearchPageBO.setExcludeFields(excludeFields);
////        Map<String, Object> fullMatchField = new HashMap<>();
////        fullMatchField.put("tenantId", "916");
////        esSearchPageBO.setFullMatchField(fullMatchField);
////        Map<String, String> queryField = new HashMap<>();
////        queryField.put("name", "天才1");
////        esSearchPageBO.setFuzzyField(queryField);
//        Map<String, Object> filterFiled = new HashMap<>();
//        List<Integer> list = new ArrayList<>();
//        list.add(3);
//        filterFiled.put("customerLevelTags", list);
//        filterFiled.put("tenantId", 916);
//        Map<String, Object> shouldFiled = new HashMap<>();
//        List<Integer> list2 = new ArrayList<>();
//        list2.add(1243);
//        list2.add(1143);
//        shouldFiled.put("followUserId", list2);
////        EsParamsRangeBO esParamsRangeBO = new EsParamsRangeBO("age.keyword", 3, 8);
////        filterFiled.put("age", esParamsRangeBO);
//
//        esSearchPageBO.setFilterFiled(filterFiled);
//        esSearchPageBO.setShouldFiled(shouldFiled);
//
//        SearchResponse response = searchCondition(esSearchPageBO);
//        SearchHits hits = response.getHits();
//        for (SearchHit hit : hits) {
//            System.out.println(hit.getSourceAsString());
//        }

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

        SearchSourceBuilder searchSourceBuilder = getSearchBuilder(esSearchPageBO);

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
        SearchSourceBuilder searchSourceBuilder = getSearchBuilder(esSearchScrollBO);
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

            SearchSourceBuilder searchSourceBuilder = getSearchBuilder(esMultiSearchBO);

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

    /**
     * 构建查询条件对象
     * @param esSearchBaseBO
     * @return
     */
    private static SearchSourceBuilder getSearchBuilder(EsSearchBaseBO esSearchBaseBO) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if (esSearchBaseBO.getFullMatchField() == null && esSearchBaseBO.getFuzzyField() == null) {
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        } else {
            if (esSearchBaseBO.getFullMatchField() != null) {
                Map<String, Object> fullMatchField = esSearchBaseBO.getFullMatchField();
                for (String field : fullMatchField.keySet()) {
                    Object value = fullMatchField.get(field);
                    QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery(field, value);
                    searchSourceBuilder.query(queryBuilder);
                }
            }
            if (esSearchBaseBO.getFuzzyField() != null) {
                Map<String, String> fuzzyField = esSearchBaseBO.getFuzzyField();
                for (String field : fuzzyField.keySet()) {
                    Object value = fuzzyField.get(field);
                    QueryBuilder queryBuilder = QueryBuilders.fuzzyQuery(field, value).fuzziness(esSearchBaseBO.getFuzziness())
                            .prefixLength(esSearchBaseBO.getPrefixLength()).maxExpansions(esSearchBaseBO.getMaxExpansions());
                    searchSourceBuilder.query(queryBuilder);
                }
            }
        }

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        Map<String, Object> filterFiled = esSearchBaseBO.getFilterFiled();
        if (filterFiled != null) {
            for (String key : filterFiled.keySet()) {
                Object o = filterFiled.get(key);
                if (o instanceof List) {
                    List list = (List) o;
                    list.forEach(item -> {
                        MatchPhraseQueryBuilder filter = QueryBuilders.matchPhraseQuery(key, item);
                        boolQueryBuilder.filter(filter);
                    });
                } else if (o instanceof EsParamsRangeBO) {
                    EsParamsRangeBO esParamsRangeBO = (EsParamsRangeBO) o;
                    Object minData = esParamsRangeBO.getMinData();
                    Object maxData = esParamsRangeBO.getMaxData();
                    if (Objects.nonNull(maxData) || Objects.nonNull(minData)) {
                        RangeQueryBuilder range = QueryBuilders.rangeQuery(key);
                        if (Objects.nonNull(maxData)) {
                            range.lt(maxData);
                        }
                        if (Objects.nonNull(minData)) {
                            range.gt(minData);
                        }
                        boolQueryBuilder.filter(range);
                    }
                } else {
                    MatchPhraseQueryBuilder filter = QueryBuilders.matchPhraseQuery(key, o);
                    boolQueryBuilder.filter(filter);
                }
            }
        }
        Map<String, Object> shouldFiled = esSearchBaseBO.getShouldFiled();
        if (shouldFiled != null) {
            BoolQueryBuilder shouldBoolQueryBuilder = QueryBuilders.boolQuery();
            for (String key : shouldFiled.keySet()) {
                Object o = shouldFiled.get(key);
                if (o instanceof List) {
                    List list = (List) o;
                    list.forEach(item -> {
                        MatchPhraseQueryBuilder filter = QueryBuilders.matchPhraseQuery(key, item);
                        shouldBoolQueryBuilder.should(filter);
                    });
                }  else {
                    MatchPhraseQueryBuilder filter = QueryBuilders.matchPhraseQuery(key, o);
                    shouldBoolQueryBuilder.should(filter);
                }
            }
            boolQueryBuilder.filter(shouldBoolQueryBuilder);
        }
        searchSourceBuilder.query(boolQueryBuilder);

        if (esSearchBaseBO.getSortFiled() != null) {
            Map<String, SortOrder> sortFiled = esSearchBaseBO.getSortFiled();
            for (String key : sortFiled.keySet()) {
                SortOrder sortOrder = sortFiled.get(key);
                searchSourceBuilder.sort(key, Objects.isNull(sortOrder)?SortOrder.ASC:sortOrder);
            }
        }
        if (esSearchBaseBO.getIncludeFields() != null || esSearchBaseBO.getExcludeFields() != null) {
            String[] includeFields = esSearchBaseBO.getIncludeFields();
            String[] excludeFields = esSearchBaseBO.getExcludeFields();
            searchSourceBuilder.fetchSource(includeFields, excludeFields);
        }

        return searchSourceBuilder;

    }

}
