package com.ydh.redsheep.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.elasticsearch.action.DocWriteRequest;

/**
*
* @author : yangdehong
* @date : 2019-09-04 14:21
*/
@Data
@AllArgsConstructor
@ToString(callSuper = true)
public class EsBulkBO extends EsBaseBO {

    /**
     * 操作类型
     */
    private DocWriteRequest.OpType opType;
    /**
     * 文档
     */
    private String json;


}
