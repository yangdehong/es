//package com.ydh.redsheep.es.search_api;
//
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.index.rankeval.*;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
///**
//* 一组搜索请求上评估排名搜索结果的质量
//* @author : yangdehong
//* @date : 2020-06-01 14:22
//*/
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class RankEval {
//
//    @Resource
//    private RestHighLevelClient restHighLevelClient;
//
//    @Test
//    public void test(String[] args) throws Exception {
//        EvaluationMetric metric = new PrecisionAtK();
//        List<RatedDocument> ratedDocs = new ArrayList<>();
//        ratedDocs.add(new RatedDocument("yiwise", "0Mc6S3IBQ5_9CYD7bU76", 1));
//        SearchSourceBuilder searchQuery = new SearchSourceBuilder();
//        searchQuery.query(QueryBuilders.matchQuery("name", "人才3"));
//        RatedRequest ratedRequest = new RatedRequest("kimchy_query", ratedDocs, searchQuery);
//        List<RatedRequest> ratedRequests = Arrays.asList(ratedRequest);
//        RankEvalSpec specification = new RankEvalSpec(ratedRequests, metric);
//        RankEvalRequest request = new RankEvalRequest(specification, new String[] { "posts" });
//
//        RankEvalResponse response = restHighLevelClient.rankEval(request, RequestOptions.DEFAULT);
//
//        double evaluationResult = response.getMetricScore();
//        Map<String, EvalQueryQuality> partialResults = response.getPartialResults();
//        EvalQueryQuality evalQuality = partialResults.get("kimchy_query");
//        double qualityLevel = evalQuality.metricScore();
//        List<RatedSearchHit> hitsAndRatings = evalQuality.getHitsAndRatings();
//        RatedSearchHit ratedSearchHit = hitsAndRatings.get(0);
//        MetricDetail metricDetails = evalQuality.getMetricDetails();
//        String metricName = metricDetails.getMetricName();
//        PrecisionAtK.Detail detail = (PrecisionAtK.Detail) metricDetails;
//    }
//
//}
