package com.ydh.redsheep.es.document_api;

import com.ydh.redsheep.util.ElasticsearchConfig;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElasticsearchReIndex {

    private static Logger logger = LoggerFactory.getLogger(ElasticsearchMultiDocumentMain.class);

    private static RestHighLevelClient restHighLevelClient = ElasticsearchConfig.restHighLevelClient();

    public static void reIndex() {


    }

    public static void rethrottle() {


    }


}
