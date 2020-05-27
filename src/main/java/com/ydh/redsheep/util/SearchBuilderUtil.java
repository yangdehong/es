package com.ydh.redsheep.util;

import com.ydh.redsheep.model.EsRangeBO;
import com.ydh.redsheep.model.EsSearchBaseBO;
import com.ydh.redsheep.model.ParamFieldBO;
import com.ydh.redsheep.model.ParamFieldFuzzyBO;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.util.Map;
import java.util.Objects;

/**
*
* @author : yangdehong
* @date : 2020-05-26 15:28
*/
public class SearchBuilderUtil {

    /**
     * 构建查询条件对象
     * @param esSearchBaseBO
     * @return
     */
    public static SearchSourceBuilder getSearchBuilder(EsSearchBaseBO esSearchBaseBO) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 排序
        if (Objects.nonNull(esSearchBaseBO.getSortFiled())) {
            Map<String, SortOrder> sortFiled = esSearchBaseBO.getSortFiled();
            for (String key : sortFiled.keySet()) {
                SortOrder sortOrder = sortFiled.get(key);
                searchSourceBuilder.sort(key, Objects.isNull(sortOrder)?SortOrder.ASC:sortOrder);
            }
        }
        // 展示那些字段
        if (Objects.nonNull(esSearchBaseBO.getIncludeFields()) || Objects.nonNull(esSearchBaseBO.getExcludeFields())) {
            String[] includeFields = esSearchBaseBO.getIncludeFields();
            String[] excludeFields = esSearchBaseBO.getExcludeFields();
            searchSourceBuilder.fetchSource(includeFields, excludeFields);
        }
        /************* 单个条件匹配的 ****************/
        // term完全匹配
        if (Objects.nonNull(esSearchBaseBO.getTermField())) {
            ParamFieldBO termField = esSearchBaseBO.getTermField();
            String field = termField.getFieldName();
            Object value = termField.getValue();
            QueryBuilder queryBuilder = QueryBuilders.termQuery(field, value);
            searchSourceBuilder.query(queryBuilder);
        }
        // 前缀匹配
        if (Objects.nonNull(esSearchBaseBO.getPrefixField())) {
            ParamFieldBO prefixField = esSearchBaseBO.getPrefixField();
            String field = prefixField.getFieldName();
            String value = (String) prefixField.getValue();
            QueryBuilder queryBuilder = QueryBuilders.prefixQuery(field, value);
            searchSourceBuilder.query(queryBuilder);
        }
        // fuzzy模糊匹配
        if (Objects.nonNull(esSearchBaseBO.getFuzzyField())) {
            ParamFieldFuzzyBO fuzzyField = esSearchBaseBO.getFuzzyField();
            String field = fuzzyField.getFieldName();
            Object value = fuzzyField.getValue();
            QueryBuilder queryBuilder = QueryBuilders.fuzzyQuery(field, value).fuzziness(fuzzyField.getFuzziness())
                    .prefixLength(fuzzyField.getPrefixLength()).maxExpansions(fuzzyField.getMaxExpansions());
            searchSourceBuilder.query(queryBuilder);
        }
        // phrase匹配
        if (Objects.nonNull(esSearchBaseBO.getPhraseField())) {
            ParamFieldBO phraseField = esSearchBaseBO.getPhraseField();
            String field = phraseField.getFieldName();
            Object value = phraseField.getValue();
            QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery(field, value);
            searchSourceBuilder.query(queryBuilder);
        }
        // phrase前缀匹配
        if (Objects.nonNull(esSearchBaseBO.getPhrasePrefixField())) {
            ParamFieldBO phrasePrefixField = esSearchBaseBO.getPhrasePrefixField();
            String field = phrasePrefixField.getFieldName();
            Object value = phrasePrefixField.getValue();
            QueryBuilder queryBuilder = QueryBuilders.matchPhrasePrefixQuery(field, value);
            searchSourceBuilder.query(queryBuilder);
        }
        // match匹配
        if (Objects.nonNull(esSearchBaseBO.getMatchField())) {
            ParamFieldBO matchField = esSearchBaseBO.getMatchField();
            String field = matchField.getFieldName();
            Object value = matchField.getValue();
            QueryBuilder queryBuilder = QueryBuilders.matchQuery(field, value);
            searchSourceBuilder.query(queryBuilder);
        }
        // 范围匹配
        if (Objects.nonNull(esSearchBaseBO.getEsRangeBO())) {
            EsRangeBO esRangeBO = esSearchBaseBO.getEsRangeBO();
            String key = esRangeBO.getFiledName();
            Object minValue = esRangeBO.getMinValue();
            Object maxValue = esRangeBO.getMaxValue();
            if (Objects.nonNull(maxValue) || Objects.nonNull(minValue)) {
                RangeQueryBuilder range = QueryBuilders.rangeQuery(key);
                if (Objects.nonNull(maxValue)) {
                    range.lte(maxValue);
                }
                if (Objects.nonNull(minValue)) {
                    range.gte(minValue);
                }
                searchSourceBuilder.query(range);
            }
        }
        // match匹配
        if (Objects.nonNull(esSearchBaseBO.getMatchField())) {
            ParamFieldBO matchField = esSearchBaseBO.getMatchField();
            String field = matchField.getFieldName();
            Object value = matchField.getValue();
            QueryBuilder queryBuilder = QueryBuilders.matchQuery(field, value);
            searchSourceBuilder.query(queryBuilder);
        }
        // 字段是否存在匹配
        if (Objects.nonNull(esSearchBaseBO.getExistsField())) {
            QueryBuilder queryBuilder = QueryBuilders.existsQuery(esSearchBaseBO.getExistsField());
            searchSourceBuilder.query(queryBuilder);
        }
        // 正则匹配
        if (Objects.nonNull(esSearchBaseBO.getRegexpField())) {
            ParamFieldBO regexpField = esSearchBaseBO.getRegexpField();
            String field = regexpField.getFieldName();
            String value = (String) regexpField.getValue();
            QueryBuilder queryBuilder = QueryBuilders.regexpQuery(field, value);
            searchSourceBuilder.query(queryBuilder);
        }

