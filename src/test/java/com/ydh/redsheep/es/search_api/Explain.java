//package com.ydh.redsheep.es.search_api;
//
//import org.apache.lucene.search.Explanation;
//import org.elasticsearch.action.explain.ExplainRequest;
//import org.elasticsearch.action.explain.ExplainResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.common.document.DocumentField;
//import org.elasticsearch.index.get.GetResult;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.annotation.Resource;
//import java.util.Map;
//
///**
//* 计算查询和特定文档的分数说明
//* @author : yangdehong
//* @date : 2020-06-01 14:43
//*/
//public class Explain {
//
//    private static Logger logger = LoggerFactory.getLogger(Explain.class);
//
//   @Resource
//    private RestHighLevelClient restHighLevelClient;
//
//    public static void main(String[] args) throws Exception {
//        ExplainRequest request = new ExplainRequest("yiwise", "test", "0Mc6S3IBQ5_9CYD7bU76");
//        request.query(QueryBuilders.termQuery("name.keyword", "人才3"));
//        ExplainResponse response = restHighLevelClient.explain(request, RequestOptions.DEFAULT);
//
//        String index = response.getIndex();
//        String type = response.getType();
//        String id = response.getId();
//
//        // 指示说明的文档是否存在
//        boolean exists = response.isExists();
//        // 指示说明的文档和提供的查询之间是否存在匹配项
//        boolean match = response.isMatch();
//        boolean hasExplanation = response.hasExplanation();
//        Explanation explanation = response.getExplanation();
//        System.out.println(exists+" "+match+" "+hasExplanation+" "+explanation);
//
//        GetResult getResult = response.getGetResult();
//        Map<String, Object> source = getResult.getSource();
//        Map<String, DocumentField> fields = getResult.getFields();
//        System.out.println(source+" "+fields);
//
//        restHighLevelClient.close();
//    }
//
//}
