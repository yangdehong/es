package com.ydh.redsheep.es;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : yangdehong
 * @date : 2019-08-28 13:39
 */
public class ElasticsearchConfig {

    private static Logger logger = LoggerFactory.getLogger(ElasticsearchConfig.class);

    private static String hostname = "es-cn-0pp1a4ci2000907e4.public.elasticsearch.aliyuncs.com";
    private static Integer port = 9200;
    private static String username = "elastic";
    private static String password = "oN6vbqaaKlYsjAin";


    public static RestClient restClient() {

        RestClient restClient = getRestClientBuilder().build();

        return restClient;
    }

    public static RestHighLevelClient restHighLevelClient() {

        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(getRestClientBuilder());

        logger.info("正在使用es的restHighLevelClient的api");

        return restHighLevelClient;
    }

    private static RestClientBuilder getRestClientBuilder() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(hostname, port, "http"));
        restClientBuilder.setHttpClientConfigCallback((httpClientBuilder) -> {
            // 可以禁用抢占式身份验证，这意味着每个请求都将在没有授权头的情况下发送，以查看它是否被接受，并且在接收到HTTP 401响应后，它将使用基本身份验证头重新发送完全相同的请求。
//          httpClientBuilder.disableAuthCaching();
            return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                    // 设置线程数量
//                  .setDefaultIOReactorConfig(IOReactorConfig.custom().setIoThreadCount(1).build())
                    ;
        });
        return restClientBuilder;
    }



}
