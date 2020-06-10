package com.ydh.redsheep.es.document_api;

import com.ydh.redsheep.model.EsBaseBO;
import com.ydh.redsheep.model.EsBulkBO;
import com.ydh.redsheep.model.EsOptBulkBO;
import com.ydh.redsheep.model.entity.PersonPO;
import com.ydh.redsheep.util.ElasticsearchConfig;
import com.ydh.redsheep.util.JsonUtils;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
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
import java.time.LocalDateTime;
import java.util.*;

/**
*
* @author : yangdehong
* @date : 2019-08-28 17:25
*/
public class ElasticsearchMultiDocument {

    private static Logger logger = LoggerFactory.getLogger(ElasticsearchMultiDocument.class);

    private static RestHighLevelClient restHighLevelClient = ElasticsearchConfig.restHighLevelClient();

    public static void main(String[] args) throws Exception {
        List<EsBulkBO> list = new ArrayList<>();
        String index = "yiwise";
        String type = "test";
        for (int i = 0; i < 100; i++) {
            PersonPO personPO = new PersonPO();
            personPO.setName("人才"+i);
            personPO.setSex(i%2==0?"男":"女");
            personPO.setAddress("中国浙江省杭州市"+new Random().nextLong());
            personPO.setBirthDay(LocalDateTime.now().minusDays(i));
            personPO.setEmail(new Random().nextLong() +"@qq.com");
            personPO.setSort(i%39);
            EsBulkBO esBulkBO = new EsBulkBO(DocWriteRequest.OpType.INDEX, JsonUtils.object2String(personPO));
            esBulkBO.setIndex(index);
            esBulkBO.setType(type);
            esBulkBO.setId(i+"");
            list.add(esBulkBO);
        }
        BulkResponse response = bulk(list);
        for (BulkItemResponse bulkItemResponse : response) {
            DocWriteResponse itemResponse = bulkItemResponse.getResponse();
            switch (bulkItemResponse.getOpType()) {
                case INDEX:
                case CREATE:
                    IndexResponse indexResponse = (IndexResponse) itemResponse;
                    System.out.println(indexResponse);
                    break;
                case UPDATE:
                    UpdateResponse updateResponse = (UpdateResponse) itemResponse;
                    System.out.println(updateResponse);
                    break;
                case DELETE:
                    DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
                    System.out.println(deleteResponse);
                    break;
            }
        }


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