        /************* 多个条件匹配的 ****************/
//
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        Map<String, Object> filterFiled = esSearchBaseBO.getFilterFiled();
//        if (filterFiled != null) {
//            for (String key : filterFiled.keySet()) {
//                Object o = filterFiled.get(key);
//                if (o instanceof List) {
//                    List list = (List) o;
//                    list.forEach(item -> {
//                        MatchPhraseQueryBuilder filter = QueryBuilders.matchPhraseQuery(key, item);
//                        boolQueryBuilder.filter(filter);
//                    });
//                } else if (o instanceof EsRangeBO) {
//                    EsRangeBO esRangeBO = (EsRangeBO) o;
//                    Object minData = esRangeBO.getMinData();
//                    Object maxData = esRangeBO.getMaxData();
//                    if (Objects.nonNull(maxData) || Objects.nonNull(minData)) {
//                        RangeQueryBuilder range = QueryBuilders.rangeQuery(key);
//                        if (Objects.nonNull(maxData)) {
//                            range.lt(maxData);
//                        }
//                        if (Objects.nonNull(minData)) {
//                            range.gt(minData);
//                        }
//                        boolQueryBuilder.filter(range);
//                    }
//                } else {
//                    MatchPhraseQueryBuilder filter = QueryBuilders.matchPhraseQuery(key, o);
//                    boolQueryBuilder.filter(filter);
//                }
//            }
//        }
//        Map<String, Object> shouldFiled = esSearchBaseBO.getShouldFiled();
//        if (shouldFiled != null) {
//            BoolQueryBuilder shouldBoolQueryBuilder = QueryBuilders.boolQuery();
//            for (String key : shouldFiled.keySet()) {
//                Object o = shouldFiled.get(key);
//                if (o instanceof List) {
//                    List list = (List) o;
//                    list.forEach(item -> {
//                        MatchPhraseQueryBuilder filter = QueryBuilders.matchPhraseQuery(key, item);
//                        shouldBoolQueryBuilder.should(filter);
//                    });
//                }  else {
//                    MatchPhraseQueryBuilder filter = QueryBuilders.matchPhraseQuery(key, o);
//                    shouldBoolQueryBuilder.should(filter);
//                }
//            }
//            boolQueryBuilder.filter(shouldBoolQueryBuilder);
//        }
//        searchSourceBuilder.query(boolQueryBuilder);


//        /*********** 高亮 **********/
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        // 高亮设置
//        highlightBuilder.preTags("<span style=\"color:red\">");
//        highlightBuilder.postTags("</span>");
//        // 高亮查询字段
//        highlightBuilder.field("name.keyword");
////        highlightBuilder.field("sort");
////        // 如果要多个字段高亮,这项要为false
////        highlightBuilder.requireFieldMatch(false);
////        // 下面这两项,如果你要高亮如文字内容等有很多字的字段,必须配置,不然会导致高亮不全,文章内容缺失等
////        // 最大高亮分片数
////        highlightBuilder.fragmentSize(800000);
////        // 从第一个分片获取高亮片段
////        highlightBuilder.numOfFragments(0);
//
//        searchSourceBuilder.highlighter(highlightBuilder);

//        // 聚合查询
//        TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_sex").field("sex.keyword")
//                .subAggregation(AggregationBuilders.sum("sort").field("sort"));
//        searchSourceBuilder.aggregation(aggregation);

//        // 提示
//        CompletionSuggestionBuilder termSuggestionBuilder = SuggestBuilders.completionSuggestion("address.keyword").prefix("中国").size(20).skipDuplicates(true);
//        SuggestBuilder suggestBuilder = new SuggestBuilder();
//        suggestBuilder.addSuggestion("suggest", termSuggestionBuilder);
//        searchSourceBuilder.suggest(suggestBuilder);

        // 可用来对一个特定的查询操作中的请求和聚合进行分析
//        searchSourceBuilder.profile(true);

        return searchSourceBuilder;

    }


}
