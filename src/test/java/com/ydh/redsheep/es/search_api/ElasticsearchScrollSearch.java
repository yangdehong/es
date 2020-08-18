//package com.ydh.redsheep.es.search_api;
//
//import com.ydh.redsheep.model.EsSearchScrollBO;
//import com.ydh.redsheep.model.ParamFieldBO;
//import com.ydh.redsheep.util.SearchBuilderUtil;
//import org.elasticsearch.action.search.*;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.common.unit.TimeValue;
//import org.elasticsearch.search.Scroll;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//
//public class ElasticsearchScrollSearch {
//
//    private static Logger logger = LoggerFactory.getLogger(ElasticsearchScrollSearch.class);
//
//   @Resource
//    private RestHighLevelClient restHighLevelClient;
//
//    public static void main(String[] args) throws Exception {
//        EsSearchScrollBO esSearchScrollBO = new EsSearchScrollBO();
//        esSearchScrollBO.setIndex("yiwise");
//        esSearchScrollBO.setType("test");
//        esSearchScrollBO.setSize(10);
//
//        ParamFieldBO matchField = new ParamFieldBO();
//        matchField.setFieldName("address");
//        matchField.setValue("中国杭州市");
//        esSearchScrollBO.setMatchField(matchField);
//
//        // 设置超时时间，当超过这个时间scroll就不能用了
//        final Scroll scroll = new Scroll(TimeValue.timeValueSeconds(30L));
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
//
//        restHighLevelClient.close();
//
//    }
//
//    /**
//     * 滚动查询-pre
//     * @param esSearchScrollBO
//     * @param scroll
//     * @return
//     */
//    public static SearchResponse searchScroll(EsSearchScrollBO esSearchScrollBO, Scroll scroll) {
//        SearchRequest searchRequest = new SearchRequest(esSearchScrollBO.getIndex());
//        searchRequest.types(esSearchScrollBO.getType());
//        searchRequest.scroll(scroll);
//        SearchSourceBuilder searchSourceBuilder = SearchBuilderUtil.getSearchBuilder(esSearchScrollBO);
//        searchSourceBuilder.size(esSearchScrollBO.getSize());
//        searchRequest.source(searchSourceBuilder);
//        try {
//            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
//            return response;
//        } catch (IOException e) {
//            log.error("启用scroll错误", e);
//        }
//        return null;
//    }
//    /**
//     * 持续滚动
//     * @param scroll
//     * @param scrollId
//     * @return
//     */
//    public static SearchResponse scroll(Scroll scroll, String scrollId) {
//        SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
//        scrollRequest.scroll(scroll);
//        try {
//            SearchResponse response = restHighLevelClient.scroll(scrollRequest, RequestOptions.DEFAULT);
//            return response;
//        } catch (IOException e) {
//            log.error("滚动scroll错误", e);
//        }
//        return null;
//    }
//    /**
//     * 清理滚动
//     * @param scrollId
//     * @return
//     */
//    public static ClearScrollResponse clearScroll(String scrollId) {
//        ClearScrollRequest request = new ClearScrollRequest();
//        request.addScrollId(scrollId);
//        try {
//            ClearScrollResponse response = restHighLevelClient.clearScroll(request, RequestOptions.DEFAULT);
//            return response;
//        } catch (IOException e) {
//            log.error("清理scroll错误", e);
//        }
//        return null;
//    }
//
//
//}
