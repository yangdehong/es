package com.ydh.redsheep.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchEntityMapper;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.EntityMapper;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

/**
 * @author : yangdehong
 * @date : 2019-08-28 13:39
 */
@Slf4j
@Configuration
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

    private String hostname = "es-cn-0pp1a4ci2000907e4.public.elasticsearch.aliyuncs.com";
    private Integer port = 9200;
    private String username = "elastic";
    private String password = "oN6vbqaaKlYsjAin";

    @Bean
    public ElasticsearchRestTemplate elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }

    @Override
    @Bean(name = "restHighLevelClient")
    public RestHighLevelClient elasticsearchClient() {
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(getRestClientBuilder());
        log.info("正在使用es的restHighLevelClient的api");
        return restHighLevelClient;
    }

    private RestClientBuilder getRestClientBuilder() {
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

    @Bean
    @Override
    public EntityMapper entityMapper() {

        ElasticsearchEntityMapper entityMapper = new ElasticsearchEntityMapper(
                elasticsearchMappingContext(), new DefaultConversionService()
        );
        entityMapper.setConversions(elasticsearchCustomConversions());

        return entityMapper;
    }

    @Bean
    @Override
    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
        return new ElasticsearchCustomConversions(
                Arrays.asList(new LocalDateTimeToDateConverter(), new DateToLocalDateTimeConverter(),
                        new LongToLocalDateTimeConverter()));
    }

    @WritingConverter
    static class LocalDateTimeToDateConverter implements Converter<LocalDateTime, Date> {

        @Override
        public Date convert(LocalDateTime localDateTime) {
            return new Date();
        }
    }

    @ReadingConverter
    static class DateToLocalDateTimeConverter implements Converter<Date, LocalDateTime> {

        @Override
        public LocalDateTime convert(Date date) {
            return LocalDateTime.now();
        }
    }

    @ReadingConverter
    static class LongToLocalDateTimeConverter implements Converter<Long, LocalDateTime> {

        @Override
        public LocalDateTime convert(Long date) {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault());
        }
    }
}
