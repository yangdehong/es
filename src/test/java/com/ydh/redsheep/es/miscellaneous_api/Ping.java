//package com.ydh.redsheep.es.miscellaneous_api;
//
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.annotation.Resource;
//
///**
//* 检查群集是否已启动并且可用于处理请求
//* @author : yangdehong
//* @date : 2020-06-01 15:17
//*/
//public class Ping {
//
//    private static Logger logger = LoggerFactory.getLogger(Ping.class);
//
//   @Resource
//    private RestHighLevelClient restHighLevelClient;
//
//    public static void main(String[] args) throws Exception {
//        boolean ping = restHighLevelClient.ping(RequestOptions.DEFAULT);
//
//        System.out.println(ping);
//
//        restHighLevelClient.close();
//
//    }
//
//}
