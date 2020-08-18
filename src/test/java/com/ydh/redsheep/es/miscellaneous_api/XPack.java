//package com.ydh.redsheep.es.miscellaneous_api;
//
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.client.xpack.XPackUsageRequest;
//import org.elasticsearch.client.xpack.XPackUsageResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.annotation.Resource;
//import java.util.Map;
//
///**
//* 检索有关已安装的X-Pack功能的常规信息----使用情况的详细信息
//* @author : yangdehong
//* @date : 2020-06-01 15:17
//*/
//public class XPack {
//
//    private static Logger logger = LoggerFactory.getLogger(XPack.class);
//
//   @Resource
//    private RestHighLevelClient restHighLevelClient;
//
//    public static void main(String[] args) throws Exception {
////        XPackInfoRequest request = new XPackInfoRequest();
////        request.setVerbose(true);
////        request.setCategories(EnumSet.of(
////                XPackInfoRequest.Category.BUILD,
////                XPackInfoRequest.Category.LICENSE,
////                XPackInfoRequest.Category.FEATURES));
////        XPackInfoResponse response = restHighLevelClient.xpack().info(request, RequestOptions.DEFAULT);
////
////        // 包含从其构建Elasticsearch的提交哈希以及创建x-pack模块的时间戳
////        XPackInfoResponse.BuildInfo build = response.getBuildInfo();
////        // 包含集群使用的许可证类型及其到期日期
////        XPackInfoResponse.LicenseInfo license = response.getLicenseInfo();
////        // 包含Map从功能名称到功能信息的信息，例如该功能是否在当前许可下可用。
////        XPackInfoResponse.FeatureSetsInfo features = response.getFeatureSetsInfo();
////
////        System.out.println(build);
////        System.out.println(license);
////        System.out.println(features);
//
//
//        XPackUsageRequest request = new XPackUsageRequest();
//        XPackUsageResponse response = restHighLevelClient.xpack().usage(request, RequestOptions.DEFAULT);
//        Map<String, Map<String, Object>> usages = response.getUsages();
//        Map<String, Object> monitoringUsage = usages.get("monitoring");
//        System.out.println(monitoringUsage);
//
//
//        restHighLevelClient.close();
//
//    }
//
//}
