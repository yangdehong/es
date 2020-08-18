//package com.ydh.redsheep.es.search_api;
//
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
//
//public class ElasticsearchSearch {
//
//    private static Logger logger = LoggerFactory.getLogger(ElasticsearchSearch.class);
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
////        Map<String, SortOrder> sortFiled = new LinkedHashMap<>();
////        sortFiled.put("sort", SortOrder.ASC);
////        esSearchPageBO.setSortFiled(sortFiled);
//
////        String[] includeFields = new String[] {"name", "sex", "age", "email"};
////        String[] excludeFields = new String[] {"email"};
////        esSearchPageBO.setIncludeFields(includeFields);
////        esSearchPageBO.setExcludeFields(excludeFields);
//
////        ParamFieldBO termField = new ParamFieldBO();
////        termField.setFieldName("name.keyword");
////        termField.setValue("人才3");
////        esSearchPageBO.setTermField(termField);
//
////        ParamFieldBO prefixField = new ParamFieldBO();
////        prefixField.setFieldName("name.keyword");
////        prefixField.setValue("人才3");
////        esSearchPageBO.setPrefixField(prefixField);
//
////        ParamFieldFuzzyBO fuzzyField = new ParamFieldFuzzyBO();
////        fuzzyField.setFieldName("name.keyword");
////        fuzzyField.setValue("人才3");
////        fuzzyField.setFuzziness(Fuzziness.ONE);
////        fuzzyField.setPrefixLength(0);
////        fuzzyField.setMaxExpansions(50);
////        esSearchPageBO.setFuzzyField(fuzzyField);
//
////        ParamFieldBO phraseField = new ParamFieldBO();
////        phraseField.setFieldName("address.keyword");
////        phraseField.setValue("中国浙江省杭州市-4583156876345471917");
////        esSearchPageBO.setPhraseField(phraseField);
//
////        ParamFieldBO phrasePrefixField = new ParamFieldBO();
////        phrasePrefixField.setFieldName("address.keyword");
////        phrasePrefixField.setValue("中国浙江省杭州市-4583156876345471917");
////        esSearchPageBO.setPhraseField(phrasePrefixField);
//
////        ParamFieldBO matchField = new ParamFieldBO();
////        matchField.setFieldName("address");
////        matchField.setValue("中国杭州市");
////        esSearchPageBO.setMatchField(matchField);
//
////        EsRangeBO esRangeBO = new EsRangeBO();
////        esRangeBO.setFiledName("sort");
////        esRangeBO.setMaxValue(3);
////        esRangeBO.setMinValue(3);
////        esSearchPageBO.setEsRangeBO(esRangeBO);
//
////        esSearchPageBO.setExistsField("name1");
//
////        ParamFieldBO regexField = new ParamFieldBO();
////        regexField.setFieldName("address.keyword");
////        regexField.setValue(".*杭州市.*");
////        esSearchPageBO.setRegexpField(regexField);
//
//
////        esSearchPageBO.setFilterFiled(filterFiled);
////        esSearchPageBO.setShouldFiled(shouldFiled);
//
//        SearchResponse response = searchCondition(esSearchPageBO);
//        SearchHits hits = response.getHits();
//        for (SearchHit hit : hits) {
//            System.out.println(hit.getSourceAsString());
//            // 高亮
////            System.out.println(hit.getHighlightFields());
//        }
//
////        // 聚合
////        Aggregation sort = response.getAggregations().get("by_sex");
////        System.out.println(sort);
//
////        // 提示
////        Suggest.Suggestion<? extends Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>> suggest_address = response.getSuggest().getSuggestion("suggest_address");
////        System.out.println(suggest_address);
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
