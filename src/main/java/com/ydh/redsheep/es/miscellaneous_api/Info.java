package com.ydh.redsheep.es.miscellaneous_api;

import com.ydh.redsheep.util.ElasticsearchConfig;
import org.elasticsearch.Build;
import org.elasticsearch.Version;
import org.elasticsearch.action.main.MainResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.ClusterName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* 检索群集信息
* @author : yangdehong
* @date : 2020-06-01 15:17
*/
public class Info {

    private static Logger logger = LoggerFactory.getLogger(Info.class);

    private static RestHighLevelClient restHighLevelClient = ElasticsearchConfig.restHighLevelClient();

    public static void main(String[] args) throws Exception {
        MainResponse response = restHighLevelClient.info(RequestOptions.DEFAULT);

        // 检索群集的名称为
        ClusterName clusterName = response.getClusterName();
        // 检索集群的唯一标识符
        String clusterUuid = response.getClusterUuid();
        // 检索已在其上执行请求的节点的名称
        String nodeName = response.getNodeName();
        // 检索已在其上执行请求的节点的版本
        Version version = response.getVersion();
        // 检索已在其上执行请求的节点的构建信息
        Build build = response.getBuild();

        System.out.println(clusterName+" "+clusterUuid+" "+nodeName+" "+version+" "+build);

        restHighLevelClient.close();

    }

}
