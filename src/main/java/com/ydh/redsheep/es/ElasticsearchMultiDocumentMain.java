package com.ydh.redsheep.es;

import com.ydh.redsheep.model.EsBaseBO;
import com.ydh.redsheep.model.EsBulkBO;
import com.ydh.redsheep.model.EsOptBulkBO;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.TermVectorsResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
*
* @author : yangdehong
* @date : 2019-08-28 17:25
*/
public class ElasticsearchMultiDocumentMain {

    private static Logger logger = LoggerFactory.getLogger(ElasticsearchMultiDocumentMain.class);

    private static RestHighLevelClient restHighLevelClient = ElasticsearchConfig.restHighLevelClient();

    public static void main(String[] args) throws Exception {
//        List<EsBulkBO> list = new ArrayList<>();
//        String index = "yiwise";
//        String type = "person";
//        for (int i = 0; i < 100; i++) {
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("name", "测试"+i%19);
//            jsonObject.addProperty("sex", i%2==0?"男":"女");
//            jsonObject.addProperty("age", i%100);
//            jsonObject.addProperty("date", LocalDateTime.now().minusDays(i).toString());
//            jsonObject.addProperty("email", new Random().nextLong() +"@163.com");
//            EsBulkBO esBulkBO = new EsBulkBO(DocWriteRequest.OpType.INDEX, jsonObject.toString());
//            esBulkBO.setIndex(index);
//            esBulkBO.setType(type);
//            esBulkBO.setId(i+"");
//            list.add(esBulkBO);
//        }
//        BulkResponse response = bulk(list);
//        for (BulkItemResponse bulkItemResponse : response) {
//            DocWriteResponse itemResponse = bulkItemResponse.getResponse();
//            switch (bulkItemResponse.getOpType()) {
//                case INDEX:
//                    System.out.println("11111");
//                    break;
//                case CREATE:
//                    System.out.println("22222");
//                    break;
//                case UPDATE:
//                    System.out.println("33333");
//                    break;
//                case DELETE:
//                    System.out.println("44444");
//                    break;
//            }
//        }





        restHighLevelClient.close();
    }

    /**
     * 批量操作，可增删改
     * @param list
     * @return
     */
    public static BulkResponse bulk(List<EsBulkBO> list) {
        BulkRequest request = new BulkRequest();
        list.forEach(item -> {
            String index = item.getIndex();
            String type = item.getType();
            String id = item.getId();
            String json = item.getJson();
            switch (item.getOpType()) {
                case INDEX:
                    request.add(new IndexRequest(index, type, id).source(json, XContentType.JSON));
                    break;
                case CREATE:
                    break;
                case UPDATE:
                    request.add(new UpdateRequest(index, type, id).doc(json, XContentType.JSON));
                    break;
                case DELETE:
                    request.add(new DeleteRequest(index, type, id));
                    break;

            }
        });

        try {
            BulkResponse bulkResponse = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            return bulkResponse;
        } catch (IOException e) {
            logger.error("bulk错误", e);
        }

        return null;

    }

    /**
     * 获取多个
     * @param list
     * @return
     */
    public MultiGetResponse multiGet(List<EsBaseBO> list) {
        MultiGetRequest request = new MultiGetRequest();
        list.forEach(item -> {
            request.add(new MultiGetRequest.Item(item.getIndex(), item.getType(), item.getId()));
        });

        try {
            MultiGetResponse response = restHighLevelClient.mget(request, RequestOptions.DEFAULT);
            return response;
        } catch (IOException e) {
            logger.error("multiGet错误", e);
        }

        return null;

    }

    public BulkByScrollResponse updateByQuery(EsOptBulkBO esOptBulkBO) {
        UpdateByQueryRequest request = new UpdateByQueryRequest(esOptBulkBO.getIndices());
        request.setConflicts("proceed");
        request.setRefresh(true);
        Map<String, Object> fullMatchField = esOptBulkBO.getFullMatchField();
        if (Objects.nonNull(fullMatchField)) {
            for (String field : fullMatchField.keySet()) {
                request.setQuery(QueryBuilders.matchPhraseQuery(field, fullMatchField.get(field)));
            }
        }

        try {
            BulkByScrollResponse bulkResponse = restHighLevelClient.updateByQuery(request, RequestOptions.DEFAULT);
            return bulkResponse;
        } catch (IOException e) {
            logger.error("updateByQuery错误", e);
        }

        return null;
    }

    public BulkByScrollResponse deleteByQuery(EsOptBulkBO esOptBulkBO) {
        DeleteByQueryRequest request = new DeleteByQueryRequest(esOptBulkBO.getIndices());
        request.setConflicts("proceed");
        request.setRefresh(true);
        request.setQuery(QueryBuilders.boolQuery().mustNot(QueryBuilders.matchPhraseQuery("sex", "小天才3")));
        try {
            BulkByScrollResponse bulkResponse = restHighLevelClient.deleteByQuery(request, RequestOptions.DEFAULT);
            return bulkResponse;
        } catch (IOException e) {
            logger.error("deleteByQuery错误", e);
        }

        return null;

    }

    /**
     * 文档统计
     * @return
     */
    public static TermVectorsResponse mulitTermVectors() {

        return null;

    }


}
