package com.ydh.redsheep.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
*
* @author : yangdehong
* @date : 2019-09-04 14:21
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EsOptBulkBO {

    /**
     * 操作类型
     */
    private String[] indices;

    /**
     * 完全匹配
     */
    private Map<String, Object> fullMatchField;
    private Map<String, Object> filterFiled;
    private Map<String, Object> shouldFiled;
    /**
     * 模糊匹配
     */
    private Map<String, String> fuzzyField;


    /**
     * 文档
     */
    private String json;


}
