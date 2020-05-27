package com.ydh.redsheep.es.document_api;

import com.ydh.redsheep.util.ElasticsearchConfig;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.TermVectorsRequest;
import org.elasticsearch.client.core.TermVectorsResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
*
* @author : yangdehong
* @date : 2019-08-28 17:25
*/
public class ElasticsearchDocumentMain {

    private static Logger logger = LoggerFactory.getLogger(ElasticsearchDocumentMain.class);

    private static RestHighLevelClient restHighLevelClient = ElasticsearchConfig.restHighLevelClient();

    public static void main(String[] args) throws Exception {
//        for (int i = 0; i < 100; i++) {
//            PersonPO personBO = new PersonPO();
//            personBO.setName("人才"+i);
//            personBO.setSex(i%2==0?"男":"女");
//            personBO.setAddress("中国浙江省杭州市"+new Random().nextLong());
//            personBO.setBirthDay(LocalDateTime.now().minusDays(i));
//            personBO.setEmail(new Random().nextLong() +"@qq.com");
//            personBO.setSort(i%39);
//            IndexResponse response = createDocument("yiwise", "test", JsonUtils.object2String(personBO));
//            String index = response.getIndex();
//            String type = response.getType();
//            String id = response.getId();
//            long version = response.getVersion();
//            System.out.println(index + "===" + id);
//        }
//
//        GetResponse response = getDocument("yiwise", "test", "YTERS3IBLhCRLgK0wGjb");
//        String index = response.getIndex();
//        String type = response.getType();
//        String id = response.getId();
//        if (response.isExists()) {
//            long version = response.getVersion();
//            String sourceAsString = response.getSourceAsString();
//            Map<String, Object> sourceAsMap = response.getSourceAsMap();
//            byte[] sourceAsBytes = response.getSourceAsBytes();
//            System.out.println(sourceAsString);
//        }
//
//        boolean exists = existsDocument("yiwise", "test", "YTERS3IBLhCRLgK0wGjb");
//        System.out.println(exists);
//
//        DeleteResponse response = deleteDocument("yiwise", "test", "ajERS3IBLhCRLgK0wWiC");
//        String index = response.getIndex();
//        String type = response.getType();
//        String id = response.getId();
//        long version = response.getVersion();
//        ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
//        System.out.println(index + "===" + id);
//
//        PersonPO personBO = new PersonPO();
//        personBO.setName("杨德宏");
//        personBO.setSex("男");
//        personBO.setAddress("中国浙江省杭州市88888888");
//        personBO.setBirthDay(LocalDateTime.now());
//        personBO.setEmail("987654321@qq.com");
//        UpdateResponse response = updateDocument("yiwise", "test", "YTERS3IBLhCRLgK0wGjb", JsonUtils.object2String(personBO));
//        String index = response.getIndex();
//        String type = response.getType();
//        String id = response.getId();
//        long version = response.getVersion();
//        System.out.println(index + "===" + id);
//
//        PersonPO personBO = new PersonPO();
//        personBO.setName("杨德宏");
//        personBO.setSex("男");
//        personBO.setAddress("中国浙江省杭州市999999999");
//        personBO.setBirthDay(LocalDateTime.now());
//        personBO.setEmail("987654321@qq.com");
//        UpdateResponse response = upsertDocument("yiwise", "test", "1", JsonUtils.object2String(personBO));
//        String index = response.getIndex();
//        String type = response.getType();
//        String id = response.getId();
//        long version = response.getVersion();
//        System.out.println(index + "===" + id);
//
        String[] fields = {"sex", "name"};
        TermVectorsResponse response = termVectors("yiwise", "test", "0Mc6S3IBQ5_9CYD7bU76", fields);
        // 是否有找到匹配的
        String index = response.getIndex();
        String type = response.getType();
        String id = response.getId();
        System.out.println(index + "===" + id);
        boolean found = response.getFound();
        if (found) {
            for (TermVectorsResponse.TermVector tv : response.getTermVectorsList()) {
                String fieldname = tv.getFieldName();
                Integer docCount = tv.getFieldStatistics().getDocCount();
                Long sumTotalTermFreq = tv.getFieldStatistics().getSumTotalTermFreq();
                Long sumDocFreq = tv.getFieldStatistics().getSumDocFreq();
                if (tv.getTerms() != null) {
                    List<TermVectorsResponse.TermVector.Term> terms = tv.getTerms();
                    for (TermVectorsResponse.TermVector.Term term : terms) {
                        String termStr = term.getTerm();
                        Integer termFreq = term.getTermFreq();
                        Integer docFreq = term.getDocFreq();
                        Long totalTermFreq = term.getTotalTermFreq();
                        Float score = term.getScore();
                        if (term.getTokens() != null) {
                            List<TermVectorsResponse.TermVector.Token> tokens = term.getTokens();
                            for (TermVectorsResponse.TermVector.Token token : tokens) {
                                Integer position = token.getPosition();
                                Integer startOffset = token.getStartOffset();
                                Integer endOffset = token.getEndOffset();
                                String payload = token.getPayload();
                                System.out.println(position + " " + startOffset + " " + endOffset + " " + payload);
                            }
                        }
                    }
                }
            }
        }

        restHighLevelClient.close();
    }

