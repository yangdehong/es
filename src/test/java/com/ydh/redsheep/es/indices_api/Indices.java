//package com.ydh.redsheep.es.indices_api;
//
//import com.ydh.redsheep.util.JsonUtils;
//import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequest;
//import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
//import org.elasticsearch.action.admin.indices.analyze.DetailAnalyzeResponse;
//import org.elasticsearch.action.admin.indices.cache.clear.ClearIndicesCacheRequest;
//import org.elasticsearch.action.admin.indices.cache.clear.ClearIndicesCacheResponse;
//import org.elasticsearch.action.admin.indices.close.CloseIndexRequest;
//import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
//import org.elasticsearch.action.admin.indices.flush.FlushRequest;
//import org.elasticsearch.action.admin.indices.flush.FlushResponse;
//import org.elasticsearch.action.admin.indices.flush.SyncedFlushRequest;
//import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeRequest;
//import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeResponse;
//import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
//import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
//import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
//import org.elasticsearch.action.admin.indices.refresh.RefreshResponse;
//import org.elasticsearch.action.admin.indices.shrink.ResizeRequest;
//import org.elasticsearch.action.admin.indices.shrink.ResizeResponse;
//import org.elasticsearch.action.admin.indices.shrink.ResizeType;
//import org.elasticsearch.action.support.DefaultShardOperationFailedException;
//import org.elasticsearch.action.support.IndicesOptions;
//import org.elasticsearch.action.support.master.AcknowledgedResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.client.SyncedFlushResponse;
//import org.elasticsearch.client.indices.CreateIndexRequest;
//import org.elasticsearch.client.indices.CreateIndexResponse;
//import org.elasticsearch.client.indices.GetIndexRequest;
//import org.elasticsearch.client.indices.rollover.RolloverRequest;
//import org.elasticsearch.client.indices.rollover.RolloverResponse;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.unit.ByteSizeUnit;
//import org.elasticsearch.common.unit.ByteSizeValue;
//import org.elasticsearch.common.unit.TimeValue;
//import org.elasticsearch.common.xcontent.XContentBuilder;
//import org.elasticsearch.common.xcontent.XContentFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.annotation.Resource;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
///**
//*
//* @author : yangdehong
//* @date : 2020-06-01 15:32
//*/
//public class Indices {
//
//    private static Logger logger = LoggerFactory.getLogger(Indices.class);
//
//   @Resource
//    private RestHighLevelClient restHighLevelClient;
//
//    public static void main(String[] args) throws Exception {
////        analyze();
////        createIndex();
////        deleteIndex();
////        existsIndex();
////        closeIndex();
////        openIndex();
////        shrinkIndex();
////        splitIndex();
////        refreshIndex();
////        flushIndex();
////        flushSyncedIndex();
////        clearIndex();
////        forceMergeIndex();
//        rolloverIndex();
//
//        restHighLevelClient.close();
//    }
//
//    /**
//     * 滚动index，当ES索引过大时，rollover，满足条件后，新建索引，将索引别名转向新索引。
//     * @throws Exception
//     */
//    private static void rolloverIndex() throws Exception {
//        RolloverRequest request = new RolloverRequest("alias", "index-2");
//        // 指数年龄的条件
//        request.addMaxIndexAgeCondition(new TimeValue(7, TimeUnit.DAYS));
//        // 索引中文件数量的条件
//        request.addMaxIndexDocsCondition(1000);
//        // 索引大小的条件
//        request.addMaxIndexSizeCondition(new ByteSizeValue(5, ByteSizeUnit.GB));
//
//        RolloverResponse rolloverResponse = restHighLevelClient.indices().rollover(request, RequestOptions.DEFAULT);
//
//        // 指示是否所有节点都已确认请求
//        boolean acknowledged = rolloverResponse.isAcknowledged();
//        // 指示在超时之前是否为索引中的每个分片启动了必要数量的分片副本
//        boolean shardsAcked = rolloverResponse.isShardsAcknowledged();
//        // 旧索引的名称，最终翻转
//        String oldIndex = rolloverResponse.getOldIndex();
//        // 新索引的名称
//        String newIndex = rolloverResponse.getNewIndex();
//        // 索引是否已翻转
//        boolean isRolledOver = rolloverResponse.isRolledOver();
//        // 无论是执行操作还是空运行
//        boolean isDryRun = rolloverResponse.isDryRun();
//        // 不同的条件以及它们是否匹配
//        Map<String, Boolean> conditionStatus = rolloverResponse.getConditionStatus();
//    }
//
//    /**
//     * 强制合并index
//     * @throws Exception
//     */
//    private static void forceMergeIndex() throws Exception {
//        ForceMergeRequest request = new ForceMergeRequest("yiwise");
//        ForceMergeResponse forceMergeResponse = restHighLevelClient.indices().forcemerge(request, RequestOptions.DEFAULT);
//        int totalShards = forceMergeResponse.getTotalShards();
//        int successfulShards = forceMergeResponse.getSuccessfulShards();
//        int failedShards = forceMergeResponse.getFailedShards();
//        DefaultShardOperationFailedException[] failures = forceMergeResponse.getShardFailures();
//        System.out.println(totalShards+" "+successfulShards+" "+failedShards+" "+failures);
//    }
//
//    /**
//     * 清楚index缓存
//     * @throws Exception
//     */
//    private static void clearIndex() throws Exception {
//        ClearIndicesCacheRequest request = new ClearIndicesCacheRequest("yiwise");
//        ClearIndicesCacheResponse clearCacheResponse = restHighLevelClient.indices().clearCache(request, RequestOptions.DEFAULT);
//        int totalShards = clearCacheResponse.getTotalShards();
//        int successfulShards = clearCacheResponse.getSuccessfulShards();
//        int failedShards = clearCacheResponse.getFailedShards();
//        DefaultShardOperationFailedException[] failures = clearCacheResponse.getShardFailures();
//        System.out.println(totalShards+" "+successfulShards+" "+failedShards+" "+failures);
//    }
//
//    /**
//     * 刷新索引，刷新到磁盘
//     * @throws Exception
//     */
//    private static void flushSyncedIndex() throws Exception {
//        SyncedFlushRequest request = new SyncedFlushRequest("yiwise");
//        SyncedFlushResponse flushSyncedResponse = restHighLevelClient.indices().flushSynced(request, RequestOptions.DEFAULT);
//        int totalShards = flushSyncedResponse.totalShards();
//        int successfulShards = flushSyncedResponse.successfulShards();
//        int failedShards = flushSyncedResponse.failedShards();
//        System.out.println(totalShards+" "+successfulShards+" "+failedShards);
//
//        for (Map.Entry<String, SyncedFlushResponse.IndexResult> responsePerIndexEntry:
//                flushSyncedResponse.getIndexResults().entrySet()) {
//            System.out.println(responsePerIndexEntry);
//            // 我们将要计算其结果的索引的名称
//            String indexName = responsePerIndexEntry.getKey();
//            SyncedFlushResponse.IndexResult indexResult = responsePerIndexEntry.getValue();
//            int totalShardsForIndex = indexResult.totalShards();
//            int successfulShardsForIndex = indexResult.successfulShards();
//            int failedShardsForIndex = indexResult.failedShards();
//            if (failedShardsForIndex > 0) {
//                for (SyncedFlushResponse.ShardFailure failureEntry: indexResult.failures()) {
//                    int shardId = failureEntry.getShardId();
//                    String failureReason = failureEntry.getFailureReason();
//                    Map<String, Object> routing = failureEntry.getRouting();
//                }
//            }
//        }
//    }
//
//    /**
//     * 刷新索引，刷新到磁盘
//     * @throws Exception
//     */
//    private static void flushIndex() throws Exception {
//        FlushRequest request = new FlushRequest("yiwise");
//        FlushResponse flushResponse = restHighLevelClient.indices().flush(request, RequestOptions.DEFAULT);
//        // 刷新请求命中的分片总数
//        int totalShards = flushResponse.getTotalShards();
//        // 刷新成功的分片数
//        int successfulShards = flushResponse.getSuccessfulShards();
//        // 刷新失败的分片数
//        int failedShards = flushResponse.getFailedShards();
//        // 失败列表
//        DefaultShardOperationFailedException[] failures = flushResponse.getShardFailures();
//        System.out.println(totalShards+" "+successfulShards+" "+failedShards+" "+failures);
//    }
//
//    /**
//     * 刷新索引，实现的是文档从内存移到文件系统缓存的过程
//     * @throws Exception
//     */
//    private static void refreshIndex() throws Exception {
//        RefreshRequest request = new RefreshRequest("yiwise");
//        RefreshResponse refreshResponse = restHighLevelClient.indices().refresh(request, RequestOptions.DEFAULT);
//        // 刷新请求命中的分片总数
//        int totalShards = refreshResponse.getTotalShards();
//        // 刷新成功的分片数
//        int successfulShards = refreshResponse.getSuccessfulShards();
//        // 刷新失败的分片数
//        int failedShards = refreshResponse.getFailedShards();
//        // 失败列表
//        DefaultShardOperationFailedException[] failures = refreshResponse.getShardFailures();
//        System.out.println(totalShards+" "+successfulShards+" "+failedShards+" "+failures);
//    }
//
//    /**
//     * 拆分索引。将一个索引的主分片个数扩容
//     * @throws Exception
//     */
//    private static void splitIndex() throws Exception {
//        ResizeRequest request = new ResizeRequest("test","yiwise");
//        request.setResizeType(ResizeType.SPLIT);
//        request.getTargetIndexRequest().settings(Settings.builder()
//                .put("index.number_of_shards", 2));
//        ResizeResponse resizeResponse = restHighLevelClient.indices().split(request, RequestOptions.DEFAULT);
//        // 指示是否所有节点都已确认请求
//        boolean acknowledged = resizeResponse.isAcknowledged();
//        // 指示在超时之前是否为索引中的每个分片启动了必要数量的分片副本
//        boolean shardsAcknowledged = resizeResponse.isShardsAcknowledged();
//        System.out.println(acknowledged+" "+shardsAcknowledged);
//    }
//
//    /**
//     * 收缩索引。收缩索引API允许您将现有索引收缩为具有较少主碎片的新索引(下文为们称之为目标索引)。
//     * 伸缩后的索引主分片的个数必须是原分片的公约数，举例说明，如果原先索引的个数为15，那伸缩后的索引主分片数量可以是3、5、1
//     * @throws Exception
//     */
//    private static void shrinkIndex() throws Exception {
//        // source必须是只读
//        ResizeRequest request = new ResizeRequest("test2","yiwise");
//        // 使用true可将设置从源索引复制到目标指数。这不可能是false。如果不使用此方法，则默认行为是不复制。此参数将在8.0.0中删除
//        request.setCopySettings(true);
//        ResizeResponse resizeResponse = restHighLevelClient.indices().shrink(request, RequestOptions.DEFAULT);
//        boolean acknowledged = resizeResponse.isAcknowledged();
//        boolean shardsAcknowledged = resizeResponse.isShardsAcknowledged();
//        System.out.println(acknowledged+" "+shardsAcknowledged);
//    }
//
//    /**
//     * 打开或关闭索引，使用close index api会使索引处于关闭状态，此时无法对该索引进行读、写，但索引数据不会被删除。
//     * @throws Exception
//     */
//    private static void closeIndex() throws Exception {
//        CloseIndexRequest request = new CloseIndexRequest("test");
//        AcknowledgedResponse closeIndexResponse = restHighLevelClient.indices().close(request, RequestOptions.DEFAULT);
//        boolean acknowledged = closeIndexResponse.isAcknowledged();
//        System.out.println(acknowledged);
//    }
//
//    /**
//     * 打开或关闭索引，使用close index api会使索引处于关闭状态，此时无法对该索引进行读、写，但索引数据不会被删除。
//     * @throws Exception
//     */
//    private static void openIndex() throws Exception {
//        OpenIndexRequest request = new OpenIndexRequest("test");
//        OpenIndexResponse openIndexResponse = restHighLevelClient.indices().open(request, RequestOptions.DEFAULT);
//        // 指示是否所有节点都已确认请求
//        boolean acknowledged = openIndexResponse.isAcknowledged();
//        // 指示在超时之前是否为索引中的每个分片启动了必要数量的分片副本
//        boolean shardsAcknowledged = openIndexResponse.isShardsAcknowledged();
//        System.out.println(acknowledged+" "+shardsAcknowledged);
//    }
//
//    /**
//     * 是否存在索引
//     * @throws Exception
//     */
//    private static void existsIndex() throws Exception {
//        GetIndexRequest request = new GetIndexRequest("yiwise");
//        // 是返回本地信息还是从主节点检索状态
//        request.local(false);
//        // 以适合人类的格式返回结果
//        request.humanReadable(true);
//        // 是否为每个索引返回所有默认设置
//        request.includeDefaults(false);
//        // 控制如何解决不可用的索引以及如何扩展通配符表达式
//        request.indicesOptions(IndicesOptions.strictExpand());
//        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
//        System.out.println(exists);
//    }
//
//    /**
//     * 删除索引
//     * @throws Exception
//     */
//    private static void deleteIndex() throws Exception {
//        DeleteIndexRequest request = new DeleteIndexRequest("test");
//        AcknowledgedResponse deleteIndexResponse = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
//        boolean acknowledged = deleteIndexResponse.isAcknowledged();
//        // 指示是否所有节点都已确认请求
//        System.out.println(acknowledged);
//    }
//
//    /**
//     * 创建索引
//     * @throws Exception
//     */
//    private static void createIndex() throws Exception {
//        CreateIndexRequest request = new CreateIndexRequest("test");
////        Map<String, Object> message = new HashMap<>();
////        message.put("type", "text");
////        Map<String, Object> properties = new HashMap<>();
////        properties.put("message", message);
////        Map<String, Object> mapping = new HashMap<>();
////        mapping.put("properties", properties);
////        request.mapping(mapping);
//        XContentBuilder builder = XContentFactory.jsonBuilder();
//        builder.startObject();
//        {
//            builder.startObject("properties");
//            {
//                builder.startObject("message");
//                {
//                    builder.field("type", "text");
//                }
//                builder.endObject();
//            }
//            builder.endObject();
//        }
//        builder.endObject();
//        request.mapping(builder);
//
//        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
//        // 指示是否所有节点都已确认请求
//        boolean acknowledged = createIndexResponse.isAcknowledged();
//        // 指示在超时之前是否为索引中的每个分片启动了必要数量的分片副本
//        boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();
//        System.out.println(acknowledged+" "+shardsAcknowledged);
//
//    }
//
//    /**
//     * 分词
//     * @throws Exception
//     */
//    private static void analyze() throws Exception {
//        AnalyzeRequest request = new AnalyzeRequest();
//        // 要包含的文本。多个字符串被视为多值字段
//        request.text("Some text to analyze", "Some more text to analyze");
//        // 内置分析仪
//        request.analyzer("english");
//
//        AnalyzeResponse response = restHighLevelClient.indices().analyze(request, RequestOptions.DEFAULT);
//        List<AnalyzeResponse.AnalyzeToken> tokens = response.getTokens();
//        tokens.forEach(analyzeToken -> {
//            System.out.println(JsonUtils.object2String(analyzeToken));
//        });
//        DetailAnalyzeResponse detail = response.detail();
//        System.out.println(detail);
//    }
//
//}
