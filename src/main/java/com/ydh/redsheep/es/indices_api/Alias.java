package com.ydh.redsheep.es.indices_api;

import com.ydh.redsheep.util.ElasticsearchConfig;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.metadata.AliasMetaData;

import java.util.Map;
import java.util.Set;

public class Alias {

    private static RestHighLevelClient restHighLevelClient = ElasticsearchConfig.restHighLevelClient();

    public static void main(String[] args) throws Exception {

//        optAliases();
//        exisistAliases();
        getAliases();

        restHighLevelClient.close();
    }

    /**
     * 获取别名
     * @throws Exception
     */
    private static void getAliases() throws Exception {
        GetAliasesRequest request = new GetAliasesRequest();
//        GetAliasesRequest requestWithAlias = new GetAliasesRequest("alias1");
//        GetAliasesRequest requestWithAliases = new GetAliasesRequest(new String[]{"alias1", "alias2"});
        request.aliases("test123");
        request.indices("test");
        GetAliasesResponse response = restHighLevelClient.indices().getAlias(request, RequestOptions.DEFAULT);
        Map<String, Set<AliasMetaData>> aliases = response.getAliases();
        System.out.println(aliases);
    }

    /**
     * 是否存在别名
     * @throws Exception
     */
    private static void exisistAliases() throws Exception {
        GetAliasesRequest request = new GetAliasesRequest();
//        GetAliasesRequest requestWithAlias = new GetAliasesRequest("alias1");
//        GetAliasesRequest requestWithAliases = new GetAliasesRequest(new String[]{"alias1", "alias2"});
        request.aliases("test123");
        request.indices("test");
        boolean exists = restHighLevelClient.indices().existsAlias(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    /**
     * 指定索引别名，索引别名API允许对别名使用名称进行别名，所有API都会自动将别名转换为实际的索引名称。
     * @throws Exception
     */
    private static void optAliases() throws Exception {
        IndicesAliasesRequest request = new IndicesAliasesRequest();
        IndicesAliasesRequest.AliasActions aliasAction = new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD)
                        .index("test")
                        .alias("test123");
        request.addAliasAction(aliasAction);

//        // alias1使用字段上的可选过滤器创建别名year
//        IndicesAliasesRequest.AliasActions addIndexAction = new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD)
//                        .index("test")
//                        .alias("alias1")
//                        .filter("{\"term\":{\"year\":2016}}");
//        // 创建alias2与两个索引和可选路由关联的别名
//        IndicesAliasesRequest.AliasActions addIndicesAction = new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD)
//                        .indices("test", "yiwise")
//                        .alias("alias2")
//                        .routing("1");
//        // 删除关联的别名 alias3
//        IndicesAliasesRequest.AliasActions removeAction = new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.REMOVE)
//                        .index("test")
//                        .alias("alias3");
//        // remove_index就像删除索引API
//        IndicesAliasesRequest.AliasActions removeIndexAction = new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.REMOVE_INDEX)
//                        .index("test");

        AcknowledgedResponse indicesAliasesResponse = restHighLevelClient.indices().updateAliases(request, RequestOptions.DEFAULT);
        boolean acknowledged = indicesAliasesResponse.isAcknowledged();
        System.out.println(acknowledged);
    }

}