    /**
     * 创建document
     * @param key
     * @param json
     * @return
     */
    public static IndexResponse createDocument(String key, String type, String json) {
        IndexRequest request = new IndexRequest(key, type);
        request.source(json, XContentType.JSON);

        try {
            IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            return response;
        } catch (IOException e) {
            logger.error("创建Document错误", e);
        }
        return null;
    }
    public static IndexResponse createDocument(String key, String type, String id, String json) {
        IndexRequest request = new IndexRequest(key, type);
        request.id(id);
        request.source(json, XContentType.JSON);

        try {
            IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            return response;
        } catch (IOException e) {
            logger.error("创建Document错误", e);
        }
        return null;
    }

    /**
     * 获取document
     * @param key
     * @param id
     * @return
     */
    public static GetResponse getDocument(String key, String type, String id) {
        GetRequest request = new GetRequest(key, type, id);
        try {
            GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
            return response;
        } catch (IOException e) {
            logger.error("查询Document错误", e);
        }
        return null;
    }

    /**
     * 是否存在这个document
     * @param key
     * @param id
     * @return
     */
    public static boolean existsDocument(String key, String type, String id) {
        GetRequest getRequest = new GetRequest(key, type, id);
        try {
            boolean exists = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
            return exists;
        } catch (IOException e) {
            logger.error("查询Document错误", e);
        }
        return false;
    }

    /**
     * 是否存在这个document异步
     * @param key
     * @param id
     * @return
     */
    public static boolean existsDocumentAsync(String key, String type, String id) {
        GetRequest getRequest = new GetRequest(key, type, id);

        ActionListener<Boolean> listener = new ActionListener<Boolean>() {
            @Override
            public void onResponse(Boolean exists) {
            }

            @Override
            public void onFailure(Exception e) {
            }
        };
        restHighLevelClient.existsAsync(getRequest, RequestOptions.DEFAULT, listener);
        return false;
    }


    /**
     * 删除document
     *
     * @param key
     * @param id
     */
    public static DeleteResponse deleteDocument(String key, String type, String id) {
        DeleteRequest request = new DeleteRequest(key, type, id);
        try {
            DeleteResponse deleteResponse = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
            return deleteResponse;
        } catch (IOException e) {
            logger.error("删除Document错误", e);
        }
        return null;
    }

    /**
     * 修改document
     *
     * @param key
     * @param id
     * @param json
     * @return
     */
    public static UpdateResponse updateDocument(String key, String type, String id, String json) {
        UpdateRequest request = new UpdateRequest(key, type, id);
        request.doc(json, XContentType.JSON);

        try {
            UpdateResponse updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);
            return updateResponse;
        } catch (IOException e) {
            logger.error("修改Document错误", e);
        }

        return null;
    }

    /**
     * 修改或者新增document
     * @param key
     * @param id
     * @param json
     * @return
     */
    public static UpdateResponse upsertDocument(String key, String type, String id, String json) {
        UpdateRequest request = new UpdateRequest(key, type, id);
        request.doc(json, XContentType.JSON);
        request.upsert(json, XContentType.JSON);

        try {
            UpdateResponse updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);
            return updateResponse;
        } catch (IOException e) {
            logger.error("修改Document错误", e);
        }

        return null;
    }

    /**
     * 文档统计
     * @param key
     * @param type
     * @param id
     * @param field
     * @return
     */
    public static TermVectorsResponse termVectors(String key, String type, String id, String[] field) {
        TermVectorsRequest request = new TermVectorsRequest(key, type, id);
        request.setFields(field);
        try {
            TermVectorsResponse response = restHighLevelClient.termvectors(request, RequestOptions.DEFAULT);
            return response;
        } catch (IOException e) {
            logger.error("termVectors错误", e);
        }

        return null;

    }

}
